package et4.ihm.mvc.panel.body;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import et4.ihm.mvc.Model;
import et4.ihm.mvc.View;
import et4.ihm.mvc.panel.UIColor;

public class BodyPanel extends JPanel{

	SearchPanel search;
	KnowledgePanel knowledge;
	LearningPanel learning;
	JScrollPane scrollSearch,scrollKnowledge;
	
	public BodyPanel(Model model) {
		super();
		
		search = new SearchPanel(model);
		search.setBackground(UIColor.BLUE_LIGHT);
		//search.setName("Search");
		knowledge = new KnowledgePanel(model);
		knowledge.setBackground(UIColor.BLUE_NORMAL);
		//knowledge.setName("Knowledge");
		learning = new LearningPanel(model);
		learning.setBackground(UIColor.BLUE_DARK);
		//learning.setName("Learning");
		
		scrollSearch = new JScrollPane();
		scrollSearch.setBackground(UIColor.BLUE_LIGHT);
		scrollKnowledge = new JScrollPane();
		scrollKnowledge.setBackground(UIColor.BLUE_NORMAL);
		
		setLayout(new BorderLayout());
		scrollSearch.setViewportView(search);
		scrollKnowledge.setViewportView(knowledge);
		
		showKnowledge();
		showSearch();


	}
	

	public SearchPanel getSearch() {
		return search;
	}

	public KnowledgePanel getKnowledge() {
		return knowledge;
	}

	public LearningPanel getLearning() {
		return learning;
	}
	
	public void hideAll() {
		
		scrollSearch.setVisible(false);
		search.setVisible(false);
		scrollKnowledge.setVisible(false);
		knowledge.setVisible(false);
		learning.setVisible(false);
	}
	
	/**
	 * Met a jour la taille de la scrollbar 
	 * Plus il y a de contenu + la scrollbar
	 * s'aggrandit
	 */
	private void updateSizeSearch() {
		search.setPreferredSize(new Dimension(View.width-20,120*search.getSearchComponent().size()+120));
		//scrollSearch.setPreferredSize(new Dimension(View.width,140*3));
		
	}
	
	/**
	 * Met a jour la taille de la scrollbar 
	 * Plus il y a de contenu + la scrollbar
	 * s'aggrandit
	 */
	private void updateSizeKnowledge() {
		knowledge.setPreferredSize(new Dimension(View.width-20,120*knowledge.getKnowledgeComponent().size()+120));
		//scrollKnowledge.setPreferredSize(new Dimension(View.width,140*3));
	}

	public void showSearch() {
		hideAll();
		updateSizeSearch();
		scrollSearch.setVisible(true);
		search.setVisible(true);
		add(scrollSearch,BorderLayout.CENTER);

		revalidate(); //forcer le refresh
	}

	public void showKnowledge() {
		hideAll();
		updateSizeKnowledge();
		knowledge.setVisible(true);
		scrollKnowledge.setVisible(true);
		add(scrollKnowledge,BorderLayout.CENTER);
		
		revalidate(); // forcer le refresh
	}
	public void showLearning() {
		hideAll();
		add(learning,BorderLayout.CENTER);
		learning.setVisible(true);
	
		revalidate(); //forcer le refresh
	}
}
