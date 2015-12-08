package annotations;

import graphics.JMessagePopup;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.CollectionUtil;
import models.Model;
import models.Polarite;
import models.Tweet;
import models.TweetList;


public class Annotation {
	
	
	public static boolean frequence;
	public static int moinsDeNMots = 0;
	public static String poss[] = null;
	public static String negs[] = null;

	public static void annoteNaif(){
		double tweetPositif = 0, tweetNeutre = 0, tweetNegatif = 0;

		List<String> negatifs = new ArrayList<String>();
		List<String> positifs = new ArrayList<String>();
		// Ouverture fichiers negatifs/positifs

		try{

			// Ouverture/lecture du fichier
			BufferedReader brn=new BufferedReader(new InputStreamReader(new FileInputStream("negative.txt")));

			String ligne;

			while ((ligne=brn.readLine()) != null){

				String [] keyWords = ligne.split(", ");

				for(String keyWord : keyWords){
					negatifs.add(keyWord);
				}
			}

			BufferedReader brp=new BufferedReader(new InputStreamReader(new FileInputStream("positive.txt")));
			while ((ligne=brp.readLine()) != null){

				String [] keyWords = ligne.split(", ");

				for(String keyWord : keyWords){
					positifs.add(keyWord);
				}
			}

			brn.close();
			brp.close();

		} catch (Exception exception){
			// Une exception est levée car il y a un pb sur l'un des dicos. N'existe pas ou autre
			// Si les dicos ont mal lus, on ne fait rien
			System.out.println("Exception sur les dicos");
			return;
		}

		// Lecture dans un fichier et nettoyage des tweets

		//System.out.println("Negatifs Positifs lus");


		try{

			
			for(Tweet tweet : Model.lesTweets.tweetList){

				// Fichiers negatifs/positifs
				int positif = 0, negatif = 0;

				String [] tweetWords = tweet.getText().split("\\s+");

				for(String tweetWord : tweetWords){
					if(positifs.contains(tweetWord))
						positif++;

					if(negatifs.contains(tweetWord))
						negatif++;
				}

				int resultat = positif - negatif;
				
				Polarite polarite;
				if(resultat < 0){
					tweetNegatif++;
					polarite = Polarite.NEGATIF;
				} else if(resultat > 0){
					tweetPositif++;
					polarite = Polarite.POSITIF;
				} else {
					tweetNeutre++;
					polarite = Polarite.NEUTRE;
				}
				tweet.setPolarite(polarite);


			}

			//System.out.println("Ecriture finie");
			Model.frame.addTweetsWithPolarite(Model.lesTweets);
			Model.frame.updateStats("Algorithme naïf", tweetNegatif, tweetNeutre, tweetPositif);

		} catch (Exception exception){
			System.out.println("Annote naif 2");
			System.out.println(exception.getMessage());
		}
	}

	public static void annoteKNN(){
		try{

			// Si la base n'est pas chargée ou est vide, on ne fait rien
			if(Model.base == null || Model.base.size() == 0){
				String message = "KNN ne peut être effectué car la bae d'apprentissage n'est pas chargée. Chargez la base avant de pouvoir faire KNN",
						 titre = "Échec !";
				JMessagePopup.showMessage(message, titre);
				return;
			}
			
			for(Tweet tweet : Model.base.tweetList){
				System.out.println(tweet.getPolarite());
			}
			
			List<Tweet> tweetBase = Model.base.tweetList;

			Map<Tweet, Double> tweetDistance = new HashMap<Tweet, Double>();

			// Pour chaque tweet, on caclule sa distance avec tous les tweets etiquetees, et on lui attribue une polarite en fonction de cela
			for(Tweet tweet : Model.lesTweets.tweetList){
				tweetDistance.clear();

				for(Tweet tweetEtiquete : tweetBase)
					tweetDistance.put(tweetEtiquete, tweet.getDistanceWith(tweetEtiquete));

				Map<Tweet, Double> distanceSorted = CollectionUtil.mapSortByValue(tweetDistance);			

				List<Double> values = new ArrayList<Double>(distanceSorted.values());

				// On en prend 7, ou moins si la liste est moins grande
				int toInd=7;
				if(toInd > values.size())
					toInd = values.size();


				values = values.subList(0, toInd);
				double moyennePolarite = CollectionUtil.listGetAvg(values);

			}

			HashMap<Polarite, Integer> map = Model.lesTweets.getPolariteFrequency();
			Model.frame.addTweetsWithPolarite(Model.lesTweets);

			Model.frame.updateStats("KNN", map.get(Polarite.NEGATIF), map.get(Polarite.NEUTRE), map.get(Polarite.POSITIF));
			
			
		} catch(Exception e){
			e.printStackTrace();
		}
	}


	public static void predictTweetsClass(TweetList learningBase){
		for(Tweet tweet : Model.lesTweets.tweetList){
			Map<Polarite, Double> map = new HashMap<Polarite, Double>();

			map.put(Polarite.NEGATIF, tweet.bayes(Polarite.NEGATIF, learningBase));
			map.put(Polarite.NEUTRE, tweet.bayes(Polarite.NEUTRE, learningBase));
			map.put(Polarite.POSITIF, tweet.bayes(Polarite.POSITIF, learningBase));

			tweet.setPolarite(CollectionUtil.getPolariteFromHighestProb(map));

		}
		HashMap<Polarite, Integer> map = Model.lesTweets.getPolariteFrequency();

		
		Model.frame.addTweetsWithPolarite(Model.lesTweets);
		Model.frame.updateStats("Bayes", map.get(Polarite.NEGATIF), map.get(Polarite.NEUTRE), map.get(Polarite.POSITIF));


		
	}

}