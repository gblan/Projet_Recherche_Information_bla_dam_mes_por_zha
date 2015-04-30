package test;

import java.io.FileNotFoundException;

import et4.beans.SuffixArray;
import et4.corpus.MonolingualCorpus;
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
//			for (int i = 0; i < 100; i++) {
//				System.out.println(suffixArray.getTabSuffix()[i]);
//			}
			
			/* tri du tableau */
			suffixArray.qsort(suffixArray.getTabSuffix(), 0, corpus.getIndex().getListTokens().size());
			System.out.println("Sort OK");
			
		} catch (FileNotFoundException e) {
			System.err.println("File Not Found");
		}
	}

}