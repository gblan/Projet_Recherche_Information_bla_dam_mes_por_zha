package et4.index;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class TokenizationChinese extends Tokenization {
	public TokenizationChinese(){
		
	}
	@Override
	public ArrayList<CorpusIndex> Tokenize(String fileName) throws FileNotFoundException {
		ArrayList<CorpusIndex> index = new ArrayList<CorpusIndex>();
		String line;
		InputStream ch = new FileInputStream(fileName);
		InputStreamReader chR = new InputStreamReader(ch,
				Charset.forName("UTF-8"));
		BufferedReader chB = new BufferedReader(chR);
		try{
			while ((line = chB.readLine()) != null) {
				CorpusIndex cp = new CorpusIndex();
				String[] tmp = line.split("");
				for(String str : tmp){
					for (int i = -1; (i = line.indexOf(str, i + 1)) != -1; ) {
						cp.addToken(new Token(fileName, i, str));
					}
				}
				index.add(cp);
			}
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
		}
		
		
		//System.out.println("Exit du system dans TokenizationFrench.java");
		//System.exit(0);
		*/
		return index;
	}

}
