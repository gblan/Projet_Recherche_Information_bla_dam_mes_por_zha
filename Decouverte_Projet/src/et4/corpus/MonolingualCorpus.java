package et4.corpus;

import java.io.FileNotFoundException;

import et4.index.CorpusIndex;
import et4.index.Tokenization;

public class MonolingualCorpus {
	private static CorpusIndex index;

	public MonolingualCorpus(Tokenization tok, String filename) throws FileNotFoundException {
		index = tok.Tokenize(filename);
	}

	public String getTokenAtPosition(int position) {
		return index.getListTokens().get(position).getStringToken();
	}

	public String getSuffixFromPosition(int position) {
		return null;
	}

	public int compareSuffixes(int pos1, int pos2) {
		return index.getListTokens().get(pos1).getStringToken()
				.compareTo(index.getListTokens().get(pos2).getStringToken());
	}

	public int getIndexSize() {
		return index.getListTokens().size();
	}
	
	public CorpusIndex getIndex(){
		return index;
	}
}
