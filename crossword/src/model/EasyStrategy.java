package model;

import dictionary.Entry;
import java.util.*;
import FitedExeptions.*;
/**
 * Concrete class of Strategy abstract class. The algorithms generates the simple crossword for
 * Crossword given. Easy strategy generates plain crossword where the first column is the solution
 * of the crossword and all of the crosswords phrases are horizontal
 * @author ADRO
 */
public class EasyStrategy extends Strategy{

    private CwEntry password;
    Random rnd = new Random();
    int passwordLetterIndex = 0;

    /**
     * Generates a random password (solution) based on database used in crossword. Password i checked by checkPassword() method
     * @param cross a Crossword whose database is used to generate the password
     * @param board a Board where password is meant to be placed. Used as separate parameter so that it is possible to work on 
     * on the Board copy (separate instance)
     * @return a String, random password from crossword database
     * @throws Exception
     */
    public CwEntry generatePassword(Crossword cross, Board board) throws NoMatchingWords{

	LinkedList <Entry> passList = new LinkedList<Entry>();
	passList = cross.getCwDB().findAll("[a-z,A-Z]{1,"+Integer.toString(board.getHeight())+"}");

	if(passList.isEmpty()) throw new NoMatchingWords();
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
	    if (cross.getCwDB().findAll(key+"[a-z,A-Z]{1,"+Integer.toString(board.getWidth()-1)+"}").size() <= passMap.get(key)){
		return false;
	    }
	}
	return true;
    }

    /**
     * Finds an entry which starts with letter pointed by class variable index. Its length cannot exceed the size of the board. 
     * @return CwEntry with correct size and starting with correct word or null when crossoword is full
     */
    public CwEntry findEntry(Crossword cw) throws NoMatchingWords {

	LinkedList <Entry> tmpList = null;;
	Entry tmp = null;
	CwEntry rand = null;

	if(cw.isEmpty()){
	    generatePassword(cw, cw.getBoardCopy());
	    resetIndex();
	}

	if (passwordLetterIndex < password.getWord().length()){
	    tmpList = cw.getCwDB().findAll(password.getWord().charAt(passwordLetterIndex)+"[a-z,A-Z]{0,"+Integer.toString(cw.getBoardCopy().getWidth()-1)+"}");
	    do{
		tmp = tmpList.get(rnd.nextInt(tmpList.size()));
		tmpList.remove(tmp);
	    }while(!tmpList.isEmpty() && cw.contains(tmp.getWord()));
	    rand = new CwEntry(tmp.getWord(), tmp.getClue(), 0, passwordLetterIndex, Direction.HORIZ);
	    passwordLetterIndex ++;
	}

	return rand;
    }

    /**
     * Adds an CwEntry to the correct position on the board.
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
	passwordLetterIndex = 0;
    }

}
