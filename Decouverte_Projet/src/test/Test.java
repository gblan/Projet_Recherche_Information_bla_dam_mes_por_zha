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

			for (Entry<String, Token> entry : tmp.entrySet()) {
				System.out.println(entry.getValue());
			}

			/* tri du tableau */
			// suffixArray.qsort(suffixArray.getTabSuffix(), 0,
			// corpus.getIndex().getListTokens().size());
			System.out.println("Sort OK");

		} catch (IOException e) {
			System.err.println("File Not Found");
		}
	}
}