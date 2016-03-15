package model;

import dictionary.Entry;

public class CwEntry extends Entry{

	private int x;
	private int y;
	private Direction d;
	
	
	public CwEntry(String word, String clue, int x, int y, Direction d) {
		super(word, clue);
		this.x = x;
		this.y = y;
		this.d = d;
	}
	
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public Direction getD(){
		return this.d;
	}


}
