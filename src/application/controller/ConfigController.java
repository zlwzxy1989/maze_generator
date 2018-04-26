package application.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import application.core.MazeMap;
import application.dto.MazeConfigDto;
import application.enumType.MazeType;
import application.service.ConfigService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class ConfigController extends BaseController implements Initializable {
  @FXML
  private ComboBox<String> CBMazeTypeList;

  @FXML
  private TextField TFMazeWidth;

  @FXML
  private TextField TFMazeHeight;
  @FXML
  private Button BInitMaze;
  @FXML
  private Button BInitMazeCancel;

  @FXML
  private GridPane GPInitMaze;

  private ConfigService configService = new ConfigService();

  public void BInitMazeCancelAction(ActionEvent event) {
    System.out.println("init cancelled...");
    closeWindow((Node) event.getSource());
  }

  public void BInitMazeAction(ActionEvent event) {
    Main.setAppMazeInitState(false);
    System.out.println("initing...");
    String mazeWidthStr = TFMazeWidth.getText();
    String mazeHeightStr = TFMazeHeight.getText();
    String mazeTypeStr = CBMazeTypeList.getValue();

    String errMsg;
    // エラーチェック
    errMsg = configService.checkMazeWidth(mazeWidthStr);
    if (!errMsg.equals("")) {
      showErrorAlert(errMsg);
      return;
    }
    errMsg = configService.checkMazeHeight(mazeHeightStr);
    if (!errMsg.equals("")) {
      showErrorAlert(errMsg);
      return;
    }
    MazeConfigDto dto = new MazeConfigDto();
    dto.setMazeWidth(Integer.parseInt(mazeWidthStr));
    dto.setMazeHeight(Integer.parseInt(mazeHeightStr));
    dto.setMazeType(MazeType.valueOf(mazeTypeStr).getValue());
    if (configService.setInitConfig(dto)) {
      Main.setAppMazeGenState(false);
      Main.setAppMazeInitState(true);
      MazeMap.getInstance(dto);
      closeWindow((Node) event.getSource());
    } else {
      showErrorAlert("初期化に失敗しました");
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // 値の初期化
    MazeConfigDto dto = ConfigService.getInitConfig();
    TFMazeWidth.setText(Integer.toString(dto.getMazeWidth()));
    TFMazeHeight.setText(Integer.toString(dto.getMazeHeight()));
    // 画面の初期化
    GridPane.setHalignment(BInitMaze, HPos.RIGHT);
    GridPane.setHalignment(BInitMazeCancel, HPos.RIGHT);

    CBMazeTypeList.getItems().clear();
    List<String> mazeTypeList = new ArrayList<>();
    for (MazeType mazeType : MazeType.values()) {
      mazeTypeList.add(mazeType.name());
    }
    CBMazeTypeList.getItems()
        .addAll(FXCollections.observableArrayList(mazeTypeList));
    CBMazeTypeList.setValue(MazeType.getByValue(dto.getMazeType()).name());

  }

}
