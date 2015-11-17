package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import javax.swing.JFileChooser;

import models.Model;
import models.TweetList;

public class LoadListener implements ActionListener {

	public void actionPerformed(ActionEvent e) {
		JFileChooser choix = new JFileChooser();
		int retour=choix.showOpenDialog(null);
		if(retour==JFileChooser.APPROVE_OPTION){
			String pathname = choix.getSelectedFile().getAbsolutePath();
			File file = new File(pathname);
			// Si le fichier existe deja
			// LECTURE ET SAUVEGARDE DANS Model.base
			if(file.exists()){
				if(!file.canRead()){
					System.out.println("tu peux pas lire");
					System.exit(0);
				}
				try {
					ObjectInputStream flotLecture = new ObjectInputStream(new FileInputStream(file));
					Object lu = flotLecture.readObject();
					flotLecture.close();
					if(lu instanceof TweetList) {
						TweetList bdd = (TweetList) lu;
						Model.base = bdd ;
					}
					
				} catch (Exception e2) {
					// TODO: handle exception
				}

			}

		}
	}
}
