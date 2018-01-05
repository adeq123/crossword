package FitedExeptions;


public class FirstCwException extends Exception{

    public FirstCwException() {

	super("That is the first crossword on the list ... ");

    }

    public FirstCwException(String messeage) {

	super(messeage);


    }
}
