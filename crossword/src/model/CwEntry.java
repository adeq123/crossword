package model;

import dictionary.Entry;
/**
 * This class extends the basic data structure of the Crossword (Entry) with additional
 * fields for describing the position and orientation on the Board. 
 * @author ADRO
 * @see Entry
 */
public class CwEntry extends Entry{

    private int x;
    private int y;
    private Direction d;
    /**
     * 
     * @param x, an int, x coordinate of the word
     * @param y, an int, y coordinate of the word
     * @param d, Direction, the orientation of the word on the board Cell - Horizontal or Vertical
     */
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
