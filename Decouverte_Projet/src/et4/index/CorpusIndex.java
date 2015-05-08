package et4.index;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;


public class CorpusIndex implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	
	@Override
	public String toString() {
		String resultat = "";
		Set<String> keySet = mapTokens.keySet();
		Iterator<String> it = keySet.iterator();
		while (it.hasNext()) {
			String key = it.next();
			Token value = mapTokens.get(key);
			resultat = resultat + key + " <-String----Token-> "
			+ value + "\n";
			// //System.out.println(key.toString() + " <-String----Token-> " +
			// value);
		}
		return resultat;
	}
}
