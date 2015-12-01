package listeners;

import graphics.ConfigFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfigListener implements ActionListener{

	ConfigFrame configFrame;
	
	public ConfigListener(ConfigFrame configFrame){
		this.configFrame = configFrame;
	}

	public void actionPerformed(ActionEvent e) {
		configFrame.setVisible(true);
		
	}

}
