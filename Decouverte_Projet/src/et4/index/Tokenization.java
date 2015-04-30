package et4.index;

import java.io.FileNotFoundException;


public abstract class Tokenization {
	/**
	 * Method which create corpusIndex
	 * @param path
	 * @throws FileNotFoundException 
	 */
	public abstract CorpusIndex Tokenize(String fileName) throws FileNotFoundException;
}
