package graphics;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import listeners.ConfigListener;
import listeners.LaunchListener;
import listeners.SauvegardeListener;
import listeners.SearchListener;
import listeners.TagListener;
import models.Model;
import models.Tweet;
import models.TweetList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PieLabelLinkStyle;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

public class MainFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	

	public TweetPanel tweetsPanel;
	public PiePanel statsPanel;
	public TopPanel topPanel;
	
	
	ConfigFrame configFrame;


	public MainFrame() {
		super();

		getContentPane().setBackground(Color.white);
		
		this.setTitle("Twitter");
		this.setVisible(true);
		

		// configFrame
		configFrame = new ConfigFrame();

		// Tweet panel
		topPanel = new TopPanel(this);
        
		tweetsPanel = new TweetPanel(this);	
	
		statsPanel = new PiePanel(this);
		
		setLayout(new GridBagLayout());


		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1000;
		c.gridheight = 300;
		add(topPanel, c );

		
	


		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 500;
		c.gridwidth = 700;
		c.gridheight = 500;

		add(tweetsPanel, c);

		c = new GridBagConstraints();
		c.gridx = 700;
		c.gridy = 500;
		c.gridwidth = 300;
		c.gridheight = 500;

		add(statsPanel, c);

		Toolkit tk = Toolkit.getDefaultToolkit();
		int xSize = ((int) tk.getScreenSize().getWidth());
		int ySize = ((int) tk.getScreenSize().getHeight());
		this.setSize(xSize,ySize);


	}
}
