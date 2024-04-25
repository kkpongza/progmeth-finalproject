package Pane;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GameOverPane extends Pane {
    private Text gameOverText;
    private Text bestScoreText;
    private Button continueButton;
    private Button restartButton;
    private Button exitButton;
    private Text lifeText;
    private int level;
    private int remainingLife;



    public void playClick(){
        new Thread(() -> {
            Sound sound = new Sound("src/music/maincc.MP3");
            sound.setVolume(20);
            sound.play();
        }).start();
    }
    public GameOverPane(int numTiles) {
        level = numTiles;
        // Set up the game over text
        gameOverText = new Text("Game Over");
        gameOverText.setFont(Font.font(64));
        gameOverText.setTranslateX(1280 / 2 - gameOverText.getLayoutBounds().getWidth() / 2);
        gameOverText.setTranslateY(720 / 2 - gameOverText.getLayoutBounds().getHeight() / 2);
        this.getChildren().add(gameOverText);

        // Set up the best score text
        bestScoreText = new Text("Best Score: " + numTiles);
        bestScoreText.setFont(Font.font(32)); // Font size of 32
        bestScoreText.setTranslateX(1280 / 2 - bestScoreText.getLayoutBounds().getWidth() / 2);
        bestScoreText.setTranslateY(720 / 2 + 50); // Position it below the game over text
        this.getChildren().add(bestScoreText);

        remainingLife = RootPane.getRootPane().getLife() - 1;
        RootPane.getRootPane().setLife(remainingLife);
        if(remainingLife != 0){
            updateLifeText();
        }else {
            Text text = new Text("You're dead!");
            text.setFont(Font.font(32)); // Font size of 32
            text.setTranslateX(1280 / 2 - text.getLayoutBounds().getWidth() / 2);
            text.setTranslateY(720 / 2 + 125); // Position it below the game over text
            this.getChildren().add(text);
        }

        // Add restart button
        restartButton = new Button("Restart");
        restartButton.setOnAction(e -> {
//            playClick();
            restartGame();

        });
        restartButton.setPrefSize(120, 50);
        restartButton.setTranslateX(1280 / 2 - 60); // Left of continue button
        restartButton.setTranslateY(720 / 2 + 170);
        restartButton.setStyle("-fx-background-color: #000000; -fx-text-fill: #ffffff; -fx-font-size: 20px; -fx-font-weight: bold;");
        this.getChildren().add(restartButton);


        // Add continue button
        if(remainingLife != 0){
            continueButton = new Button("Continue");
            continueButton.setOnAction(e -> {

                continueGame();
            });
            continueButton.setPrefSize(120, 50);
            continueButton.setTranslateX(restartButton.getTranslateX() - 140); // Centered below best score text
            continueButton.setTranslateY(restartButton.getTranslateY()); // Below best score text
            continueButton.setStyle("-fx-background-color: #000000; -fx-text-fill: #ffffff; -fx-font-size: 20px; -fx-font-weight: bold;");
            this.getChildren().add(continueButton);
        }


        // Add exit button
        exitButton = new Button("Exit");
        exitButton.setOnAction(e -> {
//            playClick();
            restartGame();

        });
        exitButton.setPrefSize(120, 50);
        exitButton.setTranslateX(remainingLife != 0? restartButton.getTranslateX() + 140 : restartButton.getTranslateX()); // Right of continue button
        exitButton.setTranslateY(remainingLife != 0? restartButton.getTranslateY() : restartButton.getTranslateY() +75);
        exitButton.setStyle("-fx-background-color: #000000; -fx-text-fill: #ffffff; -fx-font-size: 20px; -fx-font-weight: bold;");
        this.getChildren().add(exitButton);
    }

    private void restartGame() {
        playClick();
        // Implement the logic to restart the game
        RootPane.getRootPane().getChildren().clear();
        RootPane rootPane = new RootPane();
        RootPane.getRootPane().getChildren().add(rootPane);
    }

    private void continueGame(){
        playClick();
        RootPane.getRootPane().getChildren().clear();
        PlayPane playPane = new PlayPane(level);
        RootPane.getRootPane().getChildren().add(playPane);
    }

    private void exitGame() {
        playClick();
        Platform.exit();
        System.exit(0);
    }

    private void updateLifeText() {
        if (lifeText != null) {
            // Remove the previous life text if it exists
            this.getChildren().remove(lifeText);
        }

        // Display the remaining lives as hearts
        String hearts = "\u2764".repeat(remainingLife);
        lifeText = new Text("Lives: " + hearts);
        lifeText.setFont(Font.font(32)); // Set the font size to 20

        lifeText.setTranslateX(1280 / 2 - lifeText.getLayoutBounds().getWidth() / 2);
        lifeText.setTranslateY(720 / 2 + 100);
        // Add the life text to the pane
        this.getChildren().add(lifeText);
    }
}
