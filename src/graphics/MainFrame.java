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
import java.util.Map;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
	
	private int widthScreen;
	private int heightScreen;

	JTextField textField;
	JButton search;
	JLabel label;
	
	JButton tagButton;
	JButton configButton;
	JButton launchButton;
	
	JLabel titre;

	JScrollPane jsp;
	Table table;
	
	ChartPanel crepart;
	JFreeChart repart;
	PiePlot plot;
	DefaultPieDataset union;

	
	
	JButton saveButton;
	
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
			ge.registerFont(openSans);
			this.setFont(openSans) ;
		} catch (Exception e) {
			System.err.println("Problème de police d'écriture");
		}
		
		//////////////////////////////////////////////////////////
		
		this.setTitle("Twitter");
		this.setVisible(true);
		
		table = new Table();
		table.setPreferredSize(new Dimension(0, 0));
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		jsp = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		jsp.setPreferredSize(new Dimension(750, 550));
		
		union = new DefaultPieDataset();

		repart = ChartFactory.createPieChart("", union, true, true, false);

		plot = (PiePlot)repart.getPlot();
		
		crepart = new ChartPanel(repart);
		crepart.setPreferredSize(new Dimension(400, 550));
		
		
		titre = new JLabel("Tweetemotions");
		titre.setFont(titreFont);
		// configFrame
		configFrame = new ConfigFrame();

		// Search Panel

		textField = new JTextField(15);
		textField.setPreferredSize(new Dimension(200, 24));
		textField.setFont(openSans);

		search = new JButton("Get tweets !");
		search.addActionListener(new SearchListener(this, textField,true));
		search.setFont(openSans);
		search.setBackground(new Color(0x00aced));
		search.setForeground(Color.WHITE);
	
		// Algo Panel
		tagButton = new JButton("Etiquetage");
		tagButton.addActionListener(new TagListener(this));
		tagButton.setFont(openSans);
		tagButton.setBackground(new Color(0x00aced));
		tagButton.setForeground(Color.WHITE);

		configButton = new JButton("Configurer");
		configButton.addActionListener(new ConfigListener(configFrame));
		configButton.setFont(openSans);
		configButton.setBackground(new Color(0x00aced));
		configButton.setForeground(Color.WHITE);

		launchButton = new JButton("Launch");
		launchButton.addActionListener(new LaunchListener());
		launchButton.setFont(openSans);
		launchButton.setBackground(new Color(0x00aced));
		launchButton.setForeground(Color.WHITE);
		
		saveButton = new JButton("Sauvegarder");
		saveButton.addActionListener(new SauvegardeListener(this, table));
		saveButton.setFont(openSans);
		saveButton.setBackground(new Color(0xed3c00));
		saveButton.setForeground(Color.WHITE);

		// Tweet panel
		setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 8;
		c.gridheight = 1;
		this.add(titre, c );
		
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		c.gridheight = 1;
		this.add(textField, c);
		
		c.gridx = 2;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		this.add(search, c);
		
		c.gridx = 3;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		this.add(tagButton, c);
		
		c.gridx = 4;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		this.add(configButton, c);
		
		c.gridx = 5;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		this.add(launchButton, c);
		
		c.gridx = 6;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		this.add(saveButton, c);


		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 6;
		c.gridheight = 7;
		this.add(jsp, c);

		c.gridx = 6;
		c.gridy = 2;
		c.gridwidth = 3;
		c.gridheight = 7;

		this.add(crepart, c);

		Toolkit tk = Toolkit.getDefaultToolkit();
		widthScreen = ((int) tk.getScreenSize().getWidth());
		heightScreen = ((int) tk.getScreenSize().getHeight());
		this.setSize(widthScreen, heightScreen);
	}


	public void addTweets(TweetList tweetList){
		crepart.setVisible(false);

		String[] columnNames = {"Author",
								"Tweet"};

		Object[][] data = new Object[tweetList.size()][2];

		for(int i=0;i<tweetList.size();i++){
			Tweet tweet = tweetList.get(i);
			data[i][0] = tweet.getUser();
			data[i][1] = tweet.getText();
		}
		
		table.update(Model.lesTweets, data, columnNames);
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setPreferredWidth(1000);
		
		/*Component [] cs = jsp.getComponents();
	
		for(int i=0;i<cs.length;i++){
			System.out.println(cs[i].toString());
		}
		System.out.println(table.toString());*/
		
		revalidate(); 
		repaint();
	}

	public void addTweetsWithPolarite(TweetList tweetList){
		
		String[] columnNames = {"Author",
								"Tweet",
								"Polarité"};

		Object[][] data = new Object[tweetList.size()][3];
		
		for(int i=0;i<tweetList.size();i++){
			Tweet tweet = tweetList.get(i);
			data[i][0] = tweet.getUser();
			data[i][1] = tweet.getText();
			data[i][2] = tweet.getPolarite();
		}
		
		table.update(Model.lesTweets, data, columnNames);
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setPreferredWidth(1000);
		table.getColumnModel().getColumn(2).setPreferredWidth(150);
	}

	public void addTweetsWithTag(TweetList tweetList){
		/*jsp.setPreferredSize(new Dimension(750, 200));
		crepart.setPreferredSize(new Dimension(750, 200));*/
		//saveButton.setVisible(true);
		
		crepart.setVisible(false);

		String[] columnNames = {"Author",
								"Tweet",
								"Polarité"};

		Object[][] data = new Object[tweetList.size()][3];
				
		for(int i=0;i<tweetList.size();i++){
			Tweet tweet = tweetList.get(i);
			data[i][0] = tweet.getUser();
			data[i][1] = tweet.getText();
		}
		
		table.update(Model.lesTweets, data, columnNames);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setFillsViewportHeight(true);
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setPreferredWidth(1000);
		table.getColumnModel().getColumn(2).setPreferredWidth(150);
		
		JComboBox<String> comboBox = new JComboBox<String>(new String[]{"POSITIF", "NEGATIF", "NEUTRE"});
		
		TableColumn polariteColumn = table.getColumnModel().getColumn(2);
		
		polariteColumn.setCellEditor(new DefaultCellEditor(comboBox));	    //Set up tool tips for the polarite cells.
	    DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
	    renderer.setToolTipText("Polarite");
	    polariteColumn.setCellRenderer(renderer);

		revalidate(); 
		repaint();
	}

	public void updateStats(String title, double negatif, double neutre, double positif){

		//remplir l'ensemble

		double negatifPourcentage = negatif / (negatif + neutre + positif)*100,
				neutrePourcentage = neutre / (negatif + neutre + positif)*100,
				positifPourcentage = positif/ (negatif + neutre + positif)*100;
		
		union.setValue("Negatif", negatifPourcentage);
		union.setValue("Neutre", neutrePourcentage);
		union.setValue("Positif", positifPourcentage);

		repart.setTitle("Répartition par sentiments - " + title);
	
		//repart.setPieDataSet(union);

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
	
	public void updateErrorMargin(Map<String, Integer> map){

		//remplir l'ensemble

		int size = map.keySet().size();
		double bienClassePourcentage = map.get("Bien classé") / size*100,
				malClassePourcentage = map.get("Mal classé") / size*100,
				tresMalClassePourcentage = map.get("Très mal classé") / size*100;
		
		union.setValue("Bien classé", bienClassePourcentage);
		union.setValue("Mal classé", malClassePourcentage);
		union.setValue("Très mal classé", tresMalClassePourcentage);

		repart.setTitle("Analyse de la base");
	
		//repart.setPieDataSet(union);

		plot.setSectionPaint("Très mal classé", new Color(255,100,0));
		plot.setSectionPaint("Bien classé", new Color(0,102, 255));
		plot.setSectionPaint("Mal classé", new Color(40, 150, 70));
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
