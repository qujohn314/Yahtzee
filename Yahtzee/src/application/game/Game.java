package application.game;

import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Game extends BorderPane{
	private Scene scene;
	protected ArrayList<Dice> dice;
	private static final String BACKGROUND = "background.jpg";
	private Button rollButton;
	private double screenHeight,screenWidth;
	private static double baseWidth ,baseHeight;
	protected double scaleFactorX,scaleFactorY;
	private VBox leftLayout,midBox;
	private Group diceRolls;
	private Image background;
	protected Scoresheet scoresheet;
	protected Table table;
	protected int turn,rollCounter;
	protected boolean canScore,newTurn;
	protected Text turnText,rollText;
	private ImageView rollTextPic,turnTextPic,scorePic;
	private HBox turnLabels;
	private StackPane rollTextPane,stackScores;
	protected boolean gameOver,restart;
	private TextField playerNameEntry;
	private String playerName;
	private HighScores highScores;
	private GridPane scoreGrid;
	private boolean scoresUpdate,newGame;
	
	public Game() {
		getHighScores();
		newGame = false;
		restart = false;
		scoresUpdate = false;
		scoreGrid = new GridPane();
		dice = new ArrayList<Dice>();
		for(int i = 0;i<5;i++)
			dice.add(new Dice(1,this));
		try {
			scoresheet = new Scoresheet(this);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		rollButton = new Button("Start Game");
		rollButton.setOnAction(actionEvent-> {
			if(!restart) {
				if(!newGame) {
					rollButton.setText("Roll Dice");
					newGame = true;
				}
				rollDice();
			}else 
				reset();
			
		});
		scaleFactorX = screenWidth /  baseWidth;
		scaleFactorY = screenHeight / baseHeight;
		try {
			table = new Table(this);
		} catch (FileNotFoundException e1) {}	
		rollDice();	
		turn = 1;
		rollCounter = 0;
		canScore = false;
		newTurn = false;
		gameOver = false;
	}
	
	public void rollDice() {
		
		if(!gameOver && rollCounter < 3 && table.fieldDice.size() > 0) {
			rollText.setText("Roll: " + (rollCounter+1));
			newTurn = true;
			if(!canScore)
				canScore = true;
			for(Dice d : table.getDice()) {
				if(d.getValue() >= 1 && d.getValue() <= 6)
					d.roll();
			}
			rollCounter++;
		}
		table.renderDice();
		scoresheet.showScores();
	}
	
	
	protected void addDice(Dice d) {
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
	
	protected void showScores() {
		int textCount = 0;
		scoresUpdate = true;
		scoreGrid.setTranslateY(10*scaleFactorY);
		scoreGrid.setTranslateX(120*scaleFactorX);
		for (int i = 0;i<highScores.scores.size();i++) {
			scoreGrid.add(new Text((i+1)+"."+" "+highScores.scores.get(i).first), 0, i);
			scoreGrid.add(new Text(highScores.scores.get(i).second+""), 1, i);
			
			
			Text t = (Text)scoreGrid.getChildren().get(textCount);
			Text t2 = (Text)scoreGrid.getChildren().get(textCount+1);
			textCount+=2;
			
			
	    	Double newFontSizeDouble = (this.getHeight()/30);
	    	int newFontSizeInt = newFontSizeDouble.intValue();
				
			t.setFont(new Font("Rockwell", newFontSizeInt));
			t2.setFont(new Font("Rockwell", newFontSizeInt));
		}
		rollButton.setText("Restart");
		restart = true;
		endGame();
	}
	
	protected void submitScore() {
		 stackScores = new StackPane();
		
		 
		stackScores.getChildren().add(scorePic);
		if(highScores.isHighScore(scoresheet.getFinalScore()))
				stackScores.getChildren().add(playerNameEntry);
		else {
				stackScores.getChildren().add(scoreGrid);
				showScores();
		}
		gameOver = true;
		midBox.getChildren().set(1, stackScores);
		
		
	}
	
	public void endGame() {
		
		FileOutputStream fileOut = null;
		try {
			fileOut = new FileOutputStream("src/highscores/save.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         
	         out.writeObject((Object)highScores);
	         out.close();
	         fileOut.close();
	         System.out.println("Save successful");
		} catch (IOException e) {
			System.out.println("Fatal Error");
			e.printStackTrace();
		}
		
	}
	
	
	
	protected void getHighScores() {
		 InputStream fileIn = null;
		 highScores = null;
		 try {
			fileIn = new FileInputStream("src/highscores/save.ser");
			 ObjectInputStream o = new ObjectInputStream(fileIn);
			 highScores = (HighScores)o.readObject();
			 o.close();
			 fileIn.close();
			 System.out.println("Load Successful");
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Making new scores.");
			highScores = new HighScores();
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
		
		scorePic.setFitWidth(448*scaleFactorX);
		scorePic.setFitHeight(350*scaleFactorY);
	
		diceRolls.setTranslateY(10*scaleFactorY);
		diceRolls.setTranslateX(10*scaleFactorX);
		
		midBox.setTranslateX(-380 * scaleFactorX);
		
		this.getCenter().setTranslateY(140*scaleFactorY);
		
		for(Node d : dice) 
			((Dice)d).rescaleSizes(scaleFactorX,scaleFactorY);

		
		rollButton.setTranslateY(543*scaleFactorY);
		rollButton.setTranslateX(6*scaleFactorX);
		leftLayout.setPrefHeight(800 * scaleFactorY);
		leftLayout.setPrefWidth(500 * scaleFactorX);
		
		rollButton.setPrefSize(85*scaleFactorX > 100 ? 100 : 85*scaleFactorX, 40*scaleFactorY);
		Double newFontSizeDouble = Math.hypot(this.getWidth()/45, this.getHeight())/45;
    	int newFontSizeInt = newFontSizeDouble.intValue();
    	rollButton.setFont(Font.font(newFontSizeInt));
  
    	
		if(screenHeight < 451 || screenWidth < 300)
			this.setBackground(new Background(new BackgroundImage(background,null,null,null,null)));
		else this.setBackground(new Background(new BackgroundImage(background,BackgroundRepeat.ROUND,BackgroundRepeat.ROUND,null,null)));
		rollTextPic.setFitWidth(110 * scaleFactorX);
		rollTextPic.setFitHeight(50 * scaleFactorY);
		rollTextPane.setTranslateX(228 * scaleFactorX);
		
		turnTextPic.setFitWidth(110 * scaleFactorX);
		turnTextPic.setFitHeight(50 * scaleFactorY);
		
		
		scoreGrid.setVgap(10*scaleFactorY);
		scoreGrid.setHgap(100*scaleFactorX);
		
		Double newFontSizeDouble2 = (this.getWidth()/35);
    	int newFontSizeInt2 = newFontSizeDouble2.intValue();
    	rollText.setFont(Font.font("Stencil",newFontSizeInt2));	
    	turnText.setFont(Font.font("Stencil",newFontSizeInt2));
    	
    	
    	int textCount = 0;
    	scoreGrid.setTranslateY(10*scaleFactorY);
		scoreGrid.setTranslateX(100*scaleFactorX);
    	if(scoresUpdate) {
    	for (int i = 0;i<highScores.scores.size();i++) {
			
			Text t = (Text)scoreGrid.getChildren().get(textCount);
			Text t2 = (Text)scoreGrid.getChildren().get(textCount+1);
			textCount+=2;
			
			
	    	Double newFontSizeDouble3 = (this.getHeight()/30);
	    	int newFontSizeInt3 = newFontSizeDouble3.intValue();
				
			t.setFont(new Font("Rockwell", newFontSizeInt3));
			t2.setFont(new Font("Rockwell", newFontSizeInt3));
		}
    	}
		
		scoresheet.rescaleSizes();
		table.rescaleSizes();
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void reset() {
		restart = false;
		newGame = false;
		scoresUpdate = false;
		turn = 1;
		rollCounter = 0;
		canScore = false;
		newTurn = false;
		gameOver = false;
		rollText.setText("Roll: 1");
		turnText.setText("Turn: 1");
		rollButton.setText("Start Game");
		midBox.getChildren().set(1,table);
		ArrayList<Dice> holder = new ArrayList<Dice>();
		for(Dice d:dice)
			holder.add(d);
		for(Dice d:holder)
			if(d.getValue()<7) {
				removeDice(d);
				table.addDice(d);
			}
		for(Dice d:table.fieldDice)
			d.setValue(1);
		
		try {
			scoresheet = new Scoresheet(this);
			this.setRight(scoresheet);
			scoreGrid.getChildren().clear();
		} catch (FileNotFoundException e) {}
		playerNameEntry.setText("Player Name");
		rescaleSizes();
	}
	
	public void init(Scene s) throws FileNotFoundException {
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
		midBox = new VBox(10);
		
	
		
		rollTextPic = new ImageView(new Image(new FileInputStream("src/res/pics/Label.png")));
		turnTextPic = new ImageView(new Image(new FileInputStream("src/res/pics/Label.png")));
		
		rollText = new Text("Roll: 1");
		rollTextPane = new StackPane();
		rollText.setFont(new Font("Stencil",35));	
		//System.out.println(Font.getFamilies());
		rollText.setFill(Color.BLACK);
		
		rollTextPane.getChildren().add(rollTextPic);
		rollTextPane.getChildren().add(rollText);
	
		
		turnText = new Text("Turn: 1");
		StackPane turnTextPane = new StackPane();
		turnText.setFont(new Font("STENCIL",35));	
		turnText.setFill(Color.BLACK);
		
		turnTextPane.getChildren().add(turnTextPic);
		turnTextPane.getChildren().add(turnText);
		
		turnLabels = new HBox(0);
		turnLabels.getChildren().add(turnTextPane);
		turnLabels.getChildren().add(rollTextPane);
		
		midBox.getChildren().add(turnLabels);
		midBox.getChildren().add(table);
		
		playerNameEntry = new TextField();
		playerNameEntry.setText("Player Name");
		playerNameEntry.setMaxWidth(200);
		playerNameEntry.setFont(new Font(25));
		playerNameEntry.addEventFilter(javafx.scene.input.KeyEvent.KEY_PRESSED, event ->{
			if(playerNameEntry.getText().length() > 12)
				playerNameEntry.deletePreviousChar();
			if(event.getCode() == KeyCode.ENTER) {
				if(!playerNameEntry.getText().equals("")) {
					playerName = playerNameEntry.getText();
					stackScores.getChildren().remove(playerNameEntry);
					stackScores.getChildren().add(scoreGrid);
					highScores.addScore(playerName,scoresheet.getFinalScore()); 
					showScores();
					
				}
			}
			
		});
		this.addEventFilter(javafx.scene.input.KeyEvent.KEY_PRESSED, event ->{
			if(event.getCode() == KeyCode.R && event.isControlDown() && event.isAltDown()) {
				highScores = new HighScores();
				System.out.println("Scores reset");
			}
			if(event.getCode() == KeyCode.R && event.isControlDown() && event.isShiftDown()) {
				reset();
				System.out.println("Game Reset");
			}
		});

		playerNameEntry.addEventFilter(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event ->{
			if(gameOver)	
				playerNameEntry.setText("");
		});
		
			this.setLeft(diceRolls);
			this.setRight(scoresheet);
			this.setCenter(midBox);
			
		
			
			try {
				scorePic = new ImageView(new Image(new FileInputStream("src/res/pics/scores.jpg")));
			} catch (FileNotFoundException e) {}
			
		rescaleSizes();
		ArrayList<Dice> tempDice = new ArrayList<Dice>();
		for(Dice d : dice) {
			if(d.getValue() < 7)
			tempDice.add(d);
		}
		for(Dice d : tempDice) {
			removeDice(d);
			addDice(new Dice(7,this));
			table.addDice(d);
			d.kept = !d.kept;
		}
		
		rescaleSizes();
		reset();
		
	}
}
