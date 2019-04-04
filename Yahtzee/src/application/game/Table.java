package application.game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Table extends Group{
	private double baseHeight = 390;
	private double baseWidth = 450;
	private StackPane tableView;
	private GridPane diceGrid;
	private Game game;
	private VBox container;
	private ArrayList<Dice> fieldDice;
	private ImageView tablePic;
	
	public Table(Game g) throws FileNotFoundException {
		game = g;
		container = new VBox(10);
		fieldDice = new ArrayList<Dice>();
		Image scores = new Image(new FileInputStream("src/res/pics/table.png"));
		tablePic = new ImageView(scores);
		tablePic.setFitHeight(baseHeight * game.scaleFactorY);
		tablePic.setFitWidth(baseWidth * game.scaleFactorX);
		
		tableView = new StackPane();
		
		diceGrid = new GridPane();
		diceGrid.setHgap(5);
		diceGrid.setVgap(5);
		
		tableView.getChildren().add(tablePic);
		tableView.getChildren().add(diceGrid);
	
		container.getChildren().add(tableView);
		this.getChildren().add(container);
		
		rescaleSizes();
	
	}
	
	protected void rescaleSizes() {
		this.setTranslateX(-380 * game.scaleFactorX);
		this.setTranslateY(-35 * game.scaleFactorY);
		
		for(Dice d : fieldDice)
			d.rescaleSizes();
		
		tablePic.setFitHeight(baseHeight * game.scaleFactorY);
		tablePic.setFitWidth(baseWidth * game.scaleFactorX);
		
	}
	
	protected void renderDice() {
		
	}
	
	protected ArrayList<Dice> getDice() {
		return fieldDice;
	}
	
	protected void addDice(Dice d) {
		d.setBaseHeight(80);
		d.setBaseWidth(80);
		fieldDice.add(d);
		diceGrid.setPadding(new Insets(25*game.scaleFactorY, 0, 0, (tablePic.getFitWidth()/2.3)-(d.getWidth()*(fieldDice.size()-1))));
		diceGrid.add(d, fieldDice.indexOf(d), 0);
	
	}
	
	protected void removeDice(Dice d) {
		d.setBaseHeight(100);
		d.setBaseWidth(100);
		fieldDice.remove(d);
		diceGrid.getChildren().remove(d);
	}
	
	
}
