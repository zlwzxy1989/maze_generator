package application.Utils;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class FXUtil {

  public static void runAndWait(Runnable run) {
    FutureTask<Void> task = new FutureTask<>(run, null);
    Platform.runLater(task);
    try {
      task.get();
    } catch (ExecutionException | InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static void run(Runnable run) {
    Platform.runLater(run);
  }
  // ui
  public static void closeWindow(Node node) {
    Stage currentWindow = (Stage) node.getScene().getWindow();
    currentWindow.close();

  }

  public static void closeApplication() {
    Platform.exit();
  }

  public static void showErrorAlert(String msg) {
    Alert alert = new Alert(AlertType.ERROR,
        msg,
        ButtonType.OK);
    alert.show();
  }

  public static void showInfoAlert(String msg) {
    Alert alert = new Alert(AlertType.INFORMATION,
        msg,
        ButtonType.OK);
    alert.show();
  }
  public static void showWarnAlert(String msg) {
    Alert alert = new Alert(AlertType.WARNING,
        msg,
        ButtonType.OK);
    alert.show();
  }
}
