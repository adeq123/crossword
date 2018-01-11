package FitedExeptions;

/**
 * An exception which is thrown when you have no crossword to work on (nothing was neither generated nor loaded)
 * and you try to make some operations on this "NULL" type instance 
 * @author RoguskiA
 *
 */
public class NoActualCw extends Exception{

    /**
     * Automatically generated UID
     */
    private static final long serialVersionUID = -2800701348397827836L;

    public NoActualCw() {
	super("There is no actual crossword generated. Genrate the crossword or load one from the file ");
    }

    public NoActualCw(String messeage) {
	super(messeage);
    }

}