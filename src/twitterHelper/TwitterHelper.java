package twitterHelper;

import graphics.JMessagePopup;
import models.Model;
import models.TweetList;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.RateLimitStatus;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterHelper {

	private static ConfigurationBuilder etablishConnection(boolean proxy){
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		.setOAuthConsumerKey("wJLV4OdOW2HEC7VpGilIMxM35")
		.setOAuthConsumerSecret("AlVE8901hpwGa0yMUX499hFuHxjtzzD1M4gMiBEKOu4zkhRLoe")
		.setOAuthAccessToken("2918261818-nXyzFjD57Zeibh94bNiubxkSYw9Fyl6G8F2MMYX")
		.setOAuthAccessTokenSecret("KNa0kXQ7p029Pu5ZBIaq9tHJZwAUfZkUZPIsAm9xLtKhv");


		if(proxy){
			cb.setHttpProxyHost("cache-etu.univ-lille1.fr");
			cb.setHttpProxyPort(3128);
		}

		return cb;

	}

	public static TweetList getTweets(boolean proxy, int nb){
		ConfigurationBuilder cb = etablishConnection(proxy);

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
		query.setLang("fr");
		query.count(nb);
		QueryResult result = null;

		try {
			result = twitter.search(query);
		} catch (TwitterException e1) {
			String message = "Impossible de récupérer de nouveaux tweets. Veuillez réessayer",
					titre = "Échec !";
			JMessagePopup.showMessage(message, titre);
			return new TweetList();
		}
		
		

		

		// Lecture dans un fichier et nettoyage des tweets

		return TweetList.cleanTweets(result);
	}
}
