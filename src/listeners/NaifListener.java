package listeners;

import graphics.Frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import annotations.Annotation;

public class NaifListener implements ActionListener{

	protected Frame mainFrame;

	public NaifListener(Frame mainFrame){
		this.mainFrame = mainFrame;
	}
	
	public void actionPerformed(ActionEvent e) {
		Annotation.annoteNaif();
	}

}
