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
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Table extends Group{
	private double baseHeight = 390;
	private double baseWidth = 450;
	private GridPane tableView;
	private Game game;
	private VBox container;
	private ArrayList<Dice> fieldDice;
	private BackgroundSize tablePic;
	
	public Table(Game g) throws FileNotFoundException {
		game = g;
		container = new VBox(10);
		tablePic = new BackgroundSize(baseWidth*game.scaleFactorX,baseHeight*game.scaleFactorY,false,false,false,true);
		fieldDice = new ArrayList<Dice>();
		Image scores = new Image(new FileInputStream("src/res/pics/table.png"));
			
		tableView = new GridPane();
		tableView.setVgap(5);
		tableView.setHgap(8);
		tableView.setPadding(new Insets(0, 50, 0, 50));
		
		tableView.setBackground(new Background(new BackgroundImage(new Image(new FileInputStream("src/res/pics/table.png")),BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,tablePic)));
		container.getChildren().add(tableView);
		this.getChildren().add(container);
		//tableView.getChildren().add(tablePic);
		rescaleSizes();
	
	}
	
	protected void rescaleSizes() {
		this.setTranslateX(-380 * game.scaleFactorX);
		this.setTranslateY(-35 * game.scaleFactorY);
		tableView.setMinHeight(baseHeight * game.scaleFactorY);
		tableView.setMinWidth(baseWidth * game.scaleFactorX);
		for(Dice d : fieldDice)
			d.rescaleSizes();
		tableView.setPadding(new Insets(0, 0, 0, (baseWidth*game.scaleFactorX/2)-((fieldDice.size()-1)*100*game.scaleFactorX)));
		tablePic = new BackgroundSize(baseWidth*game.scaleFactorX,baseHeight*game.scaleFactorY,false,false,false,true);
	}
	
	protected void renderDice() {
		
	}
	
	protected ArrayList<Dice> getDice() {
		return fieldDice;
	}
	
	protected void addDice(Dice d) {
		fieldDice.add(d);
		tableView.add(d, fieldDice.indexOf(d), 0);
		tableView.setPadding(new Insets(0, 0, 0, (baseWidth*game.scaleFactorX/2)-((fieldDice.size()-1)*d.getWidth())));
	
	}
	
	protected void removeDice(Dice d) {
		fieldDice.remove(d);
		tableView.getChildren().remove(d);
		tableView.setPadding(new Insets(0, 0, 0, (baseWidth*game.scaleFactorX/2)-((fieldDice.size())*d.getWidth())));
	}
	
	
}
