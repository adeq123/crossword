/**
 * this class model a simple corssword
 * 
 * @author RoguskiA
 * @version 1.0
 */
package model;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import FitedExeptions.NoMatchingWords;
import FitedExeptions.WrongCoordinatesException;
import dictionary.Entry;
import dictionary.InteliCwDB;
import model.Strategy.strategyID;

public class Crossword {

	private LinkedList<CwEntry> entries = new LinkedList<CwEntry>(); /**List of entries USED on the board*/
	private Board b; /** board that where corssword is displated*/
	private InteliCwDB cwdb; /** database of entries*/
	private final long ID;
	strategyID strID;


	/**
	 * Construct new crossoword for a given height and widith
	 * @param h height of crossword
	 * @param w widith of crossword
	 */
	public Crossword (int h, int w, long ID){
		
		this.b = new Board(h,w);
		this.ID = ID;
		
	}
	
	/**
	 * Construct new crossoword for a given height and widith
	 * @param h height of crossword
	 * @param w widith of crossword
	 */
	public Crossword (int h, int w){	
		this(h,w,-1);
		
	}
	
	public Crossword (File oneFile) throws IOException{	
		
		BufferedReader czytaj = new BufferedReader(new FileReader(oneFile.getAbsolutePath()));
		Strategy s;
		if(Long.getLong(oneFile.getName().substring(0, oneFile.getName().length() - 4)) != null){
			ID = Long.getLong(oneFile.getName().substring(0, oneFile.getName().length() - 4));
		}else
			ID = -1;
		
		if(czytaj.readLine().equals("Hard")){
			this.strID = strategyID.Hard;
			s = new HardStrategy();
		}else
			s = new EasyStrategy();
		
		this.b = new Board(czytaj.readLine().toCharArray()[1], czytaj.readLine().toCharArray()[2]);		
		czytaj.readLine();
		String line= czytaj.readLine();
		
		String word, clue;
		int x,y;
		Direction dir;
		
	  while(((line = czytaj.readLine()) != null)){
		  word = Character.toString(line.toCharArray()[0]);
		  clue = cwdb.get(Character.toString(line.toCharArray()[0])).getClue();
		  x = Integer.parseInt(Character.toString(line.toCharArray()[1]));
		  y = Integer.parseInt(Character.toString(line.toCharArray()[2]));
		  if(Character.toString(line.toCharArray()[2]).equals("VERT")){
			  dir = Direction.VERT;
		  }else
			  dir = Direction.HORIZ;
		  
		  
		  this.addCwEntry(new CwEntry(word, clue, x, y, dir), s);
	//		this.addCwEntry(new CwEntry(line," ",0,y,Direction.HORIZ), new EasyStrategy());
	//		y++;
		}
		czytaj.close();
		
	}
	
	/**
	 * 
	 * @return read only iterator of entries (all entries in the given crossword)
	 */
	public ListIterator<CwEntry> getROEntryIter(){
		return Collections.unmodifiableList(entries).listIterator();
	}
		
	/**
	 * Method which checks if the given word is a part of the crossword
	 * @param word, String to search for in the crossword
	 * @return boolean indicating if the word is a part of crossword or not
	 */
	public boolean contains(String word){
		boolean result = false;
		for(int i = 0; i < this.entries.size() && !result; i++) {
			if (entries.get(i).getWord().equals(word)){
				result = true;
			}		
		}
		return result;
	}
	
	/**
	 * Getter method for board object copy
	 * @return Deep copy of the board
	 */
	public Board getBoardCopy(){
		return b.copyBoard();
	}

	/**
	 * Setter method for corsswords database of Entries (word + clues)
	 * @param cwdb, database to be set
	 */
	public void setCwDB(InteliCwDB cwdb){
		this.cwdb = cwdb;
	}
	
	/**
	 * Getter method for corsswords database of Entries (word + clues)
	 */
	public InteliCwDB getCwDB(){
		return this.cwdb;
	}
	
	/**
	 * Method which add new CwEntry (standard entry + possistion and status) to the crossword according to preferd strategy
	 * @param cwe CwEntry to be set
	 * @param s Strategy to be used
	 */
	public final void addCwEntry(CwEntry cwe, Strategy s){
		  entries.add(cwe);
		  s.updateBoard(this.b,cwe);
		}
	/**
	 * Generates a crossword
	 * @param s
	 * @throws WrongCoordinatesException 
	 * @throws Exception 
	 */
	public final void generate(Strategy s) throws NoMatchingWords, WrongCoordinatesException{
		
		  if(s instanceof EasyStrategy)
			 strID = strategyID.Easy;
		  else strID = strategyID.Hard;
		  
		  CwEntry e;
		  while((e = s.findEntry(this)) != null){
		    addCwEntry(e,s);
		  }
		}
	
	public boolean isEmpty(){
		
		return entries.isEmpty();
		
	}
//needs improvement....
	public String printBoard(){
		
		String cwString ="";
		for(int r = 0;r <  b.getHeight();r++){
			for(int c = 0;c < b.getWidth();c++){
				if(!b.getCell(r, c).checkContent()){
					cwString = cwString + " ";
				}else{
					cwString = cwString + b.getCell(r, c).getContent();
				}
			} cwString =cwString+"\n";
		}
		return cwString;
			
	}
	  public Board getBoard(){
		  return b;
	  }
	  
	  /**
	   * 
	   * @return number of vertical entries in this crossword
	   */
	  public int noOfVerEntries(){
		  int noOfVerEntries = 0;
		  
		  for(int i = 0;i < entries.size();i++)
			  if(entries.get(i).getD() == Direction.VERT)
				  noOfVerEntries++;
		  return noOfVerEntries;
	  }
	  
	  /**
	   * 
	   * @return number of horizontal entries in this crossword
	   */	  
	  public int noOfHorEntries(){
		  int noOfHorEntries = 0;
		  
		  for(int i = 0;i < entries.size();i++)
			  if(entries.get(i).getD() == Direction.VERT)
				  noOfHorEntries++;
		  return noOfHorEntries;
	  }
	  
	  public strategyID getStartegy(){
		  return strID;
	  }
}
