package graphics;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import listeners.NaifListener;
import listeners.SearchListener;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PieLabelLinkStyle;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

public class Frame extends JFrame{

	JTextField textField;
	JButton search;
	JButton naifButton;

	JPanel searchPanel;
	JPanel algoPanel;
	JPanel tweetsPanel;
	JPanel statsPanel;



	public Frame(){
		super();

		getContentPane().setBackground(Color.white);

		this.setTitle("Twitter");
		this.setVisible(true);

		// Search Panel

		textField = new JTextField(15);
		textField.setPreferredSize(new Dimension(200, 24));

		search = new JButton("Search !");
		search.addActionListener(new SearchListener(this, textField));

		searchPanel = new JPanel();
		searchPanel.setOpaque(false);
		searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));

		searchPanel.add(textField);
		searchPanel.add(search);


		// Algo Panel
		naifButton = new JButton("Algo naïf");
		naifButton.addActionListener(new NaifListener(this));

		algoPanel = new JPanel();
		algoPanel.setOpaque(false);
		algoPanel.setLayout(new BoxLayout(algoPanel, BoxLayout.X_AXIS));
		algoPanel.add(naifButton);


		tweetsPanel = new JPanel();	
		tweetsPanel.setOpaque(false);
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
		c.gridwidth = 400;
		c.gridheight = 800;

		this.add(tweetsPanel, c);

		c = new GridBagConstraints();
		c.gridx = 400;
		c.gridy = 200;
		c.gridwidth = 600;
		c.gridheight = 800;

		this.add(statsPanel, c);

		Toolkit tk = Toolkit.getDefaultToolkit();
		int xSize = ((int) tk.getScreenSize().getWidth());
		int ySize = ((int) tk.getScreenSize().getHeight());
		this.setSize(xSize,ySize);


	}

	/*protected void readFile(){


		String fichier = "tweets.csv";

		String tweets = "";

		try{

			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(fichier)));
			String ligne;
			while ((ligne=br.readLine())!=null){
				this.add(new JLabel(ligne));
			}
			br.close(); 
		}		
		catch (Exception e){
			System.out.println(e.toString());
		}

	}*/


	public void addTweets(List<String> tweets){

		tweetsPanel.removeAll();

		for(int i=0;i<tweets.size();i++){
			String stringTweet = tweets.get(i);

			JPanel tweetPanel = new JPanel();
			tweetPanel.setOpaque(false);

			tweetPanel.setLayout(new BoxLayout(tweetPanel, BoxLayout.X_AXIS));

			// Split avec ","
			String [] tweet = stringTweet.split("\",\"");

			tweetPanel.add(new JLabel(tweet[0]));
			tweetPanel.add(new JLabel(tweet[1]));

			tweetsPanel.add(tweetPanel);


		}

		revalidate(); 
		repaint();
	}

	public void updateNaifStats(double negatif, double neutre, double positif){

		statsPanel.removeAll();


		DefaultPieDataset union = new DefaultPieDataset();
		//remplir l'ensemble

		System.out.println(positif);

		double negatifPourcentage = negatif / (negatif + neutre + positif)*100,
				neutrePourcentage = neutre / (negatif + neutre + positif)*100,
				positifPourcentage = positif/ (negatif + neutre + positif)*100;
		union.setValue("Negatif", negatifPourcentage);
		union.setValue("Neutre", neutrePourcentage);
		union.setValue("Positif", positifPourcentage);

		System.out.println(union.getValue("Negatif"));
		System.out.println(union.getValue("Neutre"));
		System.out.println(union.getValue("Positif"));


		JFreeChart repart = ChartFactory.createPieChart("Répartition par sentiments (algo naïf)", union, true, true, false);
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

		((PiePlot)plot).setSimpleLabels(true);


		PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator("{0} ({2})", new DecimalFormat("0"), new DecimalFormat("0%"));
		((PiePlot)plot).setLabelGenerator(gen);

		crepart.setVisible(true);
		statsPanel.add(crepart);



		revalidate(); 
		repaint();

	}
}
