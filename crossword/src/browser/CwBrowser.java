package browser;

import java.awt.Component;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import com.itextpdf.text.DocumentException;

import model.*;
import dictionary.*;
import model.*;
import FitedExeptions.*;

public class CwBrowser {

	private LinkedList<Crossword> crosswords = new LinkedList <Crossword> (); // vector of read crosswords
	private ListIterator <Crossword> cwListIterator;// = crosswords.listIterator();
    private Crossword actual; // actual crossword
    private CwWriter myWriter;
    private CwReader myReader;
    private Crossword tmp; // actual crossword
    private String cwFolderPathStore;
    private InteliCwDB defaultCwDB;
    private boolean previousWasPressed = false;
    private boolean nextWasPressed = false;
    
    /**
     * Construct an abstract browser of crosswords
     * @param cwDataBasePath a String, path to the base of Entries (txt)
     * @param cwFolderPathStore a String, path to the folder where all crosswords are stored
     * @throws IOException
     */
    public CwBrowser (String cwDataBasePath, String cwFolderPathStore) throws IOException{
    	
    	this.cwFolderPathStore = cwFolderPathStore;
    	defaultCwDB = new InteliCwDB(cwDataBasePath);
    	myWriter = new CwWriter(cwFolderPathStore);
    	myReader = new CwReader(cwFolderPathStore, defaultCwDB);
    	crosswords = new LinkedList<>();
    }
    /**
     * Generates a crossword for a given dimensions and choosen strategy
     * @param height an int, height of crossword board
     * @param width an int, width of crossword board
     * @param strategyID, 0 = Easy strategy, 1 = hard strategy
     * @throws IOException 
     * @throws WrongCoordinatesException 
     * @throws NoActualCw 
     * @throws Exception
     */
    public void generateCW(int height, int width, Strategy strID) throws NoMatchingWords, IOException, WrongCoordinatesException, NoActualCw{
    	
    	tmp = new Crossword(height, width);
    	tmp.setCwDB(defaultCwDB);
    	tmp.generate(strID);
    	updateIterator();
    	iteratorToTheEnd();
    	saveAndMakeTmpActual(cwFolderPathStore, Long.toString(myWriter.getUniqueID()));
    }
    

    
    /**
     * save temporary crossword to the folder FilePath and makes it actual crossword. 
     * @throws IOException 
     * @throws Exception
     */
    public void saveAndMakeTmpActual(String path, String name) throws IOException, NoActualCw{
    	actual = tmp;
    	if(actual == null)
    		throw(new NoActualCw ());
    	crosswords.add(tmp);
    	myWriter.changeCwWriterPath(path);
    	myWriter.WriteCW(tmp, name);
    	updateIterator();
    	iteratorToTheEnd();
    	
    }
    
    /**
     * 
     * @return List with all loaded crosswords
     */
    public Crossword getActualCws (){
    	return this.actual;
    }
    
    /**
     * loads all crosswords from folder to linked list
     * 
     * @throws NumberFormatException
     * @throws IOException
     */
    public void loadAllCwsFromFolder () throws NumberFormatException, IOException{
    	myReader.loadAllCwsToList();
    	crosswords = myReader.getAllCws();
    	cwListIterator = crosswords.listIterator();
    	updateIterator();
    	iteratorToTheEnd();
    }
    
    /**
     * Loads single crossword from file
     * @param cwPath, path to one crossword in txt format 
     * @throws IOException 
     */
    
    public void loadSingleCwAndMakeAcutal(String cwPath) throws IOException{
    	actual = myReader.loadOneCw(new File(cwPath));
    	actual.printBoard();
    	crosswords.add(actual);
    	cwListIterator = crosswords.listIterator();
    	updateIterator();
    	iteratorToTheEnd();
    }
    
    /**
     * moves list iterator further
     * @throws LastCwExeption 
     */
    public void nextCW() throws LastCwExeption{
    	if(!nextWasPressed){
      		nextWasPressed = true;
    		cwListIterator.next();
    		previousWasPressed = false;
    	}
    		if(cwListIterator.hasNext()){
    			actual = cwListIterator.next();
    		}else throw (new LastCwExeption ());	
    }

    /**
     * moves list iterator prefious
     * @throws FirstCwException 
     */
    public void previousCW() throws FirstCwException{
    	
    	if(!previousWasPressed){
    		previousWasPressed = true;
    		cwListIterator.previous();
    		nextWasPressed = false;
    	}
    	
    	if(cwListIterator.hasPrevious()){
    	actual = cwListIterator.previous();
    	} else throw(new FirstCwException());
    }    
    
    /**
     * moves list iterator to the end of the list
     */
    private void iteratorToTheEnd(){
    	while(cwListIterator.hasNext())
    		 cwListIterator.next();		
    }

    /**
     * Prints actual CW to pdf file
     * @throws IOException 
     * @throws DocumentException 
     */
    public void printActual(String fileName, Component cwPanel) throws DocumentException, IOException{
    	
        /* Print Image to PDF */
       
        myWriter.printCwToPdf(CwWriter.getImageFromPanel(cwPanel), fileName);
    }

    /**
     * 
     * @return an int, number of crosswords in the list
     */
    public int getNumberOfCws(){
    	return crosswords.size();
    }
    
    /**
     * checks if actual crossword exists
     * @return
     */
    public boolean checkIfActualExist(){
    	return actual != null;
    }
    
    /**
     * updates iterator to the current state of the list
     */
    public void updateIterator(){
    	cwListIterator = crosswords.listIterator();
    }
    
    /**
     * checks if folder with crosswords is empty
     * @return true if fodler with crosswords is empty
     */
    public boolean folderEmpty(){
    	File folderWithCws = new File(cwFolderPathStore);
    	return folderWithCws.list().length == 0;
    }
    
    /**
     * Changes the path to the database with clues and words
     * @param filePath
     * @throws IOException
     */
    public void changeDataBasePath(String filePath) throws IOException{
    	defaultCwDB = new InteliCwDB(filePath);
    }
    
  
}
