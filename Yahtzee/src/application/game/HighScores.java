package application.game;

import java.io.Serializable;
import java.util.ArrayList;

public class HighScores implements Serializable{

	private static final long serialVersionUID = 1L;
	
	ArrayList<Pair<String,Integer>> scores; 
	
	public HighScores() {
		scores = new ArrayList<Pair<String,Integer>>();
	}
	
	public boolean addScore(String name, Integer score) {
		boolean added = false;
		for(int i = 0;i<scores.size();i++) 
			if(score > scores.get(i).second) {
				scores.add(i,new Pair<String,Integer>(name,score));
				added = true;
				break;
			}
		if(!added && scores.size() != 10) {
			scores.add(new Pair<String,Integer>(name,score));
			added = true;
			if(scores.size() == 11)
				added = false;
		}
		if(scores.size() == 11)
			scores.remove(10);
		
		return added;
	}
	
	public boolean isHighScore(int s) {
		boolean check = false;
		for(int i = 0;i<scores.size();i++) 
			if(s > scores.get(i).second) {
				check = true;
				break;
			}
		if(scores.size()<10)
			return true;
		return check;
	}
	
	protected class Pair<T,U> implements Serializable{
		private static final long serialVersionUID = 1L;
		public T first;
		public U second;
		
		public Pair(T t, U u) {
			first = t;
			second = u;
		}
	}
}
