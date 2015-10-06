package graphics;


import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import listeners.SearchListener;
import twitter4j.Status;

public class Frame extends JFrame{

	JTextField textField;
	JButton search;

	JPanel searchPanel;
	JPanel tweetsPanel;
	


	public Frame(){
		super();
		this.setTitle("Twitter");
		this.setVisible(true);
		
		textField = new JTextField(15);
		textField.setPreferredSize(new Dimension(200, 24));

		search = new JButton("Search !");
		search.addActionListener(new SearchListener(this, textField));
		
		searchPanel = new JPanel();
		searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
		
		searchPanel.add(textField);
		searchPanel.add(search);
		
		
		tweetsPanel = new JPanel();

		setLayout(new GridBagLayout());


		tweetsPanel.setLayout(new BoxLayout(tweetsPanel, BoxLayout.Y_AXIS));

		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1000;
		c.gridheight = 200;
		
		//gridx, gridy , gridwidth, gridheight
		
		this.add(searchPanel, c);
		
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 200;
		c.gridwidth = 1000;
		c.gridheight = 500;
		
		this.add(tweetsPanel, c);

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

		for(int i=0;i<tweets.size();i++){
			String stringTweet = tweets.get(i);
			
			JPanel tweetPanel = new JPanel();
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


}
