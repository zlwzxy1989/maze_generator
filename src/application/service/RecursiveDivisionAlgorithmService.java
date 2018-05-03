package application.service;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import application.Utils.CommonUtil;
import application.core.MazeGenerator;
import application.core.MazePoint;

public class RecursiveDivisionAlgorithmService extends MazeGenerator {

  public RecursiveDivisionAlgorithmService() {
    super();
  }

  public RecursiveDivisionAlgorithmService(MazePoint[][] mazePoints) {
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
    // 道からスタート
    setAllToRoad();
    // マップを壁に包まれる状態でスタート
    //マップの高さ・幅が奇数の迷宮と想定 偶数の場合は境界を壁にする
    int initBeginX = -1;
    int initEndX = maxWidth;
    int initBeginY = -1;
    int initEndY = maxHeight;
    if (maxHeight % 2 == 0) {
      initBeginY++;
    }
    if (maxWidth % 2 == 0) {
      initBeginX++;
    }
    // 最初の外壁を作る
    setRowToWall(initBeginY);
    setColToWall(initBeginX);
    // 起点の隣のマスを空けておく
    getNeighbourPoints(startPoint).forEach((p) -> setToRoad(p));

    ForkJoinPool pool = new ForkJoinPool();
    pool.invoke(new DivisionAction(mazePoints, initBeginX, initEndX, initBeginY, initEndY));
    // 最後に出口と隣接マス調整
    setToRoad(startPoint);
    setToRoad(endPoint);
    if (maxHeight % 2 == 0) {
      setToRoad(endPoint.getX() - 1, endPoint.getY());
    }
    if (maxWidth % 2 == 0) {
      setToRoad(endPoint.getX(), endPoint.getY() - 1);
    }
  }

  private class DivisionAction extends RecursiveAction {

    private MazePoint[][] mazePoints;
    private int beginX;
    private int endX;
    private int beginY;
    private int endY;

    public DivisionAction(MazePoint[][] mazePoints, int beginX, int endX, int beginY, int endY) {
      this.mazePoints = mazePoints;
      this.beginX = beginX;
      this.endX = endX;
      this.beginY = beginY;
      this.endY = endY;
    }

    @Override
    protected void compute() {
      // 通路の幅が1マスしかないなら、分割をやめる
      if (endX - beginX <= 2 || endY - beginY <= 2) {
        return;
      }
      // 水平、垂直分割線の座標を決める 道を残すように、前回との座標の差は2の倍数にする
      int divisionX = beginX + (CommonUtil.getRandomInt((endX - beginX - 2) / 2) + 1) * 2;
      int divisionY = beginY + (CommonUtil.getRandomInt((endY - beginY - 2) / 2) + 1) * 2;

      int leftUpBeginX = beginX;
      int leftUpEndX = divisionX;
      // yの座標は下に行くほど大きい
      int leftUpBeginY = beginY;
      int leftUpEndY = divisionY;

      int leftDownBeginX = beginX;
      int leftDownEndX = divisionX;
      int leftDownBeginY = divisionY;
      int leftDownEndY = endY;

      int rightUpBeginX = divisionX;
      int rightUpEndX = endX;
      int rightUpBeginY = beginY;
      int rightUpEndY = divisionY;

      int rightDownBeginX = divisionX;
      int rightDownEndX = endX;
      int rightDownBeginY = divisionY;
      int rightDownEndY = endY;

      int[][] wallLines = {
          { beginX, divisionX, divisionY, divisionY },
          { divisionX, endX, divisionY, divisionY },
          { divisionX, divisionX, beginY, divisionY },
          { divisionX, divisionX, divisionY, endY }
      };
      //壁の線を引く
      for (int x = beginX + 1; x < endX; x++) {
        setToWall(x, divisionY);
      }
      for (int y = beginY + 1; y < endY; y++) {
        setToWall(divisionX, y);
      }
      //ランダムで壁分割線の四つ中の三つに道を作る
      int randomInt = CommonUtil.getRandomInt(wallLines.length);
      for (int i = 0; i < wallLines.length; i++) {
        if (i == randomInt) {
          continue;
        }
        // 後で生成された壁に道を塞がれないよう 壁候補となるマスは対象から外す
        setToRoad(
            wallLines[i][0] == wallLines[i][1] ? wallLines[i][0]
                : wallLines[i][0] + CommonUtil.getRandomInt((wallLines[i][1] - wallLines[i][0] - 1) / 2) * 2 + 1,
            wallLines[i][2] == wallLines[i][3] ? wallLines[i][2]
                : wallLines[i][2] + CommonUtil.getRandomInt((wallLines[i][3] - wallLines[i][2] - 1) / 2) * 2 + 1);
      }

      // 次へGO
      DivisionAction leftUp = new DivisionAction(mazePoints, leftUpBeginX, leftUpEndX, leftUpBeginY,
          leftUpEndY);
      DivisionAction leftDown = new DivisionAction(mazePoints, leftDownBeginX, leftDownEndX, leftDownBeginY,
          leftDownEndY);
      DivisionAction rightUp = new DivisionAction(mazePoints, rightUpBeginX, rightUpEndX, rightUpBeginY,
          rightUpEndY);
      DivisionAction rightDown = new DivisionAction(mazePoints, rightDownBeginX, rightDownEndX, rightDownBeginY,
          rightDownEndY);

      leftUp.fork();
      leftDown.fork();
      rightUp.fork();
      rightDown.fork();
    }

  }
}
