package models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import annotations.Annotation;

public class TweetList implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 646410961337631943L;
	public List<Tweet> tweetList ;

	public TweetList(List<Tweet> l){
		tweetList = l ;
	}


	public HashMap<Polarite, Integer> getPolariteFrequency(){
		HashMap<Polarite, Integer> map = new HashMap<Polarite, Integer>();
		map.put(Polarite.NEGATIF, 0);
		map.put(Polarite.NEUTRE, 0);
		map.put(Polarite.POSITIF, 0);

		for(Tweet tweet : tweetList){
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

	public int size(){
		return tweetList.size();
	}

	public Tweet get(int i){
		return tweetList.get(i);
	}
	
	/* Formule : P(m|c) = (n(m,c) +1) / (n(c) + N) */
	public double probaOccMotDansClass(String mot, Polarite polarite){
		return (probaOccClass(mot, polarite) +1) / (nbTotalMotsClass(polarite) + nbTotalMots());
		
	}
	
	public double probaOccClass(String mot, Polarite polarite){
		return nbOccClass(mot, polarite)/nbTotalMotsClass(polarite);
	}
	
	/* Compte le nombre de fois que le mot 'mot' apparaît dans les tweets ayant la polarite 'polarite' 
	 * n(m, c)	*/
	public int nbOccClass(String mot, Polarite polarite){
		int nbOcc = 0;
		for(Tweet tweet : tweetList){
			if(tweet.getPolarite() == polarite){
				String s = tweet.getText();
				Pattern p = Pattern.compile("hello");
				Matcher m = p.matcher(s);

				while (m.find()){
					if(nbOcc == 0 || Annotation.frequence)
						nbOcc++;
				}
			}
		}
		
		return nbOcc;

	}
	
	/* Compte le nombre total des mots d'une classe de la liste de tweets
	 * n(c) */
	public int nbTotalMotsClass(Polarite polarite){
		int sum = 0;

		for(Tweet tweet : tweetList){
			if(tweet.getPolarite() == polarite)
				if(Annotation.moinsDeNMots == 0)
					sum += tweet.getText().length();
				else {
					String [] mots = tweet.getText().split("\\s+");
					for(String mot : mots){
						if(mot.length() >= Annotation.moinsDeNMots)
							sum++;
					}
				}
		}
		return sum;
	}
	
	/* Compte le nombre total de mots de la liste de tweets 
	 * N*/
	public int nbTotalMots(){
		int sum = 0;

		for(Tweet tweet : tweetList)
			sum += tweet.getText().length();


		return sum;
	}

	/* Compte le nombre de tweetayant la classe donnée */
	public int nbTweetsClass(Polarite polarite){
		int count = 0;

		for(Tweet tweet : tweetList){
			if(tweet.getPolarite() == polarite)
				count++;

		}
		return count;
	}

	public void fusionne(TweetList liste2) {
		this.tweetList.addAll(liste2.tweetList) ;
		
	}

}
