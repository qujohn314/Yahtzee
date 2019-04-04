package application.game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class Dice extends ImageView{
	private int value;
	private Image[] dicePics;
	private Game game;
	protected boolean kept;
	private static int idCount = 0;
	private int id;
	public Dice(int v, Game g) {
		value = v;
		game = g;
		kept = true;
		id = idCount++;
		try {
			dicePics = new Image[] {new Image(new FileInputStream("src/res/pics/dice1.png")),new Image(new FileInputStream("src/res/pics/dice2.png")),
											new Image(new FileInputStream("src/res/pics/dice3.png")),new Image(new FileInputStream("src/res/pics/dice4.png")),
											new Image(new FileInputStream("src/res/pics/dice5.png")),new Image(new FileInputStream("src/res/pics/dice6.png")),new Image(new FileInputStream("src/res/pics/DiceShell.png"))};
		} catch (FileNotFoundException e) {
			System.out.println("Error locating dice image files! Shutting Down!");
		} 
			
			this.setImage(dicePics[v-1]);
			this.setPreserveRatio(true);
			this.addEventFilter(MouseEvent.MOUSE_CLICKED, event ->{
				if(value >= 1 && value <= 6) {
					if(kept) {
						game.table.addDice(this);
						game.removeDice(this);
						game.addDice(new Dice(7,game));
					}else {
						game.table.removeDice(this);
						game.addDice(this);
					}
					kept = !kept;
				}
			});
			rescaleSizes();
		}
	
	@Override
	public boolean equals(Object d) {
		if(d instanceof Dice)
			return ((Dice)d).getDiceId() == this.id;
		return false;
	}
	
	protected void rescaleSizes() {
		this.setFitHeight(100*game.scaleFactorY);
		this.setFitWidth(100*game.scaleFactorX);
		
	}
	
	protected double getWidth() {
		return 100*game.scaleFactorX;
	}

	protected double getHeight() {
		return 100*game.scaleFactorY;
	}
	
	protected int getDiceId() {
		return id;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int v) {
		this.setImage(dicePics[v-1]);
		value = v;
	}
	
	public int roll() {
		int num = (int)(Math.random()*6)+1;
		this.setImage(dicePics[num-1]);
		return num;
	}
	
}
