package controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import FitedExeptions.NoActualCw;
import FitedExeptions.NoMatchingWords;
import FitedExeptions.WrongCoordinatesException;
import browser.CwBrowser;
import dictionary.*;
import model.*;
import viewer.*;

public class crosswordController {

	private CwBrowser theModel;
	private CrosswordView theView;
	private CwPanel cwPanel;
	private String newDatabasePath;
	private String crosswordPath;
	
	public crosswordController (CwBrowser theModel, CrosswordView theView) {
		
		this.theModel = theModel;
		this.theView = theView;
		this.cwPanel = theView.getCwPanel();
		this.theView.addGenerateListener(new GenerateListener());
		this.theView.addSolveListener(new SolveListener());
		this.theView.addDotsListener(new DotsListener());
		this.theView.addLoadListener(new LoadListener());
		this.theView.addSaveListener(new SaveListener());
		this.theView.addLoadCwDotsListener(new LoadCwDotsListener());
		this.theView.addLoadCwListener(new LoadCwListener());	
	}
	
	public class GenerateListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
			theModel.generateCW(theView.getCwHight(), theView.getCwWidth(), new HardStrategy());
			cwPanel.setActualCw(theModel.getActualCws());
			cwPanel.printSolvable();
					
			} catch (NoMatchingWords e1) {
				System.out.println("Nie znaleziono hasel dla podanych kryteriow! zmien kryteria lub uzupelnij baze.");
				e1.printStackTrace();
			} catch (IOException e1) {
				System.out.println("Blad wejscia/wyjscia! sprawdz pliki wejsciowe i wyjsciowe!");
				e1.printStackTrace();
			} catch (WrongCoordinatesException e1) {
				System.out.println("Niepoprawne wspolrzedne hasla");
				e1.printStackTrace();
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			
		}
		
	}
	
	public class SolveListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			cwPanel.printSolved();
			
		}
		
	}
	
	public class DotsListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			
			int returnVal = theView.getFileChooser().showOpenDialog(theView);
		    if(returnVal == JFileChooser.APPROVE_OPTION) {
		    	newDatabasePath = theView.getFileChooser().getSelectedFile().getAbsolutePath();
		    }
			
		}
		
	}
	
	public class LoadListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			if(newDatabasePath != null){
				try {	
					theModel.changeDataBasePath(newDatabasePath);
				}catch (IOException e1) {
					JOptionPane.showMessageDialog(theView, "Input / output error. Please selected correct database file");
				}
			}else
				JOptionPane.showMessageDialog(theView, "No file choosen! Please selected correct database file");
			
		    }
			
		}
	
	public class LoadCwDotsListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			
			int returnVal = theView.getCwChooser().showOpenDialog(theView);
		    if(returnVal == JFileChooser.APPROVE_OPTION) {
		    	crosswordPath = theView.getCwChooser().getSelectedFile().getAbsolutePath();
		    }
			
		}
		
	}
	public class LoadCwListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			if(crosswordPath != null){
				try {	
					
					theModel.loadSingleCwAndMakeAcutal(crosswordPath);
					cwPanel.setActualCw(theModel.getActualCws());
				
					
				}catch (IOException e1) {
					
					JOptionPane.showMessageDialog(theView, "Input / output error. Please selected correct crossword file");
				}
			}else
				JOptionPane.showMessageDialog(theView, "No file choosen! Please selected correct crossword file");
			
		    }
			
		}
	public class SaveListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			
			int retrival = theView.getFileSaver().showSaveDialog(null);			
		    if (retrival == JFileChooser.APPROVE_OPTION) {
		        try {

		            theModel.saveAndMakeTmpActual(theView.getFileSaver().getSelectedFile().getParent(), theView.getFileSaver().getSelectedFile().getName());		            
		        } catch (IOException ex) {
		        	JOptionPane.showMessageDialog(theView, "Input / output error. Please select proper name and destination");
		            ex.printStackTrace();
		        }catch (NoActualCw ncw) {
		        	JOptionPane.showMessageDialog(theView, ncw.getMessage());
		            ncw.printStackTrace();
		        }
		    }
			
		    }
			
		}
	
}
