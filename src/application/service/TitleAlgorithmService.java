package application.service;

import application.core.MazeGenerator;
import application.core.MazePoint;

// タイトル画面を生成するための固定迷宮サービス
public class TitleAlgorithmService extends MazeGenerator {
  public TitleAlgorithmService() {
    super();
  }

  public TitleAlgorithmService(MazePoint[][] mazePoints, boolean showAnime) {
    super(mazePoints, showAnime);
  }

  @Override
  public void generate() {

    setAllToWall();

    setColToRoad(13);
    setRowToRoad(14);

    //M
    setColToRoad(1, 1, 12);
    setColToRoad(11, 1, 12);
    setSlashToRoad(2, 2, 6, 6);
    setSlashToRoad(2, 3, 6, 7);
    setSlashToRoad(7, 5, 10, 2);
    setSlashToRoad(7, 6, 10, 3);

    //A
    setSlashToRoad(15, 6, 20, 1);
    setSlashToRoad(15, 7, 20, 2);
    setSlashToRoad(20, 1, 25, 6);
    setSlashToRoad(20, 2, 25, 7);
    setColToRoad(15, 6, 12);
    setColToRoad(25, 6, 12);
    setRowToRoad(7, 15, 25);

    //Z
    setRowToRoad(16, 1, 11);
    setSlashToRoad(1, 26, 10, 17);
    setSlashToRoad(2, 26, 11, 17);
    setRowToRoad(27, 1, 11);
    //E
    setColToRoad(15, 16, 27);
    setRowToRoad(16, 16, 25);
    setRowToRoad(21, 16, 25);
    setRowToRoad(27, 16, 25);

  }

}
