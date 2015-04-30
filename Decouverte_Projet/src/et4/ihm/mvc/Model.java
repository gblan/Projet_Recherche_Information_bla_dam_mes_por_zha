package et4.ihm.mvc;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Observable;

import et4.ihm.mvc.component.KnowledgeComponent;
import et4.ihm.mvc.component.SearchComponent;
import et4.ihm.mvc.panel.body.KnowledgePanel;
import et4.ihm.mvc.panel.body.SearchPanel;

/**
 * Noyau de l'interface, tout les appels aux algos doivent se faire ici meme
 * et seulement ici
 * @author All
 *
 */
public class Model extends Observable{

	public Model() {
		
	}
	
	/**
	 * Prend en parametre un objet (indefini pour le moment)
	 * qui permettra de remplir le panel de recherche
	 * @param result
	 */
	public void fillSearch(Object result) {
					
		ArrayList<SearchComponent> components = new ArrayList<SearchComponent>();

		for(int i = 0; i<15; i++) {

			//Le premier composant n'a pas de + space dans le calcul
			if(i==0) {
				components.add(new SearchComponent(new Point(0,SearchPanel.heightComponent*(i+1)),"你好吗","Comment allez-vous ?","Nǐ hǎo ma?", "allez", View.width-20, SearchPanel.heightComponent));
			}
			else {
				components.add(new SearchComponent(new Point(0,SearchPanel.heightComponent*(i+1)+SearchPanel.space*i),"你好吗","Comment allez-vous ?","Nǐ hǎo ma?", "allez", View.width-20, SearchPanel.heightComponent));
			}
		}
		
		// Pattern observer appele
		setChanged();
		notifyObservers(components);
	}
	
	/**
	 * Prend en parametre un objet (indefini pour le moment)
	 * qui permettra de remplir le panel de knowledge
	 * @param result
	 */
	public void fillKnowledge(Object result) {
		
		ArrayList<KnowledgeComponent> components = new ArrayList<KnowledgeComponent>();

		float rate = 10;
		for(int i = 0; i<15; i++) {
			//Le premier composant n'a pas de + space dans le calcul
			if(i==0) {
				components.add(new KnowledgeComponent(new Point(0,KnowledgePanel.heightComponent+KnowledgePanel.space*i),"男人","homme","Nánrén", rate, View.width, KnowledgePanel.heightComponent));
			}
			else {
				components.add(new KnowledgeComponent(new Point(0,KnowledgePanel.heightComponent*(i+1)+KnowledgePanel.space*i),"男人","homme","Nánrén", rate, View.width, KnowledgePanel.heightComponent));
			}
			//components.add(new SearchComponent(new Point(0,heightComponent*2+space),"要还是不","Etre ou ne pas etre","Yào háishì bù","not", View.width, heightComponent));
			rate+=80;
		}
		
		// Pattern observer appele
		setChanged();
		notifyObservers(components);
	}

	public void search(String text) {
		
		System.out.println("Action dans le model 'search' : "+text);
	}
}