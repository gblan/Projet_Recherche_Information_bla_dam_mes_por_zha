package et4.index;

import java.util.HashMap;

public class CorpusIndex {
	private HashMap<String, Token> mapTokens;
	
	public CorpusIndex() {
		mapTokens = new HashMap<String, Token>();
	}
	
	public HashMap<String, Token> getListTokens() {
		return mapTokens;
	}

	public void addToken(Token tok){
		if(mapTokens.containsKey(tok.getStringToken())){
			mapTokens.put(tok.getStringToken(),tok);
		}else{
			mapTokens.get(tok.getStringToken()).getPositions().add(tok.getPositions().get(0));
		}
	}
}
