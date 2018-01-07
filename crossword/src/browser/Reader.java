package browser;
import java.io.IOException;
import java.util.LinkedList;

import model.Crossword;
/**
 * This interface models simple crossword reading concept
 * @author ADRO
 *
 */
public interface Reader {

    public void setFolder(String folderPath);
    public LinkedList <Crossword> getAllCws() throws IOException, NumberFormatException;

}
