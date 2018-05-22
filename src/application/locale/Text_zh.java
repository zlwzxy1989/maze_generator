package application.locale;

import java.util.ListResourceBundle;

public class Text_zh extends ListResourceBundle {

  @Override
  protected Object[][] getContents() {
    return new Object[][] {
        // main
        { "MainTitle", "迷宮生成演示" },
        { "MTitle", "文件" },
        { "MIInitMaze", "迷宮初始化..." },
        { "MIGenerateMaze", "生成迷宮" },
        { "MIClearSight", "去除视野限制" },
        { "MISaveMaze", "导出迷宫..." },

        // config
        { "ConfigTitle", "迷宮初始化" },
        { "LMazeWidth", "迷宮宽度" },
        { "TFMazeWidthHint", "10-100间的数字" },
        { "LMazeHeight", "迷宮高度" },
        { "TFMazeHeightHint", "10-100间的数字" },
        { "LMazeGridWidth", "迷宫格边长" },
        { "TFMazeGridWidthHint", "迷宫格边长" },
        { "LMazeSightWidth", "视野" },
        { "TFMazeSightWidthHint", "0为无限制" },
        { "LMazeSightIgnoreWall", "透视墙壁" },
        { "LMazeTypeList", "迷宮类型" },
        { "MazeType_0", "深度优先" },
        { "MazeType_1", "倒杆法" },
        { "MazeType_2", "递归分割法" },
        { "LShowAnime", "动画效果" },
        { "LNightMode", "夜晚模式" },
        { "BInitMazeCancel", "取消" },
        { "BInitMaze", "确定" },

        //alert
        { "ErrInitFailed", "初始化失败" },
        { "ErrBeforeInit", "请先从菜单执行迷宫初始化" },
        { "ErrRouteNotFound", "找不到可移动的路径" },
        { "ErrSavingMaze", "导出迷宫失败" },
        { "InfoMazeSaved", "导出迷宫成功" },
        { "InfoMazeCleared", "恭喜走出迷宫" },
    };
  }

}
