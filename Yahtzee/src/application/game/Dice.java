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
				
				if(kept && value > 0) {
					game.table.addDice(this);
					game.removeDice(this);
					game.addDice(new Dice(7,game));
					
					for(Dice d : game.dice)
						System.out.println(d.getValue());
				}
			});
			rescaleSizes();
		}
	
	@Override
	public boolean equals(Object d) {
		return ((Dice)d).getDiceId() == this.id;
		
	}
	
	protected void rescaleSizes() {
		this.setFitHeight(100*game.scaleFactorY);
		this.setFitWidth(100*game.scaleFactorX);
		
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
	
}
