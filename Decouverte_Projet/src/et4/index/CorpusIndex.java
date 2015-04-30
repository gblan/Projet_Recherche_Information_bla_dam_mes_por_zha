package et4.index;

import java.util.ArrayList;

public class CorpusIndex {
	private ArrayList<Token> listTokens;
	
	public CorpusIndex() {
		listTokens = new ArrayList<Token>();
	}
	
	public ArrayList<Token> getListTokens() {
		return listTokens;
	}

	public void addToken(Token tok){
		listTokens.add(tok);
	}
}
