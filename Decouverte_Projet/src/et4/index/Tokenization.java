package et4.index;

import java.io.IOException;
import java.util.ArrayList;


public abstract class Tokenization {
	/**
	 * Method which create corpusIndex
	 * @param path
	 * @throws FileNotFoundException 
	 */
	public abstract ArrayList<CorpusIndex> Tokenize(String fileName) throws IOException;
}
