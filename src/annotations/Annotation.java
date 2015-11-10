package annotations;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
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
			System.out.println("Annote naif 1");
			System.out.println(exception.toString());
		}

		// Lecture dans un fichier et nettoyage des tweets

		//System.out.println("Negatifs Positifs lus");
		

		try{
			
			String pathNameInput = Model.theme + ".csv";

			// Ouverture/lecture du fichier
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(pathNameInput)));
			
			// Nom en sortie

			String [] pathNameFragments = pathNameInput.split("\\.");

			String pathNameOutput = pathNameFragments[0] + "-polariteNaif." + pathNameFragments[1];

			BufferedWriter output = new BufferedWriter(new FileWriter(pathNameOutput));

			String ligne;
			while ((ligne=br.readLine())!=null){

				// Split avec ","
				String [] tweetTab = ligne.split("\",\"");
				String username = tweetTab[0].substring(1, tweetTab[0].length());
				String cleaned = tweetTab[1].substring(0, tweetTab[1].length() -1);
				//tweets.add(cleaned);


				// Fichiers negatifs/positifs
				int positif = 0, negatif = 0;
				
				String [] tweetWords = cleaned.split("\\s+");
				
				for(String tweetWord : tweetWords){
					if(positifs.contains(tweetWord))
						positif++;
					
					if(negatifs.contains(tweetWord))
						negatif++;
				}
				
				int resultat = positif - negatif, polarite;
				
				if(resultat < 0){
					polarite = 0;
					tweetNegatif++;
				} else if(resultat > 0){
					polarite = 4;
					tweetPositif++;
				} else {
					polarite = 2;
					tweetNeutre++;
				}
				// ajout dans le fichier
				String string = "\"" + username + "\",\"" + cleaned + "\",\"" + polarite + "\"\n";
				
				output.write(string);

				//System.out.println("Ecriture en cours");
				
			}
			
			// Fin de lecture
			br.close(); 
			
			// Fin d'ecriture
			output.flush();

			output.close();
			
			//System.out.println("Ecriture finie");
			
			Model.frame.updateStats("Algorithme naïf", tweetNegatif, tweetNeutre, tweetPositif);
			
		} catch (Exception exception){
			System.out.println("Annote naif 2");
			System.out.println(exception.getMessage());
		}



	}

	
	public static void annoteKNN(){
		try{
			
			String pathNameInput = Model.theme + "-etiquette.csv";

			// Ouverture/lecture du fichier
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(pathNameInput)));
			
			String ligne;	
			
			List<Tweet> tweetBase = new ArrayList<Tweet>();
			
			HashMap<Tweet, Double> tweetDistance;
			
			while ((ligne=br.readLine()) != null){
				String [] tweetSplit = ligne.split("\",\"");
				Polarite polarite = Polarite.valueOf(tweetSplit[2].substring(0, tweetSplit[2].length() -1));
				
				Tweet tweet = new Tweet(tweetSplit[1], Polarite.valueOf(polarite));
				tweetBase.add(tweet);
			}
						
			// Pour chaque tweet, on caclule sa distance avec tous les tweets etiquetees, et on lui attribue une polarite en fonction de cela
			for(Tweet tweet : Model.lesTweets.tweetList){
				tweetDistance = new HashMap<Tweet, Double>();
				
				for(Tweet tweetEtiquete : tweetBase)
					tweetDistance.put(tweetEtiquete, tweet.getDistanceWith(tweetEtiquete));

				Map<Tweet, Double> distanceSorted = CollectionUtil.mapSortByValue(tweetDistance);			
				
				List<Double> values = new ArrayList<Double>(distanceSorted.values());
			
				// On en prend 5, ou moins si la list est moins grande
				int toInd=5;
				if(toInd > values.size())
					toInd = values.size();

				
				values = values.subList(0, toInd);
				double moyennePolarite = CollectionUtil.listGetAvg(values);

				tweet.setPolariteFromAvg(moyennePolarite);			
				
			}
			
			HashMap<Polarite, Integer> map = Model.lesTweets.getPolariteFrequency();
			
			Model.frame.updateStats("KNN", map.get(Polarite.NEGATIF), map.get(Polarite.NEUTRE), map.get(Polarite.POSITIF));

			br.close();
			
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
		
	}
	
	

}