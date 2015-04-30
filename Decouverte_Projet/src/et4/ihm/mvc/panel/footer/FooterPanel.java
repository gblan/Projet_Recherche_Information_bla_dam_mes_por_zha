package et4.ihm.mvc.panel.footer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FooterPanel extends JPanel{

	private final String FONT = "Helvetica-Neue";
	private final int FONT_SIZE = 20;
	
	public FooterPanel() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		add(Box.createHorizontalGlue());
		JLabel learn = new JLabel("Loading ...");
		learn.setFont(new Font(FONT, Font.PLAIN, FONT_SIZE));
		learn.setForeground(Color.WHITE);
		add(learn);
		add(Box.createHorizontalGlue());
		
	}
	
	/**
	 * AJOUTER une barre de loading ...
	 */
	@Override
	public void paint(Graphics graphics) {
		super.paint(graphics);
	}
	
}
