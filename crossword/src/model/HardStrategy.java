package model;

import java.util.*;
import dictionary.*;

public class HardStrategy extends Strategy{

	@Override
	public CwEntry findEntry(Crossword cw) throws Exception {
		
		Random rnd = new Random();
		Board board = cw.getBoardCopy();
		LinkedList <BoardCell> startCell = board.getStartCells();
		BoardCell tmp;
		Boolean flag = Boolean.FALSE;
		CwEntry toReturn = null;
		
		while(!startCell.isEmpty() && !flag){ 
			if(cw.isEmpty()){ // if crossword is empty draw horizontal / vertical position and then cell
				if(rnd.nextBoolean()){ //draw cell for horizontal Entry
					tmp = board.getCell(0, rnd.nextInt(board.getWidth()));
				}else{	//draw cell for vertical Entry 				
					tmp = board.getCell(rnd.nextInt(board.getHeight()), 0 );
				}
						
			}else{ //If crossword is not empty draw a cell from startCell list
				tmp = startCell.get(rnd.nextInt(startCell.size()));
			}
			
			if(tmp.getAbilities(BoardCell.VERTICAL, BoardCell.BEGINING)){ // if starting cell can be vertical 
				int possibleSizeOfEntry = board.getHeight() - board.getVerticalPositionOfCell(tmp);
				while(possibleSizeOfEntry>1 && !flag){
					
					
					if(checkAbilities(board, possibleSizeOfEntry, BoardCell.VERTICAL, 
							board.getHorizontalPositionOfCell(tmp), board.getVerticalPositionOfCell(tmp)) && checkIfHaveAnyContent(board, 
							possibleSizeOfEntry, BoardCell.VERTICAL, board.getHorizontalPositionOfCell(tmp), 
							board.getVerticalPositionOfCell(tmp)) || cw.isEmpty()){
						
						LinkedList <Entry> matchedEntries = new LinkedList <Entry> ();
						matchedEntries = cw.getCwDB().findAll(board.createPattern(board.getHorizontalPositionOfCell(tmp), board.getVerticalPositionOfCell(tmp), 
								board.getHorizontalPositionOfCell(tmp) ,board.getVerticalPositionOfCell(tmp) + possibleSizeOfEntry-1));
						while( matchedEntries.size() > 0 && !flag){
							
							Entry possibleToReturn = matchedEntries.get(rnd.nextInt(matchedEntries.size()));
							if(!cw.contains(possibleToReturn.getWord()) && possibleToReturn.getWord().length() <= possibleSizeOfEntry){
								flag = Boolean.TRUE;
								toReturn = new CwEntry(possibleToReturn.getWord(), possibleToReturn.getClue(), board.getHorizontalPositionOfCell(tmp), board.getVerticalPositionOfCell(tmp), Direction.VERT);
								
							}	
							matchedEntries.remove(possibleToReturn);
						}
					} possibleSizeOfEntry --; 
				}
			}
			
			if(tmp.getAbilities(BoardCell.HORIZONTAL, BoardCell.BEGINING)){ // if starting cell can be horizontal 
				int possibleSizeOfEntry = board.getWidth() - board.getHorizontalPositionOfCell(tmp);
				while(possibleSizeOfEntry>1 && !flag){
					
					if(checkAbilities(board, possibleSizeOfEntry, BoardCell.HORIZONTAL, 
							board.getHorizontalPositionOfCell(tmp), board.getVerticalPositionOfCell(tmp)) && checkIfHaveAnyContent(board, 
									possibleSizeOfEntry, BoardCell.HORIZONTAL, board.getHorizontalPositionOfCell(tmp), 
									board.getVerticalPositionOfCell(tmp)) || cw.isEmpty()){
						
						LinkedList <Entry> matchedEntries = new LinkedList <Entry> ();
						matchedEntries = cw.getCwDB().findAll(board.createPattern(board.getHorizontalPositionOfCell(tmp), board.getVerticalPositionOfCell(tmp), 
								board.getHorizontalPositionOfCell(tmp) + possibleSizeOfEntry - 1,board.getVerticalPositionOfCell(tmp)));
						
						while( matchedEntries.size() > 0 && !flag){
							Entry possibleToReturn = matchedEntries.get(rnd.nextInt(matchedEntries.size()));
							if(!cw.contains(possibleToReturn.getWord()) && possibleToReturn.getWord().length() <= possibleSizeOfEntry){
								
								
								flag = Boolean.TRUE;
								toReturn = new CwEntry(possibleToReturn.getWord(), possibleToReturn.getClue(), board.getHorizontalPositionOfCell(tmp), board.getVerticalPositionOfCell(tmp), Direction.HORIZ);
							}	
							matchedEntries.remove(possibleToReturn);
						}
						
					} possibleSizeOfEntry --; 
				}
			}
			startCell.remove(tmp);
		}
		return toReturn;
	}

	/**
	 * Method check if every cell between start and end has ability in, and it checks of last cell has 
	 * ability end. Works for both vertical and horizontal directions
	 * @param board, board where entry is going to be placed
	 * @param possibleSizeOfEntry, available length between start and end cell
	 * @param direction, planned direction of an entry
	 * @param cellHorPosition, horizontal position of start cell
	 * @param cellVerPosition, vertical position of start cell
	 * @return TRUE if very cell between start and end has ability in and last cell has ability end.
	 * FALSE if any if any cell between start and end cannot be inside (has not ability in) or last 
	 * cell cannot be placed at the end (has not ability end)
	 */
	private boolean checkAbilities(Board board, int possibleSizeOfEntry, int direction, 
			int cellHorPosition, int cellVerPosition) {
		
		boolean toReturn = Boolean.TRUE;
		
		if(direction == BoardCell.VERTICAL){ //first case vertical direction
			
			for(int pos = cellVerPosition + 1; pos < cellVerPosition + possibleSizeOfEntry - 1 ;pos++){ // check if every cell between start and end can be inside
				if(!board.getCell(pos, cellHorPosition).getAbilities(BoardCell.VERTICAL, BoardCell.IN)) 
					toReturn = Boolean.FALSE;
			}		
			if(!board.getCell(cellVerPosition + possibleSizeOfEntry - 1,cellHorPosition).getAbilities(BoardCell.VERTICAL, BoardCell.END)) // check if last cell has end ability
				toReturn = Boolean.FALSE;
			
		}else{ // horizontal direction
			
			for(int pos = cellHorPosition +1; pos < cellHorPosition + possibleSizeOfEntry -1;pos++){
				if(!board.getCell(cellVerPosition, pos).getAbilities(BoardCell.HORIZONTAL, BoardCell.IN)) 
					toReturn = Boolean.FALSE;
			}		
			if(!board.getCell(cellVerPosition, cellHorPosition + possibleSizeOfEntry - 1).getAbilities(BoardCell.VERTICAL, BoardCell.END)) // check if last cell has end ability
				toReturn = Boolean.FALSE;
			}
		return toReturn;
	}

	/**
	 * Method checks if cells on board between cellHorPosition and cellHorPosition + possibleSizeOfEntry or between
	 * cellVerPosition and cellVerPosition + possibleSizeOfEntry have any content
	 * @param board, board to be investigated
	 * @param possibleSizeOfEntry, available length between start and end cell
	 * @param direction, planned direction of an entry
	 * @param cellHorPosition, horizontal position of start cell
	 * @param cellVerPosition, vertical position of start cell
	 * @return true if there is any content
	 */
	private boolean checkIfHaveAnyContent(Board board, int possibleSizeOfEntry, int direction, 
			int cellHorPosition, int cellVerPosition){
		
		if(direction == BoardCell.VERTICAL){
			
			for(int pos = cellVerPosition; pos <= cellVerPosition + possibleSizeOfEntry - 1; pos ++){
				if(board.getCell(pos, cellHorPosition).checkContent())
					return Boolean.TRUE;
			}
			
		}else{
			
			for(int pos = cellHorPosition; pos <= cellHorPosition + possibleSizeOfEntry - 1; pos ++){
				if(board.getCell(cellVerPosition, pos).checkContent())
					return Boolean.TRUE;
			}
		
		}
		
		return Boolean.FALSE;
	}
	
	/**
	 * Method updates the content of the  board and sets proper abilities of cells. For cells where 
	 * we have a content and around them
	 */
	public void updateBoard(Board b, CwEntry e) {
		
		 if(e.getD() == Direction.VERT){
			 
			 if(e.getY() > 0 && e.getX() < b.getWidth()-1){ // don't start horizontal entry from the cell 45 deg up form starting cell 
				 b.getCell(e.getY(), e.getX()+1).setAbilities(BoardCell.HORIZONTAL, BoardCell.BEGINING, Boolean.FALSE);
			 }
			 
			 if(e.getY() > 0){ // don't put any content to one cell before the start cell
				 b.getCell(e.getY() - 1, e.getX()).setFalse();
			 }
			 
			 if (e.getY() + e.getWord().length() < b.getHeight()){ // don't put anything to one cell after the last cell where we want to put content of entry
				 b.getCell(e.getY() + e.getWord().length(), e.getX()).setFalse();
			 }
			 
			 for(int row = e.getY(); row < e.getY() + e.getWord().length(); row++){ // set content of entry to appropriate cell
				 //, and set ablilities so that we don't use it as a part of vertical entry
				
				 b.getCell(row, e.getX()).setConntent(e.getWord().substring(row - e.getY(), row - e.getY() + 1));
				 b.getCell(row, e.getX()).setVerFalse();
				 
			 }
			 
			 if(e.getX() > 0){// all the cells on right side from content can't have  vertical entry
				 //and can't end horizontal
				 for(int row = e.getY(); row < e.getY() + e.getWord().length(); row++){
					 b.getCell(row, e.getX() - 1).setVerFalse();
					 b.getCell(row, e.getX() - 1).setAbilities(BoardCell.HORIZONTAL, BoardCell.END, Boolean.FALSE);
				 }
			 }
			 
			if(e.getX() < b.getWidth() -1){// all the cells on left side from content can't have  vertical entry and
				//can't start horizontal
				for(int row = e.getY(); row < e.getY() + e.getWord().length(); row++){
					b.getCell(row, e.getX() + 1).setVerFalse();
					b.getCell(row, e.getX() + 1).setAbilities(BoardCell.HORIZONTAL, BoardCell.END, Boolean.FALSE);
					 }
					 
			 }
			 
		 }else{ // Horizontal
			 
			 if(e.getX() > 0 && e.getY() < b.getHeight()-1){ // don't start vertical entry from the cell 45 deg up form starting cell 
				 b.getCell(e.getY(), e.getX()+1).setAbilities(BoardCell.VERTICAL, BoardCell.BEGINING, Boolean.FALSE);
			 }
			 
			 if(e.getX() > 0){ // don't put any content to one cell before the start cell
				 b.getCell(e.getY(), e.getX() - 1).setFalse();
			 }
			 
			 if (e.getX() + e.getWord().length() < b.getWidth()){ // don't put anything to one cell after the last cell where we want to put content of entry
			
				 b.getCell(e.getY() , e.getX() + e.getWord().length()).setFalse();
			 }
			 
			 for(int collumn = e.getX(); collumn < e.getX() + e.getWord().length() ; collumn ++){ // set content of entry to appropriate cell
				 //, and set ablilities so that we don't use it as a part of vertical entry 
				 b.getCell(e.getY(), collumn).setConntent(e.getWord().substring(collumn - e.getX(), collumn - e.getX() + 1));
				 b.getCell(e.getY(), collumn).setHorFalse();
			 }
			 
			 if(e.getY() > 0){
				 
				 for(int collumn = e.getX(); collumn < e.getX() + e.getWord().length() - 1; collumn ++){ 
					 b.getCell(e.getY() - 1, collumn).setHorFalse();
					 b.getCell(e.getY() - 1, collumn).setAbilities(BoardCell.VERTICAL, BoardCell.END, Boolean.FALSE);
				 }
					 
			 }
			 
			 
			 if(e.getY() < b.getHeight() - 1){
				 
				 for(int collumn = e.getX(); collumn < e.getX() + e.getWord().length() - 1; collumn ++){ 
					 b.getCell(e.getY() + 1, collumn).setHorFalse();
					 b.getCell(e.getY() + 1, collumn).setAbilities(BoardCell.VERTICAL, BoardCell.END, Boolean.FALSE);
				}
					 
			 }
			 
		 }
		
	}

}
