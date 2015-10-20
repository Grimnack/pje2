package annotations;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import models.Model;


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
			
			Model.frame.updateNaifStats(tweetNegatif, tweetNeutre, tweetPositif);
			
		} catch (Exception exception){
			System.out.println(exception.toString());
		}



	}

	
	public static void annoteKNN(){
		try{
			
			String pathNameInput = Model.theme + "-polariteNaif.csv";

			// Ouverture/lecture du fichier
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(pathNameInput)));
			
			String ligne;
			
			
			List<String> tweetsAClasser = new ArrayList<String>();
			
			while ((ligne=br.readLine()) != null){
				tweetsAClasser.add(ligne.split("\",\"")[1]);
			}
			
			
			
		} catch(Exception e){
			
		}
		
		
		
	}
	
	public static double getDistanceTweet(String tweet1, String tweet2){
		
		// Split
		List<String> splitCleaned1 = Arrays.asList(tweet1.split("\\s*"));
		List<String> splitCleaned2 = Arrays.asList(tweet2.split("\\s*"));
		
		
		int nbMotsTotal = splitCleaned1.size() + splitCleaned2.size(), nbMotsCommun = 0;
		
		
		for(String mot1 : splitCleaned1){
			if(splitCleaned2.contains(mot1)){
				nbMotsCommun += 2;
			}
		}
		
		return (nbMotsTotal - nbMotsCommun) / nbMotsCommun;
		
	}

}