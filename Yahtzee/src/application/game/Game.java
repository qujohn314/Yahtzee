package application.game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class Game extends BorderPane{
	private Scene scene;
	private final ArrayList<Node> dice;
	private static final String BACKGROUND = "background.jpg";
	private Image[] dicePics;
	private Button rollButton;
	private double screenHeight,screenWidth;
	private static double baseWidth ,baseHeight;
	protected double scaleFactorX,scaleFactorY;
	private VBox leftLayout;
	private Group diceRolls;
	private Image background;
	private Scoresheet scoresheet;
	private Table table;
	
	public Game() {
		dice = new ArrayList<Node>();
		dice.add(new ImageView());
		dice.add(new ImageView());
		dice.add(new ImageView());
		dice.add(new ImageView());
		dice.add(new ImageView());
		
		rollButton = new Button("Roll Dice");
		rollButton.setOnAction(actionEvent-> {
			rollDice();
		});
		
		try {
			dicePics = new Image[] {new Image(new FileInputStream("src/res/pics/dice1.png")),new Image(new FileInputStream("src/res/pics/dice2.png")),
											new Image(new FileInputStream("src/res/pics/dice3.png")),new Image(new FileInputStream("src/res/pics/dice4.png")),
											new Image(new FileInputStream("src/res/pics/DiceShell.png")),new Image(new FileInputStream("src/res/pics/dice6.png"))};
		} catch (FileNotFoundException e) {
			System.out.println("Error locating dice image files! Shutting Down!");
		} 
		for(Node d : dice) {
			((ImageView) d).setFitHeight(100);
			((ImageView) d).setFitWidth(100);
			((ImageView) d).setPreserveRatio(true);
		}
		rollDice();	
		
	}
	
	public void rollDice() {
		for(Node d : dice) {
			int roll = (int)(Math.random()*6)+1;
			((ImageView)d).setImage(dicePics[roll-1]);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public double getBaseWidth() {
		return baseWidth;
	}
	
	public double getBaseHeight() {
		return baseHeight;
	}
	
	public void rescaleSizes() {
		
		scaleFactorX = screenWidth /  baseWidth;
		scaleFactorY = screenHeight / baseHeight;
		
		diceRolls.setTranslateY(10*scaleFactorY);
		diceRolls.setTranslateX(10*scaleFactorX);
		
		for(Node d : dice) {

			((ImageView) d).setFitHeight(100*scaleFactorY);
			((ImageView) d).setFitWidth(100*scaleFactorX);
			((ImageView) d).setPreserveRatio(true);
		}
		
		rollButton.setTranslateY(548*scaleFactorY);
		rollButton.setTranslateX(15*scaleFactorX);
		leftLayout.setPrefHeight(800 * scaleFactorY);
		leftLayout.setPrefWidth(500 * scaleFactorX);
		
		if(screenHeight < 451 || screenWidth < 300)
			this.setBackground(new Background(new BackgroundImage(background,null,null,null,null)));
		else this.setBackground(new Background(new BackgroundImage(background,BackgroundRepeat.ROUND,BackgroundRepeat.ROUND,null,null)));
		
		scoresheet.rescaleSizes();
		table.rescaleSizes();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void init(Scene s) {
		scene = s;
		screenHeight = scene.getHeight();
		screenWidth = scene.getWidth();
		scene.widthProperty().addListener(new ChangeListener<Number>() {
		   @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
			   screenWidth = (Double)newSceneWidth;
			   rescaleSizes();
		    }
		});
		scene.heightProperty().addListener(new ChangeListener<Number>() {
		   @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
			   screenHeight = (Double)newSceneHeight;
			   rescaleSizes();
		    }
		});
		
		baseWidth = scene.getWidth();
		baseHeight = scene.getHeight();
		background = null;
		leftLayout = new VBox(10);
		
		try {
			background = new Image(new FileInputStream("src/res/pics/" + BACKGROUND));
		} catch (FileNotFoundException e) {
			System.out.println("Absolute File Path not found! Checking relative");
			try {
				background = new Image(new FileInputStream("/src/res/pics/" + BACKGROUND));
			} catch (FileNotFoundException e1) {}
			e.printStackTrace();
		}
		this.setBackground(new Background(new BackgroundImage(background,BackgroundRepeat.ROUND,BackgroundRepeat.ROUND,null,null)));
		
		diceRolls = new Group();
		try {
			table = new Table(this);
		} catch (FileNotFoundException e1) {}
		diceRolls.getChildren().add(leftLayout);
		diceRolls.getChildren().add(rollButton);
		leftLayout.getChildren().addAll(dice);
		this.setLeft(diceRolls);
		
		try {
			scoresheet = new Scoresheet(this);
		} catch (FileNotFoundException e) {	}
			this.setRight(scoresheet);
			this.setCenter(table);
			
		rescaleSizes();
	}
}
