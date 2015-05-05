package et4.ihm.mvc;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import et4.beans.SuffixArray;
import et4.corpus.MonolingualCorpus;
import et4.ihm.mvc.component.KnowledgeComponent;
import et4.ihm.mvc.component.SearchComponent;
import et4.ihm.mvc.panel.body.KnowledgePanel;
import et4.ihm.mvc.panel.body.SearchPanel;
import et4.index.CorpusIndex;
import et4.index.Token;
import et4.index.Tokenization;
import et4.index.TokenizationChinese;
import et4.index.TokenizationChinese2;
import et4.index.TokenizationFrench;
import graphe.GrapheWord2Vec;
import graphe.PaireToken;
import graphe.word2vec.Word2VecObject;

/**
 * Noyau de l'interface, tout les appels aux algos doivent se faire ici meme
 * et seulement ici
 * @author All
 *
 */
public class Model extends Observable{
	private Word2VecObject tw2v;
	private MonolingualCorpus corpus;
	private GrapheWord2Vec graphe;
	public Model() {
		ArrayList<String> tokenConnu = new ArrayList<String>();
			tokenConnu.add("动物怕火。");
			tokenConnu.add("我只是觉得这里用英语比较能表达我的想法。");
			tokenConnu.add("密码是\"Muiriel\"。");
			tokenConnu.add("Ubuntu包括的软件挺多。");
			tokenConnu.add("她很少迟到。");
			
		graphe = new GrapheWord2Vec(tokenConnu);
	
	
		launch();
		ClassPathResource resource = new ClassPathResource("chCorpusUTF.txt");
        System.out.println("ClassPathRessource");
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

    	
	}
	
	public void launch() {
		/* 1 Ecrire en Chinois -- 2 Ecrire en Francais */
		System.out.println("Dans quel langue voulez vous ecrire? 1 => Chinois 2 => Francais");
		Scanner sc = new Scanner(System.in);
		int choix =  sc.nextInt();
		System.out.println("Quel mot recherchez vous?");
		  sc.nextLine();
		String search =  sc.nextLine();
		sc.close();
		Tokenization tf = new TokenizationFrench();
		Tokenization tc = new TokenizationChinese();
		try {
			
			if (choix == 1){
				corpus =  new MonolingualCorpus(tc,
						"resources/chCorpus.txt");
			}
			else {
				corpus =  new MonolingualCorpus(tf,
						"resources/frCorpus.txt");
			}
			 
			String[] tradCh = FileUtils.readFileToString(new File("resources/ch.txt"), "UTF-8").split("\n");
			String[] tradLink = FileUtils.readFileToString(new File("resources/linksChineseFrench.txt"), "UTF-8").split("\n");
			String[] tradFr = FileUtils.readFileToString(new File("resources/fr.txt"), "UTF-8").split("\n");
			
			for (int i = 0; i < corpus.getCorpusArray().length; i++) {
				if (corpus.getCorpusArray()[i].contains(search)) {
					SuffixArray suffixArray = new SuffixArray(corpus, i);
					suffixArray.initTabSuffix();
					suffixArray.qsort(suffixArray.getTabSuffix(), 0, corpus
							.getNbMots().get(i) - 1);
					suffixArray.initLCPVector();

					/*
					 * for (int j = 0; j < suffixArray.getLCPVector().length ;
					 * j++) { System.out.println(suffixArray.getLCPVector()[j]);
					 * }
					 */
					ArrayList<Integer> positions = suffixArray
							.getAllPositionsOfPhrase(search);

					corpus.getCorpusArray()[i] = corpus.getCorpusArray()[i]
							.substring(0,corpus.getCorpusArray()[i].length() - 1);
					
					if (positions.size() != 0) {
						System.out.println("");
						if (choix == 1){
							String trad = findTranslationChineseFrench(
									corpus.getCorpusArray()[i],1,tradCh,tradFr,tradLink);
							System.out.println(trad);
							System.out.println(Model.getPinyin(corpus.getCorpusArray()[i]));
						}
						else{
							String trad = findTranslationChineseFrench(
									corpus.getCorpusArray()[i], 2,tradCh,tradFr,tradLink);
							System.out.println(trad);
							System.out.println(Model.getPinyin(trad));
						}
						System.out.println(corpus.getCorpusArray()[i]);
						System.out.println("Ligne: " + i + " Positions: "
								+ positions);
						System.out.println("Occurence: " + positions.size());
					}
				}
			}

			/*
			 * tableau de suffixes initialisÃ© HashMap<String, Token> tmp =
			 * corpus.getIndex().getListTokens();
			 * 
			 * System.out.println("####    TOKENS    ####"); for (Entry<String,
			 * Token> entry : tmp.entrySet()) {
			 * System.out.println(entry.getValue()); }
			 * 
			 * System.out.println("####    TRI DU TABLEAU    ####");
			 */

			/*
			 * System.out.println("####    TABLEAU LCP    ####");
			 * suffixArray.initLCPVector(); for (int j = 0; j <
			 * suffixArray.getLCPVector().length; j++) {
			 * System.out.println(suffixArray.getLCPVector()[j]); }
			 * System.out.println("Les positions : ");
			 * System.out.println("-----> " +
			 * suffixArray.getAllPositionsOfPhrase("to be"));
			 */

		} catch (IOException e) {
			System.err.println("File Not Found");
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		if(text.equals("")) {
			return learnWord2Vec(text);
		}
		else {
			// TODO Auto-generated method stub
			return getPinyin(text);
		}
	}

	private String learnWord2Vec(String text) {
		
		System.out.println("-------------------------||||||| "+tw2v.similarity("报道称", "克里姆林宫透露了上述信息"));
		
		/**
		 * Lire le corpus => trouve phrase avec des phrases bcp de mots connu
		 */
		double SEUIL = (double)70/100.0;
		HashMap<Integer,ArrayList<String>> phraseretenu = new HashMap<Integer, ArrayList<String>>();
		
		
		int connu = 0;
		ArrayList<String> tokenretenu = new ArrayList<String>();
		int line = 0;
		//System.out.println("Phrase : "+corpus.getIndex().get(i).getListTokens());
		
		/*
		 * 
		 * Recuperation des token du fichier
		 * 
		 */
		ArrayList<String> tokenChinese = new ArrayList<String>();
		tokenChinese.addAll(corpus.getTc().getTokensSet());
		for(String token : tokenChinese) {
			
			if(graphe.contains(token)) {
				connu++;
				//System.out.println("Token connu ="+token);
			}
			else {
				tokenretenu.add(token);
			}
		}
		 
		 if(connu!=0) {
			 double pourcentage = (double)connu/(double)tokenChinese.size();
			 
			 if(pourcentage>SEUIL)
			 {
				 //System.out.println("Pourcentage "+pourcentage);
				 //System.out.println("Phrase : "+corpus.getCorpusArray()[i]);
				phraseretenu.put(line,tokenretenu); 
			 }
		 }
			 
		
		
		/**
		 * Faire Word2Vec
		 */
		ArrayList<Integer> keyCorpus = new ArrayList<Integer>();
		keyCorpus.addAll(phraseretenu.keySet());
		
		String result = "";
		double SEUIL_W2V = (double)25.0/100.0;
		for (int i = 0; i < phraseretenu.size(); i++) {
			
			System.out.println("Phrase : "+corpus.getCorpusArray()[keyCorpus.get(i)]);
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
		
		return result;
	}

	/**
	 * Prend en parametre le fichier que l'utilisateur a choisi dans le FileChooser
	 * @param fichier
	 * @throws FileNotFoundException 
	 */
	public void updateKnowledge(File file) throws FileNotFoundException {
		
		System.out.println("Model : updateKnowledge in "+file.getAbsolutePath());
		TokenizationChinese2 tokenizefile = new TokenizationChinese2();
		tokenizefile.TokenizeDeFichier(file.getAbsolutePath());
		ArrayList<String> tokennew = new ArrayList<String>();
		tokennew.addAll(tokenizefile.getTokensSet());
		
		System.out.println("Nouveaux mots connus :D "+tokennew);
		
		System.out.println("Dico avant"+graphe);
		
		graphe.addDico(tokennew);
		
		System.out.println("Dico après"+graphe);
	}
	
	public static String findTranslationChineseFrench(String A, int choice,String[] dicoCh,String[] dicoFr, String[] link) {
		/*
		 * choice = 1 => Chinese to French choice = 2 => French to Chinese
		 */
		String line;
		String idA = "";
		String idB = "";
		String B = "";
		int idLinkA;
		int idLinkB;
		String[] bufferA;
		String[] bufferB;
//		try (InputStream ch = new FileInputStream("resources/ch.txt");
//				InputStreamReader chR = new InputStreamReader(ch,
//						Charset.forName("UTF-8"));
//				BufferedReader chB = new BufferedReader(chR);
//
//				InputStream link = new FileInputStream("resources/linksChineseFrench.txt");
//				InputStreamReader linkR = new InputStreamReader(link,
//						Charset.forName("UTF-8"));
//				BufferedReader linkB = new BufferedReader(linkR);
//
//				InputStream fr = new FileInputStream("resources/fr.txt");
//				InputStreamReader frR = new InputStreamReader(fr,
//						Charset.forName("UTF-8"));
//				BufferedReader frB = new BufferedReader(frR);
		/*
		 * FileOutputStream fos1 = new FileOutputStream("ch1.txt");
		 * BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter( fos1,
		 * "UTF8"));
		 */

//		) {
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
					String cmp = tmp[2].substring(0,tmp[2].length()-1);
					if (cmp.equals(A)) {
						idA = tmp[0];
						break;
					}
				}
			}

		for(int i = 0 ; i < link.length ; i++){
				if (link[i].contains(idA)) {
					String[] tmp = link[i].split("	");
					String cmp; 
					if (choice == 1){
						cmp = tmp[idLinkA];
					}
					else {cmp = tmp[idLinkA].substring(0,tmp[idLinkA].length()-1);}
					
					if (cmp.equals(idA)) {
						if (choice == 1){
							idB = tmp[idLinkB].substring(0,tmp[idLinkB].length()-1);;
						}
						else { idB = tmp[idLinkB];}
						break;
					}
				}
			}
		for(int i = 0 ; i < bufferB.length ; i++){

				if (bufferB[i].contains(idB)) {
					String[] tmp = bufferB[i].split("	");
					if (tmp[0].equals(idB)) {
						B = tmp[2].substring(0,tmp[2].length()-1);
	
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

}
