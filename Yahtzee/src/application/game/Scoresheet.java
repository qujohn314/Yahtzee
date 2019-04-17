package application.game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;


import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Scoresheet extends Group{
	private Game game;
	private ImageView scoreView;
	private VBox container;
	private double baseHeight = 590;
	private double baseWidth = 200;
	private StackPane scoreStack;
	private HashMap<String,Score> scoreNames;
	private TilePane scores;
	private int yahtzeeCount;
	
	public Scoresheet(Game g) throws FileNotFoundException {
		game = g;
		
		container = new VBox(10);
		scoreStack = new StackPane();
		scores = new TilePane(Orientation.VERTICAL);
		Image scoreSheet = new Image(new FileInputStream("src/res/pics/scoresheet.png"));
		scoreView = new ImageView(scoreSheet);
		yahtzeeCount = 0;
		
		scores.setPrefRows(1);
		scores.setTranslateX(175*game.scaleFactorX);
		scores.setPadding(new Insets(3*game.scaleFactorY, 0, 0,0));
		
		
		
		 scoreNames = new HashMap<String,Score>();
		
		scoreNames.put("Filler", new Score(true));
		scoreNames.put("Ones", new Score());
		scoreNames.put("Twos", new Score());
		scoreNames.put("Threes", new Score());
		scoreNames.put("Fours", new Score());
		scoreNames.put("Fives", new Score());
		scoreNames.put("Sixes", new Score());
		scoreNames.put("Total Top", new Score());
		scoreNames.put("Top Bonus", new Score());
		scoreNames.put("Total Top Score", new Score());
		scoreNames.put("FillerTwo",new Score(true));
		scoreNames.put("Three Kind", new Score());
		scoreNames.put("Four Kind", new Score());
		scoreNames.put("Full House", new Score());
		scoreNames.put("Small", new Score());
		scoreNames.get("Small").setTranslateY(-2*game.scaleFactorY);
		scoreNames.put("Large", new Score());
		scoreNames.get("Large").setTranslateY(-3*game.scaleFactorY);
		scoreNames.put("Yahtzee", new Score());
		scoreNames.get("Yahtzee").setTranslateY(-5*game.scaleFactorY);
		scoreNames.put("Chance", new Score());
		scoreNames.put("Yahtzee Count", new Score());
		scoreNames.get("Yahtzee Count").setTranslateY(-3*game.scaleFactorY);
		scoreNames.get("Yahtzee Count").setTranslateX(-5*game.scaleFactorX);
		scoreNames.put("Yahtzee Bonus", new Score());
		scoreNames.get("Yahtzee Bonus").name = "Yahtzee Bonus";
		scoreNames.get("Yahtzee").name = "Yahtzee";
		scoreNames.put("Total Bottom", new Score());
		scoreNames.put("Total Top End", new Score());
		scoreNames.get("Total Top End").setTranslateY(10*game.scaleFactorY);
		scoreNames.put("Final", new Score());
		scoreNames.get("Final").setTranslateY(15*game.scaleFactorY);
		
		
		scores.getChildren().add(scoreNames.get("Filler"));
		scores.getChildren().add(scoreNames.get("Ones"));
		scores.getChildren().add(scoreNames.get("Twos"));
		scores.getChildren().add(scoreNames.get("Threes"));
		scores.getChildren().add(scoreNames.get("Fours"));
		scores.getChildren().add(scoreNames.get("Fives"));
		scores.getChildren().add(scoreNames.get("Sixes"));
		scores.getChildren().add(scoreNames.get("Total Top"));
		scores.getChildren().add(scoreNames.get("Top Bonus"));
		scores.getChildren().add(scoreNames.get("Total Top Score"));
		scores.getChildren().add(scoreNames.get("FillerTwo"));
		scores.getChildren().add(scoreNames.get("Three Kind"));
		scores.getChildren().add(scoreNames.get("Four Kind"));
		scores.getChildren().add(scoreNames.get("Full House"));
		scores.getChildren().add(scoreNames.get("Small"));
		scores.getChildren().add(scoreNames.get("Large"));
		scores.getChildren().add(scoreNames.get("Yahtzee"));
		scores.getChildren().add(scoreNames.get("Chance"));
		scores.getChildren().add(scoreNames.get("Yahtzee Count"));
		scores.getChildren().add(scoreNames.get("Yahtzee Bonus"));
		scores.getChildren().add(scoreNames.get("Total Bottom"));
		scores.getChildren().add(scoreNames.get("Total Top End"));
		scores.getChildren().add(scoreNames.get("Final"));
		
		
		
		scoreStack.getChildren().add(scoreView);
		scoreStack.getChildren().add(scores);
		scoreView.setFitHeight(baseHeight);
		scoreView.setFitWidth(baseWidth);
		container.getChildren().add(scoreStack);
		this.getChildren().add(container);
		
		rescaleSizes();
		
	}
	
	protected void rescaleSizes() {
		this.setTranslateX(-355 * game.scaleFactorX);
		this.setTranslateY(3 * game.scaleFactorY);
		scoreView.setFitHeight(baseHeight * game.scaleFactorY);
		scoreView.setFitWidth(baseWidth * game.scaleFactorX);
		scores.setVgap(6 *game.scaleFactorY);
		scores.setTranslateX(175*game.scaleFactorX);
		scores.setPadding(new Insets(1*game.scaleFactorY, 0, 0,0));
		for(Score s : scoreNames.values()) {
			Double newFontSizeDouble = Math.hypot(game.getWidth()/40, game.getHeight())/40;
	    	int newFontSizeInt = newFontSizeDouble.intValue();
	    	s.setFont(Font.font(newFontSizeInt));
	    	System.out.println(s.getFont().getSize());
		}
		scoreNames.get("Final").setTranslateY(7*game.scaleFactorY);
		scoreNames.get("Small").setTranslateY(-5*game.scaleFactorY);
		scoreNames.get("Large").setTranslateY(-6*game.scaleFactorY);
		scoreNames.get("Three Kind").setTranslateY(-3*game.scaleFactorY);
		scoreNames.get("Four Kind").setTranslateY(-4*game.scaleFactorY);
		scoreNames.get("Yahtzee Bonus").setTranslateY(-5*game.scaleFactorY);
		scoreNames.get("Full House").setTranslateY(-5*game.scaleFactorY);
		scoreNames.get("Total Top End").setTranslateY(5*game.scaleFactorY);
		scoreNames.get("Yahtzee Count").setTranslateY(-5*game.scaleFactorY);
		boolean big = false;
		for(Score s : scoreNames.values()) {
			try {
			if(Integer.parseInt(s.getText()) >= 100) 
				big = true;
			}catch(NumberFormatException e) {}
		}
		for(Score s : scoreNames.values()) {
			if(big) 
				s.setTranslateX(-5);	
			else
				s.setTranslateX(0);	
		}
	}
	
	protected void showScores() {
		
		if(!scoreNames.get("Ones").scored)
			scoreNames.get("Ones").setText(""+diceSum(1));
		if(!scoreNames.get("Twos").scored)
			scoreNames.get("Twos").setText(""+diceSum(2));
		if(!scoreNames.get("Threes").scored)
			scoreNames.get("Threes").setText(""+diceSum(3));
		if(!scoreNames.get("Fours").scored)
			scoreNames.get("Fours").setText(""+diceSum(4));
		if(!scoreNames.get("Fives").scored)
			scoreNames.get("Fives").setText(""+diceSum(5));
		if(!scoreNames.get("Sixes").scored)
			scoreNames.get("Sixes").setText(""+diceSum(6));
		
		if(fullHouse() && !scoreNames.get("Full House").scored) 
			scoreNames.get("Full House").setText("25");
		else if(!scoreNames.get("Full House").scored)
			scoreNames.get("Full House").setText("");
		
		if(threeKind() > 0 && !scoreNames.get("Three Kind").scored)
			scoreNames.get("Three Kind").setText(""+threeKind());
		else  if(threeKind() <= 0 && !scoreNames.get("Three Kind").scored)
			scoreNames.get("Three Kind").setText("");
		if(fourKind() > 0 && !scoreNames.get("Four Kind").scored)
			scoreNames.get("Four Kind").setText(""+fourKind());
		else  if(fourKind() <= 0 && !scoreNames.get("Four Kind").scored)
			scoreNames.get("Four Kind").setText("");
		if(chance() > 0 && !scoreNames.get("Chance").scored) 
			scoreNames.get("Chance").setText(""+chance());
		if(smallStraight() > 0 && !scoreNames.get("Small").scored)
			scoreNames.get("Small").setText(""+smallStraight());
		else  if(smallStraight() <= 0 && !scoreNames.get("Small").scored)
			scoreNames.get("Small").setText(""); 
		if(largeStraight() > 0 && !scoreNames.get("Large").scored)
			scoreNames.get("Large").setText(""+largeStraight());
		else if(largeStraight() <= 0 && !scoreNames.get("Large").scored)
			scoreNames.get("Large").setText("");
		if(yahtzee() > 0 && !scoreNames.get("Yahtzee").scored)
			scoreNames.get("Yahtzee").setText(""+yahtzee());
		else if(yahtzee() > 0 && scoreNames.get("Yahtzee").scored) {
			scoreNames.get("Yahtzee Bonus").scored = false;
			scoreNames.get("Yahtzee Bonus").setFill(Color.DIMGREY);
			scoreNames.get("Yahtzee Bonus").setText(""+(100*(yahtzeeCount)));
		}
		else if(yahtzee() <= 0 && !scoreNames.get("Yahtzee").scored) 
			scoreNames.get("Yahtzee").setText("");
		else if(yahtzee() <= 0 && scoreNames.get("Yahtzee").scored) {
			if(scoreNames.get("Yahtzee").val > 0) {
					scoreNames.get("Yahtzee Bonus").setText(""+(100*(yahtzeeCount-1)));
					scoreNames.get("Yahtzee Bonus").val = 100*(yahtzeeCount-1);
				if(scoreNames.get("Yahtzee Bonus").val == 0)
					scoreNames.get("Yahtzee Bonus").setText("");
				else
					scoreNames.get("Yahtzee Bonus").setFill(Color.BLACK);
			}
		}
		
		boolean big = false;
		for(Score s : scoreNames.values()) {
			try {
			if(Integer.parseInt(s.getText()) >= 100) 
				big = true;
			}catch(NumberFormatException e) {}
		}
		for(Score s : scoreNames.values()) {
			if(big) 
				s.setTranslateX(-5);
			else
				s.setTranslateX(0);	
		}
	}
	
	protected void getTotals() {
		int topTotal = 0;
		topTotal += scoreNames.get("Ones").val;
		topTotal += scoreNames.get("Twos").val;
		topTotal += scoreNames.get("Threes").val;
		topTotal += scoreNames.get("Fours").val;
		topTotal += scoreNames.get("Fives").val;
		topTotal += scoreNames.get("Sixes").val;
		
		scoreNames.get("Total Top").setText(topTotal+"");
		scoreNames.get("Total Top").val = topTotal;
		scoreNames.get("Total Top").setFill(Color.BLACK);
		if(scoreNames.get("Total Top").val >= 63) {
			scoreNames.get("Top Bonus").setText("35");
			scoreNames.get("Top Bonus").val = 35;
			scoreNames.get("Top Bonus").setFill(Color.BLACK);
		}
		else {
			scoreNames.get("Top Bonus").setText("0");
			scoreNames.get("Top Bonus").setFill(Color.BLACK);
		}
		scoreNames.get("Total Top Score").setText(""+(scoreNames.get("Total Top").val+scoreNames.get("Top Bonus").val));
		scoreNames.get("Total Top Score").val = (scoreNames.get("Total Top").val+scoreNames.get("Top Bonus").val);
		scoreNames.get("Total Top Score").setFill(Color.BLACK);
		
		int botTotal = scoreNames.get("Three Kind").val;
		botTotal += scoreNames.get("Four Kind").val;
		botTotal += scoreNames.get("Chance").val;
		botTotal += scoreNames.get("Full House").val;
		botTotal += scoreNames.get("Small").val;
		botTotal += scoreNames.get("Large").val;
		botTotal += scoreNames.get("Yahtzee").val;
		botTotal += scoreNames.get("Yahtzee Bonus").val;
		
		scoreNames.get("Total Bottom").setText(botTotal+"");
		scoreNames.get("Total Bottom").val = botTotal;
		scoreNames.get("Total Bottom").setFill(Color.BLACK);
		scoreNames.get("Total Top End").setText(scoreNames.get("Total Top Score").val+"");
		scoreNames.get("Total Top End").val = scoreNames.get("Total Top Score").val;
		scoreNames.get("Total Top End").setFill(Color.BLACK);
		scoreNames.get("Final").setText((scoreNames.get("Total Top Score").val+scoreNames.get("Total Bottom").val)+"");
		scoreNames.get("Final").val = scoreNames.get("Total Top Score").val+scoreNames.get("Total Bottom").val;
		scoreNames.get("Final").setFill(Color.BLACK);
		scoreNames.get("Yahtzee Count").setFill(Color.BLACK);
		boolean big = false;
		
		for(Score s : scoreNames.values()) {
			try {
			if(Integer.parseInt(s.getText()) >= 100) 
				big = true;
			}catch(NumberFormatException e) {}
		}
		for(Score s : scoreNames.values()) {
			if(big) 
				s.setTranslateX(-5);	
			else
				s.setTranslateX(0);	
		}
		
			
	}
	
	protected String diceSum(int num) {
		int sum = 0;
		for(int i = 0;i<game.dice.size();i++)
			if(game.dice.get(i).getValue() == num)
				sum += game.dice.get(i).getValue();
		if(sum == 0)
			return "0";
		return sum + "";
	}
	
	protected int chance() {
		int sum = 0;
		for(int i = 0;i<game.dice.size();i++) {
			if(game.dice.get(i).getValue()<7)
			sum += game.dice.get(i).getValue();
		}
		return sum;
	}
	
	protected int threeKind() {
		int threeCount = 0;
		for(int i = 1;i<=6;i++) {
			for(int ii = 0;ii<game.dice.size();ii++) {
				if(game.dice.get(ii).getValue()== i) {
					threeCount++;
				}
				if(threeCount >= 3) 
					break;
			}
			if(threeCount >= 3)
				break;
			threeCount = 0;
		}
		if(threeCount < 3)
			return 0;
		
		int total = 0;
		for(int i = 0;i<game.dice.size();i++)
			if(game.dice.get(i).getValue()<7)
				total += game.dice.get(i).getValue();
		for(int i = 0;i<game.table.fieldDice.size();i++)
			if(game.table.fieldDice.get(i).getValue()<7)
				total += game.table.fieldDice.get(i).getValue();
		return total;
	}
	protected int smallStraight() {
		int count = 0;
	
		ArrayList<Integer> diceValues = new ArrayList<Integer>();
		for(int i = 0;i<game.dice.size();i++) 
			diceValues.add(game.dice.get(i).getValue());
		Collections.sort(diceValues);
		
		for(int i = 0;i<diceValues.size();i++) {
		
			if(i<diceValues.size()-1 && diceValues.get(i+1) == diceValues.get(i)+1 && diceValues.get(i+1) <= 6) {
				count++;

				if(count >= 3)
					return 30;
			}else if(i<diceValues.size()-1 &&diceValues.get(i+1) > diceValues.get(i)+1)
				count = 0;
		}
		return 0;
	}
	
	protected int largeStraight() {
		int count = 0;
	
		ArrayList<Integer> diceValues = new ArrayList<Integer>();
		for(int i = 0;i<game.dice.size();i++) 
			diceValues.add(game.dice.get(i).getValue());
		Collections.sort(diceValues);
		
		for(int i = 0;i<diceValues.size();i++) {
			
			if(i<diceValues.size()-1 && diceValues.get(i+1) == diceValues.get(i)+1&& diceValues.get(i+1) <= 6) {
				count++;
				if(count >= 4)
					return 40;
			}else if(i<diceValues.size()-1 &&diceValues.get(i+1) > diceValues.get(i)+1)
				count = 0;
		}
		return 0;
	}
	
	protected int yahtzee() {
		int fiveCount = 0;
		for(int i = 1;i<=6;i++) {
			for(int ii = 0;ii<game.dice.size();ii++) {
				if(game.dice.get(ii).getValue()== i) {
					fiveCount++;
				}
				if(fiveCount >= 5) 
					break;
			}
			if(fiveCount >= 5)
				break;
			fiveCount = 0;
		}
		if(fiveCount < 5)
			return 0;
	
		return 50;
	}
	
	protected int fourKind() {
		int fourCount = 0;
		for(int i = 1;i<=6;i++) {
			for(int ii = 0;ii<game.dice.size();ii++) {
				if(game.dice.get(ii).getValue()== i) {
					fourCount++;
				}
				if(fourCount >= 4) 
					break;
			}
			if(fourCount >= 4)
				break;
			fourCount = 0;
		}
		if(fourCount < 4)
			return 0;
		
		int total = 0;
		for(int i = 0;i<game.dice.size();i++)
			if(game.dice.get(i).getValue()<7)
				total += game.dice.get(i).getValue();
		for(int i = 0;i<game.table.fieldDice.size();i++)
			if(game.table.fieldDice.get(i).getValue()<7)
				total += game.table.fieldDice.get(i).getValue();
		return total;
	}
	
	protected boolean fullHouse() {
		int threeCount = 0;
		int threeNum = 0;
		int twoCount = 0;
		for(int i = 1;i<=6;i++) {
			for(int ii = 0;ii<game.dice.size();ii++) {
				if(game.dice.get(ii).getValue()== i) {
					threeCount++;
				}
				if(threeCount == 3) {
					threeNum = i;
					break;
				}
			}
			if(threeCount == 3)
				break;
			threeCount = 0;
		}
		if(threeCount != 3)
			return false;
		for(int i = 1;i<=6;i++) {
			for(int ii = 0;ii<game.dice.size();ii++) { 
				if(game.dice.get(ii).getValue() == threeNum)
					continue;
				if(game.dice.get(ii).getValue()== i) {
					twoCount++;
				}
				if(twoCount == 2) {
					break;
				}
			}
			if(twoCount == 2)
				break;
			twoCount = 0;
		}
		if(twoCount != 2)
			return false;
		return true;
	}
	
	private class Score extends Text{
	
		public boolean scored;
		public int val;
		public String name;
		
		public Score(){
			val = 0;
			scored = false;
			name = "";
			
			this.setText("");
			this.setFont(new Font(20));	
			this.setFill(Color.DIMGREY);
			
			
			this.addEventFilter(MouseEvent.MOUSE_CLICKED, event ->{
				if(!scored) {
					this.setFill(Color.BLACK);
					scored = true;
					val = Integer.parseInt(this.getText());
					getTotals();
					if(this.name.equals("Yahtzee"))
						yahtzeeCount++;
					if(this.name.equals("Yahtzee Bonus")) {
						yahtzeeCount++;
						String tally = "";
						for(int i = 1;i<yahtzeeCount;i++) {
							tally+="I";
						}
						scoreNames.get("Yahtzee Count").setText(tally);
					}
					else if(!this.name.equals("Yahtzee Bonus")) 
						if(scoreNames.get("Yahtzee").val > 0) {
							scoreNames.get("Yahtzee Bonus").setText(""+(100*(yahtzeeCount-1)));
							scoreNames.get("Yahtzee Bonus").val = 100*(yahtzeeCount-1);
							if(scoreNames.get("Yahtzee Bonus").val == 0)
								scoreNames.get("Yahtzee Bonus").setText("");
							else
								scoreNames.get("Yahtzee Bonus").setFill(Color.BLACK);
						}
							
					
				}
			});
		}
		
		
		public Score(boolean empty){
			scored = false;
			if(empty)
				this.setText("");
			this.setFont(new Font(20));	
		
		}
		
		
	}
}
