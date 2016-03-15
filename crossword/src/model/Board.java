/**
- * This class model a board of a crossword
 * @author RoguskiA
 * @version 1.0
 */
package model;

import java.util.LinkedList;

public class Board {

	private BoardCell [][] board;
	private int w; /** number of rows of the board*/
	private int k; /** number of columns of the board*/

	/**
	 * contruct the board with for a given number of columns and rows
	 * @param w number of rows
	 * @param k number of columns
	 */
	public Board (int w, int k){
		
		this.w = w;
		this.k = k;
		board = new BoardCell [w] [k];
		
		for(int i = 0;i < board.length; i++) // wiersze
			  for(int j=0; j < board[0].length; j++){//kolumny
				  board [i] [j] = new BoardCell("");
			  }
		
		setDefaultAbilities();

			
	}
	/**
	 * Sets proper properties of board cells. In corners and at the borders it sets Status to DISABLED
	 */
	public void setPropertiesOfBoard(){
		
	}
	/**
	 * 
	 * @return number of columns of the board
	 */
	public int getWidth(){
		return this.board [0].length;
	}
	/**
	 * 
	 * @return number of rows of the board 
	 */
	public int getHeight(){
		return this.board.length;
		
	}
	
	/**
	 * Method which returns a cell with a given coordinates
	 * @param x, int x coordinate of the cell
	 * @param y, int y coordinate of the cell
	 * @return cell with a given coordinates
	 */
	public BoardCell getCell(int row, int col){
		return this.board [row] [col];
	}
	
	/**
	 * Method which set a cell to a given coordinates
	 * @param x, int x coordinate of the cell
	 * @param y, int y coordinate of the cell
	 */
	public void setCell(int row, int col, BoardCell c) {
		this.board [row] [col] = c;
	}
	
	/**
	 * 
	 * @return List of all start cells
	 */
	public LinkedList<BoardCell> getStartCells() {
		
		LinkedList startCells = new LinkedList<BoardCell> ();
		
		for(int i=0; i < this.board.length; i++)// wiersze
			for(int j=0; j < this.board[0].length; j++) {//kolumny
				if (board [i] [j].getAbilities()[0][0] == true || board [i] [j].getAbilities()[1][0] == true){
					startCells.add(board [i] [j]);
				}
			}
			
		return startCells;
	}
	
	/**
	 * this method creates a regular expression based on letters between given  coordinates in the board
	 * @param int, fromx x coordinate of the first sign
	 * @param int, fromy y coordinate of the first sign
	 * @param int, tox x coordinate of the last sign
	 * @param int, toy y coordinate of the first sign
	 * @return String, regular expression based on letters between given  coordinates in the board
	 * @throws Exception
	 */
	public String createPattern(int fromCol, int fromRow, int toCol, int toRow) throws Exception{
		
		String pattern = "";
		
		if(fromCol == toCol){ //vertical
		
			for(int i = fromRow; i <= toRow; i++){
				if(board [i] [fromCol].checkContent()){
					pattern += board[i] [fromCol].getContent();
				} else{
					pattern +=".";
				}
				
			}
			

			return pattern;
			
		}else if(fromRow == toRow){ //horizontal
		
			for(int i = fromCol; i <= toCol; i++){
				if(board [fromRow] [i].checkContent()){
					pattern += board[fromRow] [i].getContent();
				} else{
					pattern +=".";
				}
			}

			return pattern;
		
		}else throw new Exception ("Wrong coordinates the word needs to be either vertical or horizontal!!");
	 
	//return pattern;
}
	 /**
	   * This method makes a deep copy of the board.
	   */
	  public Board copyBoard () {
		  Board result = new Board (this.w,this.k);
		  
		  for(int i = 0;i < board.length; i++) // wiersze
			  for(int j=0; j < board[0].length; j++){//kolumny
				  if(board [i] [j] != null){
				 result.setCell(i, j, board [i] [j].copyCell());
				 result.getCell(i, j).setAbilitiesMatrix(board [i] [j].getAbilities());
				  }
			  }
	  return result;
	  }
	  
	  /**
	   * Sets default abilities for cells on a side and in corners. Abilities is 2D matrix first row says where cell could be placed in horizontal
	   *direction (beginning, in, end) and second row in vertical. 
	   */
	  private void setDefaultAbilities(){
		  
		  for (int row = 0; row < this.getHeight(); row ++)
			  for(int column = 0; column < this.getWidth(); column ++){
				  
				  if(row == 0){
					  board [row] [column].setAbilities(BoardCell.VERTICAL, BoardCell.END, false);
					  board [row] [column].setAbilities(BoardCell.VERTICAL, BoardCell.IN, false);
				  }
				  
				  if(row == (this.getHeight()-1)){
					  board [row] [column].setAbilities(BoardCell.VERTICAL, BoardCell.BEGINING, false);
					  board [row] [column].setAbilities(BoardCell.VERTICAL, BoardCell.IN, false);
				  }
				  
				  if(column == 0){
					  board [row] [column].setAbilities(BoardCell.HORIZONTAL, BoardCell.END, false);
					  board [row] [column].setAbilities(BoardCell.HORIZONTAL, BoardCell.IN, false);
				  }
				  
				  if(column == this.getWidth()-1){
					  board [row] [column].setAbilities(BoardCell.HORIZONTAL, BoardCell.BEGINING, false);
					  board [row] [column].setAbilities(BoardCell.HORIZONTAL, BoardCell.IN, false);
				  }
			  }
	  }
	  
	  /**
	   * 
	   * @param suspect, a cell which position is to be checked
	   * @return int, a vertical position of input cell
	   */
	  public int getVerticalPositionOfCell(BoardCell suspect){
		  
		  for(int row = 0; row < this.getHeight(); row++)
			  for(int collumn = 0; collumn < this.getWidth(); collumn++){
				 
				  if(this.getCell(row, collumn) == suspect)
					  return row;
			  }
		  
		  return -1;
		  
	  }

	  /**
	   * 
	   * @param suspect, a cell which position is to be checked
	   * @return int, a horizontal position of input cell
	   */
	  public int getHorizontalPositionOfCell(BoardCell suspect){
		  
		  for(int row = 0; row < this.getHeight(); row++)
			  for(int collumn = 0; collumn < this.getWidth(); collumn++)
				  if(this.getCell(row, collumn) == suspect)
					  return collumn;
				  
		  
		  return -1;
		  
	  }
	  
	}

