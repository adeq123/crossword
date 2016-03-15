package browser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import model.*;
import dictionary.*;
import model.*;

public class CwBrowser {

	private LinkedList<Crossword> crosswords = new LinkedList <Crossword> (); // vector of read crosswords
	private ListIterator <Crossword> cwListIterator;// = crosswords.listIterator();
    private Crossword actual; // actual crossword
    private InteliCwDB defaultCwDB; // crossword database
    private CwWriter myWriter;
    private CwReader myReader;
    private Crossword tmp; // actual crossword
    private EasyStrategy easyStrategy;
    private HardStrategy hardStrategy;
    private String cwFolderPathStore;
    
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
    	myReader = new CwReader(cwFolderPathStore);
    	crosswords = new LinkedList<>();
    	hardStrategy = new HardStrategy(); 
    	easyStrategy = new EasyStrategy(); 
    }
    /**
     * Generates a crossword for a given dimensions and choosen strategy
     * @param height an int, height of crossword board
     * @param width an int, width of crossword board
     * @param strategyID, 0 = Easy strategy, 1 = hard strategy
     * @throws Exception
     */
    public void generateCW(int height, int width, int strategyID) throws Exception{
    	
    	tmp = new Crossword(height, width);
    	tmp.setCwDB(defaultCwDB);
    	if(strategyID == 1){
    		tmp.generate(hardStrategy);
    		
    		} else{
    			
    			tmp.generate(easyStrategy);
    		}
    	
    	updateIterator();
    	iteratorToTheEnd();
    	System.out.print(tmp.printBoard());
    	saveAndMakeTmpActual();
    }
    
    /**
     * save temporary (just generated) crossword to the folder (cwFolderPathStore) and makes it actual crossword. 
     * @throws Exception
     */
    public void saveAndMakeTmpActual() throws Exception{
    	actual = tmp;
    	crosswords.add(tmp);
    	myWriter.WriteCW(tmp);
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
     * moves list iterator further
     */
    public void nextCW(){
    	if(cwListIterator.hasNext()){
    		actual = cwListIterator.next();
    	}else System.out.println("To jest ostatnia krzyzowka na liscie...");
    }

    /**
     * moves list iterator prefious
     */
    public void previousCW(){
    	if(cwListIterator.hasPrevious()){
    	actual = cwListIterator.previous();
    	} else System.out.println("To jest pierwsza krzyzowka na liscie...");
    }    
    
    /**
     * moves list iterator to the end of the list
     */
    private void iteratorToTheEnd(){
    	while(cwListIterator.hasNext())
    		 cwListIterator.next();		
    }
    /**
     * Saves actual crossword to the directory (cwFolderPathStore)
     * @throws Exception
     */
    private void saveActual() throws Exception{
    	myWriter.WriteCW(actual);
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
}
