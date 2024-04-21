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
        updateLifeText();

//        lifeText = new Text("Remaining Life: " + remainingLife);
//        lifeText.setFont(Font.font(32)); // Font size of 32
//        lifeText.setTranslateX(1280 / 2 - lifeText.getLayoutBounds().getWidth() / 2);
//        lifeText.setTranslateY(720 / 2 + 100); // Position it below the best score text
//        this.getChildren().add(lifeText);

        // Add continue button
        continueButton = new Button("Continue");
        continueButton.setOnAction(e -> continueGame());
        continueButton.setPrefSize(120, 50);
        continueButton.setTranslateX(1280 / 2 - 60); // Centered below best score text
        continueButton.setTranslateY(720 / 2 + 150); // Below best score text
        continueButton.setStyle("-fx-background-color: #000000; -fx-text-fill: #ffffff; -fx-font-size: 20px; -fx-font-weight: bold;");
        this.getChildren().add(continueButton);

        // Add restart button
        restartButton = new Button("Restart");
        restartButton.setOnAction(e -> restartGame());
        restartButton.setPrefSize(120, 50);
        restartButton.setTranslateX(continueButton.getTranslateX() - 140); // Left of continue button
        restartButton.setTranslateY(continueButton.getTranslateY());
        restartButton.setStyle("-fx-background-color: #000000; -fx-text-fill: #ffffff; -fx-font-size: 20px; -fx-font-weight: bold;");
        this.getChildren().add(restartButton);

        // Add exit button
        exitButton = new Button("Exit");
        exitButton.setOnAction(e -> exitGame());
        exitButton.setPrefSize(120, 50);
        exitButton.setTranslateX(continueButton.getTranslateX() + 140); // Right of continue button
        exitButton.setTranslateY(continueButton.getTranslateY());
        exitButton.setStyle("-fx-background-color: #000000; -fx-text-fill: #ffffff; -fx-font-size: 20px; -fx-font-weight: bold;");
        this.getChildren().add(exitButton);
    }

    private void restartGame() {
        // Implement the logic to restart the game
        RootPane.getRootPane().getChildren().clear();
        RootPane rootPane = new RootPane();
        RootPane.getRootPane().getChildren().add(rootPane);
    }

    private void continueGame(){
        RootPane.getRootPane().getChildren().clear();
        PlayPane playPane = new PlayPane(level);
        RootPane.getRootPane().getChildren().add(playPane);
    }

    private void exitGame() {
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
