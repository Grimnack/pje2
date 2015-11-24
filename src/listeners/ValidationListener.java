package listeners;

import graphics.MainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import models.Polarite;
import models.Tweet;

public class ValidationListener implements ActionListener {
	
	protected MainFrame mainframe ;
	protected Tweet tweet ;
	protected int i;
	protected JTable table;

	public ValidationListener(MainFrame frame, Tweet tweet, JTable table, int iPanel) {
		this.mainframe = frame;
		this.tweet = tweet;
		this.i = iPanel;
		this.table = table;
	}

	@SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent e) {
		TableModel dm = table.getModel();
		String resultat = (String)((JComboBox<String>) dm.getValueAt(i, 2)).getSelectedItem();
		Polarite pol;
		if(resultat == "non defini") {
			pol = Polarite.UNDEFINED;
		}else if (resultat == "positif"){
			pol = Polarite.POSITIF;
		}else if (resultat == "negatif"){
			pol = Polarite.NEGATIF;
		}else {
			pol = Polarite.NEUTRE ;
		}
		tweet.setPolarite(pol);
		dm.setValueAt(resultat, i, 2);
		dm.setValueAt(null, i, 3);
		
		mainframe.revalidate(); 
		mainframe.repaint();
		System.out.println(resultat);
	}

}
