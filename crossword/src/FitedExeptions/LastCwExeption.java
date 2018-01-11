package FitedExeptions;

/**
 * An exception which is if there is no next crossword on the list
 * @author ADRO
 *
 */
public class LastCwExeption extends Exception{

    /**
     * Automatically generated UID
     */
    private static final long serialVersionUID = 4509302159089043982L;

    public LastCwExeption() {
	super("That is the last crossword on the list :( ");
    }

    public LastCwExeption(String messeage) {
	super(messeage);
    }
}
