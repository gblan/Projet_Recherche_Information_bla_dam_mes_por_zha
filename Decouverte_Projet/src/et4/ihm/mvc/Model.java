package et4.ihm.mvc;

import et4.beans.SuffixArray;
import et4.corpus.MonolingualCorpus;
import et4.ihm.mvc.component.KnowledgeComponent;
import et4.ihm.mvc.component.SearchComponent;
import et4.ihm.mvc.panel.body.KnowledgePanel;
import et4.ihm.mvc.panel.body.SearchPanel;
import et4.index.Tokenization;
import et4.index.TokenizationChinese;
import et4.index.TokenizationChinese2;
import et4.index.TokenizationFrench;
import graphe.GrapheWord2Vec;
import graphe.word2vec.Word2VecObject;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Observable;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;

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
	private TokenizationChinese2 tokenizationchinese2sav;
	public Model() {
		ArrayList<String> tokenConnu = new ArrayList<String>();
			tokenConnu.add("动物怕火。");
			tokenConnu.add("我只是觉得这里用英语比较能表达我的想法。");
			tokenConnu.add("密码是\"Muiriel\"。");
			tokenConnu.add("Ubuntu包括的软件挺多。");
			tokenConnu.add("她很少迟到。");
		graphe = new GrapheWord2Vec();

		System.out.println("Tokenization en cours ...");
		tokenizationchinese2sav = new TokenizationChinese2();;
		
		try {
			tokenizationchinese2sav.load();
			System.out.println("Tokenization chargee");
		} catch (ClassNotFoundException | IOException e1) {
			// TODO Auto-generated catch block
			
			try {
				tokenizationchinese2sav.getTokensDeFichierEtoile("chCorpusUTFBis.txt");
				tokenizationchinese2sav.save();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		// launch();

				
		ClassPathResource resource = new ClassPathResource("chCorpusUTF.txt");
		System.out.println("class");
        

        File f;
        
		try {
			f = resource.getFile();
			tw2v = new Word2VecObject(f);
	    	tw2v.launch("她很少迟到。");
	    	double s = tw2v.similarity("她很少迟到。","她很少迟到。");
	    	System.out.println("s = "+s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println("model");
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

	/**
	 * @param searchinput
	 * @param langue
	 *            1 mandarin, 2 français
	 */
	public void search(String searchinput, int langue) {
		int j=0;
		listComponent.clear();
		
		if (searchinput.equals(""))
			return;
		
		Tokenization tf = new TokenizationFrench();
		Tokenization tc = new TokenizationChinese();


		try {
			String[] tradCh = FileUtils.readFileToString(new File("resources/ch.txt"), "UTF-8").split("\n");
			String[] tradLink = FileUtils.readFileToString(new File("resources/linksChineseFrench.txt"), "UTF-8").split("\n");
			String[] tradFr = FileUtils.readFileToString(new File("resources/fr.txt"), "UTF-8").split("\n");
			
			if (langue == 1){
				corpus =  new MonolingualCorpus(tc,
						"resources/chCorpus.txt");
			}
			else {
				corpus =  new MonolingualCorpus(tf,
						"resources/frCorpus.txt");
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
							trad = findTranslationChineseFrench(corpus.getCorpusArray()[i], 1, tradCh, tradFr,
									tradLink);
//							 System.out.println("Chinois ##"+corpus.getCorpusArray()[i]+"####");
//							 System.out.println(Model.getPinyin(currentCorpus.getCorpusArray()[i]));
							piying = Model.getPinyin(corpus.getCorpusArray()[i]);
						} else {
							trad = findTranslationChineseFrench(corpus.getCorpusArray()[i], 2, tradCh, tradFr,
									tradLink);
//							 System.out.println("Fr: ##"+corpus.getCorpusArray()[i]+"####");
							// System.out.println(trad);
							// System.out.println(Model.getPinyin(trad));
							piying = Model.getPinyin(trad);

						}
						// System.out.println(currentCorpus.getCorpusArray()[i]);
						// System.out.println("Ligne: " + i + " Positions: " +
						// positions);
						// System.out.println("Occurence: " + positions.size());

//						if (i == 0) {
//							listComponent.add(new SearchComponent(new Point(0, SearchPanel.heightComponent * (i + 1)),
//									currentCorpus.getCorpusArray()[i], trad, piying, "", positions.size(),
//									View.width - 20, SearchPanel.heightComponent));
//						} else {
							if(j==0){
								listComponent.add(new SearchComponent(new Point(0, SearchPanel.heightComponent
										+ SearchPanel.space * j), corpus.getCorpusArray()[i], trad, piying, "",
										positions.size(), View.width - 20, SearchPanel.heightComponent));
							}else{
								listComponent.add(new SearchComponent(new Point(0, SearchPanel.heightComponent * (j + 1)
										+ SearchPanel.space * j), corpus.getCorpusArray()[i], trad, piying, "",
										positions.size(), View.width - 20, SearchPanel.heightComponent));
							}

							
							j++;
//						}
						
//						System.out.println("corpus : " + currentCorpus.getCorpusArray()[i]);
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

		/* notifie les observers */
		setChanged();
		notifyObservers(listComponent);
	}

	public static String getPinyin(String phraseChinoise) {
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
		if(text.equals("")) {
			return learnWord2Vec(text);
		}
		else {
			// TODO Auto-generated method stub
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
		
		
		
		ArrayList<String> tokenretenu = new ArrayList<String>();
		int line = 0;
		//System.out.println("Phrase : "+corpus.getIndex().get(i).getListTokens());
		
		/*
		 * 
		 * Recuperation des token du fichier
		 * 
		 */
		List<String> sentences = null;
		try {
			sentences = tokenizationchinese2sav.getSentences();
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
			int connu = 0;
			
			tokenChinese.clear();
			
			String[] array = sentence.split(" ");
			
			
			
			tokenChinese.addAll(Arrays.asList(array));
			for(String token : tokenChinese) {
				
				System.out.println(graphe.contains(token));
				if(graphe.contains(token) && !token.replaceAll(" ", "").equals("")) {
					connu++;
					//System.out.println("Token connu ="+token);
				}
				else {
					//System.out.println("Token Inconnu ="+token);
					//System.out.println("Token inconnu ="+token);
					if(!token.replaceAll(" ", "").equals("")) {
						if(tokenretenu.size()<10) {
							tokenretenu.add(token);
						}
					}
				}
				
			}
			if (connu != 0) {
				System.out.println("************ Connu "+connu+" size"+tokenChinese.size());
				double pourcentage = (double) connu / (double) tokenChinese.size();
				
				/**
				 * Si on depace le seuil => la phrase est bonne pour l'affichage
				 */
				if (pourcentage > SEUIL) {
					System.out.println("Pourcentage "+pourcentage);
					System.out.println("Phrase : "+sentence);
					// System.out.println("Phrase : "+corpus.getCorpusArray()[i]);
					phraseretenu.add(sentence);
				}
			}
			line++;
		}
		

		String result = "";
		double SEUIL_W2V = (double)25.0/100.0;
		System.out.println("Avant"+tokenretenu.size());
		
		for (int i = 0; i < 3; i++) {
			result+=phraseretenu.get(i)+"\n";
		}
		//System.out.println("corpus.getTc().getTokensSet()"+tokenizationchinese2sav.getSentences());
		/*
		double similarity = 0;
		for(String sentence : phraseretenu) {
			similarity = 0;
			
			int i = 0;
			int cpt = 0;
			
			for(String tok : tokenizationchinese2sav.getSentences()) {
				
				String array[] = tok.split(" ");
				ArrayList<String> tmp = new ArrayList<String>();
				tmp.addAll(Arrays.asList(array));
				//System.out.println("udhsqdgdsqggsdqghdsqf ------ "+tokenretenu.size()+" i = "+i+" ppppppp "+tmp.size());
				for (int j = 0; j < tokenretenu.size(); j++) {
					for(String token : tmp) {
						//System.out.println("token"+token+" tokenretenu"+tokenretenu.size());
						if(!token.replaceAll(" ", "").equals("")) {
							System.out.println("Similarity ["+token+" - "+tokenretenu.get(j)+"]"+tw2v.similarity(token, tokenretenu.get(j)));
							similarity+=tw2v.similarity(token, tokenretenu.get(j));
							cpt++;
						}
						
					}
				}
			}
			
			
			
			System.out.println("__________RESULT = "+(double)similarity/(double)cpt+"__________");
			if((double)similarity/(double)cpt>SEUIL_W2V) {
				result+=sentence+"\n";
			}
		}*/
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
		
		for(String tok : sentences) {
			String array[] = tok.split(" ");
			tokennew.addAll(Arrays.asList(array));
		}
		
		
		
		//System.out.println("Nouveaux mots connus :D "+tokennew);
		
		//System.out.println("Dico avant"+graphe);
		System.out.println("_________\n");
		for(String s : tokennew) {
			System.out.println(s);
		}
		
		
		graphe.addDico(tokennew);
		
		//System.out.println("Dico après"+graphe);
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
			for(int i = 0 ; i < bufferA.length ; i++){
				if (bufferA[i].contains(A)) {
					String[] tmp = bufferA[i].split("	");
					String cmp = tmp[2];
					if (cmp.equals(A)) {
						idA = tmp[0];
						System.out.println(idA);
						break;
					}
				}
			}

		for(int i = 0 ; i < link.length ; i++){
				if (link[i].contains(idA)) {
					String[] tmp = link[i].split("	");
					String cmp; 
						cmp = tmp[idLinkA];
					
					if (cmp.equals(idA)) {
						idB = tmp[idLinkB];
						System.out.println(idB);
						break;
					}
				}
			}
		for(int i = 0 ; i < bufferB.length ; i++){

				if (bufferB[i].contains(idB)) {
					String[] tmp = bufferB[i].split("	");
					if (tmp[0].equals(idB)) {
						B = tmp[2];
						
						break;
					}
				}
			}
			if (B.equals("")) {
				return("Traduction non trouvee");
			}
			else{
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

}
