package annotations;

import graphics.JMessagePopup;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
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
		long StartTime = new Date().getTime() ;
		long EndTime ;

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
			EndTime = new Date().getTime();
			System.out.println("L'algorithme de dico a pris " + (EndTime-StartTime) +  " ms" ) ;
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

			List<Tweet> tweetBase = Model.base.tweetList;

			Map<Tweet, Double> tweetDistance = new HashMap<Tweet, Double>();
			long StartTime = new Date().getTime() ;
			long EndTime ;

			// Pour chaque tweet, on caclule sa distance avec tous les tweets etiquetees, et on lui attribue une polarite en fonction de cela
			for(Tweet tweet : Model.lesTweets.tweetList){
				tweetDistance.clear();

				for(Tweet tweetEtiquete : tweetBase)
					tweetDistance.put(tweetEtiquete, tweet.getDistanceWith(tweetEtiquete));

				Map<Tweet, Double> distanceSorted = CollectionUtil.mapSortByValue(tweetDistance);


				tweet.setPolarite(CollectionUtil.listGetClassFromValue(distanceSorted, 7));

			}
			EndTime = new Date().getTime() - StartTime ;
			System.out.println("Knn a pris : " + EndTime + "ms");
			HashMap<Polarite, Integer> map = Model.lesTweets.getPolariteFrequency();

			Model.frame.addTweetsWithPolarite(Model.lesTweets);

			Model.frame.updateStats("KNN", map.get(Polarite.NEGATIF), map.get(Polarite.NEUTRE), map.get(Polarite.POSITIF));


		} catch(Exception e){
			e.printStackTrace();
		}
	}


	public static HashMap<Polarite, Integer> predictTweetsClass(TweetList learningBase, TweetList toPredict){

		int pos = 0;
		int neg = 0;
		int neutre = 0;


		for(Tweet t : learningBase.tweetList){
			if(t.getPolarite() == Polarite.NEGATIF)
				neg++;
			else if(t.getPolarite() == Polarite.NEUTRE)
				neutre++;
			else if(t.getPolarite() == Polarite.POSITIF)
				pos++;
		}

		System.out.println(pos + " " + neutre + " " + neg);

		long StartTime = new Date().getTime() ;
		long EndTime ;
		for(Tweet tweet : toPredict.tweetList){
			Map<Polarite, Double> map = new HashMap<Polarite, Double>();

			map.put(Polarite.NEGATIF, tweet.bayes(Polarite.NEGATIF, learningBase));
			map.put(Polarite.NEUTRE, tweet.bayes(Polarite.NEUTRE, learningBase));
			map.put(Polarite.POSITIF, tweet.bayes(Polarite.POSITIF, learningBase));

			tweet.setPolarite(CollectionUtil.getPolariteFromHighestNb(map));

		}
		EndTime = new Date().getTime() - StartTime ;
		System.out.println("Bayes a pris : " + EndTime + "ms");

		return toPredict.getPolariteFrequency();
	}

	public static void checkBase(){
		
		int splitNb = 10;
		TweetList[] tab = Model.base.split(splitNb);

		TweetList[] tabBase = new TweetList[tab.length];
		TweetList[] tabList = new TweetList[tab.length];

		// On génère les listes qui vont être vérifiés à partir des bases générées, et on vérifie
		for(int i=0;i<tab.length;i++){
			tabList[i] = new TweetList(tab[i]);
			tabBase[i] = new TweetList(TweetList.fusionneExcept(tab, i));
			Annotation.predictTweetsClass(tabBase[i], tabList[i]);
		}

		TweetList checked = TweetList.fusionneExcept(tabList, -1);

		Model.frame.addTweetsWithPolarite(checked);

		Map<String, Integer> map = checked.getErrorMarginWithBase(Model.base);

		Model.frame.updateErrorMargin(map);


	}

}