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
	
	public Crossword (int h, int w,long ID,File oneFile) throws IOException{	
		this(h,w,ID);
		String word;
		int y = 0;
		BufferedReader czytaj = new BufferedReader(new FileReader(oneFile.getAbsolutePath()));
		while(((word = czytaj.readLine()) != null)){
			this.addCwEntry(new CwEntry(word," ",0,y,Direction.HORIZ), new EasyStrategy());
			y++;
		}
		czytaj.close();
		
	}
	
	/**
	 * 
	 * @return read only iterator of entries (all entries in the given crossword)
	 */
	public Iterator<CwEntry> getROEntryIter(){
		return Collections.unmodifiableList(entries).iterator();
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
	 * Method which add new CwEntry (standard entry + posistion and status) to the crossword according to preverd strategy
	 * @param cwe CwEntry to be set
	 * @param s Strategy to be uesed
	 */
	public final void addCwEntry(CwEntry cwe, Strategy s){
		  entries.add(cwe);
		  s.updateBoard(this.b,cwe);
		}
	/**
	 * Generates a crissword
	 * @param s
	 * @throws Exception 
	 */
	public final void generate(Strategy s) throws Exception{
		
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
}
