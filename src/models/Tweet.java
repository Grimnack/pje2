package models;

import java.util.Arrays;
import java.util.List;

public class Tweet {
	protected String user;
	protected String text;
	protected Polarite polarite;
	
	public Tweet(){
		this.polarite = Polarite.UNDEFINED ;
	}
	
	public Tweet(String text){
		this();
		setText(text);
	}
	
	public Tweet(String text, int polarite){
		setText(text);
		setPolarite(Polarite.keyOf(polarite));
	}
	
	public Tweet(String text, Polarite polarite){
		setText(text);
		setPolarite(polarite);
	}
	
	
	
	public double getDistanceWith(Tweet tweet){
		
		// Split
		List<String> splitCleaned1 = Arrays.asList(this.getText().split("\\s*"));
		List<String> splitCleaned2 = Arrays.asList(tweet.getText().split("\\s*"));
		
		int nbMotsTotal = splitCleaned1.size() + splitCleaned2.size(), nbMotsCommun = 0;
		
		for(String mot1 : splitCleaned1){
			if(splitCleaned2.contains(mot1)){
				nbMotsCommun += 2;
			}
		}
		
		return (nbMotsTotal - nbMotsCommun) / nbMotsCommun;
		
	}
	
	
	
	public void setText(String text){
		this.text = text ;
	}
	
	public void setUser(String user){
		this.user = user ;
	}
	
	public void setPolariteFromAvg(double avgKNN){
		double negDiff = Math.abs(0-avgKNN);
		double neutreDiff = Math.abs(2-avgKNN);
		double posDiff = Math.abs(4-avgKNN);
		
	}
	
	public void setPolarite(Polarite pol){
		this.polarite = pol ;
	}
	
	public String getText(){
		return text;
	}
	
}
