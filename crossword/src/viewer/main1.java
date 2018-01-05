package viewer;
import java.io.IOException;

import FitedExeptions.NoMatchingWords;
import FitedExeptions.WrongCoordinatesException;
import browser.CwBrowser;
import controller.crosswordController;
import dictionary.*;
import model.*;
import viewer.*;


public class main1 {


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
