package viewer;
import java.io.IOException;

import browser.CwBrowser;
import controller.crosswordController;

public class Main {
    public static void main(String[] args) {

	crosswordController theController;
	CwBrowser theBrowser;
	CrosswordView theView;

	String cwDataBase = "cwdb.txt";
	String folderWithCws = "C:\\Users\\Public\\cwbase";

	theView = new CrosswordView();		
	try {
	    theBrowser = new CwBrowser(cwDataBase, folderWithCws);
	    theController = new crosswordController(theBrowser, theView);

	} catch (IOException e) {
	    System.out.println("Blad wejscia/wyjscia! sprawdz pliki wejsciowe i wyjsciowe!");
	}


    }
}
