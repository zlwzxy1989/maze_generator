package application.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import application.Main;
import application.core.MazeMap;
import application.dto.MazeConfigDto;
import application.enumType.MazeType;
import application.service.ConfigService;
import application.service.DepthFirstAlgorithmService;
import application.service.MazeBaseAlgorithmService;
import application.service.StickAlgorithmService;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController extends BaseController {
  public void MIGenerateMazeAction(ActionEvent event) {
    System.out.println("generating...");
    if (!Main.getAppMazeInitState()) {
      showErrorAlert("メニューから迷宮の初期化を行ってください");
      return;
    }
    MazeConfigDto dto = ConfigService.getInitConfig();
    MazeBaseAlgorithmService service;
    switch (MazeType.getByValue(dto.getMazeType())) {
    default:
    case 棒倒し法:
      service = new StickAlgorithmService(MazeMap.getInstance().getMazePoints());
      break;
    case 穴掘り法:
      service = new DepthFirstAlgorithmService(MazeMap.getInstance().getMazePoints());
      break;
    }
    service.generate(dto.isShowAnime());
    Main.setAppMazeGenState(true);
    showInfoAlert("迷宮生成が完了しました");
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
      dialogStage.setTitle("迷宮の初期化");
      dialogStage.initModality(Modality.WINDOW_MODAL);
      Scene scene = new Scene(initPage);
      dialogStage.setScene(scene);
      Main.setOwnerOf(dialogStage);
      setInitMazeStage(dialogStage);

      dialogStage.show();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void MISaveMazeAction(ActionEvent event) {
    if (!Main.getAppMazeGenState()) {
      showErrorAlert("メニューから迷宮の生成を行ってください");
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
      showInfoAlert("迷宮ファイルを保存しました");
    } catch (IOException e) {
      showErrorAlert("迷宮ファイルの保存に失敗しました");
      e.printStackTrace();
    }
  }
}
