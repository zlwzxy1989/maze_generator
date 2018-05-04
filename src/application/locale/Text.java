package application.locale;

import java.util.ListResourceBundle;

public class Text extends ListResourceBundle {
  @Override
  protected Object[][] getContents() {
    return new Object[][] {
        // main
        { "MainTitle", "迷宮生成デモ" },
        { "MTitle", "ファイル" },
        { "MIInitMaze", "迷宮の初期化..." },
        { "MIGenerateMaze", "迷宮を生成" },
        { "MIClearSight", "視界制限を無くす" },
        { "MISaveMaze", "迷宮を出力..." },

        // config
        { "ConfigTitle", "迷宮の初期化" },
        { "LMazeWidth", "迷宮の幅" },
        { "TFMazeWidthHint", "10-100の数字" },
        { "LMazeHeight", "迷宮の高さ" },
        { "TFMazeHeightHint", "10-100の数字" },
        { "LMazeGridWidth", "マスの幅" },
        { "TFMazeGridWidthHint", "迷宮マスの幅" },
        { "LMazeSightWidth", "可視範囲" },
        { "TFMazeSightWidthHint", "0は無制限" },
        { "LMazeSightIgnoreWall", "壁透視" },
        { "LMazeTypeList", "迷宮タイプ" },
        { "MazeType_0", "穴掘り法" },
        { "MazeType_1", "棒倒し法" },
        { "MazeType_2", "再帰分割法" },
        { "LShowAnime", "アニメ効果" },
        { "BInitMazeCancel", "取消" },
        { "BInitMaze", "確定" },

        //alert
        { "ErrInitFailed", "初期化に失敗しました" },
        { "ErrBeforeInit", "メニューから迷宮の初期化を行ってください" },
        { "ErrRouteNotFound", "移動ルートが見つかりません" },
        { "ErrSavingMaze", "迷宮ファイルの出力に失敗しました" },
        { "InfoMazeSaved", "迷宮ファイルを出力しました" },
        { "InfoMazeCleared", "迷宮踏破おめでとうございます" },
    };
  }
}
