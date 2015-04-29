package et4.ihm.mvc.panel.body;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import et4.ihm.mvc.Model;

public class LearningPanel extends JPanel{

	private Model model;
	public LearningPanel(Model model) {
		this.model = model;
	}
	
	@Override
	public void paint(Graphics graphics) {
		
		super.paint(graphics);
		
		Graphics2D g = (Graphics2D) graphics;
		RenderingHints rh = new RenderingHints(
	             RenderingHints.KEY_TEXT_ANTIALIASING,
	             RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	    g.setRenderingHints(rh);
		
		
	}
}
