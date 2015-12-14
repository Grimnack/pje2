package listeners;

import graphics.JMessagePopup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.JFileChooser;

import models.Configuration;
import models.Model;
import models.Polarite;
import models.Tweet;
import models.TweetList;

public class LoadListener implements ActionListener {

	public void actionPerformed(ActionEvent e) {
		JFileChooser choix = new JFileChooser();
		int retour = choix.showOpenDialog(null);
		if(retour != JFileChooser.APPROVE_OPTION)
			return;

		String pathName = choix.getSelectedFile().getAbsolutePath();
		File file = new File(pathName);
		// Si le fichier existe deja
		// LECTURE ET SAUVEGARDE DANS Model.base
		if(file.exists()){
			if(!file.canRead()){
				String message = "La base ne peut être chargée. Vérifiez que le fichier est bien celui de la base et veuillez réessayer.",
						titre = "Échec !";
				JMessagePopup.showMessage(message, titre);
				return;
			}
			try {
				ObjectInputStream flotLecture = new ObjectInputStream(new FileInputStream(pathName));
				Object lu = flotLecture.readObject();
				flotLecture.close();
				if(lu instanceof TweetList) {
					TweetList bdd = (TweetList) lu;
					Model.base = bdd ;
				}

			} catch (Exception e2) {
				String message = "La base ne peut être chargée. Vérifiez que le fichier est bien celui de la base et veuillez réessayer.",
						titre = "Échec !";
				JMessagePopup.showMessage(message, titre);
				e2.printStackTrace();
				return;
			}

		}

		if(Configuration.memeNbClassTweet){
			int pos = 0, neg = 0, neutre = 0;
			for(Tweet t : Model.base.tweetList){
				if(t.getPolarite() == Polarite.NEGATIF)
					neg++;
				else if(t.getPolarite() == Polarite.NEUTRE)
					neutre++;
				else if(t.getPolarite() == Polarite.POSITIF)
					pos++;
			}

			int min = 0;
			if(pos < neutre && pos < neg)
				min = pos;
			else if(neg < neutre && neg < pos)
				min = neg;
			else if(neutre < neg && neutre < pos)
				min = neutre;

			Map<Polarite, Integer> map = new HashMap<Polarite, Integer>();
			map.put(Polarite.POSITIF, 0);
			map.put(Polarite.NEUTRE, 0);
			map.put(Polarite.NEGATIF, 0);

			int i=0;
			while(i<Model.base.size()){
				Tweet t = Model.base.get(i);
				if(map.get(t.getPolarite()) < min){
					map.put(t.getPolarite(), map.get(t.getPolarite()) + 1);
					i++;
				} else {
					Model.base.tweetList.remove(i);
				}

			}
			
			
		/*	i=0;
			Random r = new Random();
			while(i<Model.base.size()){
				Tweet t = Model.base.get(i);
				
				if((t.getPolarite() == Polarite.NEUTRE && r.nextDouble() < 0.20) 
						||(t.getPolarite() == Polarite.POSITIF && r.nextDouble() < 0.10)){
					Model.base.tweetList.remove(i);
				} else {
					
					i++;
				}

			}
			
			pos = 0;
			neg = 0;
			neutre = 0;


			for(Tweet t : Model.base.tweetList){
				if(t.getPolarite() == Polarite.NEGATIF)
					neg++;
				else if(t.getPolarite() == Polarite.NEUTRE)
					neutre++;
				else if(t.getPolarite() == Polarite.POSITIF)
					pos++;
			}*/

			System.out.println(pos + " " + neutre + " " + neg);
		}


		String message = "Base chargée ! " + Model.base.size() + " tweets chargés !",
				titre = "Succès !";
		JMessagePopup.showMessage(message, titre);


	}
}
