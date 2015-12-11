package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import listeners.ConfigListener;
import listeners.LaunchListener;
import listeners.SearchListener;
import listeners.TagListener;

public class TopPanel extends JPanel{

	MainFrame frame;
	
	public TopPanel(MainFrame mainFrame){
		frame = mainFrame;
		
		//setOpaque(false);
		//setPreferredSize(new Dimension(1000, 300));
		Font openSans = new Font("TimesRoman", Font.PLAIN, 18);
		Font titreFont = new Font("TimesRoman", Font.PLAIN, 18);		
		
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
		
		JLabel titre = new JLabel("Tweetemotions");
		titre.setFont(titreFont);


		// Search Panel

		JTextField textField = new JTextField(15);
		textField.setPreferredSize(new Dimension(200, 24));
		textField.setFont(openSans);

		JButton searchButton = new JButton("Get tweets !");
		searchButton.addActionListener(new SearchListener(mainFrame, textField,true));
		searchButton.setFont(openSans);
		searchButton.setBackground(new Color(0x00aced));
		searchButton.setForeground(Color.WHITE);
		
		JButton tagButton = new JButton("Etiquetage");
		tagButton.addActionListener(new TagListener(mainFrame));
		tagButton.setFont(openSans);
		tagButton.setBackground(new Color(0x00aced));
		tagButton.setForeground(Color.WHITE);

		JButton configButton = new JButton("Configurer");
		configButton.addActionListener(new ConfigListener(frame.configFrame));
		configButton.setFont(openSans);
		configButton.setBackground(new Color(0x00aced));
		configButton.setForeground(Color.WHITE);

		JButton launchButton = new JButton("Launch");
		launchButton.addActionListener(new LaunchListener());
		launchButton.setFont(openSans);
		launchButton.setBackground(new Color(0x00aced));
		launchButton.setForeground(Color.WHITE);
		

		GridBagLayout gbl = new GridBagLayout();
		
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 50;
		c.gridheight = 100;
		gbl.addLayoutComponent(titre, c);

		
		c = new GridBagConstraints();
		c.gridx = 200;
		c.gridy = 100;
		c.gridwidth = 200;
		c.gridheight = 200;

		gbl.addLayoutComponent(textField, c);
		
		c = new GridBagConstraints();
		c.gridx = 300;
		c.gridy = 100;
		c.gridwidth = 200;
		c.gridheight = 200;

		gbl.addLayoutComponent(searchButton, c);
		
		c = new GridBagConstraints();
		c.gridx = 500;
		c.gridy = 100;
		c.gridwidth = 200;
		c.gridheight = 200;

		gbl.addLayoutComponent(tagButton, c);
		
		c = new GridBagConstraints();
		c.gridx = 700;
		c.gridy = 100;
		c.gridwidth = 200;
		c.gridheight = 200;

		this.add(configButton, c);
		
		c = new GridBagConstraints();
		c.gridx = 500;
		c.gridy = 100;
		c.gridwidth = 200;
		c.gridheight = 200;

		this.add(launchButton, c);


		
		
		setLayout(gbl);

		
		
	}

}
