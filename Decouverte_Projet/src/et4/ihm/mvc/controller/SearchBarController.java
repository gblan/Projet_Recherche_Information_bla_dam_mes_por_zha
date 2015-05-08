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
//		//System.out.println("keyPressed");

		if (e.getKeyCode() == KeyEvent.VK_ENTER) {

			if (panel.getFrRadioButton().isSelected()) {
				//System.out.println("searchFR OK : "+panel.getTextAreaFR().getText());

				model.search(panel.getTextAreaFR().getText(), 2);
				model.notifyChanges();
			} else if (panel.getCnRadioButton().isSelected()) {
				//System.out.println("searchCN OK : "+panel.getTextAreaCN().getText());

				model.search(panel.getTextAreaCN().getText(), 1);
				model.notifyChanges();

			}

			/* pour ne pas afficher le caractère \n */
			e.consume();

			//System.out.println("keyPressed ENTER");
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//System.out.println("ActionPerformed searchBar");
		/* panel affiché */
		if (panel.getFrRadioButton().isSelected()) {
			//System.out.println("FRSELECTED");
			panel.setInputField(2);
			panel.revalidate();

		} else {
			//System.out.println("CNSELECTED");
			panel.setInputField(1);
			panel.revalidate();

		}

	}

}
