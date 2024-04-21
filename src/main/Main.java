package main;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Main extends Application {

	private static final long DURATION_SECONDS = 6;
	private int numTiles = 3; // Initial number of tiles
	private int lives = 3; // Initial number of lives

	private ScheduledExecutorService timerThread
			= Executors.newSingleThreadScheduledExecutor();

	private VBox root;
	private Pane tilePane;
	private List<TileView> tileSequence = new ArrayList<>();

	@Override
	public void start(Stage primaryStage) throws Exception {
		//create interesting UI for thid game
		primaryStage.setTitle("Memory Game");
		primaryStage.setScene(new Scene(createContent()));
		primaryStage.show();

	}

	@Override
	public void stop() throws Exception {
		timerThread.shutdownNow();
	}

	private Parent createContent() {
		root = new VBox();
		root.setPrefSize(1280, 720+100);
		// create title for the game
		var title = new Text("Memory Game");
		title.setFont(Font.font(64));
		title.setTranslateX((double) 1280 / 2 - title.getLayoutBounds().getWidth() / 2);
		title.setTranslateY(50);

		// add title to the root
		root.getChildren().add(title);


		var button = new Button("Start");
		button.setOnAction(e -> startGame());
		// make start button more UI friendly
		button.setPrefSize(100, 50);
		button.setTranslateX(1280 / 2 - 50);
		button.setTranslateY(720 / 2 - 25);
		// make button more beautiful
		button.setStyle("-fx-background-color: #000000; -fx-text-fill: #ffffff; -fx-font-size: 20px; -fx-font-weight: bold;");


		root.getChildren().addAll(new Pane(), button);

		return root;
	}






	private void startGame(){
		tilePane = populateGrid(numTiles);
		root.getChildren().set(0, tilePane);





		// Increase the number of tiles for the next round
		numTiles++;
	}


	private void checkGameOver() {
		if (tileSequence.isEmpty()) {
			// make Won page when player finish the game
			tilePane.getChildren().clear();
			var text = new Text("Current Level : " + (numTiles - 1));
			text.setFont(Font.font(64));
			text.setTranslateX(1280 / 2 - text.getLayoutBounds().getWidth() / 2);
			text.setTranslateY(720 / 2 - text.getLayoutBounds().getHeight() / 2);
			tilePane.getChildren().add(text);
			System.out.println("You Won");


			var newGameButton = new Button("continue");
			var ExitButton = new Button("Exit");
			ExitButton.setOnAction(e -> {
				Platform.exit();
				System.exit(0);
			});
			ExitButton.setPrefSize(120, 50);
			ExitButton.setTranslateX(1280 / 2 - 50);
			ExitButton.setTranslateY(720 / 2 + 100);
			ExitButton.setStyle("-fx-background-color: #000000; -fx-text-fill: #ffffff; -fx-font-size: 20px; -fx-font-weight: bold;");
			tilePane.getChildren().add(ExitButton);

			newGameButton.setOnAction(e -> {
				startGame();
			});
			newGameButton.setPrefSize(120, 50);
			newGameButton.setTranslateX(1280 / 2 - 50);
			newGameButton.setTranslateY(720 / 2 + 50);
			newGameButton.setStyle("-fx-background-color: #000000; -fx-text-fill: #ffffff; -fx-font-size: 20px; -fx-font-weight: bold;");
			tilePane.getChildren().add(newGameButton);

		}
	}



//	public Pane countDownPane() {
//		// Create a pane to hold the countdown components
//		Pane pane = new Pane();
//
//		// Create a label to display the countdown numbers
//		Label countdownLabel = new Label(DURATION_SECONDS+"");
//
//		countdownLabel.setStyle("-fx-font-size: 30px;"); // Style the label as desired
//		countdownLabel.setLayoutX(50);
//		countdownLabel.setLayoutY(50);
//
//		// Add the label to the pane
//		pane.getChildren().add(countdownLabel);
//
//		// Create a timeline for the countdown
//		Timeline countdown = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
//			// Get the current number from the label
//			int currentNumber = Integer.parseInt(countdownLabel.getText());
//
//			// Update the countdown label
//			if (currentNumber > 1) {
//				countdownLabel.setText(String.valueOf(currentNumber - 1));
//			} else {
//				countdownLabel.setText("GO!");
//				// Stop the timeline when the countdown reaches "GO!"
//
//				// You can add additional actions here when the countdown reaches zero
//			}
//		}));
//
//		// Set the cycle count to 3, so the timeline runs for 3 seconds (3 cycles)
//		countdown.setCycleCount(6);
//
//		// Start the countdown timeline
//		countdown.play();
//
//		// Return the pane with the countdown
//		return pane;
//	}



	private int sequence = 1;
	private Pane populateGrid(int numTiles){
		sequence = 1;
		var pane = new Pane();
		pane.setPrefSize(1280, 720);

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
				if(tileSequence.isEmpty()){
					// make Won page when player finish the game
					pane.getChildren().clear();
					var text = new Text("You Won");
					text.setFont(Font.font(64));
					text.setTranslateX(1280 / 2 - text.getLayoutBounds().getWidth() / 2);
					text.setTranslateY(720 / 2 - text.getLayoutBounds().getHeight() / 2);
					pane.getChildren().add(text);
					System.out.println("You Won");
					// Start a new game with more tiles
					startGame();
					return;
				}

				var correctTile = tileSequence.remove(0);
				if(tile == correctTile){
					if(sequence == 1){
						//hide all
						tilePane.getChildren()
								.stream()
								.map(n -> (TileView) n)
								.forEach(TileView::hide);
					}
					tile.show();
					sequence++;




					checkGameOver();
				}else {
					// make Game over page when player lose the game
					pane.getChildren().clear();
					var text = new Text("Game Over");
					var bestScore = new Text("Best score: " + numTiles);

					text.setFont(Font.font(64));
					text.setTranslateX(1280 / 2 - text.getLayoutBounds().getWidth() / 2);
					text.setTranslateY(720 / 2 - text.getLayoutBounds().getHeight() / 2);
					Pane showPane = new Pane(text, bestScore);
					pane.getChildren().add(showPane);



					tileSequence.clear();
					System.out.println("Game over");
					// show best score
					System.out.println("Best score: " + numTiles);
					// show game over page for 3 seconds then close the game
						timerThread.schedule(() -> {
						Platform.exit();
						System.exit(0);
					}, 3, TimeUnit.SECONDS);

				}
			});

			pane.getChildren().add(tile);
			tileSequence.add(tile);

		}

		return pane;
	}


	private static class TileView extends StackPane {

		private Text text;
		TileView(String content) {
			var border = new Rectangle(80, 80, null);
			border.setStroke(Color.BLACK);
			border.setStrokeWidth(4);
			border.setStrokeType(StrokeType.INSIDE);

			text = new Text(content);
			text.setFont(Font.font(64));

			getChildren().addAll(border, text);

			setPickOnBounds(true);
		}

		void hide() {
			text.setVisible(false);
		}

		void show(){
			text.setVisible(true);
		}
	}


	public static void main(String[] args) {
		launch(args);
	}
}
