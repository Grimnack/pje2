package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import models.TweetList;

public class Table extends JTable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	TweetList tweetList;
	Font openSans;
	Font openSansBold;
	

	public Table() {
	}


	
	public Table(TweetList tweetList, Object [][] data, String[] columns){
		super(data, columns);
		//////////////////////////////////////////////////////////
		// Configuration police 
		//////////////////////////////////////////////////////////
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		File fontFile = new File("fonts/open-sans/OpenSans-Regular.ttf");
		File fontFile2 = new File("fonts/open-sans/OpenSans-Bold.ttf");
		try {
			openSans = Font.createFont(Font.TRUETYPE_FONT, fontFile);
			openSans = openSans.deriveFont((float) 18.0);
			openSansBold = Font.createFont(Font.TRUETYPE_FONT, fontFile2);
			openSansBold = openSansBold.deriveFont((float) 18.0);
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ge.registerFont(openSans);
		ge.registerFont(openSansBold);
		this.setFont(openSans) ;
		this.getTableHeader().setFont(openSansBold);
		this.getTableHeader().setBackground(new Color(0x00aced));
		this.getTableHeader().setForeground(new Color(0xffffff));
		this.tweetList = tweetList;
	}
	
	public void update(TweetList tweetList, Object [][] data, String[] columns){
		((DefaultTableModel)getModel()).setRowCount(data.length);
		((DefaultTableModel)getModel()).setColumnCount(columns.length);
		for(int i=0;i<data.length;i++){
			for(int j=0;j<data[0].length;j++){
				getModel().setValueAt(data[i][j], i, j);
			}
		}
		TableColumnModel tcm = getTableHeader().getColumnModel();
		for(int i=0;i<columns.length;i++){
			tcm.getColumn(i).setHeaderValue(columns[i]);
		}
		
		setPreferredSize(new Dimension(1000 + columns.length-1, (data.length+1) * 16));
		
	}

	public boolean isCellEditable(int row, int column){  
        return column == 2;  
    }	
}
