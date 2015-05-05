package et4.ihm.mvc.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTextArea;

import et4.ihm.mvc.Model;
import et4.ihm.mvc.component.LearningSubmitBtn;

public class LearningController implements KeyListener, MouseListener{

	private Model model;
	private LearningSubmitBtn btn;
	private JTextArea input, output;
	public LearningController(Model model, LearningSubmitBtn btn, JTextArea input, JTextArea output) {
		this.model = model;
		this.btn = btn;
		this.input = input;
		this.output = output;
		this.btn.addMouseListener(this);
		this.input.addKeyListener(this);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==btn) {
			System.out.println("input.get" + input.getText());
			output.setText(model.learn(input.getText()));
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}

}
