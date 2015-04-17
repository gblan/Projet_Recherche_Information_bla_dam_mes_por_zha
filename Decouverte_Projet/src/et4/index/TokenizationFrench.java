package et4.index;

import java.io.FileNotFoundException;
import java.io.FileReader;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;

public class TokenizationFrench extends Tokenization {

	public TokenizationFrench() {
	}

	@Override
	public CorpusIndex Tokenize(String fileName) throws FileNotFoundException {
		CorpusIndex index = new CorpusIndex();
		PTBTokenizer<CoreLabel> ptbt = new PTBTokenizer<CoreLabel>(new FileReader(fileName),
				new CoreLabelTokenFactory(), "");
		for (CoreLabel label; ptbt.hasNext();) {
			label = (CoreLabel) ptbt.next();
			index.addToken(new Token(fileName, label.beginPosition(), label.value()));
		}
		return index;
	}

}
