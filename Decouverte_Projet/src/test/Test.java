package test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import et4.beans.SuffixArray;
import et4.corpus.MonolingualCorpus;
import et4.index.Token;
import et4.index.Tokenization;
import et4.index.TokenizationFrench;

public class Test {

	public static void main(String[] args) {
		Tokenization tf = new TokenizationFrench();
		try {

			MonolingualCorpus corpus = new MonolingualCorpus(tf, "resources/test.txt");
			SuffixArray suffixArray = new SuffixArray(corpus);
			suffixArray.initTabSuffix();

			/* tableau de suffixes initialisé */
			HashMap<String, Token> tmp = corpus.getIndex().getListTokens();

			System.out.println("####    TOKENS    ####");
			for (Entry<String, Token> entry : tmp.entrySet()) {
				System.out.println(entry.getValue());
			}

			System.out.println("####    TRI DU TABLEAU    ####");
			suffixArray.qsort(suffixArray.getTabSuffix(), 0, corpus.getNbMots() - 1);

			System.out.println("####    TABLEAU LCP    ####");
			int[] lcp = suffixArray.getLCPVector();
			for (int i = 0; i < lcp.length; i++) {
				System.out.println(lcp[i]);
			}
			
			System.out.println("Les positions : ");
			System.out.println("------> "+suffixArray.getAllPositionsOfPhrase("to be"));
			
		} catch (IOException e) {
			System.err.println("File Not Found");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
