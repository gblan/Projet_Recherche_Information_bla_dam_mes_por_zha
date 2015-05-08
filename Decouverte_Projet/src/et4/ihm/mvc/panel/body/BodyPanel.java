package et4.ihm.mvc.panel.body;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import et4.ihm.mvc.Model;
import et4.ihm.mvc.View;
import et4.ihm.mvc.panel.UIColor;
import et4.ihm.mvc.panel.footer.FooterPanel;

public class BodyPanel extends JPanel{

	SearchPanel search;
	KnowledgePanel knowledge;
	LearningPanel learning;
	JScrollPane scrollSearch,scrollKnowledge;
	
	public BodyPanel(Model model, FooterPanel footer) {
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
		scrollSearch.setBorder(BorderFactory.createEmptyBorder());
		scrollKnowledge = new JScrollPane();
		scrollKnowledge.setBackground(UIColor.BLUE_NORMAL);
		scrollKnowledge.setBorder(BorderFactory.createEmptyBorder());
		
		setLayout(new BorderLayout());
		scrollSearch.setViewportView(search);
		scrollKnowledge.setViewportView(knowledge);
		
		

		showSearch();
		footer.showLoading();
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
	public void updateSizeSearch() {
		//System.out.println(search.getSearchComponent().size());
		search.setPreferredSize(new Dimension(View.width-20,120*search.getSearchComponent().size()+120));
		//scrollSearch.setPreferredSize(new Dimension(View.width,140*3));
		
	}
	
	/**
	 * Met a jour la taille de la scrollbar 
	 * Plus il y a de contenu + la scrollbar
	 * s'aggrandit
	 */
	public void updateSizeKnowledge() {
		knowledge.setPreferredSize(new Dimension(View.width-20,120*knowledge.getKnowledgeComponent().size()+120));
		//scrollKnowledge.setPreferredSize(new Dimension(View.width,140*3));
	}

	public void showSearch() {
		hideAll();
		updateSizeSearch();
		
		search.setVisible(true);
		
		add(scrollSearch,BorderLayout.CENTER);
		scrollSearch.setVisible(true);
		scrollSearch.revalidate();
		
		revalidate(); //forcer le refresh
		setVisible(true);
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
