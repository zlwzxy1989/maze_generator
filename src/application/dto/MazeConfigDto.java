package application.dto;

import application.enumType.MazeType;
import application.service.ConfigService;
import lombok.Data;

@Data
public class MazeConfigDto {

  private int mazeHeight = ConfigService.MAZE_DEFAULT_HEIGHT;
  private int mazeWidth = ConfigService.MAZE_DEFAULT_WIDTH;
  private MazeType mazeType = MazeType.DEPTH_FIRST;
  private boolean showAnime = true;
  // マス幅
  private int mazeGridWidth = ConfigService.MAZE_GRID_WIDTH;

  // 視界範囲
  private int mazeSightWidth = ConfigService.MAZE_SIGHT_WIDTH;

  // 視界範囲が壁に遮断されるかどうか
  private boolean mazeSightIgnoreWall = true;
  // 夜モード ONすると現在地の視界しか表示しなくなる
  private boolean nightMode = false;

  public boolean isSightMode() {
    return mazeSightWidth > 0;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || !(o instanceof MazeConfigDto)) {
      return false;
    }
    MazeConfigDto other = (MazeConfigDto) o;
    return (this.getMazeHeight() == other.getMazeHeight() && this.getMazeWidth() == other.getMazeWidth()
        && this.getMazeGridWidth() == other.getMazeGridWidth()
        && this.getMazeSightWidth() == other.getMazeSightWidth());
  }

  @Override
  public int hashCode() {
    return Integer.parseInt(Integer.toString(getMazeHeight()) + "0" + Integer.toString(getMazeWidth()) + "_"
        + Integer.toString(getMazeGridWidth()) + "0" + Integer.toString(getMazeSightWidth()));
  }
}
