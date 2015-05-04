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

	public void addToken(Token tok) {
		if (mapTokens.containsKey(tok.getStringToken())) {
			if (!mapTokens.get(tok.getStringToken()).getPositions()
					.contains(tok.getPositions().get(0))) {
				mapTokens.get(tok.getStringToken()).getPositions()
						.add(tok.getPositions().get(0));
			}
		} else {
			mapTokens.put(tok.getStringToken(), tok);
		}
	}
}
