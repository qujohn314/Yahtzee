package application;
	
import java.awt.Toolkit;
import java.io.FileInputStream;

import application.game.Game;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;



public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Game game = new Game();
			
			//System.out.println(Toolkit.getDefaultToolkit().getScreenSize().getWidth()+" "+Toolkit.getDefaultToolkit().getScreenSize().getHeight());
			Scene scene = new Scene(game,800,600);
			
			
			game.init(scene);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Quincy's Yahtzee");
			primaryStage.show();
			
			primaryStage.setMaximized(true);
			
			primaryStage.setResizable(false);
			
			
			primaryStage.getIcons().add(new Image(new FileInputStream("src/res/pics/dice5.png")));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static void main(String[] args) {
		launch(args);
	}
}


