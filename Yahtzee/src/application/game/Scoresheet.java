package application.game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class Scoresheet extends Group{
	private Game game;
	private ImageView scoreView;
	private VBox container;
	private double baseHeight = 590;
	private double baseWidth = 200;
	
	public Scoresheet(Game g) throws FileNotFoundException {
		game = g;
		container = new VBox(10);
		
		
		Image scores = new Image(new FileInputStream("src/res/pics/scoresheet.png"));
			scoreView = new ImageView(scores);
		scoreView.setFitHeight(baseHeight);
		
		scoreView.setFitWidth(baseWidth);
		container.getChildren().add(scoreView);
		this.getChildren().add(container);
		rescaleSizes();
	}
	
	protected void rescaleSizes() {
		this.setTranslateX(-355 * game.scaleFactorX);
		this.setTranslateY(4 * game.scaleFactorY);
		scoreView.setFitHeight(baseHeight * game.scaleFactorY);
		scoreView.setFitWidth(baseWidth * game.scaleFactorX);
	}
}
