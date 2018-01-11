package controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.itextpdf.text.DocumentException;

import FitedExeptions.FirstCwException;
import FitedExeptions.LastCwExeption;
import FitedExeptions.NoActualCw;
import FitedExeptions.NoMatchingWords;
import FitedExeptions.WrongCoordinatesException;
import browser.CwBrowser;
import model.*;
import viewer.*;

/**
 * A controller part of MVC model. Being a connection point between Model and Viewer
 * @author ADRO
 *
 */
public class crosswordController {

    private CwBrowser theModel;
    private CrosswordView theView;
    private CwPanel cwPanel;
    private String newDatabasePath;
    private String crosswordPath;
    private Strategy actualStrategy;

    public crosswordController (CwBrowser theModel, CrosswordView theView) {

	this.theModel = theModel;
	this.theView = theView;
	this.cwPanel = theView.getCwPanel();
	this.actualStrategy = new HardStrategy();
	this.theView.disableNext();
	this.theView.disablePrevious();
	this.theView.addGenerateListener(new GenerateListener());
	this.theView.addSolveListener(new SolveListener());
	this.theView.addDotsListener(new DotsListener());
	this.theView.addLoadListener(new LoadListener());
	this.theView.addSaveListener(new SaveListener());
	this.theView.addLoadCwDotsListener(new LoadCwDotsListener());
	this.theView.addLoadCwListener(new LoadCwListener());
	this.theView.addPrintCwListener(new PrintCwListener());
	this.theView.addEasyStrategyListener(new EasyStrategyListener());
	this.theView.addHardStrategyListener(new HardStrategyListener());
	this.theView.addNextListener(new NextListener());
	this.theView.addPreviousListener(new PreviousListener());
    }

    public class GenerateListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
	    try {
		theModel.generateCW(theView.getCwHight(), theView.getCwWidth(), actualStrategy);
		cwPanel.setActualCw(theModel.getActualCws());
		cwPanel.printSolvable();
		if(theModel.isMoreThenOneCw()){
		    theView.enableNext();
		    theView.enablePrevious();
		}
	    } catch (NoMatchingWords e1) {
		JOptionPane.showMessageDialog(theView,"No crossword was found for input entered. Please chage input data or database.");
		e1.printStackTrace();
	    } catch (IOException e1) {
		JOptionPane.showMessageDialog(theView,"Input / output error. Please check files used");
		e1.printStackTrace();
	    } catch (WrongCoordinatesException e1) {
		JOptionPane.showMessageDialog(theView,"Incorrect coordinates of a word!");
		e1.printStackTrace();
	    } catch (ParseException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	    } catch (NoActualCw e1) {
		JOptionPane.showMessageDialog(theView,"No actual cw please generate the crossword!");
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
		    cwPanel.printSolvable();
		}catch (IOException e1) {
		    JOptionPane.showMessageDialog(theView, "Input / output error. Please selected correct crossword file");
		} catch (ParseException e1) {
		    JOptionPane.showMessageDialog(theView, "Single letter only!");
		    e1.printStackTrace();
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
    
    public class PrintCwListener implements ActionListener{

	public void actionPerformed(ActionEvent e) {
	    String fileName = null;
	    int returnVal = theView.getCwChooser().showSaveDialog(null);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
		fileName = theView.getCwChooser().getSelectedFile().getAbsolutePath();
	    }

	    try {
		theModel.printActual(fileName + ".pdf", cwPanel);
	    } catch (DocumentException  e1) {
		JOptionPane.showMessageDialog(theView, "Problem with the file you want write to. Please check the file!");
		e1.printStackTrace();
	    }catch (IOException   e2) {
		JOptionPane.showMessageDialog(theView, "Problem with image you want to print out!");
		e2.printStackTrace();
	    }
	}
    }

    public class EasyStrategyListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
	    actualStrategy = new EasyStrategy();
	}
    }

    public class HardStrategyListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
	    actualStrategy = new HardStrategy();
	}
    }

    public class NextListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
	    try {
		theModel.nextCW();
		theView.enablePrevious();
	    } catch (LastCwExeption e2) {
		theView.disableNext();
		JOptionPane.showMessageDialog(theView, e2.getMessage());
	    }
	    cwPanel.setActualCw(theModel.getActualCws());
	    try {
		cwPanel.printSolvable();
	    } catch (ParseException e1) {
		e1.printStackTrace();
	    }
	}
    }

    public class PreviousListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
	    try {
		theModel.previousCW();
		theView.enableNext();
	    } catch (FirstCwException e2) {
		theView.disablePrevious();
		JOptionPane.showMessageDialog(theView, e2.getMessage());
	    }
	    cwPanel.setActualCw(theModel.getActualCws());
	    try {
		cwPanel.printSolvable();
	    } catch (ParseException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	    }
	}

    }
}
