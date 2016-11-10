package controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import FitedExeptions.NoMatchingWords;
import FitedExeptions.WrongCoordinatesException;
import browser.CwBrowser;
import dictionary.*;
import model.*;
import viewer.*;

public class crosswordController {

	private CwBrowser theModel;
	private CrosswordView theView;
	CwPanel cwPanel;
	public crosswordController (CwBrowser theModel, CrosswordView theView) {
		
		this.theModel = theModel;
		this.theView = theView;
		this.cwPanel = theView.getCwPanel();
		this.theView.addGenerateListener(new GenerateListener());
		
	}
	
	public class GenerateListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
			theModel.generateCW(theView.getCwHight(), theView.getCwWidth(), 1);
			cwPanel.setActualCw(theModel.getActualCws());
				System.out.println(theModel.getActualCws().getBoard().getHeight());
								
			} catch (NoMatchingWords e1) {
				System.out.println("Nie znaleziono hasel dla podanych kryteriow! zmien kryteria lub uzupelnij baze.");
				e1.printStackTrace();
			} catch (IOException e1) {
				System.out.println("Blad wejscia/wyjscia! sprawdz pliki wejsciowe i wyjsciowe!");
				e1.printStackTrace();
			} catch (WrongCoordinatesException e1) {
				System.out.println("Niepoprawne wspolrzedne hasla");
				e1.printStackTrace();
			}
			
			
			
		}
		
	}
}
