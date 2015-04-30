package et4.index;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

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
		
		String text = FileUtils.readFileToString(new File(fileName),"UTF-8");
		
		System.out.println("Text "+text);
		
		for (CoreLabel label; ptbt.hasNext();) {
			label = (CoreLabel) ptbt.next();
			index.addToken(new Token(fileName, label.beginPosition(), label.value()));
		}
		return index;
	}

}
