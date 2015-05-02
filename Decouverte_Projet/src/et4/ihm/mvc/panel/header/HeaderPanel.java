package et4.ihm.mvc.panel.header;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import et4.ihm.mvc.View;
import et4.ihm.mvc.controller.HeaderController;
import et4.ihm.mvc.panel.UIColor;
import et4.ihm.mvc.panel.body.BodyPanel;
import et4.ihm.mvc.panel.footer.FooterPanel;

public class HeaderPanel extends JPanel{
	
	JPanel searchBtn,knowledgeBtn,learningBtn;
	private final String FONT = "Helvetica-Neue";
	private final int FONT_SIZE = 20;
	
	public HeaderPanel(BodyPanel bodyPanel, FooterPanel footerPanel) {
		super();
		HeaderController controller = new HeaderController(this,bodyPanel, footerPanel);
		searchBtn = new JPanel();
		searchBtn.setLayout(new BoxLayout(searchBtn, BoxLayout.X_AXIS));
		searchBtn.add(Box.createHorizontalGlue());
		JLabel search = new JLabel("Search");
		search.setFont(new Font(FONT, Font.PLAIN, FONT_SIZE));
		search.setForeground(Color.WHITE);
		searchBtn.add(search);
		searchBtn.add(Box.createHorizontalGlue());

		knowledgeBtn = new JPanel();
		knowledgeBtn.setLayout(new BoxLayout(knowledgeBtn, BoxLayout.X_AXIS));
		knowledgeBtn.add(Box.createHorizontalGlue());
		JLabel knowledge = new JLabel("Knowledge");
		knowledge.setFont(new Font(FONT, Font.PLAIN, FONT_SIZE));
		knowledge.setForeground(Color.WHITE);
		knowledgeBtn.add(knowledge);
		knowledgeBtn.add(Box.createHorizontalGlue());
		
		learningBtn = new JPanel();
		learningBtn.setLayout(new BoxLayout(learningBtn, BoxLayout.X_AXIS));
		learningBtn.add(Box.createHorizontalGlue());
		JLabel learn = new JLabel("Learn");
		learn.setFont(new Font(FONT, Font.PLAIN, FONT_SIZE));
		learn.setForeground(Color.WHITE);
		learningBtn.add(learn);
		learningBtn.add(Box.createHorizontalGlue());
		
		searchBtn.addMouseListener(controller); searchBtn.setBackground(UIColor.BLUE_LIGHT);
		searchBtn.setPreferredSize(new Dimension(View.width*33/100,50));
		
		
		knowledgeBtn.addMouseListener(controller); knowledgeBtn.setBackground(UIColor.BLUE_NORMAL);
		knowledgeBtn.setPreferredSize(new Dimension(View.width*33/100,50));

		learningBtn.addMouseListener(controller); learningBtn.setBackground(UIColor.BLUE_DARK);
		learningBtn.setPreferredSize(new Dimension(View.width*33/100,50));

		
		setLayout(new BorderLayout());
		add(searchBtn, BorderLayout.LINE_START);
		add(knowledgeBtn,BorderLayout.CENTER);
		add(learningBtn,BorderLayout.LINE_END);

	}
	
	@Override
	public void paint(Graphics graphics) {
		super.paint(graphics);	
		
	}

	public JPanel getSearchBtn() {
		return searchBtn;
	}

	public JPanel getKnowledgeBtn() {
		return knowledgeBtn;
	}

	public JPanel getLearningBtn() {
		return learningBtn;
	}
	
	
}
