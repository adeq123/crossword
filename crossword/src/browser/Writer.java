package browser;

import model.*;

public interface Writer {

	/**
	 * Method which writes corssword to the 
	 * @param cw, Crossword to be written to the folder
	 * @throws Exception 
	 */
	public void WriteCW (Crossword cw) throws Exception;

	/**
	 * 
	 * @return date in ms which is Unique ID used for file naming
	 */
	public long getUniqueID ();
	
}
