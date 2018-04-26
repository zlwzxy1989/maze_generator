package application.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Properties;

import application.dto.MazeConfigDto;

public class ConfigService {

  public static final int MAZE_MAX_WIDTH = 100;
  public static final int MAZE_MIN_WIDTH = 10;
  public static final int MAZE_MAX_HEIGHT = 100;
  public static final int MAZE_MIN_HEIGHT = 5;

  public static final int MAZE_GRID_WIDTH = 12;

  private static MazeConfigDto dto = null;

  protected static MazeConfigDto getInitConfigFromFile() {
    MazeConfigDto dto = new MazeConfigDto();
    Properties prop = new Properties();
    try (InputStream in = new FileInputStream("application.properties")) {
      prop.load(in);
      dto.setMazeWidth(Integer.parseInt(prop.getProperty("mazeWidth")));
      dto.setMazeHeight(Integer.parseInt(prop.getProperty("mazeHeight")));
      dto.setMazeType(Integer.parseInt(prop.getProperty("mazeType")));
    } catch (IOException | NumberFormatException e) {
      System.out.println("初期設定の読込みに失敗しました");
      dto.setMazeWidth(30);
      dto.setMazeHeight(20);
      dto.setMazeType(0);

    }
    return dto;
  }

  public static MazeConfigDto getInitConfig() {
    if (dto == null) {
      dto = getInitConfigFromFile();
    }
    return dto;
  }

  public boolean setInitConfig(MazeConfigDto dto) {
    if (dto == null) {
      return false;
    }
    Properties prop = new Properties();
    prop.setProperty("mazeWidth", Integer.toString(dto.getMazeWidth()));
    prop.setProperty("mazeHeight", Integer.toString(dto.getMazeHeight()));
    prop.setProperty("mazeType", Integer.toString(dto.getMazeType()));
    try {
      prop.store(new FileOutputStream("application.properties"),
          "updated at " + LocalDateTime.now());
      ConfigService.dto = dto;
      return true;
    } catch (IOException e) {
      System.out.println("初期設定の書込みに失敗しました");
      e.printStackTrace();
      return false;
    }

  }

  public boolean isValidMazeWidth(String mazeWidthStr) {
    try {
      int mazeWidth = Integer.parseInt(mazeWidthStr);
      return isValidMazeWidth(mazeWidth);
    } catch (NumberFormatException e) {
      return false;
    }
  }

  public boolean isValidMazeWidth(int mazeWidth) {
    if (mazeWidth >= MAZE_MIN_WIDTH && mazeWidth <= MAZE_MAX_WIDTH) {
      return true;
    }
    return false;
  }

  public String checkMazeWidth(String mazeWidthStr) {
    if (isValidMazeWidth(mazeWidthStr)) {
      return "";
    }
    return "迷宮の幅は" + MAZE_MIN_WIDTH + "から" + MAZE_MAX_WIDTH + "までの値である必要があります";
  }

  public boolean isValidMazeHeight(String mazeHeightStr) {
    try {
      int mazeHeight = Integer.parseInt(mazeHeightStr);
      return isValidMazeHeight(mazeHeight);
    } catch (NumberFormatException e) {
      return false;
    }
  }

  public boolean isValidMazeHeight(int mazeHeight) {
    if (mazeHeight >= MAZE_MIN_HEIGHT && mazeHeight <= MAZE_MAX_HEIGHT) {
      return true;
    }
    return false;
  }

  public String checkMazeHeight(String mazeHeightStr) {
    if (isValidMazeHeight(mazeHeightStr)) {
      return "";
    }
    return "迷宮の幅は" + MAZE_MIN_HEIGHT + "から" + MAZE_MAX_HEIGHT + "までの値である必要があります";
  }

}
