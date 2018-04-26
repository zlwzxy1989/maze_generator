package application.enumType;

// 二つの隣のマスの相対位置を表す列挙型
public enum MazePointPosition {
  UP(1), DOWN(2), LEFT(3), RIGHT(4), OTHER(5);

  private int value;

  private MazePointPosition(int val) {
    value = val;
  }

  public int getValue() {
    return value;
  }
}
