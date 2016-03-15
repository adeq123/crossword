package model;

/**
 * this class model one BoardCell an atom of a Crossword
 * @author RoguskiA
 * @version 1.0
 *
 */
public class BoardCell {

	private String content; /** content of the cell*/

	
	public static final int HORIZONTAL = 0;
	public static final int VERTICAL = 1;
	public static final int BEGINING = 0;
	public static final int IN = 1;
	public static final int END = 2;
	
	private Boolean [][] abilities; //2D array describing possible placement and direction in which single 
	//cell could be used. First row descirbes possible placement in horizontal direction. 
	//The second one describes all possible placements in vertical directions
	
	/**
	 * contruct a simple cell of the board
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
	 * 
	 * @return content of the single cell
	 */
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
	public void setFalse(){
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
}
