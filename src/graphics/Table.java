package graphics;

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
	
	public Table(TweetList tweetList, Object [][] data, String[] columns){
		super(data, columns);
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
