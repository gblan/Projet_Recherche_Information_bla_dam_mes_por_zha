package test;

import java.io.FileNotFoundException;

import et4.beans.SuffixArray;
import et4.corpus.MonolingualCorpus;
import et4.index.CorpusIndex;
import et4.index.Token;
import et4.index.Tokenization;
import et4.index.TokenizationFrench;

public class Test {



	public static void main(String[] args) {
		Tokenization tf = new TokenizationFrench();
		try {

			MonolingualCorpus corpus = new MonolingualCorpus(tf, "resources/europarl-intersect11.train.fr");
			SuffixArray suffixArray = new SuffixArray(corpus);
			suffixArray.initTabSuffix();
			
			/* tableau de suffixes initialisé */
			for (int i = 0; i < 100; i++) {
				System.out.println(suffixArray.getTabSuffix()[i]);
			}

			
			/* tri du tableau */
		
			
		} catch (FileNotFoundException e) {
			System.err.println("File Not Found");
		}
	}


}
