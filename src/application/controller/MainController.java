package application.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import application.Main;
import application.Utils.FXUtil;
import application.core.MazeButton;
import application.core.MazeGenerator;
import application.core.MazeMap;
import application.dto.MazeConfigDto;
import application.service.ConfigService;
import application.service.DepthFirstAlgorithmService;
import application.service.RecursiveDivisionAlgorithmService;
import application.service.StickAlgorithmService;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController extends BaseController {
  public void MIGenerateMazeAction(ActionEvent event) {
    System.out.println("generating...");
    if (!Main.getAppMazeInitState()) {
      FXUtil.showErrorAlert(FXUtil.getLocaleText("ErrBeforeInit"));
      return;
    }
    MazeConfigDto dto = ConfigService.getInitConfig();
    MazeGenerator service;

    MazeMap mazeMap = MazeMap.getInstance();
    if (dto.isShowAnime()) {
      mazeMap.resetPointsStateForGenerator();
    }
    switch (dto.getMazeType()) {
    default:
    case STICK:
      service = new StickAlgorithmService(mazeMap.getMazePoints(), dto.isShowAnime());
      break;
    case DEPTH_FIRST:
      service = new DepthFirstAlgorithmService(mazeMap.getMazePoints(), dto.isShowAnime());
      break;
    case RECURSIVE_DIVISION:
      service = new RecursiveDivisionAlgorithmService(mazeMap.getMazePoints(), dto.isShowAnime());
      break;
    }
    service.generate();
    Main.setAppMazeGenState(true);
    FXUtil.run(() -> mazeMap.newGame(), 0);
    //FXUtil.showInfoAlert("迷宮生成が完了しました");
  }

  static private Stage initMazeStage = null;
  private File saveMazeFile = null;

  static public Stage getInitMazeStage() {
    return initMazeStage;
  }

  private void setInitMazeStage(Stage s) {
    initMazeStage = s;
  }

  public void MIInitMazeAction(ActionEvent event) {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(Main.class.getResource("view/Config.fxml"));
      GridPane initPage = (GridPane) loader.load();
      Stage dialogStage = new Stage();
      dialogStage.setTitle(FXUtil.getLocaleText("ConfigTitle"));
      dialogStage.initModality(Modality.WINDOW_MODAL);
      Scene scene = new Scene(initPage);
      dialogStage.setScene(scene);
      dialogStage.setResizable(false);
      Stage primaryStage = Main.getPrimaryStage();
      dialogStage.initOwner(primaryStage);
      setInitMazeStage(dialogStage);

      // parentWindow.setCenter(childStage)欲しい...
      double parentCenterX = primaryStage.getX() + primaryStage.getWidth() / 2d;
      double parentCenterY = primaryStage.getY() + primaryStage.getHeight() / 2d;

      dialogStage.setOnShowing(ev -> dialogStage.hide());

      dialogStage.setOnShown(ev -> {
        dialogStage.setX(parentCenterX - dialogStage.getWidth() / 2d);
        dialogStage.setY(parentCenterY - dialogStage.getHeight() / 2d);
        dialogStage.show();
      });
      dialogStage.showAndWait();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void MISaveMazeAction(ActionEvent event) {
    if (!Main.getAppMazeGenState()) {
      FXUtil.showErrorAlert(FXUtil.getLocaleText("ErrBeforeInit"));
      return;

    }
    String mazeStr = MazeMap.getInstance().toText();
    FileChooser fileChooser = new FileChooser();

    if (saveMazeFile != null) {
      File existDirectory = saveMazeFile.getParentFile();
      fileChooser.setInitialDirectory(existDirectory);
    }

    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("テキスト ファイル (*.txt)", "*.txt");
    fileChooser.getExtensionFilters().add(extFilter);
    fileChooser.setInitialFileName("maze.txt");
    saveMazeFile = fileChooser.showSaveDialog(initMazeStage);
    System.out.println("save to file:" + saveMazeFile.getAbsolutePath());
    try (BufferedWriter w = Files.newBufferedWriter(saveMazeFile.toPath())) {
      w.write(mazeStr);
      w.flush();
      FXUtil.showInfoAlert(FXUtil.getLocaleText("InfoMazeSaved"));
    } catch (IOException e) {
      FXUtil.showErrorAlert(FXUtil.getLocaleText("ErrSavingMaze"));
      e.printStackTrace();
    }
  }

  public void MIClearSightAction(ActionEvent event) {
    if (!Main.getAppMazeGenState()) {
      FXUtil.showErrorAlert(FXUtil.getLocaleText("ErrBeforeInit"));
      return;
    }
    MazeMap.getInstance().setAllVisible();
  }

  public void BPMazeMoveAction(KeyEvent event) {
    MazeMap mazeMap = MazeMap.getInstance();
    if (!mazeMap.isPlayable()) {
      return;
    }
    KeyCode keyPressed = event.getCode();
    int x = mazeMap.getCurrentX();
    int y = mazeMap.getCurrentY();
    // 上へ移動はy座標マイナス
    if (keyPressed == KeyCode.UP || keyPressed == KeyCode.W) {
      System.out.println("keyboard moving up...");
      y--;
    }
    // 下へ移動はy座標プラス
    else if (keyPressed == KeyCode.DOWN || keyPressed == KeyCode.S) {
      System.out.println("keyboard moving down...");
      y++;
    } // 左へ移動はx座標マイナス
    else if (keyPressed == KeyCode.LEFT || keyPressed == KeyCode.A) {
      System.out.println("keyboard moving left...");
      x--;
    } // 右へ移動はx座標プラス
    else if (keyPressed == KeyCode.RIGHT || keyPressed == KeyCode.D) {
      System.out.println("keyboard moving right...");
      x++;
    }
    System.out
        .println(mazeMap.getCurrentX() + "," + mazeMap.getCurrentY() + " -> " + x + "," + y);
    MazeButton ui = mazeMap.getUIByCoordinate(x, y);
    if (ui != null) {
      ui.fire();
    }

  }
}
