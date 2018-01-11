package model;

/**
 * This class model one BoardCell an atom of a Crossword
 * @author ADRO
 * @version 1.0
 *
 */
public class BoardCell {

    private String content; 

    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    public static final int BEGINING = 0;
    public static final int IN = 1;
    public static final int END = 2;

    /**
     * 2D array describing possible placement and direction in each single 
     * cell could be used. First row describes possible placement in horizontal direction. 
     * The second one describes all possible placements in vertical directions
     * 
     * For example if the cell is located in the last column it is obvious that it cannot be 
     * a starting cell of any horizontal word since one letter word does not exist. 
     * Analogically the cells from last for cannot be a starting cell of any vertical word.
     */
    private Boolean [][] abilities; 

    /**
     * Constructs a simple cell of the board
     * @param content, String a content of the cell
     */
    public BoardCell(String content){
	this.content = content;
	this.abilities = new Boolean [2] [3];
	for (int row = 0; row < 2; row ++)
	    for( int collumn =0; collumn < 3; collumn ++)
		abilities [row] [collumn] = true;
    }


    /** Method used to set the content of single cell*/
    public void setConntent(String content) {
	this.content = content;
    }


    /**
     * Returns a new instance of the BoardCell with the same content and abilities
     * @return, copy of this board cell
     */
    public BoardCell copyCell(){
	BoardCell result = new BoardCell(this.content);
	result.abilities = this.abilities;
	return result;
    }

    /**
     * 
     * @return true if the cell has any content
     */
    public boolean checkContent () {
	return this.content.length() > 0;
    }

    /**
     * Sets all abilities of the cell to false
     */
    public void setAllAbilitiesFalse(){
	for(int row = 0; row < this.abilities.length; row++)
	    for(int collumn = 0; collumn < this.abilities [0].length; collumn++)
		this.setAbilities(row, collumn, Boolean.FALSE);
    }

    /**
     * Sets all vertical abilities to false
     */
    public void setVerFalse (){
	for(int collumn = 0; collumn < this.abilities [0].length; collumn++)
	    this.setAbilities(BoardCell.VERTICAL, collumn, Boolean.FALSE);
    }

    /**
     * Sets all horizontal abilities to false
     */
    public void setHorFalse (){
	for(int collumn = 0; collumn < this.abilities [0].length; collumn++)
	    this.setAbilities(BoardCell.HORIZONTAL, collumn, Boolean.FALSE);
    }

    public String getContent(){
	return this.content;
    }

    public Boolean [] [] getAbilities(){
	return abilities;
    }

    public void setAbilities (int row, int collumn, Boolean value){
	abilities [row] [collumn] = value;
    }

    public Boolean getAbilities (int row, int collumn){
	return abilities [row]  [collumn];
    }

    public void setAbilitiesMatrix (Boolean [] [] abilities){
	this.abilities = abilities;
    }
}
