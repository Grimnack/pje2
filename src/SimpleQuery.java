import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;


public class SimpleQuery {
	Twitter twitter = TwitterFactory.getSingleton();
	Query query = new Query("source:twitter4j yusukey");
	QueryResult result = twitter.search(query);
    for (Status status : result.getTweets()) {
        System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
    }
}
