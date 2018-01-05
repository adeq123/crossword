package browser;

import java.io.*;
import java.util.*;

import dictionary.InteliCwDB;
import model.*;

public class CwReader implements Reader{

    private File folderWithCWs;
    private LinkedList <Crossword> CwList = new LinkedList<Crossword>();
    private InteliCwDB defaultCwDB;
    public CwReader(String folderPath, InteliCwDB defaultCwDB){
	this.folderWithCWs = new File(folderPath);
	this.defaultCwDB = defaultCwDB;
    }

    public LinkedList <String> loadSingleCWToList(File oneFilePath) throws IOException{
	String word;
	LinkedList <String> listWithLines = new LinkedList <String> ();
	BufferedReader czytaj = new BufferedReader(new FileReader(oneFilePath.getAbsolutePath()));
	while(((word = czytaj.readLine()) != null)){ //&& ((clue = czytaj.readLine()) != null)){	

	    listWithLines.add(word);
	}
	czytaj.close();
	return listWithLines;
    }

    public void loadAllCwsToList() throws NumberFormatException, IOException {


	LinkedList <String> listWithWords;
	File [] filesPathsArray = folderWithCWs.listFiles();
	for(File oneFilePath:filesPathsArray){	
	    CwList.add(new Crossword(oneFilePath, defaultCwDB));
	}
    }

    public Crossword loadOneCw(File oneFilePath) throws IOException{

	return new Crossword(oneFilePath, defaultCwDB);

    }
    @Override
    public void setFolder(String folderPath) {
	this.folderWithCWs = new File(folderPath);

    }

    public void setDB(InteliCwDB defaultCwDB) {
	this.defaultCwDB = defaultCwDB;

    }

    @Override
    public LinkedList <Crossword> getAllCws() {
	return CwList;

    }





}
