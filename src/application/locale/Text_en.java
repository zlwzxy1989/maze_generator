package application.locale;

import java.util.ListResourceBundle;

import application.service.ConfigService;

public class Text_en extends ListResourceBundle {

  @Override
  protected Object[][] getContents() {
    return new Object[][] {
        // main
        { "MainTitle", "maze generation demo" },
        { "MTitle", "file" },
        { "MIInitMaze", "maze initialization..." },
        { "MIGenerateMaze", "generate maze" },
        { "MIClearSight", "remove sight limit" },
        { "MISaveMaze", "output maze..." },

        // config
        { "ConfigTitle", "maze initialization" },
        { "LMazeWidth", "maze width" },
        { "TFMazeWidthHint", "number between 10 and 100" },
        { "LMazeHeight", "maze height" },
        { "TFMazeHeightHint", "number between 10 and 100" },
        { "LMazeGridWidth", "grid width" },
        { "TFMazeGridWidthHint", "maze grid width" },
        { "LMazeSightWidth", "sight width" },
        { "TFMazeSightWidthHint", "unlimited when set to 0" },
        { "LMazeSightIgnoreWall", "sight through wall" },
        { "LMazeTypeList", "maze type" },
        { "MazeType_0", "depth-first" },
        { "MazeType_1", "stick-push" },
        { "MazeType_2", "recursive division" },
        { "LShowAnime", "animation" },
        { "LNightMode", "night mode" },
        { "BInitMazeCancel", "cancel" },
        { "BInitMaze", "ok" },

        //alert
        { "ErrInitFailed", "initialization failed" },
        { "ErrBeforeInit", "please run maze initialization from menu first" },
        { "ErrRouteNotFound", "can`t find route to go there" },
        { "ErrMazeWidth", "maze width should be an integer between " + ConfigService.MAZE_MIN_WIDTH + " and " + ConfigService.MAZE_MAX_WIDTH + "" },
        { "ErrMazeHeight", "maze height should be an integer between " + ConfigService.MAZE_MIN_HEIGHT + " and " + ConfigService.MAZE_MAX_HEIGHT + "" },
        { "ErrMazeGridWidth", "maze grid width should be an integer greater than 0" },
        { "ErrMazeSightWidth", "grid width should be an integer equal or greater than 0" },
        { "ErrSavingMaze", "failed saving maze to file" },
        { "InfoMazeSaved", "maze saved to file successfully" },
        { "InfoMazeCleared", "Congratulations on getting out of the maze" },
    };
  }

}