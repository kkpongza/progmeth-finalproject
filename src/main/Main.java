package main;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Main extends Application {

	private static final long DURATION_SECONDS = 6;

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
		title.setTranslateX(1280 / 2 - title.getLayoutBounds().getWidth() / 2);
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
		tilePane = populateGrid();
		root.getChildren().set(0, tilePane);
		timerThread.schedule(() -> {
			tilePane.getChildren()
					.stream()
					.map(n -> (TileView) n)
					.forEach(TileView::hide);

		}, DURATION_SECONDS, TimeUnit.SECONDS);
	}

	private Pane populateGrid(){
		var pane = new Pane();
		pane.setPrefSize(1280, 720);

		Random random = new Random();

		List<Point2D> usePoints = new ArrayList<>();
		for(int i = 1; i <= 5; i++){
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
					System.out.println("You Won");
					return;
				}

				var correctTile = tileSequence.remove(0);
				if(tile == correctTile){
					tile.show();
				}else {
					tileSequence.clear();
					System.out.println("Game over");

					Platform.exit();
					System.exit(0);

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
