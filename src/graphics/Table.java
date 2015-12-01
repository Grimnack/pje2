package graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import models.Polarite;
import models.TweetList;

public class Table extends JTable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	TweetList tweetList;
	Font openSans;
	Font openSansBold;
	
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
		this.getTableHeader().setBackground(new Color(0x1dcaff));
		this.tweetList = tweetList;
		
	}

	public boolean isCellEditable(int row, int column){  
        return column == 2;  
    }

	
	public void setValueAt(Object value, int row, int col) {
		if(value != null){
			tweetList.get(col).setPolarite(Polarite.valueOf(value.toString()));
			((AbstractTableModel) getModel()).setValueAt(value, row, col);
		}
    }
	
}
