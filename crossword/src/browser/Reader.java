package browser;
import java.io.IOException;
import java.util.LinkedList;

import model.*;

public interface Reader {
	
	public void setFolder(String folderPath);
	public LinkedList <Crossword> getAllCws();

}
