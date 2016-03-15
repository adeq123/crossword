package model;

import dictionary.Entry;
import java.util.*;

public class EasyStrategy extends Strategy{

	private CwEntry password;
	Random rnd = new Random();
	int index = 0;

	
	/**
	 * Generates a random password based on database used in crossword. Password i checked by checkPassword() method
	 * @param cross a Crossword whose database is used to generate the password
	 * @param board a Board where password is meant to be placed
	 * @return a String, random password from crossword database
	 * @throws Exception
	 */
	public CwEntry generatePassword(Crossword cross, Board board) throws Exception{
	
		LinkedList <Entry> passList = new LinkedList<Entry>();
		passList = cross.getCwDB().findAll("[a-z,A-Z]{1,"+Integer.toString(board.getHeight())+"}");
		
		if(passList.isEmpty()) throw new Exception("No matching words");
		Entry tmp;
		do{			
			tmp = passList.get(rnd.nextInt(passList.size()-1));
			
		}while(!checkPassword(cross, board, tmp.getWord()) && !passList.isEmpty());
		password = new CwEntry(tmp.getWord(),tmp.getClue(),0,0,Direction.VERT);
		return password;
	}
	
	/**
	 * Method check Correctness of potential password to the crossword (TMPpass). Basically it checks if there is enough words starting 
	 * with correct letter with correct length that can be use as a part of crossword. Next word starts
	 * with next TMPpass letter 
	 * @param cross a Crossword whose database is used to check the Password
	 * @param board a Board where password is meant to be placed
	 * @param TMPpass a String, Potential password to be checked 
	 * @return boolean, true - password correct, false - password incorrect
	 */
	public boolean checkPassword(Crossword cross, Board board,String TMPpass){
		
		Map <Character, Integer> passMap = new HashMap<Character, Integer>();
		char [] passArray = TMPpass.toCharArray();
		for(char letter : passArray){
			if(!passMap.containsKey(letter)){
			passMap.put(letter, 1);
			}else{
				passMap.put(letter,passMap.get(letter)+1);
			}
		}
		 
		
		for(char key : passMap.keySet()){
			
			if (cross.getCwDB().findAll(key+"[a-z,A-Z]{1,"+Integer.toString(board.getWidth()-1)+"}").size() <= passMap.get(key))
				return false;
	
			
		}
		return true;
	}
	/**
	 * Finds an entry which starts with letter pointed by global variable index. Its length cannot exceede the size of the board
	 */
	public CwEntry findEntry(Crossword cw) throws Exception {
		
		LinkedList <Entry> tmpList = null;;
		Entry tmp = null;
		CwEntry rand = null;
		
		if(cw.isEmpty()){
			generatePassword(cw, cw.getBoardCopy());
			resetIndex();
		}
		
		if (index < password.getWord().length()){
			
			tmpList = cw.getCwDB().findAll(password.getWord().charAt(index)+"[a-z,A-Z]{0,"+Integer.toString(cw.getBoardCopy().getWidth()-1)+"}");
		
			do{
			
				tmp = tmpList.get(rnd.nextInt(tmpList.size()));
				tmpList.remove(tmp);
			
			}while(!tmpList.isEmpty() && cw.contains(tmp.getWord()));
		
			rand = new CwEntry(tmp.getWord(), tmp.getClue(), 0, index, Direction.HORIZ);
			index++;
			}
	
		return rand;
	}

	/**
	 * adds an CwEntry to the correct position on the board.
	 */
	public void updateBoard(Board b, CwEntry e) {
		if(e.getD() == Direction.VERT){
			for(int i = e.getY(); i < e.getWord().length(); i++){
				b.getCell(i, e.getX()).setConntent(e.getWord().substring(i-e.getY(), i-e.getY()+1));//(i-e.getY()));
				
			}
		}else if(e.getD() == Direction.HORIZ){
		for(int i = e.getX();i < e.getWord().length();  i++){
			
			b.getCell(e.getY(), i).setConntent(e.getWord().substring(i-e.getX(), i-e.getX()+1));
			
			
		}
		}
		
	}
	
	public String getPass(){
		return password.getWord();
	}
	
	public void resetIndex (){
		index = 0;
	}

}
