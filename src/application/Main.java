package application;

import java.util.List;

import application.Utils.FXUtil;
import application.core.MazeMap;
import application.dto.AppStateDto;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
  private static Stage primaryStage = null;
  private static AppStateDto appState = new AppStateDto();
  private static double decorationHeight = 0d;

  @Override
  public void start(Stage primaryStage) {
    try {
      BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("view/Main.fxml"));
      // オープニング動画が終わるまで一旦使えないようにする
      MenuBar mb = (MenuBar) root.getTop();
      mb.setDisable(true);
      Menu m = mb.getMenus().get(0);
      m.setText(FXUtil.getLocaleText("MTitle"));
      List<MenuItem> miList = m.getItems();
      miList.get(0).setText(FXUtil.getLocaleText("MIInitMaze"));
      miList.get(1).setText(FXUtil.getLocaleText("MIGenerateMaze"));
      miList.get(2).setText(FXUtil.getLocaleText("MIClearSight"));
      miList.get(3).setText(FXUtil.getLocaleText("MISaveMaze"));

      Scene scene = new Scene(root, 400, 400);

      primaryStage.setScene(scene);
      primaryStage.setTitle(FXUtil.getLocaleText("MainTitle"));
      setPrimaryStage(primaryStage);
      primaryStage.setResizable(false);
      primaryStage.show();
      // 迷宮エリア以外のパーツのheight合計を取得
      decorationHeight = primaryStage.getHeight() - root.getHeight() + mb.getHeight();
      FXUtil.run(() -> MazeMap.getInstance().showTitleMaze());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    launch(args);
  }

  public static Stage getPrimaryStage() {
    return primaryStage;
  }

  private void setPrimaryStage(Stage s) {
    primaryStage = s;
  }

  public static void enableMenu() {
    BorderPane root = (BorderPane) primaryStage.getScene().getRoot();
    root.getTop().setDisable(false);
  }

  public static boolean getAppMazeInitState() {
    return appState.isMazeInitialized();
  }

  public static void setAppMazeInitState(boolean appInitState) {
    appState.setMazeInitialized(appInitState);
  }

  public static boolean getAppMazeGenState() {
    return appState.isMazeGenerated();
  }

  public static void setAppMazeGenState(boolean appGenState) {
    appState.setMazeGenerated(appGenState);
  }

  public static void setMazeMap(Button[][] map) {
    if (map == null || map.length == 0 || map[0].length == 0) {
      return;
    }
    double width = map[0][0].getMaxWidth() * map[0].length;
    double height = map[0][0].getMaxHeight() * map.length;
    primaryStage.setWidth(width);
    primaryStage.setHeight(height + decorationHeight);

    BorderPane root = (BorderPane) primaryStage.getScene().getRoot();
    VBox mapRoot = (VBox) root.getBottom();
    mapRoot.setMinHeight(height);
    mapRoot.getChildren().clear();
    ObservableList<Node> rows = mapRoot.getChildren();
    for (int i = 0; i < map.length; i++) {
      HBox hBox = new HBox();
      rows.add(hBox);
      hBox.setMaxHeight(map[0][0].getMaxHeight());
      hBox.setMaxWidth(width);
      for (int j = 0; j < map[i].length; j++) {
        hBox.getChildren().add(map[i][j]);
      }
    }

  }

  @Override
  public void stop() {
    FXUtil.stopService();
  }
  /*
  public static AppStateDto getAppState() {
    return appState;
  }

  public static void setAppState(AppStateDto dto) {
    appState = dto;
  }
  */
}
