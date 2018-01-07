package browser;

import java.io.*;
import java.util.*;

import dictionary.InteliCwDB;
import model.*;
/**
 * This class implements Reader interface and model crossword reading from TXT file.
 * @author ADRO
 */
public class CwReader implements Reader{

    private File folderWithCWs;
    private InteliCwDB entriesDB; 

    public CwReader(String folderPath, InteliCwDB entriesDB){
	this.folderWithCWs = new File(folderPath);
	this.entriesDB = entriesDB;
    }

    /**
     * The method loads the crossword from TXT file to the linked list, line bz line
     * @param oneFilePath, crossword file path
     * @return LinkedList, List with crossword file loaded 
     * @throws IOException
     */
    public LinkedList <String> loadSingleCWToList(File oneFilePath) throws IOException{
	String fileLine;
	LinkedList <String> listWithLines = new LinkedList <String> ();
	BufferedReader czytaj = new BufferedReader(new FileReader(oneFilePath.getAbsolutePath()));
	while(((fileLine = czytaj.readLine()) != null)){ 	
	    listWithLines.add(fileLine);
	}
	czytaj.close();
	return listWithLines;
    }
    /**
     * The method creates new crossword based on txt crossword file
     * @param oneFilePath
     * @return
     * @throws IOException
     */
    public Crossword loadOneCw(File oneFilePath) throws IOException{
	return new Crossword(oneFilePath, entriesDB);
    }

    /**
     * The method creates a crosswords list based on crossword TXT files located in folderWithCWs folder
     * @throws NumberFormatException
     * @throws IOException
     * @return list of crosswords from folderWithCWs folder
     */
    public LinkedList <Crossword> getAllCws() throws IOException, NumberFormatException {
	LinkedList <Crossword> CwList = new LinkedList<Crossword>();
	File [] filesPathsArray = folderWithCWs.listFiles();
	for(File oneFilePath:filesPathsArray){	
	    CwList.add(new Crossword(oneFilePath, entriesDB));
	}
	return CwList;

    }
    @Override
    public void setFolder(String folderPath) {
	this.folderWithCWs = new File(folderPath);

    }

    public void setDB(InteliCwDB defaultCwDB) {
	this.entriesDB = defaultCwDB;

    }
}
