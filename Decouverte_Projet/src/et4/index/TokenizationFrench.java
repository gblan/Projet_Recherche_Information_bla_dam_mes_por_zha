package et4.index;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;

import java.util.Map.Entry;
public class TokenizationFrench extends Tokenization {

	public TokenizationFrench() {
	}

	@Override
	public ArrayList<CorpusIndex> Tokenize(String fileName) throws IOException {
		ArrayList<CorpusIndex> index = new ArrayList<CorpusIndex>();
		String line;
		InputStream fr = new FileInputStream(fileName);
		InputStreamReader frR = new InputStreamReader(fr,
				Charset.forName("UTF-8"));
		BufferedReader frB = new BufferedReader(frR);
		try{
			while ((line = frB.readLine()) != null) {
				CorpusIndex cp = new CorpusIndex();
				String[] tmp = line.replaceAll("[(){},.;'!?<>%]", "").split("\\s+");
				for(String str : tmp){
					for (int i = -1; (i = line.indexOf(str, i + 1)) != -1; ) {
						cp.addToken(new Token(fileName, i, str));
					}
				}
				index.add(cp);
				
			}
			frB.close();
		}catch(Exception e){
			System.out.println(e);
		}
		
		
		/*CorpusIndex index = new CorpusIndex();
		PTBTokenizer<CoreLabel> ptbt = new PTBTokenizer<CoreLabel>(new FileReader(fileName),
				new CoreLabelTokenFactory(), "");
		
		for (CoreLabel label; ptbt.hasNext();) {
			label = (CoreLabel) ptbt.next();
			//System.out.println("label "+label+" label position "+label.beginPosition()+ " label.value = "+label.value());
			index.addToken(new Token(fileName, label.beginPosition(), label.value()));
		}*/
		
		
		
		return index;
	}

}
