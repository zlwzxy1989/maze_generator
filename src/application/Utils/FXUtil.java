package application.Utils;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import javafx.application.Platform;

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
}
