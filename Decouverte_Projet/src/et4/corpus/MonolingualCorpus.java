package et4.corpus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;

import et4.index.CorpusIndex;
import et4.index.Token;
import et4.index.Tokenization;
import et4.index.TokenizationChinese2;

public class MonolingualCorpus {
	private static ArrayList<CorpusIndex> index;
	private ArrayList<Integer> nbMots = new ArrayList<Integer>();
	private String corpus;
	private String[] corpusArray;
	private TokenizationChinese2 tc;

	/*
	 * 1 = Francais 2 = Chinois
	 */
	public MonolingualCorpus(Tokenization tok, String filename)
			throws IOException {
		index = tok.Tokenize(filename);
		corpus = FileUtils.readFileToString(new File(filename), "UTF-8");
		corpusArray = corpus.split("\n");
		for (int i = 0; i < corpusArray.length; i++) {
			int nbMot=0;
			for (Entry<String, Token> entry : index.get(i).getListTokens()
					.entrySet()) {
				nbMot += entry.getValue().getPositions().size();
			}
			nbMots.add(nbMot);
		}
		
		
		tc = new TokenizationChinese2();
		tc.TokenizeDeFichier("chCorpusUTF.txt");
		System.out.println("End");
		
	}

	public String[] getCorpusArray() {
		return corpusArray;
	}

	public String getTokenAtPosition(int position, int ligne) {
		return index.get(ligne).getListTokens().get(position).getStringToken();
	}

	public String getSuffixFromPosition(int position, int ligne) {
		return corpusArray[ligne].substring(position);
	}

	public int compareSuffixes(int pos1, int pos2,int line) {
		String a = corpusArray[line].substring(pos1).toLowerCase();
		String b = corpusArray[line].substring(pos2).toLowerCase();
		return a.compareTo(b);
	}

	public int getIndexSize(int line) {
		return index.get(line).getListTokens().size();
	}

	public ArrayList<CorpusIndex> getIndex() {
		return index;
	}

	public ArrayList<Integer> getNbMots() {
		return nbMots;
	}

	public String getCorpus() {
		return corpus;
	}

	public TokenizationChinese2 getTc() {
		return tc;
	}
	
	
}
