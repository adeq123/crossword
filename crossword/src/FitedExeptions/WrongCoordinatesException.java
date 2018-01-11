package FitedExeptions;
/**
 * Exception which is thrown when coordinates generation algorithm failed.
 * @author ADRO
 *
 */
public class WrongCoordinatesException extends Exception{

    /**
     * Automatically generated UID
     */
    private static final long serialVersionUID = -4848582919166828513L;

    public WrongCoordinatesException() {
	super("Wrong coordinates. Word needs to be either Horizontal or Vertical");
    }

    public WrongCoordinatesException(String messeage) {
	super(messeage);
    }

}
