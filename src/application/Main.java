package application;

import java.util.ArrayList;
import java.util.List;

import application.dto.AppStateDto;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
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
      Scene scene = new Scene(root, 400, 400);

      primaryStage.setScene(scene);
      primaryStage.setTitle("迷宮生成デモ");
      setPrimaryStage(primaryStage);
      primaryStage.setResizable(false);
      primaryStage.show();
      // 迷宮エリア以外のパーツのheight合計を取得
      MenuBar mb = (MenuBar) root.getTop();
      decorationHeight = primaryStage.getHeight() - root.getHeight() + mb.getHeight();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    launch(args);
  }

  //
  public static void setOwnerOf(Stage s) {
    s.initOwner(primaryStage);
    ;
  }

  private void setPrimaryStage(Stage s) {
    primaryStage = s;
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
    List<HBox> rows = new ArrayList<>();
    for (int i = 0; i < map.length; i++) {
      rows.add(new HBox());
      rows.get(i).setMaxHeight(map[0][0].getMaxHeight());
      rows.get(i).setMaxWidth(width);
      for (int j = 0; j < map[i].length; j++) {
        rows.get(i).getChildren().add(map[i][j]);
      }
    }
    mapRoot.getChildren().addAll(FXCollections.observableArrayList(rows));
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
