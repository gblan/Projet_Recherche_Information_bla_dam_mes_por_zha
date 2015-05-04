package et4.ihm.mvc;

import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.Observable;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
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
				components.add(new SearchComponent(new Point(0,SearchPanel.heightComponent*(i+1)),"ä½ å¥½å�—","Comment allez-vous ?",getPinyin("ä½ å¥½å�—"), "allez", View.width-20, SearchPanel.heightComponent));
			}
			else {
				//components.add(new SearchComponent(new Point(0,SearchPanel.heightComponent*(i+1)+SearchPanel.space*i),"ä½ å¥½å�—","Comment allez-vous ?","NÇ� hÇŽo ma?", "allez", View.width-20, SearchPanel.heightComponent));
				components.add(new SearchComponent(new Point(0,SearchPanel.heightComponent*(i+1)+SearchPanel.space*i),"ä½ å¥½å�—","Comment allez-vous ?",getPinyin("ä½ å¥½å�—"), "allez", View.width-20, SearchPanel.heightComponent));
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
				components.add(new KnowledgeComponent(new Point(0,KnowledgePanel.heightComponent+KnowledgePanel.space*i),"é¸Ÿ","oiseau",getPinyin("é¸Ÿ"), rate, View.width, KnowledgePanel.heightComponent));
			}
			else {
				//components.add(new KnowledgeComponent(new Point(0,KnowledgePanel.heightComponent*(i+1)+KnowledgePanel.space*i),"ç”·äºº","homme","NÃ¡nrÃ©n", rate, View.width, KnowledgePanel.heightComponent));
				components.add(new KnowledgeComponent(new Point(0,KnowledgePanel.heightComponent*(i+1)+KnowledgePanel.space*i),"é¸Ÿ","oiseau", getPinyin("é¸Ÿ"), rate, View.width, KnowledgePanel.heightComponent));
			}
			//components.add(new SearchComponent(new Point(0,heightComponent*2+space),"è¦�è¿˜æ˜¯ä¸�","Etre ou ne pas etre","YÃ o hÃ¡ishÃ¬ bÃ¹","not", View.width, heightComponent));
			rate+=80;
		}
		
		// Pattern observer appele
		setChanged();
		notifyObservers(components);
	}

	public void search(String text) {
		
		System.out.println("Action dans le model 'search' : "+text);
	}
	
	public static String getPinyin(String phraseChinoise){
		String pinYinDePhrase = null;
		HanyuPinyinOutputFormat format= new HanyuPinyinOutputFormat();
		format.setToneType(HanyuPinyinToneType.WITH_TONE_MARK);
		format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
		                  
		for (int j = 0; j < phraseChinoise.length(); j++) {
			char caractereChinois = phraseChinoise.charAt(j);
			String[] pinyinArray = null;
			try {
				pinyinArray = PinyinHelper
						.toHanyuPinyinStringArray(caractereChinois, format);
			} catch (BadHanyuPinyinOutputFormatCombination e) {
				e.printStackTrace();
			}
			/* afficher toutes les possibilites de Pinyin
			for (int i = 0; i < pinyinArray.length; ++i) {
				System.out.println(pinyinArray[i]);
			}
			*/
			//System.out.println(pinyinArray[0]);
			try {
				//temp est inutile, c'est juste pour savoir pinyinArray est null ou pas
				int temp = pinyinArray.length;
				if(pinYinDePhrase == null){
					pinYinDePhrase = pinyinArray[0];
					}else{
						pinYinDePhrase = pinYinDePhrase + " " + pinyinArray[0];
					}
			} catch (NullPointerException e){
				pinYinDePhrase = pinYinDePhrase + " " + caractereChinois;
			}
		}
		return pinYinDePhrase;
	}

	public String learn(String text) {
		// TODO Auto-generated method stub
		return getPinyin(text);
	}

	/**
	 * Prend en parametre le fichier que l'utilisateur a choisi dans le FileChooser
	 * @param fichier
	 */
	public void updateKnowledge(File file) {
		
		System.out.println("Model : updateKnowledge in "+file.getAbsolutePath());
		
	}
}
