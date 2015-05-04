package et4.ihm.mvc.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

import et4.ihm.mvc.Model;
import et4.ihm.mvc.panel.body.SearchPanel;

public class SearchBarController implements ActionListener, KeyListener {

	private Model model;
	private SearchPanel panel;

	public SearchBarController(Model model, SearchPanel panel) {
		this.model = model;
		this.panel = panel;
		this.panel.getFrRadioButton().addActionListener(this);
		this.panel.getCnRadioButton().addActionListener(this);
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
		try {
			JTextField txt = (JTextField) e.getSource();
			int langue;
			if (panel.getFrRadioButton().isSelected()) {
				langue = 2;
			} else {
				langue = 1;
			}
			model.search(txt.getText(),langue);
			System.out.println(txt+", "+langue);
		} catch (ClassCastException e2) {
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(panel.getFrRadioButton().isSelected()){
			panel.setInputField(1);

		}else{
			panel.setInputField(2);

		}
		
		try {
			JTextField txt = (JTextField) e.getSource();
			int langue;
			if (panel.getFrRadioButton().isSelected()) {
				langue = 2;
			} else {
				langue = 1;
			}
			model.search(txt.getText(),langue);
			System.out.println(txt+", "+langue);
		} catch (ClassCastException e2) {
		}
	}

}
