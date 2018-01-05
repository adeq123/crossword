package FitedExeptions;

/**
 * An exception which is thrown when no matching words were found
 * @author RoguskiA
 *
 */
public class NoActualCw extends Exception{

    public NoActualCw() {

	super("There is not actual crossword generated. Genrate the crossword or load one from the file ");

    }

    public NoActualCw(String messeage) {

	super(messeage);

    }

}