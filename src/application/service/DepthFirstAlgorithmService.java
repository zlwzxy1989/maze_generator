package application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import application.core.MazePoint;

public class DepthFirstAlgorithmService extends MazeBaseAlgorithmService {

  public DepthFirstAlgorithmService() {
    super();
  }

  public DepthFirstAlgorithmService(MazePoint[][] mazePoints) {
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

    // 探索起点となるマス 中身が空になったら終了
    List<MazePoint> buffer = new ArrayList<>();

    // 全部壁にする
    setAllToWall();

    //起点を道にする
    setToRoad(startPoint);
    //迷宮サイズの端数処理
    int widthFix = (maxWidth + 1) % 2;
    int heightFix = (maxHeight + 1) % 2;
    buffer.add(
        getPointByCoordinate(getRandomInt(maxWidth / 2) * 2 + widthFix, getRandomInt(maxHeight / 2) * 2 + heightFix));
    while (!buffer.isEmpty()) {
      // 深さ優先なので、最後に追加した候補を使う
      MazePoint currentPoint = buffer.get(buffer.size() - 1);
      buffer.remove(currentPoint);
      // 現在のマスから四方向探索し、2マス連続壁だった場合は進行候補にする
      List<MazePoint> nextPointList = getNextPointList(currentPoint);
      if (nextPointList.size() != 0) {
        // まだ複数あるならもう一回bufferに入れる
        if (nextPointList.size() >= 1) {
          buffer.add(currentPoint);
        }
        // ランダムで一つのルートを選ぶ
        MazePoint next = nextPointList.get(getRandomInt(nextPointList.size()));
        //current->nextの道を作る
        setToRoad(currentPoint);
        setToRoad(next);
        MazePoint middle = getPointByCoordinate((currentPoint.getX() + next.getX()) / 2,
            (currentPoint.getY() + next.getY()) / 2);
        setToRoad(middle);
        // 次はnextから探索
        buffer.add(next);
      }
    }
    // 終点を道にする
    setToRoad(endPoint);
    // 起点と終点が壁で囲まれた場合、ランダムで壁を一つ壊す
    Set<MazePoint> startPointNeighbours = getNeighbourPoints(startPoint);
    List<MazePoint> startPointNeighboursList = new ArrayList<>();
    startPointNeighboursList.addAll(startPointNeighbours);
    if (isWall(startPointNeighboursList)) {
      for (MazePoint p : startPointNeighbours) {
        setToRoad(p);
        break;
      }
    }
    Set<MazePoint> endPointNeighbours = getNeighbourPoints(endPoint);
    List<MazePoint> endPointNeighboursList = new ArrayList<>();
    endPointNeighboursList.addAll(endPointNeighbours);
    if (isWall(endPointNeighboursList)) {
      for (MazePoint p : endPointNeighbours) {
        setToRoad(p);
        break;
      }
    }

  }

  private List<MazePoint> getNextPointList(MazePoint p) {
    List<MazePoint> ret = new ArrayList<>();
    int x = p.getX();
    int y = p.getY();
    // 四方向で壁を壊せるかどうかをチェック
    // 右へ
    if (isWall(x + 1, y) && isWall(x + 2, y)) {
      ret.add(getPointByCoordinate(x + 2, y));
    }
    // 左へ
    if (isWall(x - 1, y) && isWall(x - 2, y)) {
      ret.add(getPointByCoordinate(x - 2, y));
    }
    // 下へ
    if (isWall(x, y + 1) && isWall(x, y + 2)) {
      ret.add(getPointByCoordinate(x, y + 2));
    }
    // 上へ
    if (isWall(x, y - 1) && isWall(x, y - 2)) {
      ret.add(getPointByCoordinate(x, y - 2));
    }
    return ret;
  }
}
