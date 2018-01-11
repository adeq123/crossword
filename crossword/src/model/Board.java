/**
 * This class model a board used to place a crossword on it
 * @author ADRO
 * @version 1.0
 */
package model;

import java.util.LinkedList;

import FitedExeptions.WrongCoordinatesException;

public class Board {

    private BoardCell [][] board;
    private int noRows; 
    private int noColumns;

    /**
     * Constructs the board  for a given number of columns and rows
     * @param noRows number of rows
     * @param noColumns number of columns
     */
    public Board (int noRows, int noColumns){

	this.noRows = noRows;
	this.noColumns = noColumns;
	board = new BoardCell [noRows] [noColumns];

	for(int row = 0;row < board.length; row++) 
	    for(int col=0; col < board[0].length; col++){
		board [row] [col] = new BoardCell("");
	    }
	setDefaultAbilities();
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
     * This method runs through all of the cells on the board and creates
     * a list of cells that can be used as starting cell 
     * @return List of all start cells
     */
    public LinkedList<BoardCell> getStartCells() {

	LinkedList<BoardCell> startCells = new LinkedList<BoardCell> ();

	for(int row=0; row < this.board.length; row++)
	    for(int col=0; col < this.board[0].length; col++) {
		if (board [row] [col].getAbilities()[0][0] == true || board [row] [col].getAbilities()[1][0] == true){
		    startCells.add(board [row] [col]);
		}
	    }

	return startCells;
    }

    /**
     * This method creates a regular expression based on board cells between given  coordinates in the board
     * @param int, fromx x coordinate of the first sign
     * @param int, fromy y coordinate of the first sign
     * @param int, tox x coordinate of the last sign
     * @param int, toy y coordinate of the first sign
     * @return String, regular expression based on letters between given  coordinates in the board
     * @throws WrongCoordinatesException 
     * @throws Exception
     */
    public String createPattern(int fromCol, int fromRow, int toCol, int toRow) throws WrongCoordinatesException{

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

	}else throw new WrongCoordinatesException();
    }

    /**
     * This method makes a deep copy of the board.
     */
    public Board copyBoard () {

	Board result = new Board (this.noRows,this.noColumns);

	for(int row = 0;row < board.length; row++)
	    for(int col=0; col < board[0].length; col++){
		if(board [row] [col] != null){
		    result.setCell(row, col, board [row] [col].copyCell());
		    result.getCell(row, col).setAbilitiesMatrix(board [row] [col].getAbilities());
		}
	    }
	return result;
    }

    /**
     * Sets default abilities for cells on a side and in corners. In 2D abilities matrix first row says where cell could be placed in horizontal
     *direction (beginning, in, end) and second row in vertical. 
     *
     *Basically we have fours cases:
     *
     * - first row: the cells in the first row cannot be neither END nor IN cell of any word in VERTICAL direction.
     * - last row: the cells in the last row cannot be neither BEGINING nor IN cell of any word in VERTICAL direction.
     * - first column: the cells in the first row cannot be neither END nor IN cell of any word in HORIZONTAL direction.
     * - last column: the cells in the last row cannot be neither BEGINING nor IN cell of any word in HORIZONTAL direction.
     * 
     * The corners conditions are included in those four as a mix of two.
     *@see BoardCell
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
     * The function traverse through the Board and search for 
     * BoardCell given. 
     * @param suspect, a cell which position is to be checked
     * @return an int, a vertical position of input cell if found. -1 otherwise
     */
    public int getVerticalPositionOfCell(BoardCell suspect){

	for(int row = 0; row < this.getHeight(); row++)
	    for(int collumn = 0; collumn < this.getWidth(); collumn++){
		if(this.getCell(row, collumn) == suspect){
		    return row;
		}    
	    }
	return -1;

    }

    /**
     * The function traverse through the Board and search for 
     * BoardCell given. 
     * @param suspect, a cell which position is to be checked
     * @return an int, a horizontal position of input cell if found. -1 otherwise
     */
    public int getHorizontalPositionOfCell(BoardCell suspect){

	for(int row = 0; row < this.getHeight(); row++)
	    for(int collumn = 0; collumn < this.getWidth(); collumn++)
		if(this.getCell(row, collumn) == suspect){
		    return collumn;
		}
	return -1;
    }
}

