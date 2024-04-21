package Pane;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class WinPane extends Pane {
    RootPane rootPane;

    private int level;

    public WinPane(int numTiles) {
        level = numTiles;
        var text = new Text("Current Level : " + level);
        text.setFont(Font.font(64));
        text.setTranslateX(1280 / 2 - text.getLayoutBounds().getWidth() / 2);
        text.setTranslateY(720 / 2 - text.getLayoutBounds().getHeight() / 2);
        this.getChildren().add(text);
        System.out.println("You Won");


        var continueButton = new Button("continue");
        var ExitButton = new Button("Exit");
        ExitButton.setOnAction(e -> {
            handleExitButton();
        });
        ExitButton.setPrefSize(120, 50);
        ExitButton.setTranslateX(1280 / 2 - 50);
        ExitButton.setTranslateY(720 / 2 + 100);
        ExitButton.setStyle("-fx-background-color: #000000; -fx-text-fill: #ffffff; -fx-font-size: 20px; -fx-font-weight: bold;");
        this.getChildren().add(ExitButton);

        continueButton.setOnAction(e -> {
            handleContinueButton();
        });

        continueButton.setPrefSize(120, 50);
        continueButton.setTranslateX(1280 / 2 - 50);
        continueButton.setTranslateY(720 / 2 + 50);
        continueButton.setStyle("-fx-background-color: #000000; -fx-text-fill: #ffffff; -fx-font-size: 20px; -fx-font-weight: bold;");
        this.getChildren().add(continueButton);

    }

    private void handleContinueButton(){
        PlayPane playPane = new PlayPane(level + 1);
        RootPane.getRootPane().getChildren().clear();
        RootPane.getRootPane().getChildren().add(playPane);
    }

    private void handleExitButton(){
        RootPane.getRootPane().getChildren().clear();
        RootPane rootPane = new RootPane();
        RootPane.getRootPane().getChildren().add(rootPane);
    }


}
