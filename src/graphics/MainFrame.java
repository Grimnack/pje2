package graphics;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.io.File;
import java.text.DecimalFormat;

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
	JButton load;
	
	JButton tagButton;
	JButton configButton;
	JButton launchButton;
	
	Table table;
	JScrollPane jsp;
	
	ChartPanel crepart;
	
	JLabel titre;

	Font openSans = new Font("TimesRoman", Font.PLAIN, 18);
	Font titreFont = new Font("TimesRoman", Font.PLAIN, 18);
	
	ConfigFrame configFrame;


	public MainFrame() {
		super();

		getContentPane().setBackground(Color.white);

		//////////////////////////////////////////////////////////
		// Configuration police 
		//////////////////////////////////////////////////////////
		
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		File fontFile = new File("fonts/open-sans/OpenSans-Bold.ttf");
		try {
			openSans = Font.createFont(Font.TRUETYPE_FONT, fontFile);
			openSans = openSans.deriveFont((float) 18.0);
			titreFont = openSans.deriveFont((float) 32.);
		} catch (Exception e) {
			
			e.printStackTrace();
			System.exit(0);
		}
		ge.registerFont(openSans);
		this.setFont(openSans) ;
			
		
		
		//////////////////////////////////////////////////////////
		
		this.setTitle("Twitter");
		this.setVisible(true);
		
		titre = new JLabel("Tweetemotions");
		titre.setFont(titreFont);
		// configFrame
		configFrame = new ConfigFrame();

		// Search Panel

		textField = new JTextField(15);
		textField.setPreferredSize(new Dimension(200, 24));
		textField.setFont(openSans);

		search = new JButton("Search from scratch!");
		search.addActionListener(new SearchListener(this, textField,true));
		search.setFont(openSans);
		search.setBackground(new Color(0x00aced));
		search.setForeground(Color.WHITE);
		
		load = new JButton("Load");
		load.addActionListener(new LoadListener());
		load.setFont(openSans);
		load.setBackground(new Color(0x00aced));
		load.setForeground(Color.WHITE);

		// Algo Panel
		tagButton = new JButton("Etiquetage");
		tagButton.addActionListener(new TagListener(this));
		tagButton.setFont(openSans);

		configButton = new JButton("Configurer");
		configButton.addActionListener(new ConfigListener(configFrame));
		configButton.setFont(openSans);

		launchButton = new JButton("Launch");
		launchButton.addActionListener(new LaunchListener());
		launchButton.setFont(openSans);

		tagButton.setBackground(new Color(0x00aced));
		tagButton.setForeground(Color.WHITE);
		configButton.setBackground(new Color(0x00aced));
		configButton.setForeground(Color.WHITE);
		launchButton.setBackground(new Color(0x00aced));
		launchButton.setForeground(Color.WHITE);
		
		table = new Table();
		jsp = new JScrollPane(table);
		
		crepart = new ChartPanel(null);


		setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 3;
		c.gridy = 0;
		c.gridwidth = 3;
		c.gridheight = 1;
		this.add(titre, c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		this.add(textField, c);
		
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;

		this.add(search, c);

		c.gridx = 6;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;

		this.add(tagButton, c);
		
		c.gridx = 7;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;

		this.add(configButton, c);
		
		c.gridx = 8;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;

		this.add(launchButton, c);

		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 6;
		c.gridheight = 10;

		this.add(jsp, c);

		c = new GridBagConstraints();
		c.gridx = 6;
		c.gridy = 3;
		c.gridwidth = 2;
		c.gridheight = 10;

		this.add(crepart, c);

		Toolkit tk = Toolkit.getDefaultToolkit();
		int xSize = ((int) tk.getScreenSize().getWidth());
		int ySize = ((int) tk.getScreenSize().getHeight());
		this.setSize(xSize,ySize);


	}


	public void addTweets(TweetList tweetList){

		String[] columnNames = {"Author",
								"Tweet"};

		Object[][] data = new Object[tweetList.size()][2];
		
		table = new Table(Model.lesTweets, data, columnNames);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(200);
		table.getColumnModel().getColumn(1).setPreferredWidth(600);


		table.setPreferredSize(new Dimension(800, 800));


		for(int i=0;i<tweetList.size();i++){
			Tweet tweet = tweetList.get(i);
			data[i][0] = tweet.getUser();
			data[i][1] = tweet.getText();
		}
		
		
		jsp = new JScrollPane(table);
		jsp.setOpaque(false);
		jsp.setPreferredSize(new Dimension(800, 800));

		revalidate(); 
		repaint();
	}

	public void addTweetsWithPolarite(TweetList tweetList){
		
		String[] columnNames = {"Author",
								"Tweet",
								"Polarité"};

		Object[][] data = new Object[tweetList.size()][3];
		Table table = new Table(Model.lesTweets, data, columnNames);
		
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(200);
		table.getColumnModel().getColumn(1).setPreferredWidth(600);
		table.getColumnModel().getColumn(2).setPreferredWidth(200);
		
		for(int i=0;i<tweetList.size();i++){
			Tweet tweet = tweetList.get(i);
			data[i][0] = tweet.getUser();
			data[i][1] = tweet.getText();
			data[i][2] = tweet.getPolarite();
		}

		JScrollPane jsp = new JScrollPane(table);
		jsp.setOpaque(false);
		jsp.setSize(900, 600);

	}

	public void addTweetsWithTag(TweetList tweetList){

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
		
		for(int i=0;i<tweetList.size();i++){
			Tweet tweet = tweetList.get(i);
			data[i][0] = tweet.getUser();
			data[i][1] = tweet.getText();
		}
		
		jsp = new JScrollPane(table);
		jsp.setSize(900, 600);
		jsp.setOpaque(false);
		
		JPanel sauvegardePanel = new JPanel();
		sauvegardePanel.setOpaque(false);
		
		JButton sauvegarde = new JButton("sauvegarder");
		sauvegarde.addActionListener(new SauvegardeListener(this, Model.lesTweets, table));
		sauvegardePanel.add(sauvegarde);
		
		revalidate(); 
		repaint();
	}

	public void updateStats(String title, double negatif, double neutre, double positif){

		DefaultPieDataset union = new DefaultPieDataset();
		//remplir l'ensemble

		double negatifPourcentage = negatif / (negatif + neutre + positif)*100,
				neutrePourcentage = neutre / (negatif + neutre + positif)*100,
				positifPourcentage = positif/ (negatif + neutre + positif)*100;
		union.setValue("Negatif", negatifPourcentage);
		union.setValue("Neutre", neutrePourcentage);
		union.setValue("Positif", positifPourcentage);

		JFreeChart repart = ChartFactory.createPieChart("Répartition par sentiments - " + title, union, true, true, false);
		crepart = new ChartPanel(repart);

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
		
		revalidate(); 
		repaint();

	}
}