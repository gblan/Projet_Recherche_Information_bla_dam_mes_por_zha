package et4.ihm.mvc.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			/* pour ne pas afficher le caractère \n */
			e.consume();

			if (panel.getFrRadioButton().isSelected()) {
				model.search(panel.getTextAreaFR().getText(), 2);

			} else {
				model.search(panel.getTextAreaCN().getText(), 1);

			}
			System.out.println("search");

		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("ActionPerformed searchBar");
		/* panel affiché */
		if (panel.getFrRadioButton().isSelected()) {
			System.out.println("FRSELECTED");
			panel.setInputField(2);
			panel.revalidate();

		} else {
			System.out.println("CNSELECTED");
			panel.setInputField(1);
			panel.revalidate();

		}

	}

}
