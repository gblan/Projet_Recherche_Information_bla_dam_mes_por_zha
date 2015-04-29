package et4.ihm.mvc.panel.body;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import et4.ihm.mvc.Model;
import et4.ihm.mvc.View;
import et4.ihm.mvc.component.SearchComponent;
import et4.ihm.mvc.controller.SearchBarController;

public class SearchPanel extends JPanel{

	JTextField textField;
	private ArrayList<SearchComponent> components;
	private final String FONT = "Helvetica-Neue";
	private final int FONT_SIZE = 20;
	public final static int heightComponent = 100;
	public final static int space = 20;
	
	public SearchPanel(Model model) {
		super();
		
		components = new ArrayList<SearchComponent>();
		
		
		textField = new JTextField();
		textField.setPreferredSize(new Dimension(View.width-20,50));
		textField.setFont(new Font(FONT, Font.PLAIN, FONT_SIZE));
		textField.setCaretColor(Color.WHITE);
		int r = 241, g = 196, b = 15, a = 255;
		textField.setBackground(new Color(r,g,b,a));
		textField.setForeground(Color.white);
		//
		
		JLabel label = new JLabel("Que recherchez vous ?");
		label.setFont(new Font(FONT, Font.PLAIN, FONT_SIZE));
		label.setForeground(Color.WHITE);
		
		add(label);
		
		SearchBarController c = new SearchBarController(model);
		textField.addKeyListener(c);
		textField.addActionListener(c);
		
		add(textField);

	}
	
	public void update(ArrayList<SearchComponent> components) {
		
		this.components.clear();
		this.components.addAll(components);
		repaint();
	}
	
	@Override
	public void paint(Graphics graphics) {
		
		super.paint(graphics);

		Graphics2D g = (Graphics2D) graphics;
		RenderingHints rh = new RenderingHints(
	             RenderingHints.KEY_TEXT_ANTIALIASING,
	             RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	    g.setRenderingHints(rh);
		
		int ysentence = 0;
		int ynewword = 0;
		int ytranslation = 0;
		int yphonetic = 0;
		int yglobal = heightComponent;
		
		for(SearchComponent sc : components) {
			
			ysentence = yglobal+25;
			ytranslation = ysentence+20;
			yphonetic = ytranslation+20;
			ynewword = yphonetic+20;
			
			g.setColor(sc.getBackground());
			g.fill(sc.getR().r);
			g.setColor(sc.getBorder());
			g.draw(sc.getR().r);
			
			g.setColor(Color.WHITE);

			g.setFont(new Font("Helvetica-Neue", Font.PLAIN, 18));
			g.drawString("Sentence : "+sc.getSentence(), 20, ysentence);
			
			g.setFont(new Font("Helvetica-Neue", Font.PLAIN, 16));
			g.drawString("Translation : "+sc.getTranslation(), 20, ytranslation);
			
			g.setFont(new Font("Helvetica-Neue", Font.PLAIN, 16));
			g.drawString("Phonetic : "+sc.getPhonetic(), 20, yphonetic);
			
			g.setFont(new Font("Helvetica-Neue", Font.PLAIN, 12));
			g.drawString("New Words : "+sc.getNewWords(), 20, ynewword);
			
			yglobal+=heightComponent+space;
			
		}
		
		
	}

	public ArrayList<SearchComponent> getSearchComponent() {
		return components;
	}
	
	
	
}
