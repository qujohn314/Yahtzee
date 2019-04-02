package application.game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class Table extends Group{
	private double baseHeight = 375;
	private double baseWidth = 450;
	private ImageView tableView;
	private Game game;
	private VBox container;
	private ArrayList<Dice> fieldDice;
	
	public Table(Game g) throws FileNotFoundException {
		game = g;
		container = new VBox(10);
		
		fieldDice = new ArrayList<Dice>();
		Image scores = new Image(new FileInputStream("src/res/pics/table.png"));
			tableView = new ImageView(scores);
		tableView.setFitHeight(baseHeight);
		
		tableView.setFitWidth(baseWidth);
		tableView.setPreserveRatio(true);
		container.getChildren().add(tableView);
		this.getChildren().add(container);
		rescaleSizes();
	}
	
	protected void rescaleSizes() {
		this.setTranslateX(-380 * game.scaleFactorX);
		this.setTranslateY(-35 * game.scaleFactorY);
		tableView.setFitHeight(baseHeight * game.scaleFactorY);
		tableView.setFitWidth(baseWidth * game.scaleFactorX);
	}
	
	protected void renderDice() {
		
	}
	
	protected void addDice(Dice d) {
		fieldDice.add(d);
		this.getChildren().add(d);
	}
	
	
}
