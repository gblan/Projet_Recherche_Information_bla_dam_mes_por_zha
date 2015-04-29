package et4.ihm.mvc.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

import et4.ihm.mvc.Model;

public class SearchBarController implements ActionListener, KeyListener{

	private Model model;
	public SearchBarController(Model model) {
		this.model = model;
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		try {
			JTextField txt = (JTextField) e.getSource();
			model.search(txt.getText());

		} catch (ClassCastException e2) {
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		try {
			JTextField txt = (JTextField) e.getSource();
			model.search(txt.getText());

		} catch (ClassCastException e2) {
		}
	}

}
