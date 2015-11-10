package models;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Tweet implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3977451258097107705L;
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
	
	public boolean isNegatif(){
		return polarite == Polarite.NEGATIF;
	}
	
	public boolean isNeutre(){
		return polarite == Polarite.NEUTRE;
	}
	
	public boolean isPositif(){
		return polarite == Polarite.POSITIF;
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
		
		// avg est plus proche de negatif
		if(negDiff < neutreDiff && negDiff < posDiff)
			this.polarite = Polarite.NEGATIF;
		
		else if(neutreDiff < posDiff)
			this.polarite = Polarite.NEUTRE;
		
		else
			this.polarite = Polarite.POSITIF;
		
	}
	
	public void setPolarite(Polarite pol){
		this.polarite = pol ;
	}
	
	public String getText(){
		return text;
	}

	public String getUser() {
		return user; 
	}
	
	
	public static HashMap<Polarite, Integer> getPolariteFrequency(List<Tweet> tweets){
		HashMap<Polarite, Integer> map = new HashMap<Polarite, Integer>();
		map.put(Polarite.NEGATIF, 0);
		map.put(Polarite.NEUTRE, 0);
		map.put(Polarite.POSITIF, 0);
		
		for(Tweet tweet : tweets){
			Polarite polarite;
			if(tweet.isNegatif()){
				polarite = Polarite.NEGATIF;
				System.out.println("Negatif");

			} else if(tweet.isPositif()){
				polarite = Polarite.POSITIF;			
				System.out.println("Positif");
				
			} else {
				polarite = Polarite.NEUTRE;
				System.out.println("Neutre");
				
			}
			
			Integer value = map.get(polarite); 
			map.put(polarite, value + 1);
		}
		
		return map;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((polarite == null) ? 0 : polarite.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tweet other = (Tweet) obj;
		if (polarite != other.polarite)
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	
	
	public String toString(){
		return "[" + user + ", " + text + ", " + polarite + "]";
	}
	
}
