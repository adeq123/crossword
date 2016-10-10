package FitedExeptions;

public class WrongCoordinatesException extends Exception{

	public WrongCoordinatesException() {
		super("Wrong coordinates. Word needs to be either Horizontal or Vertical");
	}
	
	public WrongCoordinatesException(String messeage) {
		super(messeage);
	}

}
