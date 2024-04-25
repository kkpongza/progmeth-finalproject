package Pane;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class RootPane extends VBox {
    private static RootPane instance;
    private int numTiles = 3;
    private int life  = 3;
    private Text lifeText;
    public void playClick(){
        new Thread(() -> {
            Sound sound = new Sound("src/music/maincc.MP3");
            sound.setVolume(20);
            sound.play();
        }).start();
    }
    public RootPane(){
        this.setPrefSize(1280, 720+100);
        var title = new Text("Memory Game");
        title.setFont(Font.font(64));
        title.setTranslateX((double) 1280 / 2 - title.getLayoutBounds().getWidth() / 2);
        title.setTranslateY(50);

        // add title to the root
        this.getChildren().add(title);

        // Create a Text element to display the number of lives
//        lifeText = new Text("Lives: " + life);
//        lifeText.setFont(Font.font(20)); // Set the font size to 20
//        lifeText.setTranslateX(10); // Position it on the left side
//        lifeText.setTranslateY(80); // Position it below the title
//        this.getChildren().add(lifeText);
        updateLifeText();

        //add this button in Goto class
        var button = new Button("Start");
        button.setOnAction(e -> {
            startGame();
            playClick();});
        button.setPrefSize(100, 50);
        button.setTranslateX(1280 / 2 - 50);
        button.setTranslateY(720 / 2 - 100);
        button.setStyle("-fx-background-color: #000000; -fx-text-fill: #ffffff; -fx-font-size: 20px; -fx-font-weight: bold;");


        this.getChildren().addAll(new Pane(), button);
    }

    public static RootPane getRootPane(){
        if(instance == null) {
            instance = new RootPane();
        }
        return instance;
    }

    private void clear(){
        if(this.getChildren().isEmpty() || this.getChildren().size() == 1){

        }else{
            this.getChildren().remove(1,this.getChildren().size());
        }
    }

    private void startGame(){
        PlayPane playPane = new PlayPane(numTiles);
        instance.clear();
        instance.getChildren().set(0, playPane);
        numTiles++;
    }
    private void updateLifeText() {
        if (lifeText != null) {
            // Remove the previous life text if it exists
            this.getChildren().remove(lifeText);
        }

        // Display the remaining lives as hearts
        String hearts = "\u2764".repeat(life);
        lifeText = new Text("Lives: " + hearts);
        lifeText.setFont(Font.font(32)); // Set the font size to 20

        lifeText.setTranslateX((double) 1280 / 2 - lifeText.getLayoutBounds().getWidth() / 2); // Position it on the left side
        lifeText.setTranslateY(100); // Position it below the title

        // Add the life text to the pane
        this.getChildren().add(lifeText);
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }
}
