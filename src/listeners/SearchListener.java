package listeners;

import graphics.MainFrame;
import graphics.JMessagePopup;

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

import models.Model;
import models.Tweet;
import models.TweetList;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.RateLimitStatus;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;


public class SearchListener implements ActionListener{

	protected JTextField textField;
	protected boolean fromScratch;

	public SearchListener(MainFrame mainFrame, JTextField textField, boolean fromScratch){
		this.textField = textField;
		this.fromScratch = fromScratch;
	}

	public void actionPerformed(ActionEvent e) {
		
		// Mise a jour du theme dans le model
		if(fromScratch){
			Model.theme =  textField.getText();
		}
		String fileName = Model.theme + ".csv";


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
		RateLimitStatus rateLimitStatus = null;
		int remaining = -1, limit = -1;
		
		try {
			rateLimitStatus = twitter.getRateLimitStatus().get("/search/tweets");
			remaining = rateLimitStatus.getRemaining();
			limit = rateLimitStatus.getLimit();
		} catch(Exception exception){
			// Do nothing special when it's not working
			exception.printStackTrace();
		}
		
		
		
		Query query = new Query(Model.theme);
		QueryResult result = null;
		try {
			result = twitter.search(query);
		} catch (TwitterException e1) {
			String message = "Impossible de récupérer de nouveaux tweets. Veuillez réessayer",
					 titre = "Échec !";
			JMessagePopup.showMessage(message, titre);
			return;
		}

		List<String> stringTweets = new ArrayList<String>();
		List<Tweet> lesTweets = new ArrayList<Tweet>();

		// Ecriture dans un fichier
		try {

			File fileToBeDeleted = new File(fileName);
			if(fileToBeDeleted.exists() && !fileToBeDeleted.isDirectory()) { 
				fileToBeDeleted.delete();
			}

			File file = new File(fileName);

			BufferedWriter output = new BufferedWriter(new FileWriter(file));

			for (Status status : result.getTweets()) {
				
				Tweet tweet = new Tweet();

				String toBeCleaned = status.getText();

				// Pattern - Matcher
				Pattern pattern = Pattern.compile("([@#\"\r\n(RT)]|https?:[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)*");
				Matcher matcher = pattern.matcher(toBeCleaned);

				String cleanedHalf = matcher.replaceAll(""); 
				
				Pattern patternPonctu = Pattern.compile("\\p{Punct}");
				Matcher matcherPonctu = patternPonctu.matcher(cleanedHalf);
				
				String cleaned = matcherPonctu.replaceAll("");
				
				tweet.setText(cleaned);
				tweet.setUser(status.getUser().getScreenName()) ;
				lesTweets.add(tweet);
				
				// ajout dans le fichier
				String string = "\"" + status.getUser().getScreenName() + "\",\"" + cleaned + "\"\n";


				// Verifier que le tweet n'est pas redondant
				if(!stringTweets.contains(string)){

					output.write(string);

					// ajout a la liste pour le graphique
					stringTweets.add(string); 
				}

			}
			output.flush();

			output.close();
		} catch(Exception exception){
			System.out.println(exception.toString());
			String message = "Erreur lors du nettoyage de tweets. Contactez les auteurs de l'API.",
					 titre = "Échec !";
			JMessagePopup.showMessage(message, titre);
			exception.printStackTrace();
			return;
		}

		// Lecture dans un fichier et nettoyage des tweets
		if(!this.fromScratch ){
			Model.lesTweets.fusionne(new TweetList(lesTweets)) ;
		} else {
			Model.lesTweets = new TweetList(lesTweets) ;
		}
		
		Model.frame.addTweets(Model.lesTweets, this.fromScratch);
		
	}



}
