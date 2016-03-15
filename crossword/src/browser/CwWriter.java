package browser;

import model.Crossword;

import java.io.*;
import java.util.*;

public class CwWriter implements Writer{
	
	File cwFolder;
	

	/**
	 * Method which writes corssword to the folder
	 * @param cw, Crossword to be written to the folder
	 * @throws FileNotFoundException 
	 */
	
	public CwWriter (String cwFolderPath) throws FileNotFoundException{
		
		this.cwFolder = new File(cwFolderPath);
		
		
		
	}
	
	public void WriteCW(Crossword cw) throws Exception {
		
		String cwFileName =Long.toString(getUniqueID());
		File cwTxt = new File(cwFolder.getAbsolutePath()+"\\"+cwFileName);
		cwTxt.createNewFile();
		
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(cwTxt.getAbsolutePath())));
		
		String cwInString = cw.printBoard();
		String [] cwWordByWord = cwInString.split("\n");
		for(String word : cwWordByWord)
			writer.write(word+System.lineSeparator());
		writer.close();
		
		
	}

	/**
	 * 
	 * @return long, Number of ms since 1970. Unique ID used for file naming
	 */
	public long getUniqueID() {
		return new Date().getTime();
	}

}
