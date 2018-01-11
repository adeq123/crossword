package FitedExeptions;

/**
 * The exception is thrown when you hit the first crossword in the list when browsing through them
 * @author ADRO
 */
public class FirstCwException extends Exception{

    /**
     * Automatically generated UID
     */
    private static final long serialVersionUID = -1652185540663878123L;

    public FirstCwException() {
	super("That is the first crossword on the list ... ");
    }

    public FirstCwException(String messeage) {
	super(messeage);
    }
}
