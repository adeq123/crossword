package browser;

import java.io.*;
import java.util.*;
import model.*;

public class CwReader implements Reader{

	File folderWithCWs;
	LinkedList <Crossword> CwList = new LinkedList<Crossword>();
	
	public CwReader(String folderPath){
		this.folderWithCWs = new File(folderPath);
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
			CwList.add(new Crossword(oneFilePath));
			}
		}
		
	public Crossword loadOneCw(File oneFilePath) throws IOException{
		
	return new Crossword(oneFilePath);
		
	}
	@Override
	public void setFolder(String folderPath) {
		this.folderWithCWs = new File(folderPath);
		
	}

	@Override
	public LinkedList <Crossword> getAllCws() {
		return CwList;
		
	}


	
	
	
}
