package graphics;

import javax.swing.JTable;

public class Table extends JTable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Table(Object [][] data, String[] columns){
		super(data, columns);
	}

	public boolean isCellEditable(int row, int column){  
        return false;  
    }

}
