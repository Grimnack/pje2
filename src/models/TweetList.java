package models;

import java.io.Serializable;
import java.util.List;

public class TweetList implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 646410961337631943L;
	public List<Tweet> tweetlist ;
	
	public TweetList(List<Tweet> l){
		tweetlist = l ;
	}

	public void fusionne(TweetList liste2) {
		this.tweetlist.addAll(liste2.tweetlist) ;
		
	}

}
