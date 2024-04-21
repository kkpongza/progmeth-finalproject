package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Pane.RootPane;


public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		RootPane rootPane = RootPane.getRootPane();
		primaryStage.setTitle("Memory Game");
		primaryStage.setScene(new Scene(rootPane));
		primaryStage.show();
	}


	public static void main(String[] args) {
		launch(args);
	}
}
