/**
 * this class model a simple data base, allows us to conduct some operations on it
 * @author RoguskiA
 * @version 1.0
 */
package dictionary;

import java.io.*;
import java.util.LinkedList;

public class CwDB{
	
	private String filename; /** filename of source of the database **/
	protected LinkedList <Entry> dict = new LinkedList <Entry>(); /** a listed list of our entries **/
	 
	/**
	 * construct a new database based on specified file
	 * @param filename a string, path to our input file
	 * @throws IOException 
	 */
	public CwDB(String filename) throws IOException{
		this.filename = filename;
		this.createDB(filename);
	}
	
	/**
	 * Adds a new Entry to our Linked List
	 * @param word a String, Word parameter of our new Entry
	 * @param clue a String, Clue parameter of our new Entry
	 * @see Entry
	 */
	public void add(String word, String clue){
		System.out.println(word+" "+clue);
		dict.add(new Entry(word,clue));
		
	}
	
	public void add(int i, Entry en){
		
		dict.add(i, en);
		
	}
	
	/**
	 * Returns an Entry from our Linked List which includes specified word
	 * @param word a string by which we search for a word
	 * @return
	 */
	public Entry get (String word){
		
		for(int i=0; i<=dict.size()-1;i++) // traverse through the list
			if(dict.get(i).getWord().contains(word))
				return dict.get(i);
			
	
		
		return null;
	}
	
	/**
	 * Removes an Entry from our List which includes a specified word
	 * @param word A string which allows us to find an Entry do delete
	 */
	public void remove(String word){
		
		for(int i=0; i<=dict.size()-1;i++) // traverse through the list
			if(dict.get(i).getWord().equals(word)) 
				dict.remove(i);
		
	}
	
/**
 * Saves LinkedList to the specified file	
 * @param filename a path to the file where list of entries is saved
 */
	public void saveDB (String filename){
		
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
	              new FileOutputStream(filename), "utf-8"))) {
			for(int i=0; i<=dict.size()-1;i++)
				writer.write(i+" "+dict.get(i).getWord()+System.lineSeparator()); //writer.write(i+". "+dict.get(i).getClue()+" "+dict.get(i).getWord()+System.lineSeparator());
			
			writer.close();
			
	}catch (IOException ex) {
		  // report
	} 
	}
	
	/**
	 * Returns the size of the list
	 * @return an int, the size of the list
	 */
	public int getSize(){
		
		return dict.size();
				
	}
	
	/**
	 * Creates the List based on the given filename
	 * @param filename source of the list
	 * @throws IOException the exception is throwed when file doesn't exist
	 */
	protected void createDB (String filename) throws IOException{
		
		String word;
		String clue;
		
		BufferedReader czytaj = new BufferedReader(new FileReader(filename));
		
		while(((word = czytaj.readLine()) != null) && ((clue = czytaj.readLine()) != null)){	
			
			add(word,clue);	
		}
	}
	
	/**
	 * Prints whole list
	 */
	public void printOut(){
		
		for(int i = 0; i <= dict.size()-1; i++){
			
			System.out.println(i+". "+dict.get(i).getClue()+" "+dict.get(i).getWord());
		}
		
	}
}
