package application.core;

import application.enumType.MazePointType;

public abstract class MazeGenerator extends MazeMapBase {

  protected boolean showAnime = true;

  protected int animeDelay = 10;
  protected int animeDelayAll = 0;

  public MazeGenerator() {
    super();
  }

  public MazeGenerator(MazePoint[][] mazePoints, boolean showAnime) {
    super(mazePoints);
    animeDelay = showAnime ? 10 : 0;
    animeDelayAll = showAnime ? 1 : 0;
  }

  public abstract void generate();

  //== set
  // to road
  protected void setToRoad(MazePoint mazePoint) {
    setToRoad(mazePoint, animeDelay);
  }

  protected void setToRoad(MazePoint mazePoint, int animeDelay) {
    System.out.println("set to road:" + mazePoint.getX() + "," + mazePoint.getY());
    mazePoint.setType(MazePointType.ROAD);
    if (showAnime) {
      refreshUI(mazePoint, animeDelay);
    }
  }

  protected void setToRoad(int x, int y) {
    if (isValidCoordinate(x, y)) {
      setToRoad(getPointByCoordinate(x, y));
    }
  }

  protected void setRowToRoad(int y) {
    setRowToRoad(y, 0, maxWidth - 1);
  }

  protected void setRowToRoad(int y, int beginX, int endX) {
    for (int x = beginX; x <= endX; x++) {
      setToRoad(x, y);
    }
  }

  protected void setColToRoad(int x) {
    setColToRoad(x, 0, maxHeight - 1);
  }

  protected void setColToRoad(int x, int beginY, int endY) {
    for (int y = beginY; y <= endY; y++) {
      setToRoad(x, y);
    }
  }

  protected void setSlashToRoad(int leftX, int leftY, int rightX, int rightY) {
    int iteratorY = Math.min(leftY, rightY) == leftY ? 1 : -1;
    int y = leftY;
    for (int x = leftX; x <= rightX; x++) {
      setToRoad(x, y);
      y = y + iteratorY;
    }
  }

  protected void setAllToRoad() {
    for (int i = mazePoints.length - 1; i >= 0; i--) {
      for (int j = mazePoints[i].length - 1; j >= 0; j--) {
        setToRoad(mazePoints[i][j], animeDelayAll);
      }
    }
  }

  // to wall
  protected void setToWall(MazePoint mazePoint) {
    setToWall(mazePoint, animeDelay);
  }

  protected void setToWall(MazePoint mazePoint, int animeDelay) {
    System.out.println("set to wall:" + mazePoint.getX() + "," + mazePoint.getY());
    mazePoint.setType(MazePointType.WALL);
    if (showAnime) {
      refreshUI(mazePoint, animeDelay);
    }
  }

  protected void setToWall(int x, int y) {
    if (isValidCoordinate(x, y)) {
      setToWall(getPointByCoordinate(x, y));
    }
  }

  protected void setColToWall(int x) {
    setColToWall(x, 0, maxHeight - 1);
  }

  protected void setColToWall(int x, int beginY, int endY) {
    for (int y = beginY; y <= endY; y++) {
      setToWall(x, y);
    }
  }

  protected void setRowToWall(int y) {
    setRowToWall(y, 0, maxWidth - 1);
  }

  protected void setRowToWall(int y, int beginX, int endX) {
    for (int x = beginX; x <= endX; x++) {
      setToWall(x, y);
    }
  }

  protected void setSlashToWall(int leftX, int leftY, int rightX, int rightY) {
    int iteratorY = Math.min(leftY, rightY) == leftY ? 1 : -1;
    int y = leftY;
    for (int x = leftX; x <= rightX; x++) {
      setToWall(x, y);
      y = y + iteratorY;
    }
  }

  protected void setAllToWall() {
    for (int i = mazePoints.length - 1; i >= 0; i--) {
      for (int j = mazePoints[i].length - 1; j >= 0; j--) {
        setToWall(mazePoints[i][j], animeDelayAll);
      }
    }
  }

  // UI
  protected void refreshUI(MazePoint mazePoint) {
    refreshUI(mazePoint, animeDelay);
  }

  protected void refreshUI(MazePoint mazePoint, int animeDelay) {
    MazeMap.getInstance().refreshUI(mazePoint, animeDelay);
  }

  protected void refreshUI() {
    MazeMap.getInstance().refreshUI(animeDelayAll);
  }

  protected void refreshUI(int x, int y) {
    refreshUI(getPointByCoordinate(x, y));
  }

}
