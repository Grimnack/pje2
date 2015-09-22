package graphics;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import listeners.SearchListener;

public class Frame extends JFrame{

	JLabel label;
	List<JLabel> tweets;
	JButton search;
	
	public Frame(){
		super();
		this.setSize(1000, 1000);
		this.setTitle("Twitter");
		this.setVisible(true);
		
		search = new JButton("Search !");
		search.addActionListener(new SearchListener());
	
		label = new JLabel("Tweets : ");
		
		tweets = new ArrayList();
		
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		
		this.add(search);
		this.add(label);
		readFile();
		
		
	}
	
	protected void readFile(){


		String fichier = "tweets.txt";
		
		String tweets = "";

		//lecture du fichier texte	
		try{

			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(fichier)));
			String ligne;
			while ((ligne=br.readLine())!=null){
				this.add(new JLabel(ligne));
			}
			br.close(); 
		}		
		catch (Exception e){
			System.out.println(e.toString());
		}
		
		
	}
	
	
}
