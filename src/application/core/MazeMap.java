package application.core;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import application.Main;
import application.Utils.CommonUtil;
import application.Utils.FXUtil;
import application.dto.MazeConfigDto;
import application.service.TitleAlgorithmService;
import javafx.application.Platform;

public class MazeMap extends MazeMapBase {

  protected MazeConfigDto conf;
  protected MazeButton[][] map;
  protected MazePoint lastVisitedPoint;
  private static MazeMap instance;

  protected boolean started = false;
  protected boolean finished = false;

  protected int animeDelayAll = 1;
  protected int animeDelay = 10;

  protected MazeMap(MazeConfigDto dto) {
    resetConf(dto);
  }

  public void newGame() {
    resetPointsState();
    refreshUI();
    // 起点を最初にクリックしたことにする
    lastVisitedPoint = null;
    started = true;
    finished = false;
    FXUtil.run(() -> getUIByCoordinate(getStartPoint().getX(), getStartPoint().getY()).fire(), 0);
  }

  public boolean isPlayable() {
    return started == true && finished == false;
  }

  public static MazeMap getInstance(MazeConfigDto dto) {
    if (instance == null) {
      instance = new MazeMap(dto);
      return instance;
    } else {
      instance.resetConf(dto);
      return instance;
    }
  }

  public static MazeMap getInstance() {
    return instance == null ? getInstance(null) : instance;
  }

  public void resetConf(MazeConfigDto dto) {
    if (dto == null) {
      return;
    }
    animeDelay = dto.isShowAnime() ? 10 : 0;
    animeDelayAll = dto.isShowAnime() ? 1 : 0;
    // 迷宮の寸法が変わった場合、UIを再生成する
    if (conf != null && !conf.equals(dto)) {
      map = null;
    }
    conf = dto;
    started = false;
    finished = false;
    resetPoints();
    resetMap();
  }

  private void resetPoints() {
    MazePoint[][] mazePoints = new MazePoint[conf.getMazeHeight()][conf.getMazeWidth()];
    for (int y = 0; y < conf.getMazeHeight(); y++) {
      mazePoints[y] = new MazePoint[conf.getMazeWidth()];
      for (int x = 0; x < conf.getMazeWidth(); x++) {
        mazePoints[y][x] = new MazePoint();
        mazePoints[y][x].setX(x);
        mazePoints[y][x].setY(y);
      }
    }
    // 起点と終点は固定
    mazePoints[0][0].setStartPoint(true);
    mazePoints[conf.getMazeHeight() - 1][conf.getMazeWidth() - 1].setEndPoint(true);
    setMazePoints(mazePoints);
    resetPointsState();
  }

  public void resetPointsState() {
    for (int y = 0; y < conf.getMazeHeight(); y++) {
      for (int x = 0; x < conf.getMazeWidth(); x++) {
        resetPointsState(getPointByCoordinate(x, y));
      }
    }
  }

  private void resetPointsState(MazePoint p) {
    p.setCurrentPoint(false);
    p.setVisited(false);
    if (conf.getMazeSightWidth() > 0 && !p.isStartPoint() && !p.isEndPoint()) {
      p.setVisible(false);
    } else {
      p.setVisible(true);
    }
  }

  public void resetPointsStateForGenerator() {
    for (int y = 0; y < conf.getMazeHeight(); y++) {
      for (int x = 0; x < conf.getMazeWidth(); x++) {
        resetPointsState(getPointByCoordinate(x, y));
        getPointByCoordinate(x, y).setVisible(true);
      }
    }
  }

  private void resetMap() {
    // UIがなければ作る
    if (map == null) {
      map = new MazeButton[conf.getMazeHeight()][conf.getMazeWidth()];
      for (int y = 0; y < conf.getMazeHeight(); y++) {
        map[y] = new MazeButton[conf.getMazeWidth()];
        for (int x = 0; x < conf.getMazeWidth(); x++) {
          map[y][x] = new MazeButton();
          map[y][x].setX(x);
          map[y][x].setY(y);
          map[y][x].setText("");
          map[y][x].setDisable(true);
          map[y][x].setPrefWidth(conf.getMazeGridWidth());
          map[y][x].setMinWidth(conf.getMazeGridWidth());
          map[y][x].setMaxWidth(conf.getMazeGridWidth());
          map[y][x].setPrefHeight(conf.getMazeGridWidth());
          map[y][x].setMinHeight(conf.getMazeGridWidth());
          map[y][x].setMaxHeight(conf.getMazeGridWidth());
          map[y][x].setOnAction(event -> {
            MazeButton b = (MazeButton) event.getSource();
            if (b.isDisabled() || finished) {
              return;
            }
            if (!started) {
              FXUtil.showInfoAlert(FXUtil.getLocaleText("ErrBeforeInit"));
              return;
            }
            boolean isWin = setCurrentPoint(getPointByCoordinate(b.getX(), b.getY()));
            if (isWin) {
              FXUtil.showInfoAlert(FXUtil.getLocaleText("InfoMazeCleared"));
              finished = true;
            }
          });
        }
      }
      Main.setMazeMap(map);
    }
    // マスの状態を反映
    refreshUI();
  }

  // 指定マスを中心に視界内のマスを可視にする
  protected void setSight(MazePoint point) {
    if (!conf.isSightMode()) {
      return;
    }
    // 壁による視界遮断は考慮しない
    int currentX = point.getX();
    int currentY = point.getY();
    int sight = conf.getMazeSightWidth();
    // 壁を無視なら視界内のマスは全部表示
    if (conf.isMazeSightIgnoreWall()) {
      for (int i = 0; i <= sight; i++) {
        for (int x = currentX - i; x <= currentX + i; x++) {
          for (int y = currentY - (sight - i); y <= currentY + (sight - i); y++) {
            MazePoint p = getPointByCoordinate(getSafeX(x), getSafeY(y));
            if (!p.isVisible()) {
              p.setVisible(true);
              refreshUI(p);
            }
          }
        }
      }
    } else {
      // 四方向しか表示しない、且つ壁に当たると壁だけ表示して終了
      for (int x = 1; x <= sight; x++) {
        MazePoint p = getPointByCoordinate(getSafeX(currentX + x), currentY);
        if (!p.isVisible()) {
          p.setVisible(true);
          refreshUI(p);
        }
        if (isWall(p)) {
          break;
        }
      }
      for (int x = 1; x <= sight; x++) {
        MazePoint p = getPointByCoordinate(getSafeX(currentX - x), currentY);
        if (!p.isVisible()) {
          p.setVisible(true);
          refreshUI(p);
        }
        if (isWall(p)) {
          break;
        }
      }
      for (int y = 1; y <= sight; y++) {
        MazePoint p = getPointByCoordinate(currentX, getSafeY(currentY + y));
        if (!p.isVisible()) {
          p.setVisible(true);
          refreshUI(p);
        }
        if (isWall(p)) {
          break;
        }
      }
      for (int y = 1; y <= sight; y++) {
        MazePoint p = getPointByCoordinate(currentX, getSafeY(currentY - y));
        if (!p.isVisible()) {
          p.setVisible(true);
          refreshUI(p);
        }
        if (isWall(p)) {
          break;
        }
      }
    }
  }

  protected boolean setCurrentPoint(MazePoint point) {
    return setCurrentPoint(point, true);
  }

  protected boolean setCurrentPoint(MazePoint point, boolean isLineMode) {
    if (!point.isVisible()) {
      showUnclickable(point);
      return false;
    }

    if (point.equals(lastVisitedPoint)) {
      return false;
    }
    // 前の道に戻るなら他の計算は特に必要ない
    if (point.isVisited()) {
      lastVisitedPoint.setCurrentPoint(false);
      refreshUI(lastVisitedPoint);
      point.setCurrentPoint(true);
      refreshUI(point);
      lastVisitedPoint = point;
      return false;
    }

    // 未訪問のマスにアクセスしようとしている
    if (lastVisitedPoint != null && isLineMode) {
      int minX = Math.min(lastVisitedPoint.getX(), point.getX());
      int maxX = Math.max(lastVisitedPoint.getX(), point.getX());
      int minY = Math.min(lastVisitedPoint.getY(), point.getY());
      int maxY = Math.max(lastVisitedPoint.getY(), point.getY());
      // 途中の点なら最後の経過点から縦、あるいは横移動しかできない
      // 縦移動
      if (lastVisitedPoint.getX() == point.getX()) {
        // 壁があるなら処理しない
        if (!isColRoad(point.getX(), minY, maxY)) {
          showUnclickable(point);
          return false;
        }
        for (int y = minY; y <= maxY; y++) {
          MazePoint p = getPointByCoordinate(point.getX(), y);
          p.setVisited(true);
          p.setCurrentPoint(false);
          setSight(p);
          refreshUI(p, animeDelay);
        }

      }
      // 横移動
      else if (lastVisitedPoint.getY() == point.getY()) {
        // 壁があるなら処理しない
        if (!isRowRoad(point.getY(), minX, maxX)) {
          showUnclickable(point);
          return false;
        }
        for (int x = minX; x <= maxX; x++) {
          MazePoint p = getPointByCoordinate(x, point.getY());
          p.setVisited(true);
          p.setCurrentPoint(false);
          setSight(p);
          refreshUI(p, animeDelay);
        }

      } else {
        // 一回だけ曲がることを許可する
        MazePoint leftUpPoint = getPointByCoordinate(minX, maxY);
        MazePoint leftDownPoint = getPointByCoordinate(minX, minY);
        int roadX = 0;
        int roadY = 0;
        boolean hasRoad = false;
        // 左上と右下の場合
        if (lastVisitedPoint.equals(leftUpPoint) || point.equals(leftUpPoint)) {
          if (isColRoad(minX, minY, maxY) && isRowRoad(minY, minX, maxX)) {
            roadX = minX;
            roadY = minY;
            hasRoad = true;
          } else if (isColRoad(maxX, minY, maxY) && isRowRoad(maxY, minX, maxX)) {
            roadX = maxX;
            roadY = maxY;
            hasRoad = true;
          }
          // 左下と右上の場合
        } else if (lastVisitedPoint.equals(leftDownPoint) || point.equals(leftDownPoint)) {
          if (isColRoad(minX, minY, maxY) && isRowRoad(maxY, minX, maxX)) {
            roadX = minX;
            roadY = maxY;
            hasRoad = true;
          } else if (isColRoad(maxX, minY, maxY) && isRowRoad(minY, minX, maxX)) {
            roadX = maxX;
            roadY = minY;
            hasRoad = true;
          }
        }
        if (hasRoad) {
          for (int y = minY; y <= maxY; y++) {
            MazePoint p = getPointByCoordinate(roadX, y);
            p.setVisited(true);
            p.setCurrentPoint(false);
            setSight(p);
            refreshUI(p, animeDelay);
          }
          for (int x = minX; x <= maxX; x++) {
            MazePoint p = getPointByCoordinate(x, roadY);
            p.setVisited(true);
            p.setCurrentPoint(false);
            setSight(p);
            refreshUI(p, animeDelay);
          }
        } else {
          showUnclickable(point);
          return false;
        }
      }
      lastVisitedPoint.setCurrentPoint(false);
      refreshUI(lastVisitedPoint);
    }
    //指定マスの共通処理
    point.setCurrentPoint(true);
    point.setVisited(true);
    setSight(point);
    refreshUI(point, animeDelay);
    lastVisitedPoint = point;
    // 終点に着いたならtrue
    if (point.equals(getEndPoint())) {
      return true;
    }
    return false;
  }

  public void setAllVisible() {
    for (int y = 0; y < maxHeight; y++) {
      for (int x = 0; x < maxWidth; x++) {
        MazePoint p = getPointByCoordinate(x, y);
        p.setVisible(true);
        refreshUI(p);
      }
    }
  }

  //UI
  public void refreshUI() {
    refreshUI(animeDelayAll);
  }

  public void refreshUI(int animeDelay) {
    for (int y = 0; y < maxHeight; y++) {
      for (int x = 0; x < maxWidth; x++) {
        refreshUI(getPointByCoordinate(x, y), animeDelay);
      }
    }
  }

  public void refreshUI(MazePoint point, int animeDelay) {
    if (animeDelay > 0) {
      MazePoint copy = new MazePoint(point);
      FXUtil.run(() -> {
        refreshUIImmediately(copy);

      }, animeDelay);

    } else {
      refreshUIImmediately(point);
    }
  }

  public void refreshUI(MazePoint point) {
    refreshUI(point, 0);
  }

  protected void refreshUIImmediately(MazePoint p) {
    int x = p.getX();
    int y = p.getY();
    System.out.println("refreshing UI:" + x + "," + y);
    MazeButton ui = getUIByCoordinate(x, y);
    List<String> styleList = ui.getStyleClass();
    // 最初の一個目はbuttonというclass
    if (styleList.size() > 1) {
      String nodeClass = styleList.get(0);
      styleList.clear();
      styleList.add(nodeClass);
    }

    if (p.isEndPoint()) {
      ui.setDisable(false);
      styleList.add("warning");
      return;
    }
    if (p.isCurrentPoint()) {
      ui.setDisable(false);
      styleList.add("current");
      return;
    }
    if (p.isStartPoint()) {
      ui.setDisable(false);
      styleList.add("info");
      return;
    }
    if (!p.isVisible()) {
      ui.setDisable(true);
      styleList.add("unvisible");
      return;
    }
    if (p.isVisited()) {
      ui.setDisable(false);
      styleList.add("visited");
    }
    if (isRoad(p)) {
      ui.setDisable(false);
      //styleList.add("success");
    } else if (isWall(p)) {
      ui.setDisable(true);
      styleList.add("danger");
    }
  }

  public int getCurrentX() {
    return lastVisitedPoint == null ? -1 : lastVisitedPoint.getX();
  }

  public int getCurrentY() {
    return lastVisitedPoint == null ? -1 : lastVisitedPoint.getY();
  }

  public MazeButton getUIByCoordinate(int x, int y) {
    if (!isValidCoordinate(x, y)) {
      return null;
    }
    return map[y][x];
  }

  protected void showUnclickable(int x, int y) {
    showUnclickable(getUIByCoordinate(x, y));
  }

  protected void showUnclickable(MazePoint p) {
    showUnclickable(getUIByCoordinate(p.getX(), p.getY()));
  }

  protected void showUnclickable(MazeButton p) {
    FXUtil.showInfoAlert(FXUtil.getLocaleText("ErrRouteNotFound"));
  }

  public void showTitleMaze() {
    int titleType = CommonUtil.getRandomInt(4);
    MazeConfigDto titleConf = new MazeConfigDto();
    titleConf.setShowAnime(false);
    String[] route;
    if (titleType == 0) {
      //maze文字
      titleConf.setMazeWidth(27);
      titleConf.setMazeHeight(29);
      route = new String[]{
          //M
          "1,12", "1,11", "1,10", "1,9", "1,8", "1,7", "1,6", "1,5", "1,4", "1,3", "1,2","1,1", "2,2", "2,3", "3,3", "3,4", "4,4", "4,5",
          "5,5", "5,6", "6,6","6,7",
          "7,6", "7,5", "8,5", "8,4", "9,4", "9,3", "10,3","10,2","11,2","11,1", "11,3", "11,4", "11,5", "11,6", "11,7", "11,8", "11,9",
          "11,10", "11,11", "11,12"
          //A
          ,"15,12","15,11","15,10","15,9","15,8","15,7","15,6","16,6","16,5","17,5","17,4","18,4","18,3","19,3",
          "19,2","20,2","20,1","21,2","21,3","22,3","22,4","23,4","23,5","24,5","24,6","25,6","25,7","25,8","25,9","25,10"
          ,"25,11","25,12"
          ,"16,7","17,7","18,7","19,7","20,7","21,7","22,7","23,7","24,7"
          //Z
          ,"1,16","2,16","3,16","4,16","5,16","6,16","7,16","8,16","9,16","10,16","11,16","11,17","10,17","10,18","9,18"
          ,"9,19","8,19","8,20","7,20","7,21","6,21","6,22","5,22","5,23","4,23","4,24","3,24","3,25","2,25","2,26","1,26",
          "1,27","2,27","3,27","4,27","5,27","6,27","7,27","8,27","9,27","10,27","11,27"
          //E
          ,"25,16","24,16","23,16","22,16","21,16","20,16","19,16","18,16","17,16","16,16"
          ,"15,16","15,17","15,18","15,19","15,20","15,21","15,22","15,23","15,24","15,25","15,26","15,27",
          "16,27","17,27","18,27","19,27","20,27","21,27","22,27","23,27","24,27","25,27",
          "16,21","17,21","18,21","19,21","20,21","21,21","22,21","23,21","24,21","25,21"
      };
    } else {
      //ドット絵
      titleConf.setMazeWidth(35);
      titleConf.setMazeHeight(25);
      String[] route1;
      String[] route2;
      route1 = new String[]{
          "10,13","10,14","10,15","10,16","9,17","8,18","7,18","7,19","8,20","9,21",
          "8,22","7,22","6,22","5,22","4,22","3,21","2,20","1,19","1,18","1,17","1,16","1,15","2,14","2,13","3,12","3,11",
          "4,10","4,9","5,8","6,7","6,6","6,5","7,5","8,5","7,4","8,3","9,2","10,1","11,1","12,2","13,3","14,3","15,2","16,2","17,2",
          "18,2","19,2","20,3","21,3","22,2","23,1","24,1","25,2","26,3","27,4","27,5","26,5","28,5","28,6","28,7","29,8","30,9",
          "30,10","31,11","31,12","32,13","32,14","33,15","33,16","33,17","33,18","33,19","32,20","31,21","30,22","29,22",
          "28,22","27,22","26,22","25,21","26,20","27,19","27,18","26,18","25,17","24,16","24,15","24,14","24,13","23,13",
          "22,14","21,15","20,16","21,17","22,18","22,19","21,19","21,20","20,20","20,19","20,18","19,20","19,19","19,18",
          "19,17","18,20","18,21","18,22","18,23","17,20","16,20","16,21","16,22","16,23","15,20","15,19","15,18","16,18","16,17","15,17",
          "14,20","14,19","14,18","13,20","13,19","12,19","12,18","13,17","14,16","13,15","12,14","11,13","11,12","10,12","9,12","9,11","10,11","10,10",
          "10,9","10,8","9,8","8,9","10,7","10,6","9,6","11,6","11,5","12,4","13,5","14,5","15,5","16,5","17,5","18,5","19,5","20,5","21,5",
          "22,4","23,5","23,6","24,6","25,6","24,7","24,8","25,8","26,9","24,9","24,10","24,11","25,11","25,12","24,12","23,12",
          "14,14","15,14","15,15","16,15","17,16","18,15","19,15","19,14","20,14",
          "5,10","6,10","7,10","5,11","6,11","7,11","5,12","6,12","7,12","8,13","7,14","7,15","6,16","6,17","3,15","3,16","3,17","3,18","3,19","4,20","5,21",
          "27,10","28,10","29,10","27,11","28,11","29,11","27,12","28,12","29,12","26,13","27,14","27,15","28,16","28,17","31,15","31,16","31,17","31,18","31,19","30,20","29,21"
          };
      if (titleType == 1) {
        route2 = new String[] {"14,7","14,8","14,9","20,7","20,8","20,9","15,11","16,12","17,12","18,12","19,11"};
      }else if (titleType == 2) {
        route2 = new String[] {"13,7","14,8","15,9","14,10","13,11","21,7","20,8","19,9","20,10","21,11","17,12"};
      } else {
        route2 = new String[] {"14,9","15,9","14,10","20,9","21,9","20,10"};
      }
      route = new String[route1.length + route2.length];
      System.arraycopy(route1, 0, route, 0, route1.length);
      System.arraycopy(route2, 0, route, route1.length, route2.length);
    }
    resetConf(titleConf);

    new TitleAlgorithmService(getMazePoints(), false).generate();
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    scheduler.scheduleWithFixedDelay(new Runnable() {

      int step = 0;

      @Override
      public void run() {
        System.out.println("running title step" + step);
        if (step >= route.length) {
          scheduler.shutdown();
          Main.enableMenu();
          return;
        }
        String[] coordinate = route[step].split(",");
        MazePoint p = getPointByCoordinate(Integer.parseInt(coordinate[0]), Integer.parseInt(coordinate[1]));
        if (step == 0) {
          p.setVisible(true);
        }
        Platform.runLater(() -> setCurrentPoint(p, false));
        step++;
      }

    }, 2000, 15, TimeUnit.MILLISECONDS);
  }
}
