package application.core;

import application.Main;
import application.dto.MazeConfigDto;
import application.enumType.MazePointType;
import javafx.application.Platform;
import javafx.scene.control.Button;

public class MazeMap {

  private MazePoint[][] points;
  private Button[][] map;
  private MazeConfigDto conf;

  private static MazeMap instance;

  private MazeMap(MazeConfigDto dto) {
    resetConf(dto);
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

  public MazePoint[][] getMazePoints() {
    return points;
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
    resetPoints();
    resetMap();
  }

  private void resetPoints() {
    points = new MazePoint[conf.getMazeHeight()][conf.getMazeWidth()];
    for (int i = 0; i < conf.getMazeHeight(); i++) {
      points[i] = new MazePoint[conf.getMazeWidth()];
      for (int j = 0; j < conf.getMazeWidth(); j++) {
        points[i][j] = new MazePoint();
        points[i][j].setX(j);
        points[i][j].setY(i);
      }
    }
    // 起点と終点は固定
    points[0][0].setStartPoint(true);
    points[conf.getMazeHeight() - 1][conf.getMazeWidth() - 1].setEndPoint(true);
  }

  private void resetMap() {
    // UIがなければ作る
    if (map == null) {
      map = new Button[conf.getMazeHeight()][conf.getMazeWidth()];
      for (int i = 0; i < conf.getMazeHeight(); i++) {
        map[i] = new Button[conf.getMazeWidth()];
        for (int j = 0; j < conf.getMazeWidth(); j++) {
          map[i][j] = new Button();
          map[i][j].setText("");
          map[i][j].setDisable(true);
          map[i][j].setPrefWidth(conf.getMazeGridWidth());
          map[i][j].setMinWidth(conf.getMazeGridWidth());
          map[i][j].setMaxWidth(conf.getMazeGridWidth());
          map[i][j].setPrefHeight(conf.getMazeGridWidth());
          map[i][j].setMinHeight(conf.getMazeGridWidth());
          map[i][j].setMaxHeight(conf.getMazeGridWidth());
        }
      }
      Main.setMazeMap(map);
    }
    // マスの状態を反映
    refreshUI();
  }

  public void refreshUI() {
    for (int i = 0; i < conf.getMazeHeight(); i++) {
      for (int j = 0; j < conf.getMazeWidth(); j++) {
        refreshUI(points[i][j]);
      }
    }
  }

  public void refreshUI(MazePoint point) {
    Platform.runLater(() -> {
      int i = point.getY();
      int j = point.getX();

      if (map[i][j].getStyleClass().size() > 1) {
        map[i][j].getStyleClass().remove(map[i][j].getStyleClass().size() - 1);
      }

      if (points[i][j].isStartPoint()) {
        map[i][j].getStyleClass().add("info");
        return;
      }
      if (points[i][j].isEndPoint()) {
        map[i][j].getStyleClass().add("warning");
        return;
      }
      if (points[i][j].getType().equals(MazePointType.ROAD)) {
        map[i][j].getStyleClass().add("success");
      } else if (points[i][j].getType().equals(MazePointType.WALL)) {
        map[i][j].getStyleClass().add("danger");
      }
    });

  }

  public String toText() {
    String ret = "";
    if (points == null) {
      return ret;
    }
    for (int i = 0; i < points.length; i++) {

      for (int j = 0; j < points[i].length; j++) {
        ret += Integer.toString(points[i][j].getType().getValue());
      }
      ret += System.lineSeparator();
    }
    return ret;
  }
}
