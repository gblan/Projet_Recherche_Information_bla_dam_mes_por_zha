package et4.ihm.mvc.panel.body;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import et4.ihm.mvc.Model;
import et4.ihm.mvc.View;
import et4.ihm.mvc.component.LearningSubmitBtn;
import et4.ihm.mvc.controller.LearningController;
import et4.ihm.mvc.panel.UIColor;

public class LearningPanel extends JPanel{

	private final String FONT = "Helvetica-Neue";
	private final int FONT_SIZE = 20;
	private Model model;
	public LearningPanel(Model model) {
		this.model = model;
		
		JTextArea inputarea = new JTextArea(); inputarea.setBackground(UIColor.BLUE_DARK);
			inputarea.setForeground(Color.WHITE);
			inputarea.setFont(new Font(FONT, Font.PLAIN, FONT_SIZE));
			inputarea.setCaretColor(Color.WHITE);
		JLabel inputlabel = new JLabel("Input");
			inputlabel.setBackground(UIColor.BLUE_DARK); 
			inputlabel.setOpaque(true);
			inputlabel.setForeground(Color.WHITE);
			inputlabel.setFont(new Font(FONT, Font.PLAIN, FONT_SIZE));
			
		JPanel input = new JPanel();
		input.setLayout(new BorderLayout());
		
		input.add(inputlabel, BorderLayout.PAGE_START);
		input.add(inputarea, BorderLayout.CENTER);
		
		/*
		 * knowledgeBtn.add(Box.createHorizontalGlue());
		JLabel knowledge = new JLabel("Knowledge");
		knowledge.setFont(new Font(FONT, Font.PLAIN, FONT_SIZE));
		knowledge.setForeground(Color.WHITE);
		knowledgeBtn.add(knowledge);
		knowledgeBtn.add(Box.createHorizontalGlue());
		 */

		
		JTextArea outputarea = new JTextArea(); outputarea.setBackground(UIColor.BLUE_DARK);
			outputarea.setForeground(Color.WHITE);
			outputarea.setFont(new Font(FONT, Font.PLAIN, FONT_SIZE));
			outputarea.setEditable(false);
			outputarea.setCaretColor(Color.WHITE);
		JLabel outputlabel = new JLabel("Output"); 
			outputlabel.setBackground(UIColor.BLUE_DARK); 
			outputlabel.setOpaque(true);
			outputlabel.setForeground(Color.WHITE);
			outputlabel.setFont(new Font(FONT, Font.PLAIN, FONT_SIZE));
		
		JPanel output = new JPanel();
		output.setLayout(new BorderLayout());
		output.add(outputlabel, BorderLayout.PAGE_START);
		output.add(outputarea, BorderLayout.CENTER);
		
		LearningSubmitBtn btn = new LearningSubmitBtn("Submit");
		output.add(btn, BorderLayout.PAGE_END);
		
		setLayout(new GridLayout(2, 1));
		
		add(input);
		add(output);
		
		
		LearningController c = new LearningController(model, btn, inputarea, outputarea);
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
