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
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Table extends Group{
	private double baseHeight = 390;
	private double baseWidth = 448;
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
		diceGrid.setHgap(6*game.scaleFactorX);
		diceGrid.setVgap(6*game.scaleFactorY);
		
		tableView.getChildren().add(tablePic);
		tableView.getChildren().add(diceGrid);
	
		diceGrid.setStyle("-fx-grid-lines-visible: true;");
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
		diceGrid.setPadding(new Insets(25*game.scaleFactorY, 0, 0, 20*game.scaleFactorX));
		diceGrid.setHgap(6*game.scaleFactorX);
		diceGrid.setVgap(6*game.scaleFactorY);
		
	}
	
	protected void renderDice() {
		ArrayList<Node> addDice = new ArrayList<Node>();
		for(Node d : diceGrid.getChildren()) {
			if(d instanceof Dice) {
			((Dice)d).setBaseHeight(45);
			((Dice)d).setBaseWidth(45);
			addDice.add(d);
			}
		}
		diceGrid.getChildren().removeAll(addDice);
		for(Node d : addDice) {
			if(d instanceof Dice) {
			((Dice)d).setBaseHeight(45);
			((Dice)d).setBaseWidth(45);
			//Y RANGE: 18-30
			//X RANGE: 12-50
			diceGrid.add(d,(int)(Math.random()*13)+18,(int)(Math.random()*39)+12);
			}
		}
		
	}
	
	protected ArrayList<Dice> getDice() {
		return fieldDice;
	}
	
	protected void addDice(Dice d) {
		d.setBaseHeight(80);
		d.setBaseWidth(80);
		fieldDice.add(d);
		diceGrid.setPadding(new Insets(25*game.scaleFactorY, 0, 0, 20*game.scaleFactorX));
		diceGrid.add(d,fieldDice.size(), 0);
	
	
	}
	
	protected void removeDice(Dice d) {
		d.setBaseHeight(100);
		d.setBaseWidth(100);
		fieldDice.remove(d);
		diceGrid.getChildren().remove(d);
		
	}
	
	
}
