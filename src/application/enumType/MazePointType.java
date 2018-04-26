package application.enumType;

public enum MazePointType {
  // BORDERは範囲外
  ROAD(0), WALL(1), BORDER(2);
  private int value;

  private MazePointType(int val) {
    value = val;
  }

  public int getValue() {
    return value;
  }

}
