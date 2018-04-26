package application.dto;

import lombok.Data;

@Data
public class MazeConfigDto {

  private int mazeHeight;
  private int mazeWidth;
  private int mazeType;
  private boolean showAnime = true;
  // マス幅
  private int pointWidth = 12;

  public boolean equals(Object o) {
    if (o == null || !(o instanceof MazeConfigDto)) {
      return false;
    }
    MazeConfigDto other = (MazeConfigDto) o;
    return (this.getMazeHeight() == other.getMazeHeight() && this.getMazeWidth() == other.getMazeWidth());
  }

  public int hashCode() {
    return Integer.parseInt(Integer.toString(getMazeHeight()) + "0" + Integer.toString(getMazeWidth()));
  }
}
