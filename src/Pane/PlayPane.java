package Pane;

import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import main.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import Pane.TileView;

public class PlayPane extends Pane {
    private Pane tilePane;
    private List<TileView> tileSequence = new ArrayList<>();

    private int sequence = 1;

    private ScheduledExecutorService timerThread
            = Executors.newSingleThreadScheduledExecutor();

    public Pane getTilePane() {
        return tilePane;
    }

    public void setTilePane(Pane tilePane) {
        this.tilePane = tilePane;
    }

    public List<TileView> getTileSequence() {
        return tileSequence;
    }

    public void setTileSequence(List<TileView> tileSequence) {
        this.tileSequence = tileSequence;
    }


    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public ScheduledExecutorService getTimerThread() {
        return timerThread;
    }

    public void setTimerThread(ScheduledExecutorService timerThread) {
        this.timerThread = timerThread;
    }

    public PlayPane(int numTiles){

        this.setPrefSize(1280, 720);

        Random random = new Random();

        List<Point2D> usePoints = new ArrayList<>();

        for(int i = 1; i <= numTiles; i++){
            int randomX = random.nextInt(1280 / 80);
            int randomY = random.nextInt(720 / 80);

            Point2D p = new Point2D(randomX, randomY);

            while(usePoints.contains(p)){
                randomX = random.nextInt(1280 / 80);
                randomY = random.nextInt(720 / 80);

                p = new Point2D(randomX, randomY);
            }

            usePoints.add(p);

            var tile = new TileView(Integer.toString(i));
            tile.setTranslateX(randomX * 80);
            tile.setTranslateY(randomY * 80);
            tile.setOnMouseClicked(e -> {
                var correctTile = tileSequence.remove(0);

                if(tileSequence.isEmpty()){
                    // Win page when player finish the game
                    RootPane.getRootPane().getChildren().clear();
                    WinPane winPane = new WinPane(numTiles);
                    RootPane.getRootPane().getChildren().add(winPane);
                }


                if(tile == correctTile){
                    if(sequence == 1){
                        //hide all
                        this.getChildren()
                                .stream()
                                .map(n -> (TileView) n)
                                .forEach(TileView::hide);
                    }
                    tile.show();
                    sequence++;

                }
                else {
                    //player press the wrong button
                    // make Game over page when player lose the game
//                    this.getChildren().clear();
                    RootPane.getRootPane().getChildren().clear();

                    GameOverPane gameOverPane = new GameOverPane(numTiles);
//                    this.getChildren().add(gameOverPane);
                    RootPane.getRootPane().getChildren().add(gameOverPane);

                    tileSequence.clear();
                    System.out.println("Game over");
                    // show best score
                    System.out.println("Best score: " + numTiles);
                    // show game over page for 3 seconds then close the game
//                    timerThread.schedule(() -> {
//                        Platform.exit();
//                        System.exit(0);
//                    }, 3, TimeUnit.SECONDS);

                }
            });

            this.getChildren().add(tile);
            tileSequence.add(tile);

        }
    }
}
