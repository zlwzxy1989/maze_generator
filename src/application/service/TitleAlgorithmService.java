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

    // maze文字
    if (maxWidth == 27 && maxHeight == 29) {

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
    } else if (maxWidth == 35 && maxHeight == 25){
      setRowToRoad(1,10,11);
      setRowToRoad(1,23,24);

      setToRoad(9,2);
      setToRoad(12,2);
      setToRoad(22,2);
      setToRoad(25,2);
      setRowToRoad(2,15,19);

      setToRoad(8,3);
      setToRoad(26,3);
      setRowToRoad(3,13,14);
      setRowToRoad(3,20,21);

      setToRoad(7,4);
      setToRoad(12,4);
      setToRoad(22,4);
      setToRoad(27,4);

      setRowToRoad(5,6,8);
      setRowToRoad(5,13,21);
      setRowToRoad(5,26,28);
      setToRoad(11,5);
      setToRoad(23,5);

      setToRoad(6,6);
      setRowToRoad(6,9,11);
      setRowToRoad(6,23,25);
      setToRoad(28,6);

      setSlashToRoad(4,9,6,7);
      setSlashToRoad(8,9,10,7);
      setSlashToRoad(24,7,27,10);
      setSlashToRoad(28,7,30,9);

      setColToRoad(10,6,16);
      setColToRoad(24,6,16);
      //setColToRoad(14,7,9);
      //setColToRoad(20,7,9);

      setRowToRoad(10,4,7);
      setRowToRoad(11,5,7);
      setRowToRoad(12,5,7);
      setRowToRoad(10,27,30);
      setRowToRoad(11,27,29);
      setRowToRoad(12,27,29);

      setColToRoad(3,11,12);
      setColToRoad(2,13,14);
      setColToRoad(31,11,12);
      setColToRoad(32,13,14);

      //setToRoad(15,11);
      //setToRoad(19,11);
      //setRowToRoad(12,16,18);

      setSlashToRoad(9,11,16,18);
      setSlashToRoad(19,17,25,11);
      setSlashToRoad(7,14,9,12);
      setSlashToRoad(25,12,27,14);
      setSlashToRoad(14,14,15,15);
      setSlashToRoad(15,14,17,16);
      setSlashToRoad(17,16,19,14);
      setSlashToRoad(19,15,20,14);
      setToRoad(11,12);
      setToRoad(23,12);

      setColToRoad(1,15,19);
      setColToRoad(3,15,19);
      setColToRoad(31,15,19);
      setColToRoad(33,15,19);

      setSlashToRoad(6,16,7,15);
      setSlashToRoad(6,17,7,18);
      setSlashToRoad(8,18,9,17);
      setSlashToRoad(7,19,9,21);
      setSlashToRoad(2,20,4,22);
      setSlashToRoad(4,20,6,22);
      setRowToRoad(22,4,8);
      setToRoad(15,18);

      setSlashToRoad(27,15,28,16);
      setSlashToRoad(25,17,27,19);
      setSlashToRoad(27,18,28,17);
      setSlashToRoad(25,21,27,19);
      setSlashToRoad(29,21,30,20);
      setSlashToRoad(31,21,32,20);
      setRowToRoad(22,26,30);

      setSlashToRoad(14,19,16,21);
      setSlashToRoad(13,17,15,19);
      setSlashToRoad(12,18,14,20);
      setToRoad(12,19);
      setSlashToRoad(21,17,22,18);
      setRowToRoad(18,19,20);
      setRowToRoad(19,19,22);
      setRowToRoad(20,13,21);

      setColToRoad(16,21,23);
      setColToRoad(18,21,23);
    }

  }

}
