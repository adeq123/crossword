package browser;

import model.Crossword;
import model.CwEntry;

import java.io.*;
import java.util.*;

import FitedExeptions.NoActualCw;

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
	
	public void WriteCW(Crossword cw) throws IOException  {
		
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

	public void WriteCW(Crossword cw, String name) throws IOException {
		
		String cwFileName = name;
		File cwTxt = new File(cwFolder.getAbsolutePath()+"\\"+cwFileName+".txt");
		cwTxt.createNewFile();
		
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(cwTxt.getAbsolutePath())));
		
		writer.write(cw.getStartegy().toString()+System.lineSeparator());
		writer.write(Integer.toString(cw.getBoard().getWidth()) + " " + Integer.toString(cw.getBoard().getHeight()) + System.lineSeparator());
		writer.write("--------------List of words-----------------"+System.lineSeparator());
		
		ListIterator <CwEntry> entriesIter = cw.getROEntryIter();
		CwEntry tmpEntry;
		while(entriesIter.hasNext()){
			tmpEntry = entriesIter.next();
			writer.write(tmpEntry.getWord() + " "  + tmpEntry.getX() + " " + tmpEntry.getY() + " " + tmpEntry.getD() + " " + System.lineSeparator());
		}
		
		writer.write( "***end***" + System.lineSeparator()+ System.lineSeparator());
		
		String cwInString = cw.printBoard();
		String [] cwLineByLine = cwInString.split("\n");
		for(String word : cwLineByLine)
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
	
	public void changeCwWriterPath(String path){
		cwFolder = new File(path);
	}

}
