package graphics;

import javax.swing.JOptionPane;

public class JMessagePopup {

	public static void showMessage(String titre, String message){
		JOptionPane.showMessageDialog(null, message, titre, JOptionPane.INFORMATION_MESSAGE);

	}
}
