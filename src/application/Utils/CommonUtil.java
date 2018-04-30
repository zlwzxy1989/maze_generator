package application.Utils;

import java.util.Random;

public class CommonUtil {
  // 0 <= x < max のxをランダムで返す
  public static int getRandomInt(int max) {
    if (max <= 0) {
      return max;
    }
    return new Random().nextInt(max);
  }
}
