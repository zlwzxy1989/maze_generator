package application.Utils;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class FXUtil {

  private static ExecutorService service = Executors.newFixedThreadPool(1);

  public static void stopService() {
    service.shutdown();
  }

  public static void runAndWait(Runnable run) {
    FutureTask<Void> task = new FutureTask<>(run, null);
    Platform.runLater(task);
    try {
      task.get();
    } catch (InterruptedException | ExecutionException e) {
      // TODO 自動生成された catch ブロック
      e.printStackTrace();
    }
  }

  public static void run(Runnable run, int delay) {

    Task<Void> task = new Task<Void>() {

      @Override
      protected Void call() throws Exception {
        Platform.runLater(run);
        if (delay > 0) {
          Thread.sleep(delay);
        }
        return null;
      }

    };
    service.submit(task);
  }

  public static void run(Runnable run) {
    run(run, 0);
  }

  // ui
  public static void closeWindow(Node node) {
    Stage currentWindow = (Stage) node.getScene().getWindow();
    currentWindow.close();

  }

  public static void closeApplication() {
    stopService();
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

  public static String getLocaleText(String key) {
    System.out.println("reading locale text :" + key);
    return CommonUtil.getLocaleText("application.locale.Text", key);
  }
}
