package graphics;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.text.DecimalFormat;

import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import listeners.BayesListener;
import listeners.ConfigListener;
import listeners.LaunchListener;
import listeners.LoadListener;
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

	JTextField textField;
	JButton search;
	JButton search2;
	JButton load;
	
	JButton tagButton;
	JButton configButton;
	JButton launchButton;
	

	JPanel searchPanel;
	JPanel algoPanel;
	JPanel tweetsPanel;
	JPanel statsPanel;
	
	ConfigFrame configFrame;

	public MainFrame(){
		super();

		getContentPane().setBackground(Color.white);

		this.setTitle("Twitter");
		this.setVisible(true);
		
		// configFrame
		configFrame = new ConfigFrame();

		// Search Panel

		textField = new JTextField(15);
		textField.setPreferredSize(new Dimension(200, 24));

		search = new JButton("Search from scratch!");
		search.addActionListener(new SearchListener(this, textField,true));

		search2 = new JButton("More tweets");
		search2.addActionListener(new SearchListener(this, textField,false));

		load = new JButton("Load");
		load.addActionListener(new LoadListener());


		searchPanel = new JPanel();
		searchPanel.setOpaque(false);
		searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));

		searchPanel.add(textField);
		searchPanel.add(search);
		searchPanel.add(search2);
		searchPanel.add(load);

		// Algo Panel


		tagButton = new JButton("Etiquetage");
		tagButton.addActionListener(new TagListener(this));

		configButton = new JButton("Configurer");
		configButton.addActionListener(new ConfigListener(configFrame));

		launchButton = new JButton("Launch");
		launchButton.addActionListener(new LaunchListener(configFrame));


		algoPanel = new JPanel();
		algoPanel.setOpaque(false);
		algoPanel.setLayout(new BoxLayout(algoPanel, BoxLayout.X_AXIS));
		algoPanel.add(tagButton);
		algoPanel.add(configButton);
		algoPanel.add(launchButton);


		// Tweet panel
		tweetsPanel = new JPanel();	
		tweetsPanel.setOpaque(false);
		tweetsPanel.setPreferredSize(new Dimension(850, 800));
		tweetsPanel.setLayout(new BoxLayout(tweetsPanel, BoxLayout.Y_AXIS));

		setLayout(new GridBagLayout());

		statsPanel = new JPanel();

		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1000;
		c.gridheight = 100;

		//gridx, gridy , gridwidth, gridheight

		this.add(searchPanel, c);

		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 100;
		c.gridwidth = 1000;
		c.gridheight = 100;

		this.add(algoPanel, c);

		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 200;
		c.gridwidth = 700;
		c.gridheight = 800;

		this.add(tweetsPanel, c);

		c = new GridBagConstraints();
		c.gridx = 700;
		c.gridy = 200;
		c.gridwidth = 300;
		c.gridheight = 800;

		this.add(statsPanel, c);

		Toolkit tk = Toolkit.getDefaultToolkit();
		int xSize = ((int) tk.getScreenSize().getWidth());
		int ySize = ((int) tk.getScreenSize().getHeight());
		this.setSize(xSize,ySize);


	}


	public void addTweets(TweetList tweetList, boolean fromScratch){
		if(fromScratch){
			tweetsPanel.removeAll();
		}
		String[] columnNames = {"Author",
								"Tweet"};

		Object[][] data = new Object[tweetList.size()][2];
		
		Table table = new Table(Model.lesTweets, data, columnNames);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(200);
		table.getColumnModel().getColumn(1).setPreferredWidth(600);

		tweetsPanel.setPreferredSize(new Dimension(810, 800));

		table.setPreferredSize(new Dimension(800, 800));


		for(int i=0;i<tweetList.size();i++){
			Tweet tweet = tweetList.get(i);
			data[i][0] = tweet.getUser();
			data[i][1] = tweet.getText();
		}
		
		
		JScrollPane jsp = new JScrollPane(table);
		jsp.setOpaque(false);
		jsp.setPreferredSize(new Dimension(800, 800));
		tweetsPanel.add(jsp);

		revalidate(); 
		repaint();
	}

	public void addTweetsWithPolarite(TweetList tweetList){
		tweetsPanel.removeAll();
		
		String[] columnNames = {"Author",
								"Tweet",
								"Polarité"};

		Object[][] data = new Object[tweetList.size()][3];
		Table table = new Table(Model.lesTweets, data, columnNames);
		
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(200);
		table.getColumnModel().getColumn(1).setPreferredWidth(600);
		table.getColumnModel().getColumn(2).setPreferredWidth(200);
		
		tweetsPanel.setPreferredSize(new Dimension(1020, 800));

		for(int i=0;i<tweetList.size();i++){
			Tweet tweet = tweetList.get(i);
			data[i][0] = tweet.getUser();
			data[i][1] = tweet.getText();
			data[i][2] = tweet.getPolarite();
		}

		JScrollPane jsp = new JScrollPane(table);
		jsp.setOpaque(false);
		jsp.setSize(900, 600);
		tweetsPanel.add(jsp);

	}

	public void addTweetsWithTag(TweetList tweetList){

		tweetsPanel.removeAll();
		statsPanel.removeAll();

		String[] columnNames = {"Author",
								"Tweet",
								"Polarité"};

		Object[][] data = new Object[tweetList.size()][3];
		
		Table table = new Table(Model.lesTweets, data, columnNames);
		
		// Size
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(200);
		table.getColumnModel().getColumn(1).setPreferredWidth(600);
		table.getColumnModel().getColumn(2).setPreferredWidth(200);
		
		JComboBox<String> comboBox = new JComboBox<String>(new String[]{"POSITIF", "NEGATIF", "NEUTRE"});
		
		TableColumn polariteColumn = table.getColumnModel().getColumn(2);
		
		polariteColumn.setCellEditor(new DefaultCellEditor(comboBox));

	    //Set up tool tips for the polarite cells.
	    DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
	    renderer.setToolTipText("Polarite");
	    polariteColumn.setCellRenderer(renderer);
		
		tweetsPanel.setPreferredSize(new Dimension(1020, 800));

		for(int i=0;i<tweetList.size();i++){
			Tweet tweet = tweetList.get(i);
			data[i][0] = tweet.getUser();
			data[i][1] = tweet.getText();
		}
		
		JScrollPane jsp = new JScrollPane(table);
		jsp.setSize(900, 600);
		jsp.setOpaque(false);
		tweetsPanel.add(jsp);

		
		JPanel sauvegardePanel = new JPanel();
		sauvegardePanel.setOpaque(false);
		
		JButton sauvegarde = new JButton("sauvegarder");
		sauvegarde.addActionListener(new SauvegardeListener(this, Model.lesTweets, table));
		sauvegardePanel.add(sauvegarde);
		
		tweetsPanel.add(sauvegardePanel);

		revalidate(); 
		repaint();
	}

	public void updateStats(String title, double negatif, double neutre, double positif){

		statsPanel.removeAll();


		DefaultPieDataset union = new DefaultPieDataset();
		//remplir l'ensemble

		double negatifPourcentage = negatif / (negatif + neutre + positif)*100,
				neutrePourcentage = neutre / (negatif + neutre + positif)*100,
				positifPourcentage = positif/ (negatif + neutre + positif)*100;
		union.setValue("Negatif", negatifPourcentage);
		union.setValue("Neutre", neutrePourcentage);
		union.setValue("Positif", positifPourcentage);

		JFreeChart repart = ChartFactory.createPieChart("Répartition par sentiments - " + title, union, true, true, false);
		ChartPanel crepart = new ChartPanel(repart);

		PiePlot plot = (PiePlot)repart.getPlot();
		plot.setSectionPaint("Negatif", new Color(255,100,0));
		plot.setSectionPaint("Neutre", new Color(0,102, 255));
		plot.setSectionPaint("Positif", new Color(40, 150, 70));
		plot.setExplodePercent("Negatif", 0.10);
		plot.setExplodePercent("Neutre", 0.10);
		plot.setExplodePercent("Positif", 0.10);

		plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
		plot.setLabelGap(0.02);
		plot.setLabelOutlinePaint(null);
		plot.setLabelShadowPaint(null);
		plot.setLabelBackgroundPaint(Color.WHITE);
		plot.setMaximumLabelWidth(0.20);
		plot.setLabelLinkStyle(PieLabelLinkStyle.CUBIC_CURVE);

		plot.setSimpleLabels(true);


		PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator("{0} ({2})", new DecimalFormat("0"), new DecimalFormat("0%"));
		plot.setLabelGenerator(gen);

		crepart.setVisible(true);
		statsPanel.add(crepart);
		
		revalidate(); 
		repaint();

	}
}
