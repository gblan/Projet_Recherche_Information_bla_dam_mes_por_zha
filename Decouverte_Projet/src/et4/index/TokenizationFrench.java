package et4.index;

import java.io.FileReader;
import java.io.IOException;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;

public class TokenizationFrench extends Tokenization {

	public TokenizationFrench() {
	}

	@Override
	public CorpusIndex Tokenize(String fileName) throws IOException {
		CorpusIndex index = new CorpusIndex();
		PTBTokenizer<CoreLabel> ptbt = new PTBTokenizer<CoreLabel>(new FileReader(fileName),
				new CoreLabelTokenFactory(), "");
		
		for (CoreLabel label; ptbt.hasNext();) {
			label = (CoreLabel) ptbt.next();
			System.out.println("label "+label+" label position "+label.beginPosition()+ " label.value = "+label.value());
			index.addToken(new Token(fileName, label.beginPosition(), label.value()));
		}
		
		
		
		return index;
	}

}
