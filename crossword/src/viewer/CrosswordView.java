package viewer;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import browser.CwBrowser;
import model.Crossword;

import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class CrosswordView extends JFrame{
		
		public final static int FRAME_WIDTH = 1000;
		public final static int FRAME_HIGHT = 900;
		
		
		private JSpinner cwHeightSpinner;
		private JSpinner cwWidthSpinner;
		private JButton genButton;
		
		private JPanel generateCrossPanel;
		private JPanel loadPanel;
		private JPanel controlPanel;
		

		private JLabel heightLabel;
		private JLabel widthLabel;
		
		private ArrayList <CwField> fieldList;
		
		
		CwPanel cwPanel;
		Crossword actualCW;
		
	public CrosswordView(){
		
		setSize(FRAME_WIDTH, FRAME_HIGHT);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints ();
		
		TopPanel topPanel = new TopPanel();
		cwPanel = new CwPanel();	
		
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 20; //height of this pane
		
		c.gridx = 0;
		c.gridy = 0;
		c.weighty = 0;
		c.weightx = 1;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		add(topPanel, c);
		
		
		
		c.gridx = 0;
		c.gridy = 1;
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 1;
		c.weightx = 1;
		
		add(cwPanel, c);
		
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
		

		
	public class TopPanel extends JPanel{
		
	
		
		
		public TopPanel(){
			
			setLayout(new GridLayout(1,3));
			generateCrossPanel = new JPanel();
			generateCrossPanel.setBorder(BorderFactory.createTitledBorder("Nowa krzyzowka"));
		
			cwHeightSpinner = new JSpinner();
			cwWidthSpinner = new JSpinner();
			heightLabel = new JLabel("Wysokosc ");
			widthLabel = new JLabel("Szerokosc ");
			genButton = new JButton("Generate");
			

			
			generateCrossPanel.add(heightLabel);
			generateCrossPanel.add(cwHeightSpinner);
			generateCrossPanel.add(widthLabel);
			generateCrossPanel.add(cwWidthSpinner);
			generateCrossPanel.add(genButton);
			
			loadPanel = new JPanel();
			loadPanel.setBorder(BorderFactory.createTitledBorder("Z pliku..."));
			
			JButton wczytaj = new JButton("Wczytaj");
			JButton kropki = new JButton("...");
			JTextField sciezka = new JTextField("Sciezka...                                                 ");
			sciezka.setSize(new Dimension(10, 40));
			
			loadPanel.add(sciezka);
			loadPanel.add(kropki);
			loadPanel.add(wczytaj);
			
			controlPanel = new JPanel();
			controlPanel.setBorder(BorderFactory.createTitledBorder("Kontrola"));
			
			JButton drukuj = new JButton("Drukuj");
			JButton zapisz = new JButton("Zapisz");
			JButton rozwiaz = new JButton("Rozwiaz");
			
			controlPanel.add(drukuj);
			controlPanel.add(zapisz);
			controlPanel.add(rozwiaz);
			
			this.add(generateCrossPanel);
			this.add(loadPanel);
			this.add(controlPanel);
				
			
			}	
	
	
		
	}
	
	
	public int getCwHight(){
		
		return (Integer) cwHeightSpinner.getValue();
	}
	
	public int getCwWidth(){
		
		return (Integer) cwWidthSpinner.getValue();
	}
	
	public void addGenerateListener(ActionListener buttonPressed){
		genButton.addActionListener(buttonPressed);
	}
	
	public void clearTheBoard(){
		
		/*
		 * 
		 * 
		 * to be implemented
		 * 
		 */
	}
	
	public CwPanel getCwPanel(){
		return this.cwPanel;
	}
	
	
}
