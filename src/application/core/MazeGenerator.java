package application.core;

import application.enumType.MazePointType;

public abstract class MazeGenerator extends MazeMapBase {

  protected boolean showAnime = true;

  public MazeGenerator() {
    super();
  }

  public MazeGenerator(MazePoint[][] mazePoints) {
    super(mazePoints);
  }

  public abstract void generate(boolean showAnime);

  //== set
  // to road
  protected void setToRoad(MazePoint mazePoint) {
    System.out.println("set to road:" + mazePoint.getX() + "," + mazePoint.getY());
    mazePoint.setType(MazePointType.ROAD);
    if (showAnime) {
      //Platform.runLater(() -> refreshUI(mazePoint));
      refreshUI(mazePoint);
    }
  }

  protected void setToRoad(int x, int y) {
    if (isValidCoordinate(x, y)) {
      setToRoad(getPointByCoordinate(x, y));
    }
  }

  protected void setRowToRoad(int y) {
    for (int x = 0; x < maxWidth; x++) {
      setToRoad(x, y);
    }
  }

  protected void setColToRoad(int x) {
    for (int y = 0; y < maxHeight; y++) {
      setToRoad(x, y);
    }
  }

  protected void setAllToRoad() {
    for (int i = mazePoints.length - 1; i >= 0; i--) {
      for (int j = mazePoints[i].length - 1; j >= 0; j--) {
        setToRoad(mazePoints[i][j]);
      }
    }
  }

  // to wall
  protected void setToWall(MazePoint mazePoint) {
    System.out.println("set to wall:" + mazePoint.getX() + "," + mazePoint.getY());
    mazePoint.setType(MazePointType.WALL);
    if (showAnime) {
      //Platform.runLater(() -> refreshUI(mazePoint));
      refreshUI(mazePoint);
    }
  }

  protected void setToWall(int x, int y) {
    if (isValidCoordinate(x, y)) {
      setToWall(getPointByCoordinate(x, y));
    }
  }

  protected void setColToWall(int x) {
    for (int y = 0; y < maxHeight; y++) {
      setToWall(x, y);
    }
  }

  protected void setRowToWall(int y) {
    for (int x = 0; x < maxWidth; x++) {
      setToWall(x, y);
    }
  }

  protected void setAllToWall() {
    for (int i = mazePoints.length - 1; i >= 0; i--) {
      for (int j = mazePoints[i].length - 1; j >= 0; j--) {
        setToWall(mazePoints[i][j]);
      }
    }
  }

  // UI
  protected void refreshUI(MazePoint mazePoint) {
    MazeMap.getInstance().refreshUI(mazePoint);
  }

  protected void refreshUI() {
    MazeMap.getInstance().refreshUI();
  }

  protected void refreshUI(int x, int y) {
    refreshUI(getPointByCoordinate(x, y));
  }

}
