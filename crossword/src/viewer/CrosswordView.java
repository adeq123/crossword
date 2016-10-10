package viewer;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class CrosswordView extends JFrame{
		
		public final int FRAME_WIDTH = 1200;
		public final int FRAME_HEIGHT = 990;
		
	public CrosswordView(){
		
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints ();
		
		TopPanel topPanel = new TopPanel();
		CwPanel cwPanel = new CwPanel();	
		
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 40; //height of this pane
		
		c.gridx = 0;
		c.gridy = 0;
		c.weighty = 1;
		c.weightx = 1;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		add(topPanel, c);
		
		
		
		c.gridx = 0;
		c.gridy = 1;
		c.weighty = 5;
		c.weightx = 1;
		c.anchor = GridBagConstraints.CENTER;
		add(cwPanel, c);
		
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
		

		
	public class TopPanel extends JPanel{
		
		private JPanel generateCrossPane;
		private JPanel loadPane;
		private JPanel controlPane;
		
		public TopPanel(){
			
			setLayout(new GridLayout(1,3));
			generateCrossPane = new JPanel();
			generateCrossPane.setBorder(BorderFactory.createTitledBorder("Nowa krzyzowka"));
		
			JSpinner cwHeight = new JSpinner();
			JSpinner cwWidth = new JSpinner();
			
			generateCrossPane.add(cwHeight);
			generateCrossPane.add(cwWidth);
			
			loadPane = new JPanel();
			loadPane.setBorder(BorderFactory.createTitledBorder("Z pliku..."));
		
			controlPane = new JPanel();
			controlPane.setBorder(BorderFactory.createTitledBorder("Kontrola"));
			
			this.add(generateCrossPane);
			this.add(loadPane);
			this.add(controlPane);
					
				
		}
		
	}
	
	public class CwPanel extends JPanel{
		
		public CwPanel(){
			this.add(new JButton("huj"));
		}
		
	}

}
