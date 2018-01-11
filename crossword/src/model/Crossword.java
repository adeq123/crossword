/**
 * This class model a simple Crossword with Board of given size, database to search for words / clues, list of entries use on the board
 * and strategy used to generate the Crossword.
 * @author ADRO
 * @version 1.0
 */
package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import FitedExeptions.NoMatchingWords;
import FitedExeptions.WrongCoordinatesException;
import dictionary.InteliCwDB;
import model.Strategy.strategyID;

public class Crossword {

    private LinkedList<CwEntry> entriesUsed = new LinkedList<CwEntry>(); 
    private Board crosswordBoard; 
    private InteliCwDB entriesDataBase; 
    private strategyID strID;

    /**
     * Construct new crossword for a given height and width
     * @param h height of crossword
     * @param w width of crossword
     */
    public Crossword (int h, int w){
	this.crosswordBoard = new Board(h,w);
    }

    /**
     * Construct new crossword based on the crosswords txt file stored
     * @param oneFile a path to the crossword to be loaded
     * @param cwdb a database to be used to find clues for this crossword
     * @throws IOException
     */
    public Crossword (File oneFile, InteliCwDB cwdb) throws IOException{
	this.entriesDataBase = cwdb;
	String line;
	BufferedReader read = new BufferedReader(new FileReader(oneFile.getAbsolutePath()));
	Strategy s;

	//check strategy
	line = read.readLine();
	if(line.equals("Hard")){
	    this.strID = strategyID.Hard;
	    s = new HardStrategy();
	}else{
	    s = new EasyStrategy();
	}

	// cunstruct the board of given size
	line = read.readLine();
	this.crosswordBoard = new Board(Integer.parseInt(line.split(" ")[0]), Integer.parseInt(line.split(" ")[1]));		

	read.readLine(); //skip line
	//read the entries line by line and build the crossword
	String word, clue;
	int x,y;
	Direction dir;

	while((!(line = read.readLine()).equals("***end***"))){
	    word = line.split(" ")[0];
	    clue = cwdb.get(word).getClue();
	    x = Integer.parseInt(line.split(" ")[1]);
	    y = Integer.parseInt(line.split(" ")[2]);
	    if(line.split(" ")[3].equals("VERT")){
		dir = Direction.VERT;
	    }else{
		dir = Direction.HORIZ;
	    }
	    this.addCwEntry(new CwEntry(word, clue, x, y, dir), s);
	}
	read.close();
    }

    /**
     * 
     * @return read only iterator of entries (all entries in the given crossword)
     */
    public ListIterator<CwEntry> getROEntryIter(){
	return Collections.unmodifiableList(entriesUsed).listIterator();
    }

    /**
     * Method which checks if the given word is a part of the crossword
     * @param word, String to search for in the crossword
     * @return boolean indicating if the word is a part of crossword or not
     */
    public boolean contains(String word){
	boolean result = false;
	for(int i = 0; i < this.entriesUsed.size() && !result; i++) {
	    if (entriesUsed.get(i).getWord().equals(word)){
		result = true;
	    }		
	}
	return result;
    }


    /**
     * Method which add new CwEntry (standard entry + possistion and status) to the crossword according to preferd strategy
     * @param cwe CwEntry to be set
     * @param s Strategy to be used
     */
    public final void addCwEntry(CwEntry cwe, Strategy s){
	entriesUsed.add(cwe);
	s.updateBoard(this.crosswordBoard,cwe);
    }

    /**
     * Generates a crossword for a given strategy
     * @param strategy
     * @throws WrongCoordinatesException 
     * @throws Exception 
     */
    public final void generate(Strategy strategy) throws NoMatchingWords, WrongCoordinatesException{

	if(strategy instanceof EasyStrategy)
	    strID = strategyID.Easy;
	else strID = strategyID.Hard;
	CwEntry e;
	while((e = strategy.findEntry(this)) != null){
	    addCwEntry(e,strategy);
	}
    }
    /**
     * 
     * @return true if crossword is empty, true otherwise
     */
    public boolean isEmpty(){
	return entriesUsed.isEmpty();
    }

    /**
     * Prints a crosswords board sign by sign
     * @return, String representation of the crossword
     */
    public String toString(){
	String cwString ="";
	
	for(int r = 0;r <  crosswordBoard.getHeight();r++){
	    for(int c = 0;c < crosswordBoard.getWidth();c++){
		if(!crosswordBoard.getCell(r, c).checkContent()){
		    cwString = cwString + " ";
		}else{
		    cwString = cwString + crosswordBoard.getCell(r, c).getContent();
		}
	    } cwString =cwString+"\n";
	}
	return cwString;
    }
    
    public Board getBoard(){
	return crosswordBoard;
    }

    /**
     * 
     * @return number of vertical entries in this crossword
     */
    public int noOfVerEntries(){
	int noOfVerEntries = 0;

	for(int i = 0;i < entriesUsed.size();i++)
	    if(entriesUsed.get(i).getD() == Direction.VERT)
		noOfVerEntries++;
	return noOfVerEntries;
    }

    /**
     * 
     * @return number of horizontal entries in this crossword
     */	  
    public int noOfHorEntries(){
	int noOfHorEntries = 0;

	for(int i = 0;i < entriesUsed.size();i++)
	    if(entriesUsed.get(i).getD() == Direction.VERT)
		noOfHorEntries++;
	return noOfHorEntries;
    }

    public strategyID getStartegy(){
	return strID;
    }

    public Board getBoardCopy(){
	return crosswordBoard.copyBoard();
    }

    public void setCwDB(InteliCwDB cwdb){
	this.entriesDataBase = cwdb;
    }


    public InteliCwDB getCwDB(){
	return this.entriesDataBase;
    }
}
