package graphics;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import listeners.SauvegardeListener;
import models.Model;
import models.Tweet;
import models.TweetList;

public class TweetPanel extends JPanel{


	private static final long serialVersionUID = 1L;
	public MainFrame mainFrame;
	
	public TweetPanel(MainFrame mainFrame){
		setOpaque(false);
		setPreferredSize(new Dimension(850, 800));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
	}
	

	public void addTweets(TweetList tweetList){

		removeAll();
		
		String[] columnNames = {"Author",
								"Tweet"};

		Object[][] data = new Object[tweetList.size()][2];
		
		Table table = new Table(Model.lesTweets, data, columnNames);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(200);
		table.getColumnModel().getColumn(1).setPreferredWidth(600);

		setPreferredSize(new Dimension(810, 800));

		table.setPreferredSize(new Dimension(800, 800));


		for(int i=0;i<tweetList.size();i++){
			Tweet tweet = tweetList.get(i);
			data[i][0] = tweet.getUser();
			data[i][1] = tweet.getText();
		}
		
		
		JScrollPane jsp = new JScrollPane(table);
		jsp.setOpaque(false);
		jsp.setPreferredSize(new Dimension(800, 800));
		add(jsp);

		revalidate(); 
		repaint();
	}

	public void addTweetsWithPolarite(TweetList tweetList){
		removeAll();
		
		String[] columnNames = {"Author",
								"Tweet",
								"Polarité"};

		Object[][] data = new Object[tweetList.size()][3];
		Table table = new Table(Model.lesTweets, data, columnNames);
		
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(200);
		table.getColumnModel().getColumn(1).setPreferredWidth(600);
		table.getColumnModel().getColumn(2).setPreferredWidth(200);
		
		setPreferredSize(new Dimension(1020, 800));

		for(int i=0;i<tweetList.size();i++){
			Tweet tweet = tweetList.get(i);
			data[i][0] = tweet.getUser();
			data[i][1] = tweet.getText();
			data[i][2] = tweet.getPolarite();
		}

		JScrollPane jsp = new JScrollPane(table);
		jsp.setOpaque(false);
		jsp.setSize(900, 600);
		add(jsp);

	}

	public void addTweetsWithTag(TweetList tweetList){

		removeAll();
		//mainFrame.statsPanel.removeAll();

		String[] columnNames = {"Author",
								"Tweet",
								"Polarité"};

		Object[][] data = new Object[tweetList.size()][3];
		
		Table table = new Table(Model.lesTweets, data, columnNames);
		
		// Size
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(200);
		table.getColumnModel().getColumn(1).setPreferredWidth(600);
		table.getColumnModel().getColumn(2).setPreferredWidth(200);
		
		JComboBox<String> comboBox = new JComboBox<String>(new String[]{"POSITIF", "NEGATIF", "NEUTRE"});
		
		TableColumn polariteColumn = table.getColumnModel().getColumn(2);
		
		polariteColumn.setCellEditor(new DefaultCellEditor(comboBox));

	    //Set up tool tips for the polarite cells.
	    DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
	    renderer.setToolTipText("Polarite");
	    polariteColumn.setCellRenderer(renderer);
		
		setPreferredSize(new Dimension(1020, 800));

		for(int i=0;i<tweetList.size();i++){
			Tweet tweet = tweetList.get(i);
			data[i][0] = tweet.getUser();
			data[i][1] = tweet.getText();
		}
		
		JScrollPane jsp = new JScrollPane(table);
		jsp.setSize(900, 600);
		jsp.setOpaque(false);
		add(jsp);

		
		JPanel sauvegardePanel = new JPanel();
		sauvegardePanel.setOpaque(false);
		
		JButton sauvegarde = new JButton("sauvegarder");
		sauvegarde.addActionListener(new SauvegardeListener(mainFrame, Model.lesTweets, table));
		sauvegardePanel.add(sauvegarde);
		
		add(sauvegardePanel);

		revalidate(); 
		repaint();
	}

}
