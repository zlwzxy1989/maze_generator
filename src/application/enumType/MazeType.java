package application.enumType;

public enum MazeType {

  穴掘り法(0), 棒倒し法(1), 再帰分割法(2);
  private int value;

  private MazeType(int v) {
    this.value = v;
  }

  public int getValue() {
    return value;
  }

  public static MazeType getByValue(int value) {
    for (MazeType mazeType : MazeType.values()) {
      if (mazeType.getValue() == value) {
        return mazeType;
      }
    }
    return null;
  }
}
