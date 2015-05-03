package et4.ihm.mvc.panel.footer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import et4.ihm.mvc.FooterOpenFileBtn;
import et4.ihm.mvc.Model;
import et4.ihm.mvc.controller.FooterController;

public class FooterPanel extends JPanel{

	private final String FONT = "Helvetica-Neue";
	private final int FONT_SIZE = 20;
	private FooterOpenFileBtn btn;
	private JLabel loading;
	private Component boxright, boxleft;
	public FooterPanel(Model model) {
		
		btn = new FooterOpenFileBtn("I have learned new vocab ! (Click Me :D)");
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		boxleft = Box.createHorizontalGlue();
		add(boxleft);
		loading = new JLabel("Loading ...");
		loading.setFont(new Font(FONT, Font.PLAIN, FONT_SIZE));
		loading.setForeground(Color.WHITE);
		add(loading);
		boxright = Box.createHorizontalGlue();
		add(boxright);
		
		FooterController c = new FooterController(model,btn);
		
		showLoading();
	}
	
	public void showBtn() {
		boxleft.setVisible(false);
		boxright.setVisible(false);
		loading.setVisible(false);
		setLayout(new BorderLayout());
		add(btn,BorderLayout.CENTER);
		btn.setVisible(true);
	}
	
	public void showLoading() {
		btn.setVisible(false);
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		boxleft.setVisible(true);
		boxright.setVisible(true);
		loading.setVisible(true);
	}
	
	/**
	 * AJOUTER une barre de loading ...
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		Graphics2D graphics = (Graphics2D) g;
		RenderingHints rh = new RenderingHints(
				RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		graphics.setRenderingHints(rh);
	}
	
}
