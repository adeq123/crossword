package browser;

import model.*;
/**
 * This interface in general models writing crossword
 * @author ADRO
 *
 */
public interface Writer {

    /**
     * Method which writes corssword to the 
     * @param cw, Crossword to be written to the folder
     * @throws Exception 
     */
    public void WriteCW (Crossword cw, String name) throws Exception;

    /**
     * 
     * @return date in ms which is Unique ID used for file naming
     */
    public long getUniqueID ();

}
