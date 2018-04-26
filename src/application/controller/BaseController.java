package application.controller;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public abstract class BaseController {
  protected void closeWindow(Node node) {
    Stage currentWindow = (Stage) node.getScene().getWindow();
    currentWindow.close();

  }

  protected void closeApplication() {
    Platform.exit();
  }

  protected void showErrorAlert(String msg) {
    Alert alert = new Alert(AlertType.ERROR,
        msg,
        ButtonType.OK);
    alert.show();
  }

  protected void showInfoAlert(String msg) {
    Alert alert = new Alert(AlertType.INFORMATION,
        msg,
        ButtonType.OK);
    alert.show();
  }
  protected void showWarnAlert(String msg) {
    Alert alert = new Alert(AlertType.WARNING,
        msg,
        ButtonType.OK);
    alert.show();
  }
}
