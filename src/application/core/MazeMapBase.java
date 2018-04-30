package application.core;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import application.enumType.MazePointPosition;
import application.enumType.MazePointType;

public abstract class MazeMapBase {
  protected MazePoint[][] mazePoints;
  protected int maxWidth;
  protected int maxHeight;

  public MazeMapBase() {

  }

  public MazeMapBase(MazePoint[][] mazePoints) {
    this();
    setMazePoints(mazePoints);
  }

  public MazePoint[][] getMazePoints() {
    return mazePoints;
  }

  public void setMazePoints(MazePoint[][] mazePoints) {
    this.mazePoints = mazePoints;
    maxWidth = mazePoints[0].length;
    maxHeight = mazePoints.length;
  }

  //== get
  protected MazePoint getStartPoint() {
    for (int i = 0; i < mazePoints.length; i++) {
      for (int j = 0; j < mazePoints[i].length; j++) {
        if (mazePoints[i][j].isStartPoint()) {
          return mazePoints[i][j];
        }
      }
    }
    return null;
  }

  protected MazePoint getEndPoint() {
    for (int i = mazePoints.length - 1; i >= 0; i--) {
      for (int j = mazePoints[i].length - 1; j >= 0; j--) {
        if (mazePoints[i][j].isEndPoint()) {
          return mazePoints[i][j];
        }
      }
    }
    return null;
  }

  protected MazePoint getPointByIndex(int x, int y) {
    return mazePoints[x][y];
  }

  protected MazePoint getPointByCoordinate(int x, int y) {
    return mazePoints[y][x];
  }

  // 座標でやった方が読みやすいので以降は座標で操作
  protected boolean isValidCoordinate(int x, int y) {
    try {
      getPointByCoordinate(x, y);
      return true;
    } catch (ArrayIndexOutOfBoundsException e) {
      return false;
    }
  }

  protected boolean isRoad(MazePoint mazePoint) {
    return mazePoint.getType().equals(MazePointType.ROAD);
  }

  protected boolean isRoad(int x, int y) {
    if (!isValidCoordinate(x, y)) {
      return false;
    }
    return getPointByCoordinate(x, y).getType().equals(MazePointType.ROAD);
  }

  protected boolean isRoad(List<MazePoint> points) {
    for (MazePoint point : points) {
      if (!isRoad(point)) {
        return false;
      }
    }
    return true;
  }

  protected boolean isRowRoad(int y, int beginX, int endX) {
    for (int x = beginX; x <= endX; x++) {
      if (!isRoad(x, y)) {
        return false;
      }
    }
    return true;
  }

  protected boolean isRowRoad(int y) {
    return isRowRoad(y, 0, maxWidth - 1);
  }

  protected boolean isColRoad(int x, int beginY, int endY) {
    for (int y = beginY; y <= endY; y++) {
      if (!isRoad(x, y)) {
        return false;
      }
    }
    return true;
  }

  protected boolean isColRoad(int x) {
    return isColRoad(x, 0, maxHeight - 1);
  }

  protected boolean isWall(MazePoint mazePoint) {
    return mazePoint.getType().equals(MazePointType.WALL);
  }

  protected boolean isWall(int x, int y) {
    if (!isValidCoordinate(x, y)) {
      return false;
    }
    return getPointByCoordinate(x, y).getType().equals(MazePointType.WALL);
  }

  protected boolean isWall(List<MazePoint> points) {
    for (MazePoint point : points) {
      if (!isWall(point)) {
        return false;
      }
    }
    return true;
  }

  protected boolean isRowWall(int y, int beginX, int endX) {
    for (int x = beginX; x <= endX; x++) {
      if (!isWall(x, y)) {
        return false;
      }
    }
    return true;
  }

  protected boolean isRowWall(int y) {
    return isRowWall(y, 0, maxWidth - 1);
  }

  protected boolean isColWall(int x, int beginY, int endY) {
    for (int y = beginY; y <= endY; y++) {
      if (!isWall(x, y)) {
        return false;
      }
    }
    return true;
  }

  protected boolean isColWall(int x) {
    return isColWall(x, 0, maxHeight - 1);
  }

  protected Set<MazePoint> getNeighbourPoints(MazePoint mazePoint) {
    Set<MazePoint> ret = new HashSet<>();
    int x = mazePoint.getX();
    int y = mazePoint.getY();
    try {
      ret.add(getPointByCoordinate(x - 1, y));
    } catch (ArrayIndexOutOfBoundsException e) {

    }
    try {
      ret.add(getPointByCoordinate(x + 1, y));
    } catch (ArrayIndexOutOfBoundsException e) {

    }
    try {
      ret.add(getPointByCoordinate(x, y - 1));
    } catch (ArrayIndexOutOfBoundsException e) {

    }
    try {
      ret.add(getPointByCoordinate(x, y + 1));
    } catch (ArrayIndexOutOfBoundsException e) {

    }
    return ret;
  }

  protected Set<MazePoint> getNeighbourPoints(int x, int y) {
    return getNeighbourPoints(getPointByCoordinate(x, y));
  }

  // p1からp2との相対位置を返す
  // UI作成の都合でy座標は上からになっているので方向が逆になる
  protected MazePointPosition getRelativePosition(MazePoint p1, MazePoint p2) {
    if (p1.getX() == p2.getX() && p1.getY() == p2.getY() - 1) {
      return MazePointPosition.DOWN;
    }
    if (p1.getX() == p2.getX() && p1.getY() == p2.getY() + 1) {
      return MazePointPosition.UP;
    }
    if (p1.getX() == p2.getX() - 1 && p1.getY() == p2.getY()) {
      return MazePointPosition.RIGHT;
    }
    if (p1.getX() == p2.getX() + 1 && p1.getY() == p2.getY()) {
      return MazePointPosition.LEFT;
    }
    return MazePointPosition.OTHER;
  }

  // other
  public String toText() {
    String ret = "";
    if (mazePoints == null) {
      return ret;
    }
    for (int i = 0; i < mazePoints.length; i++) {

      for (int j = 0; j < mazePoints[i].length; j++) {
        ret += Integer.toString(mazePoints[i][j].getType().getValue());
      }
      ret += System.lineSeparator();
    }
    return ret;
  }
}
