package application.Utils;

import java.util.Random;
import java.util.ResourceBundle;

public class CommonUtil {
  // 0 <= x < max のxをランダムで返す
  public static int getRandomInt(int max) {
    if (max <= 0) {
      return max;
    }
    return new Random().nextInt(max);
  }

  public static String getLocaleText(String baseName, String key) {
    try {

      return ResourceBundle.getBundle(baseName).getString(key);
    } catch (NullPointerException e) {
      System.out.println("文言が見つかりません");
      e.printStackTrace();
      return "";
    }
  }
}
