package et4.ihm.mvc;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import et4.ihm.mvc.panel.UIColor;

public class FooterOpenFileBtn extends JPanel{
	
	private final String FONT = "Helvetica-Neue";
	private final int FONT_SIZE = 20;
	
	public FooterOpenFileBtn(String text) {
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		add(Box.createHorizontalGlue());
		JLabel open = new JLabel(text);
		open.setFont(new Font(FONT, Font.PLAIN, FONT_SIZE));
		open.setForeground(Color.WHITE);
		add(open);
		add(Box.createHorizontalGlue());
		setBackground(UIColor.APSHALT_DARK);
	}

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
