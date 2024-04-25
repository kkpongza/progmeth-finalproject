package Pane;


import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayPane extends Pane {
    private List<TileView> tileSequence = new ArrayList<>();

    private int sequence = 1;

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

                new Thread(() -> {
                    Sound sound = new Sound("src/music/0425.MP3");
                    sound.setVolume(20);
                    sound.play();
                }).start();

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
                    //Player lose the game
                    RootPane.getRootPane().getChildren().clear();
                    GameOverPane gameOverPane = new GameOverPane(numTiles);
                    RootPane.getRootPane().getChildren().add(gameOverPane);
                    tileSequence.clear();
                    System.out.println("Game over");
                    System.out.println("Best score: " + numTiles);
                }
            });

            this.getChildren().add(tile);
            tileSequence.add(tile);

        }
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
}
