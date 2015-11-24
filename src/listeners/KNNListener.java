package listeners;

import graphics.MainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import annotations.Annotation;

public class KNNListener implements ActionListener{

	protected MainFrame mainFrame;

	public KNNListener(MainFrame mainFrame){
		this.mainFrame = mainFrame;
	}
	
	public void actionPerformed(ActionEvent e) {
		Annotation.annoteKNN();
	}
}
