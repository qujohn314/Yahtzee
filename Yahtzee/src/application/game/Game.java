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
	protected ArrayList<Dice> dice;
	private static final String BACKGROUND = "background.jpg";
	private Button rollButton;
	private double screenHeight,screenWidth;
	private static double baseWidth ,baseHeight;
	protected double scaleFactorX,scaleFactorY;
	private VBox leftLayout;
	private Group diceRolls;
	private Image background;
	protected Scoresheet scoresheet;
	protected Table table;
	
	public Game() {
		dice = new ArrayList<Dice>();
		for(int i = 0;i<5;i++)
			dice.add(new Dice((int)(Math.random()*6)+1,this));
		try {
			scoresheet = new Scoresheet(this);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rollButton = new Button("Roll Dice");
		rollButton.setOnAction(actionEvent-> {
			rollDice();
		});
		scaleFactorX = screenWidth /  baseWidth;
		scaleFactorY = screenHeight / baseHeight;
		try {
			table = new Table(this);
		} catch (FileNotFoundException e1) {}
		
		rollDice();	
		
		
	}
	
	public void rollDice() {
		for(Dice d : table.getDice()) {
			if(d.getValue() >= 1 && d.getValue() <= 6)
				d.roll();
		}
		table.renderDice();
		scoresheet.showScores();
	}
	
	
	protected void addDice(Dice d) {
		int index = 0;
		if(d.getValue() >= 1 && d.getValue() <= 6)
		for(int i = 0;i<dice.size();i++) {
			if(dice.get(i).getValue() == 7) {
				leftLayout.getChildren().set(i,d);
				dice.set(i,d);
				break;
			}
		}else {
			dice.add(d);
			leftLayout.getChildren().add(d);
		}
	}
	
	protected void removeDice(Dice d) {
		dice.remove(d);
		leftLayout.getChildren().remove(d);
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
		
		for(Node d : dice) 
			((Dice)d).rescaleSizes(scaleFactorX,scaleFactorY);

		
		rollButton.setTranslateY(548*scaleFactorY);
		rollButton.setTranslateX(18*scaleFactorX);
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
		
		diceRolls.getChildren().add(leftLayout);
		diceRolls.getChildren().add(rollButton);
		leftLayout.getChildren().addAll(dice);
		this.setLeft(diceRolls);
		
		
			this.setRight(scoresheet);
			this.setCenter(table);
			
		rescaleSizes();
		
	}
}
