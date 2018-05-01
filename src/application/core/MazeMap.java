package application.core;

import java.util.List;

import application.Main;
import application.Utils.FXUtil;
import application.dto.MazeConfigDto;

public class MazeMap extends MazeMapBase {

  protected MazeConfigDto conf;
  protected MazeButton[][] map;
  protected MazePoint lastVisitedPoint;
  private static MazeMap instance;

  protected boolean started = false;
  protected boolean finished = false;

  private MazeMap(MazeConfigDto dto) {
    resetConf(dto);
  }

  public void newGame() {
    resetPointsState();
    refreshUI();
    // 起点を最初にクリックしたことにする
    lastVisitedPoint = null;
    started = true;
    finished = false;
    getUIByCoordinate(getStartPoint().getX(), getStartPoint().getY()).fire();
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
    return instance == null ? getInstance(new MazeConfigDto()) : instance;
  }

  public void resetConf(MazeConfigDto dto) {
    if (dto == null) {
      return;
    }
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

  private void resetPointsState() {
    for (int y = 0; y < conf.getMazeHeight(); y++) {
      for (int x = 0; x < conf.getMazeWidth(); x++) {
        resetPointsState(getPointByCoordinate(x, y));

      }
    }
  }

  private void resetPointsState(MazePoint p) {
    p.setCuurentPoint(false);
    p.setVisited(false);
    if (conf.getMazeSightWidth() > 0 && !p.isStartPoint() && !p.isEndPoint()) {
      p.setVisible(false);
    } else {
      p.setVisible(true);
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
              FXUtil.showInfoAlert("メニューから迷宮の生成を行ってください");
              return;
            }
            boolean isWin = setCurrentPoint(getPointByCoordinate(b.getX(), b.getY()));
            if (isWin) {
              FXUtil.showInfoAlert("迷宮踏破おめでとうございます");
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
            p.setVisible(true);
            refreshUI(p);
          }
        }
      }
    } else {
      // 四方向しか表示しない、且つ壁に当たると壁だけ表示して終了
      for (int x = 1; x <= sight; x++) {
        MazePoint p = getPointByCoordinate(getSafeX(currentX + x), currentY);
        p.setVisible(true);
        refreshUI(p);
        if (isWall(p)) {
          break;
        }
      }
      for (int x = 1; x <= sight; x++) {
        MazePoint p = getPointByCoordinate(getSafeX(currentX - x), currentY);
        p.setVisible(true);
        refreshUI(p);
        if (isWall(p)) {
          break;
        }
      }
      for (int y = 1; y <= sight; y++) {
        MazePoint p = getPointByCoordinate(currentX, getSafeY(currentY + y));
        p.setVisible(true);
        refreshUI(p);
        if (isWall(p)) {
          break;
        }
      }
      for (int y = 1; y <= sight; y++) {
        MazePoint p = getPointByCoordinate(currentX, getSafeY(currentY - y));
        p.setVisible(true);
        refreshUI(p);
        if (isWall(p)) {
          break;
        }
      }
    }
  }

  protected boolean setCurrentPoint(MazePoint point) {
    if (!point.isVisible()) {
      showUnclickable(point);
      return false;
    }

    if (point.equals(lastVisitedPoint)) {
      return false;
    }
    // 前の道に戻るなら他の計算は特に必要ない
    if (point.isVisited()) {
      lastVisitedPoint.setCuurentPoint(false);
      refreshUI(lastVisitedPoint);
      point.setCuurentPoint(true);
      refreshUI(point);
      lastVisitedPoint = point;
      return false;
    }

    // 未訪問のマスにアクセスしようとしている
    if (lastVisitedPoint != null) {
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
          setSight(p);
          refreshUI(p);
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
          setSight(p);
          refreshUI(p);
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
            setSight(p);
            refreshUI(p);
          }
          for (int x = minX; x <= maxX; x++) {
            MazePoint p = getPointByCoordinate(x, roadY);
            p.setVisited(true);
            setSight(p);
            refreshUI(p);
          }
        } else {
          showUnclickable(point);
          return false;
        }
      }
      lastVisitedPoint.setCuurentPoint(false);
      refreshUI(lastVisitedPoint);
    }
    //指定マスの共通処理
    point.setCuurentPoint(true);
    point.setVisited(true);
    setSight(point);
    refreshUI(point);
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
    for (int y = 0; y < maxHeight; y++) {
      for (int x = 0; x < maxWidth; x++) {
        refreshUI(getPointByCoordinate(x, y));
      }
    }
  }

  public void refreshUI(MazePoint point) {
    //Platform.runLater(() -> {
      int x = point.getX();
      int y = point.getY();
      System.out.println("refreshing UI:" + x + "," + y);
      MazeButton ui = getUIByCoordinate(x, y);
      List<String> styleList = ui.getStyleClass();
      // 最初の一個目はbuttonというclass
      if (styleList.size() > 1) {
        String nodeClass = styleList.get(0);
        styleList.clear();
        styleList.add(nodeClass);
      }

      if (point.isEndPoint()) {
        ui.setDisable(false);
        styleList.add("warning");
        return;
      }
      if (point.isCuurentPoint()) {
        ui.setDisable(false);
        styleList.add("current");
        return;
      }
      if (point.isStartPoint()) {
        ui.setDisable(false);
        styleList.add("info");
        return;
      }
      if (!point.isVisible()) {
        ui.setDisable(true);
        styleList.add("unvisible");
        return;
      }
      if (point.isVisited()) {
        ui.setDisable(false);
        styleList.add("visited");
      }
      if (isRoad(point)) {
        ui.setDisable(false);
        //styleList.add("success");
      } else if (isWall(point)) {
        ui.setDisable(true);
        styleList.add("danger");
      }

    //});

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
    FXUtil.showInfoAlert("移動ルートが見つかりません");
  }
}
