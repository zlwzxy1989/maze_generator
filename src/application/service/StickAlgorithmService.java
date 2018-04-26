package application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import application.core.MazePoint;
import application.enumType.MazePointPosition;

// 棒倒し法
public class StickAlgorithmService extends MazeBaseAlgorithmService {
  public StickAlgorithmService() {
    super();
  }

  public StickAlgorithmService(MazePoint[][] mazePoints) {
    super(mazePoints);
  }

  @Override
  public void generate(boolean showAnime) {
    if (mazePoints == null) {
      return;
    }
    MazePoint startPoint = getStartPoint();
    MazePoint endPoint = getEndPoint();
    if (startPoint == null || endPoint == null) {
      return;
    }
    this.showAnime = showAnime;
    // 全部道にする
    setAllToRoad();
    // 2行目から1マス間隔で棒を作っていく
    for (int row = 1; row < maxHeight; row = row + 2) {
      for (int col = 1; col < maxWidth; col = col + 2) {
        MazePoint point = getPointByCoordinate(col, row);
        // 棒を壁にする
        setToWall(point);
        // 壁際の棒は倒さない
        if (row == maxHeight - 1 || col == maxWidth - 1) {
          continue;
        }
        // 棒を倒していく 最初の行の棒だけ上へ倒れることを許可する
        pullDownStick(point, row == 1);
      }
    }
    // 起点と終点を道にする
    setToRoad(startPoint);
    setToWall(endPoint);
  }

  private void pullDownStick(MazePoint point, boolean allowedPullUp) {
    // 上へ倒すことができれば四方向、できなければ三方向へランダムで倒していく
    // まず候補方向を決める すでに他の棒が倒れた方向はパス
    List<MazePoint> availablePoints = new ArrayList<MazePoint>();
    Set<MazePoint> neighbourPoints = getNeighbourPoints(point);
    for (MazePoint p : neighbourPoints) {
      if (isWall(p) || (getRelativePosition(point, p).equals(MazePointPosition.UP) && !allowedPullUp)) {
        continue;
      }
      availablePoints.add(p);
    }
    if (availablePoints.size() == 0) {
      return;
    }
    // ランダムで一つ候補を決める
    setToWall(availablePoints.get(getRandomInt(availablePoints.size())));
  }
}
