package et4.ihm.mvc;

import java.awt.Container;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Scanner;

import org.springframework.core.io.ClassPathResource;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.commons.io.FileUtils;

import et4.beans.SuffixArray;
import et4.corpus.MonolingualCorpus;
import et4.ihm.mvc.component.KnowledgeComponent;
import et4.ihm.mvc.component.SearchComponent;
import et4.ihm.mvc.panel.body.KnowledgePanel;
import et4.ihm.mvc.panel.body.SearchPanel;
import et4.index.Token;
import et4.index.Tokenization;
import et4.index.TokenizationChinese;
import et4.index.TokenizationChinese2;
import et4.index.TokenizationFrench;
import graphe.GrapheWord2Vec;
import graphe.word2vec.Word2VecObject;

/**
 * Noyau de l'interface, tout les appels aux algos doivent se faire ici meme et
 * seulement ici
 * 
 * @author All
 *
 */
public class Model extends Observable {
	private Word2VecObject tw2v;
	private GrapheWord2Vec graphe;
	private MonolingualCorpus corpus;
	private ArrayList<SearchComponent> listComponent = new ArrayList<SearchComponent>();;

	public Model() {
		ArrayList<String> tokenConnu = new ArrayList<String>();
		tokenConnu.add("国");
		tokenConnu.add("中");
		tokenConnu.add("中国");
		tokenConnu.add("法国	");
		tokenConnu.add("京");
		tokenConnu.add("北京");
		tokenConnu.add("南");
		tokenConnu.add("南京");
		tokenConnu.add("海");
		tokenConnu.add("上海");
		tokenConnu.add("天");
		tokenConnu.add("穴");
		tokenConnu.add("头");
		tokenConnu.add("茶");
		tokenConnu.add("工");
		graphe = new GrapheWord2Vec();

		// launch();

		ClassPathResource resource = new ClassPathResource("chCorpusUTF.txt");
		System.out.println("ClassPathRessource");

		File f;

		try {
			f = resource.getFile();
			tw2v = new Word2VecObject(f);
			tw2v.launch("她很少迟到。");
			double s = tw2v.similarity("她很少迟到。", "她很少迟到。");
			System.out.println("s = " + s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println("model");
	}

	/**
	 * Prend en parametre un objet (indefini pour le moment) qui permettra de
	 * remplir le panel de knowledge
	 * 
	 * @param result
	 */
	public void fillKnowledge(Object result) {

		ArrayList<KnowledgeComponent> components = new ArrayList<KnowledgeComponent>();

		int i = 0;
		for (Entry<String, Double> entry : graphe.getDico().entrySet()) {
			if (i == 0) {
				components.add(new KnowledgeComponent(new Point(0, KnowledgePanel.heightComponent
						+ KnowledgePanel.space * i), entry.getKey(), getTranslation(entry.getKey()), getPinyin(entry
						.getKey()), entry.getValue(), View.width, KnowledgePanel.heightComponent));
			} else {
				// components.add(new KnowledgeComponent(new
				// Point(0,KnowledgePanel.heightComponent*(i+1)+KnowledgePanel.space*i),"ç”·äºº","homme","NÃ¡nrÃ©n",
				// rate, View.width, KnowledgePanel.heightComponent));
				components.add(new KnowledgeComponent(new Point(0, KnowledgePanel.heightComponent * (i + 1)
						+ KnowledgePanel.space * i), entry.getKey(), getTranslation(entry.getKey()), getPinyin(entry
						.getKey()), entry.getValue(), View.width, KnowledgePanel.heightComponent));
			}
			i++;
		}
		
		// Pattern observer appele
		setChanged();
		notifyObservers(components);
	}

	/**
	 * @param map
	 * @param desc
	 * @return Map Sorted by value
	 */
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map, boolean desc) {
		List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
			public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
				if (desc) {
					return (o1.getValue()).compareTo(o2.getValue());
				} else {
					return (o2.getValue()).compareTo(o1.getValue());
				}
			}
		});

		Map<K, V> result = new LinkedHashMap<K, V>();
		for (Map.Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}
	
	private String getTranslation(String key) {
		System.out.println("#### Key : "+key);
		ArrayList<String> sentencesFrench = new ArrayList<String>();
		/* recherche des phrases contenant le mot key */
		search(key, 1);

		/* recupération des phrases traduites */
		for (int i = 0; i < listComponent.size(); i++) {
			sentencesFrench.add(listComponent.get(i).getTranslation());
			if(i==100){
				break;
			}
		}

		/*
		 * recherche du mot le plus présent dans les phrases en français en le
		 * triant selon le plus présent
		 */
		HashMap<String, Integer> map = new HashMap<String, Integer>();

		for (String str : sentencesFrench) {
			String[] strings = str.replaceAll("[(){},.;'!?<>%]", "").split("\\s+");

			/* remplissage de la hashMap*/
			for (int i = 0; i < strings.length; i++) {
				if (map.containsKey(strings[i])) {
					map.put(strings[i], 1 + map.get(strings[i]));
				} else {
					map.put(strings[i], 1);
				}
			}
					
		}
		
		/* tri de la hashmap*/
		Map<String, Integer> result = sortByValue(map, false);
		
//		for(Entry<String, Integer> entry : result.entrySet()){
//			System.out.println("key : "+entry.getKey());
//			System.out.println("value : "+entry.getValue());
//		}
//		
		HashMap<String, Integer> commonToken = new HashMap<String, Integer>();

		commonToken.put("la", 1);
		commonToken.put("le", 1);
		commonToken.put("les", 1);
		commonToken.put("l", 1);
		commonToken.put("un", 1);
		commonToken.put("une", 1);
		commonToken.put("des", 1);
		commonToken.put("d", 1);
		commonToken.put("du", 1);
		commonToken.put("de", 1);
		commonToken.put("des", 1);
		commonToken.put("au", 1);
		commonToken.put("que", 1);
		commonToken.put("et", 1);
		commonToken.put("à", 1);
		
		for(Entry<String, Integer> entry : result.entrySet()){
			if(!commonToken.containsKey(entry.getKey())){
				return entry.getKey();
			}
		}
		return "KO";
	}

	/**
	 * @param searchinput
	 * @param langue
	 *            1 mandarin, 2 français
	 */
	public void search(String searchinput, int langue) {
		int j = 0;
		listComponent.clear();

		if (searchinput.equals(""))
			return;

		Tokenization tf = new TokenizationFrench();
		Tokenization tc = new TokenizationChinese();

		try {
			String[] tradCh = FileUtils.readFileToString(new File("resources/ch.txt"), "UTF-8").split("\n");
			String[] tradLink = FileUtils.readFileToString(new File("resources/linksChineseFrench.txt"), "UTF-8")
					.split("\n");
			String[] tradFr = FileUtils.readFileToString(new File("resources/fr.txt"), "UTF-8").split("\n");

			MonolingualCorpus corpus;
			if (langue == 1) {
				corpus = new MonolingualCorpus(tc, "resources/chCorpus.txt");
			} else {
				corpus = new MonolingualCorpus(tf, "resources/frCorpus.txt");
			}

			for (int i = 0; i < corpus.getCorpusArray().length; i++) {
				if (corpus.getCorpusArray()[i].contains(searchinput)) {
					SuffixArray suffixArray = new SuffixArray(corpus, i);
					suffixArray.initTabSuffix();
					suffixArray.qsort(suffixArray.getTabSuffix(), 0, corpus.getNbMots().get(i) - 1);
					suffixArray.initLCPVector();

					/*
					 * for (int j = 0; j < suffixArray.getLCPVector().length ;
					 * j++) { System.out.println(suffixArray.getLCPVector()[j]);
					 * }
					 */
					ArrayList<Integer> positions = suffixArray.getAllPositionsOfPhrase(searchinput);
					// currentCorpus.getCorpusArray()[i] =
					// currentCorpus.getCorpusArray()[i].substring(0,
					// currentCorpus.getCorpusArray()[i].length() - 1);

					if (positions.size() != 0) {

						String trad = "";
						String piying = "";
						if (langue == 1) {
							trad = findTranslationChineseFrench(corpus.getCorpusArray()[i], 1, tradCh, tradFr, tradLink);
							// System.out.println("Chinois ##"+corpus.getCorpusArray()[i]+"####");
							// System.out.println(Model.getPinyin(currentCorpus.getCorpusArray()[i]));
							piying = Model.getPinyin(corpus.getCorpusArray()[i]);
						} else {
							trad = findTranslationChineseFrench(corpus.getCorpusArray()[i], 2, tradCh, tradFr, tradLink);
							// System.out.println("Fr: ##"+corpus.getCorpusArray()[i]+"####");
							// System.out.println(trad);
							// System.out.println(Model.getPinyin(trad));
							piying = Model.getPinyin(trad);

						}
						// System.out.println(currentCorpus.getCorpusArray()[i]);
						// System.out.println("Ligne: " + i + " Positions: " +
						// positions);
						// System.out.println("Occurence: " + positions.size());

						if (j == 0) {
							listComponent.add(new SearchComponent(new Point(0, SearchPanel.heightComponent
									+ SearchPanel.space * j), corpus.getCorpusArray()[i], trad, piying, "", positions
									.size(), View.width - 20, SearchPanel.heightComponent));
						} else {
							listComponent.add(new SearchComponent(new Point(0, SearchPanel.heightComponent * (j + 1)
									+ SearchPanel.space * j), corpus.getCorpusArray()[i], trad, piying, "", positions
									.size(), View.width - 20, SearchPanel.heightComponent));
						}

						j++;
						// }

						// System.out.println("corpus : " +
						// currentCorpus.getCorpusArray()[i]);
					}
				}
			}

		} catch (IOException e) {
			System.err.println("File Not Found");
		} catch (Exception e) {
			e.printStackTrace();
		}
		// System.out.println("Action dans le model 'search' : " + searchinput);

		/* tri par pertinence */
		Collections.sort(listComponent, comparePertinence());
		// listComponent.sort(comparePertinence());

	}

	public void notifyChanges() {

		/* notifie les observers */
		setChanged();
		notifyObservers(listComponent);
	}

	public static String getPinyin(String phraseChinoise) {
		String pinYinDePhrase = null;
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setToneType(HanyuPinyinToneType.WITH_TONE_MARK);
		format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);

		for (int j = 0; j < phraseChinoise.length(); j++) {
			char caractereChinois = phraseChinoise.charAt(j);
			String[] pinyinArray = null;
			try {
				pinyinArray = PinyinHelper.toHanyuPinyinStringArray(caractereChinois, format);
			} catch (BadHanyuPinyinOutputFormatCombination e) {
				e.printStackTrace();
			}
			/*
			 * afficher toutes les possibilites de Pinyin for (int i = 0; i <
			 * pinyinArray.length; ++i) { System.out.println(pinyinArray[i]); }
			 */
			// System.out.println(pinyinArray[0]);
			try {
				// temp est inutile, c'est juste pour savoir pinyinArray est
				// null ou pas
				int temp = pinyinArray.length;
				if (pinYinDePhrase == null) {
					pinYinDePhrase = pinyinArray[0];
				} else {
					pinYinDePhrase = pinYinDePhrase + " " + pinyinArray[0];
				}
			} catch (NullPointerException e) {
				pinYinDePhrase = pinYinDePhrase + " " + caractereChinois;
			}
		}
		return pinYinDePhrase;
	}

	/**
	 * @param text : input learn
	 * @return
	 */
	public String learn(String text) {
		double SEUIL = (double)70/100.0;
		String[] tokens;
		String str = "";
		if (text.equals("")) {
			return learnWord2Vec(text);
		} else {
			TokenizationChinese2 tok2 = new TokenizationChinese2();
			ArrayList<String> tokenChinese = new ArrayList<String>();
			ArrayList<String> tokensInconnu = new ArrayList<String>();
			HashMap<String, ArrayList<String>> mapPhraseRetenu = new HashMap<String, ArrayList<String>>();

			try {
				str = tok2.getTokensDePhraseEtoile(text);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			tokens = str.split("\\s+");
			
			for(int i=0;i<tokens.length;i++){

				System.out.println("tokens[i] : "+tokens[i]);
		
			}
			
			tokenChinese.addAll(Arrays.asList(tokens));
			
			for(String token : tokenChinese) {
				System.out.println(graphe.contains(token));
				if(!graphe.contains(token) || graphe.getDico().get(token) < 0.2) {
					/* ajout du mot inconnu a la liste*/
					tokensInconnu.add(token);
					System.out.println("Token inconnu ="+token);
				}
			}
			
			for(String token : tokensInconnu) {
				System.out.println("Boucle 1 : "+token);
				ArrayList<String> phrasesResult = new ArrayList<String>();
				
				/* learn automatique */
				
				/* filtre 1 : phrases qui contiennent le token */
				search(token, 1);				
				for(SearchComponent search : listComponent){
					System.out.println("Boucle 2");

					int connu = 0;
					String[] tmp = null;
					try {
						tmp = tok2.getTokensDePhraseEtoile(search.getSentence()).split("\\s+");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					for(int i=0;i<tmp.length;i++){

						if(!graphe.contains(tmp[i]) || graphe.getDico().get(tmp[i]) < 0.2) {
							/* ajout du mot inconnu a la liste*/
							connu++;
						}
					}
					
					double pourcentage = (double) connu / (double) tmp.length;

					if (pourcentage > SEUIL) {
						phrasesResult.add(search.getSentence());
					}
				}
				mapPhraseRetenu.put(token, phrasesResult);
				
				/* filtre 2 : phrases qui contiennent plus de 70% de mots conus*/
				
				
				
			}
			
			return getPinyin(text);
		}
	}

private String learnWord2Vec(String text) {
		
		System.out.println("-------------------------||||||| "+tw2v.similarity("买完整版", "买完整版"));
		
		/**
		 * Lire le corpus => trouve phrase avec des phrases bcp de mots connu
		 */
		double SEUIL = (double)70/100.0;
		ArrayList<String> phraseretenu = new ArrayList<String>();
		
		
		int connu = 0;
		ArrayList<String> tokenretenu = new ArrayList<String>();
		int line = 0;
		//System.out.println("Phrase : "+corpus.getIndex().get(i).getListTokens());
		
		/*
		 * 
		 * Recuperation des token du fichier
		 * 
		 */
		TokenizationChinese2 tc = new TokenizationChinese2();
		List<String> sentences = null;
		try {
			sentences = tc.getTokensDeFichierEtoile("chCorpusUTF.txt");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(sentences==null)
		{
			System.out.println("Error sentences null in Model");
			System.exit(0);
		}
		ArrayList<String> tokenChinese = new ArrayList<String>();
		for(String sentence : sentences) {
			tokenretenu.clear();
			tokenChinese.clear();
			
			String[] array = sentence.split(" ");
			
			for(String str : array) {
				System.out.println("str = "+str);
			}
			
			tokenChinese.addAll(Arrays.asList(array));
			
			for(String token : tokenChinese) {
				System.out.println(graphe.contains(token));
				if(graphe.contains(token)) {
					connu++;
					System.out.println("Token connu ="+token);
				}
				else {
					//System.out.println("Token Inconnu ="+token);
					//System.out.println("Token inconnu ="+token);
				}
			}
			if (connu != 0) {
				double pourcentage = (double) connu / (double) tokenChinese.size();
				
				/**
				 * Si on depace le seuil => la phrase est bonne pour l'affichage
				 */
				System.out.println("Pourcentage "+pourcentage);
				System.out.println("Phrase : "+sentence);
				if (pourcentage > SEUIL) {
					System.out.println("Pourcentage "+pourcentage);
					System.out.println("Phrase : "+sentence);
					// System.out.println("Phrase : "+corpus.getCorpusArray()[i]);
					phraseretenu.add(sentence);
				}
			}
			line++;
		}
		
		System.out.println("Fin");
		System.exit(0);
		String result = "";
		/**
		 * Faire Word2Vec
		 */
		/*ArrayList<Integer> keyCorpus = new ArrayList<Integer>();
		keyCorpus.addAll(phraseretenu.keySet());
		
		
		double SEUIL_W2V = (double)25.0/100.0;
		for (int i = 0; i < phraseretenu.size(); i++) {
			
			System.out.println("Les tokens inconnu : "+phraseretenu.get(keyCorpus.get(i)));
			String tokenrand = phraseretenu.get(keyCorpus.get(i)).get(0);
			
			double similarity = 0;
			for(String token : corpus.getIndex().get(keyCorpus.get(i)).getListTokens().keySet()) {
				if(graphe.contains(token)) {
					similarity+=tw2v.similarity(token, tokenrand);
					//System.out.println("Similarity Entre ["+token+","+tokenrand+"] ======== "+similarity);
				}
			}
			
			System.out.println("__________RESULT = "+(double)similarity/(double)corpus.getIndex().get(keyCorpus.get(i)).getListTokens().size()+"__________");
			if((double)similarity/(double)corpus.getIndex().get(keyCorpus.get(i)).getListTokens().size()>SEUIL_W2V) {
				result+=corpus.getCorpusArray()[keyCorpus.get(i)]+"\n";
			}
		}
		*/
		return result;
	}

	/**
	 * Prend en parametre le fichier que l'utilisateur a choisi dans le
	 * FileChooser
	 * 
	 * @param fichier
	 * @throws FileNotFoundException 
	 */
	public void updateKnowledge(File file) throws FileNotFoundException {
		
		System.out.println("Model : updateKnowledge in "+file.getAbsolutePath());
		TokenizationChinese2 tc = new TokenizationChinese2();
		List<String> sentences = null;
		try {
			sentences = tc.getTokensDeFichierEtoile(file.getAbsolutePath());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayList<String> tokennew = new ArrayList<String>();
		tokennew.addAll(sentences);
		
		//System.out.println("Nouveaux mots connus :D "+tokennew);
		
		System.out.println("Dico avant"+graphe);
		
		graphe.addDico(tokennew);
		
		System.out.println("Dico après"+graphe);
	}

	public static String findTranslationChineseFrench(String A, int choice, String[] dicoCh, String[] dicoFr,
			String[] link) {
		/*
		 * choice = 1 => Chinese to French choice = 2 => French to Chinese
		 */
		String idA = "";
		String idB = "";
		String B = "";
		int idLinkA;
		int idLinkB;
		String[] bufferA;
		String[] bufferB;

		if (choice == 1) {
			bufferA = dicoCh;
			bufferB = dicoFr;
			idLinkA = 0;
			idLinkB = 1;
		} else {
			bufferA = dicoFr;
			bufferB = dicoCh;
			idLinkA = 1;
			idLinkB = 0;
		}
		for (int i = 0; i < bufferA.length; i++) {
			if (bufferA[i].contains(A)) {
				String[] tmp = bufferA[i].split("	");
				String cmp = tmp[2];
				if (cmp.equals(A)) {
					idA = tmp[0];
//					System.out.println(idA);
					break;
				}
			}
		}

		for (int i = 0; i < link.length; i++) {
			if (link[i].contains(idA)) {
				String[] tmp = link[i].split("	");
				String cmp;
				cmp = tmp[idLinkA];

				if (cmp.equals(idA)) {
					idB = tmp[idLinkB];
//					System.out.println(idB);
					break;
				}
			}
		}
		for (int i = 0; i < bufferB.length; i++) {

			if (bufferB[i].contains(idB)) {
				String[] tmp = bufferB[i].split("	");
				if (tmp[0].equals(idB)) {
					B = tmp[2];

					break;
				}
			}
		}
		if (B.equals("")) {
			return ("Traduction non trouvee");
		} else {
			return B;
		}

	}

	public Comparator<SearchComponent> comparePertinence() {
		Comparator<SearchComponent> comparePertinence = new Comparator<SearchComponent>() {
			@Override
			public int compare(SearchComponent o1, SearchComponent o2) {
				return Integer.compare(o2.getOccurence(), o1.getOccurence());
			}
		};

		return comparePertinence;
	}
	
    /**
     * @param list1
     * @param list2
     * @return intersection entre plusieurs listes
     */
    public <T> List<T> intersection(List<T> list1, List<T> list2) {
        List<T> list = new ArrayList<T>();

        for (T t : list1) {
            if(list2.contains(t)) {
                list.add(t);
            }
        }

        return list;
    }

}
