package test;

import java.io.FileNotFoundException;

import et4.index.CorpusIndex;
import et4.index.Token;
import et4.index.Tokenization;
import et4.index.TokenizationFrench;

public class Test {

	public static void main(String[] args) {
		Tokenization tf = new TokenizationFrench();
		try {
			CorpusIndex index = tf.Tokenize("resources/europarl-intersect11.train.fr");
			int i = 0;
			for (Token tok : index.getListTokens()) {
				if (i == 10) {
					break;
				}

				System.out.println(tok);
				i++;
			}
		} catch (FileNotFoundException e) {
			System.err.println("File Not Found");
		}
	}

}
