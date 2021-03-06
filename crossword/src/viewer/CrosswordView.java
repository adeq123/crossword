package viewer;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.Crossword;

import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.util.ArrayList;
/**
 * This is Viewer part of MVC model. It builds up the visual interface of the crossword
 * @author ADRO
 *
 */
public class CrosswordView extends JFrame{

    public final static int FRAME_WIDTH = 1000;
    public final static int FRAME_HIGHT = 900;

    private JSpinner cwHeightSpinner;
    private JSpinner cwWidthSpinner;
    private JButton genButton;

    private JButton print;
    private JButton save;
    private JButton solve;

    private JPanel generateCrossPanel;
    private JPanel loadPanel;
    private JPanel controlPanel;
    private JPanel loadCwPanel;
    private JPanel strategyPanel;
    private JPanel browserPanel;
    private JButton loadCw;
    private JButton loadCwDots;
    private JButton load;
    private JButton next;
    private JButton previous;
    private JFileChooser chooser;
    private JFileChooser cwChooser;
    private JFileChooser saver;
    private JFileChooser printer;
    private JButton dots;

    private JLabel heightLabel;
    private JLabel widthLabel;

    private JRadioButton easyStrategy;
    private JRadioButton hardStrategy;
    private ButtonGroup group;

    CwPanel cwPanel;
    Crossword actualCW;
    JScrollPane scroller;

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

	setVisible(true);
	setDefaultCloseOperation(EXIT_ON_CLOSE);

	scroller = new JScrollPane(cwPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	scroller.setPreferredSize(new Dimension(0,0));
	add(scroller, c);
	this.revalidate();

	chooser = new JFileChooser();
	FileNameExtensionFilter filter = new FileNameExtensionFilter("Text file crossword database","txt");
	chooser.setFileFilter(filter);

	cwChooser = new JFileChooser();
	FileNameExtensionFilter filterCw = new FileNameExtensionFilter("Text file with crossword ","txt");
	cwChooser.setFileFilter(filterCw);

	saver = new JFileChooser();       
	printer = new JFileChooser();
    }

    public class TopPanel extends JPanel{
	
	public TopPanel(){

	    setLayout(new GridLayout(2,3));
	    generateCrossPanel = new JPanel();
	    generateCrossPanel.setBorder(BorderFactory.createTitledBorder("New crossword"));

	    SpinnerNumberModel hightSpinnerModel = new SpinnerNumberModel(10, 0, 99, 1);
	    cwHeightSpinner = new JSpinner(hightSpinnerModel);
	    cwHeightSpinner.setPreferredSize(new Dimension(33, cwHeightSpinner.getPreferredSize().height));

	    SpinnerNumberModel widthSpinnerModel = new SpinnerNumberModel(10, 0, 99, 1);
	    cwWidthSpinner = new JSpinner(widthSpinnerModel);
	    cwWidthSpinner.setPreferredSize(new Dimension(33, cwWidthSpinner.getPreferredSize().height));

	    heightLabel = new JLabel("Hight ");
	    widthLabel = new JLabel("Width ");
	    genButton = new JButton("Generate");

	    generateCrossPanel.add(heightLabel);
	    generateCrossPanel.add(cwHeightSpinner);
	    generateCrossPanel.add(widthLabel);
	    generateCrossPanel.add(cwWidthSpinner);
	    generateCrossPanel.add(genButton);

	    strategyPanel = new JPanel();
	    strategyPanel.setBorder(BorderFactory.createTitledBorder("Strategy:"));

	    easyStrategy = new JRadioButton("Easy");
	    hardStrategy = new JRadioButton("Hard");
	    hardStrategy.setSelected(true);

	    group = new ButtonGroup();
	    group.add(easyStrategy);
	    group.add(hardStrategy);

	    strategyPanel.add(easyStrategy);
	    strategyPanel.add(hardStrategy);

	    loadPanel = new JPanel();
	    loadPanel.setBorder(BorderFactory.createTitledBorder("Load database"));

	    load = new JButton("Load");
	    dots = new JButton("...");
	    JTextField path = new JTextField("Path...                                                 ");
	    path.setSize(new Dimension(10, 40));

	    loadPanel.add(path);
	    loadPanel.add(dots);
	    loadPanel.add(load);

	    loadCwPanel = new JPanel();
	    loadCwPanel.setBorder(BorderFactory.createTitledBorder("Load a crossword"));

	    loadCw = new JButton("Load ");
	    loadCwDots = new JButton("...");
	    JTextField cwPath = new JTextField("Path...                                                 ");
	    cwPath.setSize(new Dimension(10, 40));

	    controlPanel = new JPanel();
	    controlPanel.setBorder(BorderFactory.createTitledBorder("Control"));

	    loadCwPanel.add(cwPath);
	    loadCwPanel.add(loadCwDots);
	    loadCwPanel.add(loadCw);

	    print = new JButton("Print");
	    save = new JButton("Save");
	    solve = new JButton("Solve");

	    controlPanel.add(print);
	    controlPanel.add(save);
	    controlPanel.add(solve);

	    next = new JButton("Next");
	    previous = new JButton("Previous");

	    browserPanel = new JPanel();
	    browserPanel.setBorder(BorderFactory.createTitledBorder("Browser..."));
	    browserPanel.add(next);
	    browserPanel.add(previous);

	    this.add(generateCrossPanel);
	    this.add(strategyPanel);
	    this.add(loadPanel);
	    this.add(controlPanel);
	    this.add(loadCwPanel);
	    this.add(browserPanel);

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

    public void addSolveListener(ActionListener buttonPressed){
	solve.addActionListener(buttonPressed);
    }

    public void addDotsListener(ActionListener buttonPressed){
	dots.addActionListener(buttonPressed);
    }

    public void addLoadListener(ActionListener buttonPressed){
	load.addActionListener(buttonPressed);
    }

    public void addLoadCwDotsListener(ActionListener buttonPressed){
	loadCwDots.addActionListener(buttonPressed);
    }

    public void addLoadCwListener(ActionListener buttonPressed){
	loadCw.addActionListener(buttonPressed);
    }
    public void addSaveListener(ActionListener buttonPressed){
	save.addActionListener(buttonPressed);
    }

    public void addPrintCwListener(ActionListener buttonPressed){
	print.addActionListener(buttonPressed);
    }

    public void addEasyStrategyListener(ActionListener strategySelected){
	easyStrategy.addActionListener(strategySelected);
    }

    public void addHardStrategyListener(ActionListener strategySelected){
	hardStrategy.addActionListener(strategySelected);
    }

    public void addNextListener(ActionListener buttonPressed){
	next.addActionListener(buttonPressed);
    }

    public void addPreviousListener(ActionListener buttonPressed){
	previous.addActionListener(buttonPressed);
    }
    public CwPanel getCwPanel(){
	return this.cwPanel;
    }

    public JFileChooser getFileChooser(){
	return chooser;
    }

    public JFileChooser getFileSaver(){
	return saver;
    }

    public JFileChooser getCwChooser(){
	return cwChooser;
    }
    public JFileChooser getPrintChooser(){
	return printer;
    }


    public void disableNext(){
	next.setEnabled(false);
    }

    public void enableNext(){
	next.setEnabled(true);
    }
    public void disablePrevious(){
	previous.setEnabled(false);
    }

    public void enablePrevious(){
	previous.setEnabled(true);
    }
}
