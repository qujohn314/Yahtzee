package application.game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Table extends Group{
	private double baseHeight = 350;
	private double baseWidth = 448;
	private StackPane tableView;
	private TilePane diceGrid;
	private Game game;
	private VBox rollBox;
	protected ArrayList<Dice> fieldDice;
	private ImageView tablePic;
	
	public Table(Game g) throws FileNotFoundException {
		game = g;
		
		rollBox = new VBox(10);
		
		fieldDice = new ArrayList<Dice>();
		Image scores = new Image(new FileInputStream("src/res/pics/table.png"));
		tablePic = new ImageView(scores);
		tablePic.setFitHeight(baseHeight * game.scaleFactorY);
		tablePic.setFitWidth(baseWidth * game.scaleFactorX);
		
		tableView = new StackPane();
		
		diceGrid = new TilePane();
		diceGrid.setHgap(6*game.scaleFactorX);
		diceGrid.setVgap(6*game.scaleFactorY);
		diceGrid.setPrefColumns(5);
		
		
		rollBox.getChildren().add(diceGrid);
		
		
		tableView.getChildren().add(tablePic);
		tableView.getChildren().add(rollBox);
	
		//diceGrid.setStyle("-fx-grid-lines-visible: true;");
		this.getChildren().add(tableView);
	
		
		
		rescaleSizes();
	}
	
	protected void rescaleSizes() {
		

		for(Dice d : fieldDice)
			d.rescaleSizes(game.scaleFactorX,game.scaleFactorX);
		
		tablePic.setFitHeight(baseHeight * game.scaleFactorY);
		tablePic.setFitWidth(baseWidth * game.scaleFactorX);
		diceGrid.setPadding(new Insets(10*game.scaleFactorY, 0, 0, 24*game.scaleFactorX));
		diceGrid.setHgap(6*game.scaleFactorX);
		diceGrid.setVgap(6*game.scaleFactorY);
	}
	
	protected void renderDice() {
		ArrayList<Node> addDice = new ArrayList<Node>();
		for(Node d : diceGrid.getChildren()) {
			if(d instanceof Dice) {

			addDice.add(d);
			}
		}
		diceGrid.getChildren().removeAll(addDice);
		for(Node d : addDice) {
			if(d instanceof Dice) {
				//Y RANGE: 18-30
				//X RANGE: 12-50
				diceGrid.getChildren().add(d);
			}
		}

	}
	
	protected ArrayList<Dice> getDice() {
		return fieldDice;
	}
	
	protected void addDice(Dice d) {
		d.setBaseHeight(75,game.scaleFactorX,game.scaleFactorX);
		d.setBaseWidth(75,game.scaleFactorX,game.scaleFactorX);
		fieldDice.add(d);

		diceGrid.setPadding(new Insets(10*game.scaleFactorY, 0, 0, 24*game.scaleFactorX));
		diceGrid.getChildren().add(d);
		
	}
	
	protected void removeDice(Dice d) {
		d.setBaseHeight(100,game.scaleFactorX,game.scaleFactorY);
		d.setBaseWidth(100,game.scaleFactorX,game.scaleFactorY);
		fieldDice.remove(d);
		diceGrid.getChildren().remove(d);
		
	}
	
	
}
