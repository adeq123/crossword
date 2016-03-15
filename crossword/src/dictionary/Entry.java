/**
 * This class model a single entry to our crossword
 * @author RoguskiA
 * @version 1.0
 */
package dictionary;

public class Entry {

	private String word; /** String which is soolution to our clue	*/
	private String clue; /** part of the Entry, question asked */
	
	/**
	 * Construct a new Entry using a given word and clue
	 * @param word a String which is soolution to our clue	
	 * @param clue a String, part of the Entry, question asked
	 */
	public Entry (String word, String clue){
		this.word = word;
		this.clue = clue;

	}
	
	public String getWord(){
		return this.word;
	}
	
	public String getClue(){
		return this.clue;
	}
}
