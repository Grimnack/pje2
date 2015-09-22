import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;


public class SimpleQuery {
	
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
	
    Query query = new Query("source:twitter4j #RWC");
    QueryResult result = twitter.search(query);
    for (Status status : result.getTweets()) {
        System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
    }
    
    System.out.println("Fini");
}
