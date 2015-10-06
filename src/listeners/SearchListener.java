package listeners;

import graphics.Frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextField;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;


public class SearchListener implements ActionListener{


	protected Frame mainFrame;
	protected JTextField textField;

	public SearchListener(Frame mainFrame, JTextField textField){
		this.textField = textField;
		this.mainFrame = mainFrame;
	}

	public void actionPerformed(ActionEvent e) {

		String fileName = textField.getText() + ".csv";
		String fileNameNettoye = textField.getText() + "Clean.csv";


		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		.setOAuthConsumerKey("wJLV4OdOW2HEC7VpGilIMxM35")
		.setOAuthConsumerSecret("AlVE8901hpwGa0yMUX499hFuHxjtzzD1M4gMiBEKOu4zkhRLoe")
		.setOAuthAccessToken("2918261818-nXyzFjD57Zeibh94bNiubxkSYw9Fyl6G8F2MMYX")
		.setOAuthAccessTokenSecret("KNa0kXQ7p029Pu5ZBIaq9tHJZwAUfZkUZPIsAm9xLtKhv");


		cb.setHttpProxyHost("cache-etu.univ-lille1.fr");
		cb.setHttpProxyPort(3128);

		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();

		Query query = new Query(textField.getText());
		QueryResult result = null;
		try {
			result = twitter.search(query);
		} catch (TwitterException e1) {
		}

		List<String> stringTweets = new ArrayList<String>();

		// Ecriture dans un fichier
		try {

			File fileToBeDeleted = new File(fileName);
			if(fileToBeDeleted.exists() && !fileToBeDeleted.isDirectory()) { 
			   fileToBeDeleted.delete();
			}

			File file = new File(fileName);

			BufferedWriter output = new BufferedWriter(new FileWriter(file));

			for (Status status : result.getTweets()) {
				
				String toBeCleaned = status.getText();
				System.out.println(toBeCleaned);
				
				// Pattern - Matcher
				Pattern pattern = Pattern.compile("([@#\"\r\n(RT )]|https?:[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)*");
				Matcher matcher = pattern.matcher(toBeCleaned);

				String cleaned = matcher.replaceAll(""); 

				// ajout a la liste pour le graphique
				stringTweets.add(cleaned); 
				
				// ajout dans le fichier
				String string = "\"" + status.getUser().getScreenName() + "\",\"" + cleaned + "\"\n";
				output.write(string);
				System.out.println(string);
				
				
				
				/* 
				 * Pattern pattern = Pattern.compile("^[\r\n]+[\r\n]+$");
				Matcher matcher = pattern.matcher(ligne);

				String nettoyee = matcher.replaceAll(""); 

				System.out.println(nettoyee);
				System.out.println();
				
				stringTweets.add(nettoyee); 
				
				
				 */
			}
			output.flush();

			output.close();
		} catch(Exception exception){

		}

		// Lecture dans un fichier et nettoyage des tweets


/*
		List<String> stringTweets = new ArrayList<String>();

		try{
			// Test si fichier existe deja pour le supprimer
			File fileToBeDeleted = new File(fileNameNettoye);
			if(fileToBeDeleted.exists() && !fileToBeDeleted.isDirectory()) { 
				fileToBeDeleted.delete();
			}
			
			// Ouverture/lecture du fichier non nettoye
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
			
			// Creation du nouveau fichier nettoye
			File file = new File(fileNameNettoye);
			BufferedWriter output = new BufferedWriter(new FileWriter(file));

			String ligne;
		
			while ((ligne=br.readLine())!=null){

				
				
				
				
				output.write(nettoyee);
			}
			// Fin de lecture
			br.close(); 
			
			// Fin de l'ecriture
			output.flush();
			output.close();
			
		} catch (Exception exception){
			System.out.println(e.toString());
		}*/


		mainFrame.addTweets(stringTweets);
	}



}
