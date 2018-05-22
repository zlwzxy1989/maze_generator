package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import application.Utils.FXUtil;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class ConfigController extends BaseController implements Initializable {

  @FXML
  private Label LMazeTypeList;
  @FXML
  private ComboBox<MazeType> CBMazeTypeList;

  @FXML
  private Label LMazeWidth;
  @FXML
  private TextField TFMazeWidth;
  @FXML
  private Label LMazeHeight;
  @FXML
  private TextField TFMazeHeight;
  @FXML
  private Label LMazeGridWidth;
  @FXML
  private TextField TFMazeGridWidth;
  @FXML
  private Label LMazeSightWidth;
  @FXML
  private TextField TFMazeSightWidth;
  @FXML
  private Label LMazeSightIgnoreWall;
  @FXML
  private CheckBox ChkBMazeSightIgnoreWall;
  @FXML
  private Label LShowAnime;
  @FXML
  private CheckBox ChkBShowAnime;
  @FXML
  private Button BInitMaze;
  @FXML
  private Button BInitMazeCancel;
  @FXML
  private Label LNightMode;
  @FXML
  private CheckBox ChkBNightMode;
  @FXML
  private GridPane GPInitMaze;

  private ConfigService configService = new ConfigService();

  public void BInitMazeCancelAction(ActionEvent event) {
    System.out.println("init cancelled...");
    FXUtil.closeWindow((Node) event.getSource());
  }

  public void BInitMazeAction(ActionEvent event) {
    Main.setAppMazeInitState(false);
    System.out.println("initing...");
    String mazeWidthStr = TFMazeWidth.getText();
    String mazeHeightStr = TFMazeHeight.getText();
    MazeType mazeType = CBMazeTypeList.getValue();
    String mazeGridWidthStr = TFMazeGridWidth.getText();
    String mazeSightWidth = TFMazeSightWidth.getText();
    boolean mazeSightIgnoreWall = ChkBMazeSightIgnoreWall.isSelected();
    boolean showAnime = ChkBShowAnime.isSelected();
    boolean nightMode = ChkBNightMode.isSelected();
    String errMsg;
    // エラーチェック
    errMsg = configService.checkMazeWidth(mazeWidthStr);
    if (!errMsg.equals("")) {
      FXUtil.showErrorAlert(errMsg);
      return;
    }
    errMsg = configService.checkMazeHeight(mazeHeightStr);
    if (!errMsg.equals("")) {
      FXUtil.showErrorAlert(errMsg);
      return;
    }
    errMsg = configService.checkMazeGridWidth(mazeGridWidthStr);
    if (!errMsg.equals("")) {
      FXUtil.showErrorAlert(errMsg);
      return;
    }
    errMsg = configService.checkMazeSightWidth(mazeSightWidth);
    if (!errMsg.equals("")) {
      FXUtil.showErrorAlert(errMsg);
      return;
    }
    MazeConfigDto dto = new MazeConfigDto();
    dto.setMazeWidth(Integer.parseInt(mazeWidthStr));
    dto.setMazeHeight(Integer.parseInt(mazeHeightStr));
    dto.setMazeGridWidth(Integer.parseInt(mazeGridWidthStr));
    dto.setMazeSightWidth(Integer.parseInt(mazeSightWidth));
    dto.setMazeType(mazeType);
    dto.setMazeSightIgnoreWall(mazeSightIgnoreWall);
    dto.setShowAnime(showAnime);
    dto.setNightMode(nightMode);
    if (configService.setInitConfig(dto)) {
      Main.setAppMazeGenState(false);
      Main.setAppMazeInitState(true);
      MazeMap.getInstance(dto);
      FXUtil.closeWindow((Node) event.getSource());
    } else {
      FXUtil.showErrorAlert(FXUtil.getLocaleText("ErrInitFailed"));
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // 値の初期化
    MazeConfigDto dto = ConfigService.getInitConfig();
    TFMazeWidth.setText(Integer.toString(dto.getMazeWidth()));
    TFMazeHeight.setText(Integer.toString(dto.getMazeHeight()));
    TFMazeGridWidth.setText(Integer.toString(dto.getMazeGridWidth()));
    TFMazeSightWidth.setText(Integer.toString(dto.getMazeSightWidth()));
    ChkBMazeSightIgnoreWall.setSelected(dto.isMazeSightIgnoreWall());
    ChkBShowAnime.setSelected(dto.isShowAnime());
    ChkBNightMode.setSelected(dto.isNightMode());
    // 画面の初期化
    GridPane.setHalignment(BInitMaze, HPos.RIGHT);
    GridPane.setHalignment(BInitMazeCancel, HPos.RIGHT);
    // 文言
    LMazeWidth.setText(FXUtil.getLocaleText("LMazeWidth"));
    TFMazeWidth.setPromptText(FXUtil.getLocaleText("TFMazeWidthHint"));

    LMazeHeight.setText(FXUtil.getLocaleText("LMazeHeight"));
    TFMazeHeight.setPromptText(FXUtil.getLocaleText("TFMazeHeightHint"));

    LMazeGridWidth.setText(FXUtil.getLocaleText("LMazeGridWidth"));
    TFMazeHeight.setPromptText(FXUtil.getLocaleText("TFMazeHeightHint"));

    LMazeGridWidth.setText(FXUtil.getLocaleText("LMazeGridWidth"));
    TFMazeGridWidth.setPromptText(FXUtil.getLocaleText("TFMazeGridWidthHint"));

    LMazeSightWidth.setText(FXUtil.getLocaleText("LMazeSightWidth"));
    TFMazeSightWidth.setPromptText(FXUtil.getLocaleText("TFMazeSightWidthHint"));

    LMazeSightIgnoreWall.setText(FXUtil.getLocaleText("LMazeSightIgnoreWall"));
    LMazeTypeList.setText(FXUtil.getLocaleText("LMazeTypeList"));

    LMazeTypeList.setText(FXUtil.getLocaleText("LMazeTypeList"));

    LShowAnime.setText(FXUtil.getLocaleText("LShowAnime"));

    LNightMode.setText(FXUtil.getLocaleText("LNightMode"));

    BInitMazeCancel.setText(FXUtil.getLocaleText("BInitMazeCancel"));
    BInitMaze.setText(FXUtil.getLocaleText("BInitMaze"));


    CBMazeTypeList.getItems().clear();
    CBMazeTypeList.getItems()
        .addAll(FXCollections.observableArrayList(MazeType.values()));
    CBMazeTypeList.setValue(dto.getMazeType());

  }

}
