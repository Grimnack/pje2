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

import models.Model;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;


public class SearchListener implements ActionListener{

	protected JTextField textField;

	public SearchListener(Frame mainFrame, JTextField textField){
		this.textField = textField;
	}

	public void actionPerformed(ActionEvent e) {
		
		// Mise a jour du theme dans le model
		Model.theme =  textField.getText();

		String fileName = textField.getText() + ".csv";


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

				/*Pattern smiley = Pattern.compile("[(:-\\)|:\\)|:\\]|=\\)|:-D|:D|=D|;-\\)|;\\))(:-\\(|:\\(|:\\[|=\\(|>:\\(|>:-\\(|;-\\()]"
						+ "[(:-\\(|:\\(|:\\[|=\\(|>:\\(|>:-\\(|;-\\()(:-\\)|:\\)|:\\]|=\\)|:-D|:D|=D|;-\\)|;\\))]") ;
				Matcher matcherSmiley = smiley.matcher(toBeCleaned);*/

				//if(!matcherSmiley.matches()){



				// Pattern - Matcher
				Pattern pattern = Pattern.compile("([@#\"\r\n(RT)]|https?:[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)*");
				Matcher matcher = pattern.matcher(toBeCleaned);

				String cleaned = matcher.replaceAll(""); 
				
				


				// ajout dans le fichier
				String string = "\"" + status.getUser().getScreenName() + "\",\"" + cleaned + "\"\n";


				// Verifier que le tweet n'est pas redondant
				if(!stringTweets.contains(string)){

					output.write(string);

					// ajout a la liste pour le graphique
					stringTweets.add(string); 
				}


				//}


			}
			output.flush();

			output.close();
		} catch(Exception exception){

		}

		// Lecture dans un fichier et nettoyage des tweets



		/*List<String> stringTweets = new ArrayList<String>();

		try{

			// Ouverture/lecture du fichier
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));

			String ligne;

			while ((ligne=br.readLine())!=null){
				stringTweets.add(ligne);

			}
			// Fin de lecture
			br.close(); 


		} catch (Exception exception){
			System.out.println(e.toString());
		}*/
		
		Model.frame.addTweets(stringTweets);
	}



}
