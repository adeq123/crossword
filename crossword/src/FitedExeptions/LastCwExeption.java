package FitedExeptions;

/**
 * An exception which is if there is no next crossword on the list
 * @author RoguskiA
 *
 */
public class LastCwExeption extends Exception{

    public LastCwExeption() {

	super("That is the last crossword on the list :( ");

    }

    public LastCwExeption(String messeage) {

	super(messeage);


    }
}
