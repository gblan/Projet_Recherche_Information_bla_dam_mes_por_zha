package et4.corpus;

import java.io.File;
import java.io.IOException;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;

import et4.index.CorpusIndex;
import et4.index.Token;
import et4.index.Tokenization;

public class MonolingualCorpus {
	private static CorpusIndex index;
	private int nbMots = 0;
	private String corpus;

	public MonolingualCorpus(Tokenization tok, String filename) throws IOException {
		index = tok.Tokenize(filename);
		corpus = FileUtils.readFileToString(new File(filename), "UTF-8");
		for (Entry<String, Token> entry : index.getListTokens().entrySet()) {
			nbMots += entry.getValue().getPositions().size();
		}
	}

	public String getTokenAtPosition(int position) {
		return index.getListTokens().get(position).getStringToken();
	}

	public String getSuffixFromPosition(int position) {
		return corpus.substring(position);
	}

	public int compareSuffixes(int pos1, int pos2) {
		return corpus.substring(pos1).compareTo(corpus.substring(pos2));
	}

	public int getIndexSize() {
		return index.getListTokens().size();
	}

	public CorpusIndex getIndex() {
		return index;
	}

	public int getNbMots() {
		return nbMots;
	}

	public String getCorpus() {
		return corpus;
	}
}
