/**
 * The class model a database with more functionalities than CwDB
 * @author RoguskiA
 * @version 1.0
 * @see CwDB
 */
package dictionary;

import java.io.IOException;
import java.util.*;
import java.util.regex.*;

public class InteliCwDB extends CwDB{

	private String fileName;
	
	/**
	 * construct a new database based on specified file
	 * @param filename a string, path to our input file
	 * @throws IOException 
	 */
	public InteliCwDB (String fileName) throws IOException{
		super(fileName);
	}
	
	/**
	 * Returns a List of Entries which include the specified pattern	
	 * @param pattern a String by which we search for Entries
	 * @return List of entries which include the specified pattern
	 */
	public LinkedList<Entry> findAll (String pattern){
		
		LinkedList <Entry> result = new LinkedList <Entry>();
		Pattern p = Pattern.compile(pattern,Pattern.CASE_INSENSITIVE);
		
		for(int i = 0; i < dict.size(); i++){// traverse through the list
			Matcher m = p.matcher(dict.get(i).getWord());
			if(m.matches())
				result.add(dict.get(i));
			
		}
		return result;
		
	}
	
	/**
	 * Returns random Entry from the list
	 * @return random Entry
	 */
	public Entry getRandom(){
		
		if (dict.size() > 0)
			return dict.get(new Random().nextInt(dict.size())-1);
		return new Entry (" "," ");
		
	}
	
	/**
	 * Return a random Entry from the list with a given length
	 * @param length an int, the length of the searched word
	 * @return random word with a given length
	 */
	public Entry getRandom (int length){
		
		LinkedList <Entry> wordLength = new LinkedList<Entry>();
		
		for(int i = 0; i <= dict.size()-1; i++){// traverse through the list
			
			if(dict.get(i).getWord().length() == length) {
				wordLength.add(dict.get(i));
				}
		}
		
		if(wordLength.size()>0)
			return wordLength.get((new Random().nextInt(wordLength.size()))-1);
		
		return new Entry(" "," ");
	}
	
	/**
	 * Returns a random entry which includes a given pattern in the word 
	 * @param pattern regular expression which describes a pattern searched in the word of the entry
	 * @return random entry which includes a given pattern in the word
	 */
	public Entry getRandom (String pattern){
		
		Pattern p = Pattern.compile(pattern,Pattern.CASE_INSENSITIVE);
		LinkedList <Entry> randomPattern = new LinkedList<Entry>();
		
		for(int i = 0; i <= dict.size()-1; i++){
			
			Matcher m = p.matcher(dict.get(i).getWord());
			
			if(m.matches())
				randomPattern.add(dict.get(i));	
		}
		if(randomPattern.size()>0)
			return randomPattern.get((new Random().nextInt(randomPattern.size()))-1);
		
		return new Entry(" "," ");
		
	}
	
	
	/**
	 * Add new Entry to our List  in a alfabetical way
	 */
	
	public void add (String word, String clue){
		
		/*This block traverse through list and if it find a word which should be before any of the words on list and it adds it there move the rest to the right and come out of method*/
		for(int i = 0; i <= dict.size()-1; i++){
				if(dict.get(i).getWord().compareToIgnoreCase(word)>=0){
					dict.add(i, new Entry(word,clue));
					return;				
				}	
			}
		/*If there is no word which should be before those on the list than add the word at the end*/	
		dict.add(new Entry(word,clue));
	}
	
	
	
}
