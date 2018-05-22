package application.dto;

import lombok.Data;

@Data
public class AppStateDto {

  private boolean mazeInitialized = false;
  private boolean mazeGenerated = false;
  private boolean unlimitedSight = false;
}
