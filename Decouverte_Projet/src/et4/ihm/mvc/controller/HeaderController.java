package et4.ihm.mvc.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import et4.ihm.mvc.panel.body.BodyPanel;
import et4.ihm.mvc.panel.body.KnowledgePanel;
import et4.ihm.mvc.panel.body.LearningPanel;
import et4.ihm.mvc.panel.body.SearchPanel;
import et4.ihm.mvc.panel.header.HeaderPanel;

public class HeaderController implements ActionListener, MouseListener{

	HeaderPanel header;
	BodyPanel body;
	SearchPanel search;
	KnowledgePanel knowledge;
	LearningPanel learning;
	
	public HeaderController(HeaderPanel header, BodyPanel body, SearchPanel search, KnowledgePanel knowledge, LearningPanel learning) {
		this.header = header;
		this.body = body;
		this.search = search;
		this.knowledge = knowledge;
		this.learning = learning;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
		//System.out.println(e.getSource());
		
		if(header.getSearchBtn().equals(e.getSource())){
			System.out.println("Search");
			body.showSearch();
		}
		else if(header.getKnowledgeBtn().equals(e.getSource())){
			System.out.println("Knowledge");
			body.showKnowledge();
		}
		else if(header.getLearningBtn().equals(e.getSource())){
			System.out.println("Learning");
			body.showLearning();
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
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Action");

	}

}
