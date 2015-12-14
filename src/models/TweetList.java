package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import twitter4j.QueryResult;
import twitter4j.Status;

public class TweetList implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 646410961337631943L;
	public List<Tweet> tweetList ;


	public TweetList(){

	}

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

			} else if(tweet.isPositif()){
				polarite = Polarite.POSITIF;			

			} else {
				polarite = Polarite.NEUTRE;

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

	/* Formule : P(m|c) = ((n(m,c) +1) / (n(c) + N) */
	public double probaOccMotDansClass(String mot, Polarite polarite){
		return (probaOccClass(mot, polarite) +1) / (nbTotalMotsClass(polarite) + nbTotalMots());

	}

	public double probaOccClass(String mot, Polarite polarite){
		//System.out.println(polarite);
		return nbOccClass(mot, polarite)/(nbTotalMotsClass(polarite) + 1);
	}

	/* Compte le nombre de fois que le mot 'mot' apparaît dans les tweets ayant la polarite 'polarite' 
	 * n(m, c)	*/
	public int nbOccClass(String mot, Polarite polarite){
		int nbOcc = 0;
		boolean trouve;
		for(Tweet tweet : tweetList){
			trouve = false;
			if(tweet.getPolarite() == polarite){
				String s = tweet.getText();
				Pattern p = Pattern.compile(mot);
				Matcher m = p.matcher(s);

				while (m.find()){
					if(!trouve)
						nbOcc++;
						if(!Configuration.frequence)
							trouve = true;
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
				if(Configuration.moinsDeN == 0)
					
					sum += tweet.getText().split("\\s+").length;
				else {
					String [] mots = tweet.getText().split("\\s+");
					for(String mot : mots){
						if(mot.length() >= Configuration.moinsDeN)
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

	/* Compte le nombre de tweet ayant la classe donnée */
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

	public static TweetList cleanTweets(QueryResult result){
		List<Tweet> lesTweets = new ArrayList<Tweet>();

		// Ecriture dans un fichier


		for (Status status : result.getTweets()) {

			Tweet tweet = new Tweet();

			String toBeCleaned = status.getText();

			// Pattern - Matcher
			Pattern pattern = Pattern.compile("([@#\"\r\n]|RT |https?:[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)*");
			Matcher matcher = pattern.matcher(toBeCleaned);

			String cleanedHalf = matcher.replaceAll(""); 

			Pattern patternPonctu = Pattern.compile("\\p{Punct}");
			Matcher matcherPonctu = patternPonctu.matcher(cleanedHalf);

			String cleaned = matcherPonctu.replaceAll("");

			tweet.setText(cleaned);
			tweet.setUser(status.getUser().getScreenName()) ;
			lesTweets.add(tweet);

		}

		return new TweetList(lesTweets);
	}


	public String toString(){
		String s = "";
		for(Tweet tweet : tweetList){
			s += tweet.toString() + " . ";
		}
		return s;
	}

	public TweetList[] split(int nb){
		TweetList[] tab = new TweetList [nb];
		Collections.shuffle(tweetList);
		int mult = tweetList.size() / nb;
		for(int i=0;i<nb-1;i++){
			tab[i] = new TweetList(tweetList.subList(i*mult, (i+1)*mult));
		}
		tab[nb-1] = new TweetList(tweetList.subList((nb-1)*mult, tweetList.size()));
		return tab;

	}

	public static TweetList fusionneExcept(TweetList[] tab, int except){
		List<Tweet> list = new ArrayList<Tweet>();
		for(int i=0;i<tab.length;i++){
			if(i != except){
				list.addAll(tab[i].tweetList);
			}
		}

		return new TweetList(list);
	}

	public Tweet getTweetFromText(String text){
		for(Tweet tweet : tweetList){
			if(tweet.getText().equals(text))
				return tweet;
		}
		return null;
	}

	public Map<String, Integer> getErrorMarginWithBase(TweetList base){
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("Bien classé", 0);
		map.put("Mal classé", 0);
		map.put("Très mal classé", 0);

		for(Tweet tweet : tweetList){
			Tweet tweetBase = base.getTweetFromText(tweet.getText());
			
			System.out.println(tweet.getPolarite() + " = " + tweetBase.getPolarite());
			if(tweet.getPolarite() == tweetBase.getPolarite()){
				map.put("Bien classé", map.get("Bien classé") + 1);
			} else if((tweet.isPositif() && tweetBase.isNegatif()) || (tweet.isPositif() && tweetBase.isPositif())){
				map.put("Très mal classé", map.get("Très mal classé") + 1);
			} else {
				map.put("Mal classé", map.get("Mal classé") + 1);
			}
		}
		
		System.out.println(base.toString());

		return map;
	}

}
