package application.core;

import application.enumType.MazePointType;
import lombok.Data;

@Data
public class MazePoint {

  // 配列のインデックスではなく座標
  private int x = 0;
  private int y = 0;

  public String getId() {
    return Integer.toString(x) + "," + Integer.toString(y);
  }

  private boolean visited = false;
  private boolean startPoint = false;
  private boolean endPoint = false;
  private boolean visible = true;
  private boolean currentPoint = false;
  private MazePointType type = MazePointType.WALL;

  public MazePoint(int x, int y, MazePointType type, boolean visited) {
    this.x = x;
    this.y = y;
    this.type = type;
    this.visited = visited;
  }

  public MazePoint() {

  }

  public MazePoint(MazePoint p) {
    this.x = p.x;
    this.y = p.y;
    this.type = p.type;
    this.visited = p.visited;
    this.startPoint = p.startPoint;
    this.endPoint = p.endPoint;
    this.visible = p.visible;
    this.currentPoint = p.currentPoint;
  }
}
