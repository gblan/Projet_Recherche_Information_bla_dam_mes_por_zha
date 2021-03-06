package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;

import et4.beans.SuffixArray;
import et4.corpus.MonolingualCorpus;
import et4.ihm.mvc.Model;
import et4.index.Token;
import et4.index.Tokenization;
import et4.index.TokenizationChinese;
import et4.index.TokenizationFrench;

public class Test {
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
						//System.out.println(idA);
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
						//System.out.println(idB);
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

	public static void main(String[] args) {
		try {
			String[] tradCh = FileUtils.readFileToString(new File("resources/ch.txt"), "UTF-8").split("\n");
			String[] tradLink = FileUtils.readFileToString(new File("resources/linksChineseFrench.txt"), "UTF-8").split("\n");
			String[] tradFr = FileUtils.readFileToString(new File("resources/fr.txt"), "UTF-8").split("\n");
			//System.out.println(findTranslationChineseFrench("我们不会打网球。", 1,tradCh,tradFr,tradLink));
			//System.out.println(findTranslationChineseFrench("J'en perds mes mots.", 2,tradCh,tradFr,tradLink));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	//		
		/* 1 Ecrire en Chinois -- 2 Ecrire en Francais */
//		//System.out.println("Dans quel langue voulez vous ecrire? 1 => Chinois 2 => Francais");
//		Scanner sc = new Scanner(//System.in);
//		int choix =  sc.nextInt();
//		//System.out.println("Quel mot recherchez vous?");
//		  sc.nextLine();
//		String search =  sc.nextLine();
//		sc.close();
//		Tokenization tf = new TokenizationFrench();
//		Tokenization tc = new TokenizationChinese();
//		try {
//			MonolingualCorpus corpus;
//			if (choix == 1){
//				corpus =  new MonolingualCorpus(tc,
//						"resources/chCorpus.txt");
//			}
//			else {
//				corpus =  new MonolingualCorpus(tf,
//						"resources/frCorpus.txt");
//			}
//			 
//			String[] tradCh = FileUtils.readFileToString(new File("resources/ch.txt"), "UTF-8").split("\n");
//			String[] tradLink = FileUtils.readFileToString(new File("resources/linksChineseFrench.txt"), "UTF-8").split("\n");
//			String[] tradFr = FileUtils.readFileToString(new File("resources/fr.txt"), "UTF-8").split("\n");
//			
//			for (int i = 0; i < corpus.getCorpusArray().length; i++) {
//				if (corpus.getCorpusArray()[i].contains(search)) {
//					SuffixArray suffixArray = new SuffixArray(corpus, i);
//					suffixArray.initTabSuffix();
//					suffixArray.qsort(suffixArray.getTabSuffix(), 0, corpus
//							.getNbMots().get(i) - 1);
//					suffixArray.initLCPVector();
//
//					/*
//					 * for (int j = 0; j < suffixArray.getLCPVector().length ;
//					 * j++) { //System.out.println(suffixArray.getLCPVector()[j]);
//					 * }
//					 */
//					ArrayList<Integer> positions = suffixArray
//							.getAllPositionsOfPhrase(search);
//
//					corpus.getCorpusArray()[i] = corpus.getCorpusArray()[i]
//							.substring(0,corpus.getCorpusArray()[i].length() - 1);
//					
//					if (positions.size() != 0) {
//						//System.out.println("");
//						if (choix == 1){
//							String trad = findTranslationChineseFrench(
//									corpus.getCorpusArray()[i],1,tradCh,tradFr,tradLink);
//							//System.out.println(trad);
//							//System.out.println(Model.getPinyin(corpus.getCorpusArray()[i]));
//						}
//						else{
//							String trad = 
//							//System.out.println(trad);
//							//System.out.println(Model.getPinyin(trad));
//						}
//						//System.out.println(corpus.getCorpusArray()[i]);
//						//System.out.println("Ligne: " + i + " Positions: "
//								+ positions);
//						//System.out.println("Occurence: " + positions.size());
//					}
//				}
//			}
//
//			/*
//			 * tableau de suffixes initialisÃ© HashMap<String, Token> tmp =
//			 * corpus.getIndex().getListTokens();
//			 * 
//			 * //System.out.println("####    TOKENS    ####"); for (Entry<String,
//			 * Token> entry : tmp.entrySet()) {
//			 * //System.out.println(entry.getValue()); }
//			 * 
//			 * //System.out.println("####    TRI DU TABLEAU    ####");
//			 */
//
//			/*
//			 * //System.out.println("####    TABLEAU LCP    ####");
//			 * suffixArray.initLCPVector(); for (int j = 0; j <
//			 * suffixArray.getLCPVector().length; j++) {
//			 * //System.out.println(suffixArray.getLCPVector()[j]); }
//			 * //System.out.println("Les positions : ");
//			 * //System.out.println("-----> " +
//			 * suffixArray.getAllPositionsOfPhrase("to be"));
//			 */
//
//		} catch (IOException e) {
//			//System.err.println("File Not Found");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
}
