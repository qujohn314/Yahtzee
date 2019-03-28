package application.game;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;

public class Game extends BorderPane{
	private Scene scene;
	private static final String BACKGROUND = "background.jpg";
	public void init(Scene s) {
		
		scene = s;
		Image background = null;
		try {
			background = new Image(new FileInputStream("src/res/pics/" + BACKGROUND));
		} catch (FileNotFoundException e) {
			System.out.println("Absolute File Path not found! Checking relative");
			try {
				background = new Image(new FileInputStream("/src/res/pics/" + BACKGROUND));
			} catch (FileNotFoundException e1) {}
			e.printStackTrace();
		}
		
		this.setBackground(new Background(new BackgroundImage(background,null,null,null,null)));
		
	}
}
