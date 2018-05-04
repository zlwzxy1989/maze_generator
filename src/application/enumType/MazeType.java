package application.enumType;

import application.Utils.FXUtil;

public enum MazeType {

  DEPTH_FIRST(0), STICK(1), RECURSIVE_DIVISION(2);
  private int value;

  private MazeType(int v) {
    this.value = v;
  }

  public int getValue() {
    return value;
  }

  @Override
  public String toString() {
    return FXUtil.getLocaleText("MazeType_" + getValue());
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
