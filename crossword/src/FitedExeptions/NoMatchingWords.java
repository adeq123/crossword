package FitedExeptions;

/**
 * An exception which is thrown when no matching words were found
 * @author RoguskiA
 *
 */
public class NoMatchingWords extends Exception{

    public NoMatchingWords() {

	super("No words which match your criteria. Crossword cannot be generated please change size or fill DB with new words and generate again ");

    }

    public NoMatchingWords(String messeage) {

	super(messeage);

    }

}
