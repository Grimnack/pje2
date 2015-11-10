package listeners;

import graphics.Frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;

import models.Model;
import models.TweetList;

public class SauvegardeListener implements ActionListener {
	protected Frame mainFrame ;
	protected TweetList lesTweets;

	public SauvegardeListener(Frame frame, TweetList lesTweets) {
		mainFrame = frame ;
		this.lesTweets = lesTweets ;
	}

	public void actionPerformed(ActionEvent e) {
		JFileChooser choix = new JFileChooser();
		int retour=choix.showOpenDialog(null);
		if(retour==JFileChooser.APPROVE_OPTION){
			String pathname = choix.getSelectedFile().getAbsolutePath();
			File file = new File(pathname);
			// Si le fichier existe deja
			// LECTURE ET FUSION
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
						Model.lesTweets.fusionne(bdd) ;
					}
					
				} catch (Exception e2) {
					// TODO: handle exception
				}
				
			}
			if(!file.exists()){
				try {
					file.createNewFile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			//ECRITURE
			if (file.canWrite()) {
			       try {
			         ObjectOutputStream flotEcriture = 
			             new ObjectOutputStream(
			                new FileOutputStream(file)); 
			         flotEcriture.writeObject(Model.lesTweets);
			         flotEcriture.close();
			       } catch (IOException e3) {
			         System.out.println(" erreur :" + e3.toString());
			       }   
			     }
			  }
		}
	}
