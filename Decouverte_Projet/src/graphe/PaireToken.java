package graphe;

import et4.index.Token;

public class PaireToken extends Paire<Token, Token>{

	Token first,second;
	
	public PaireToken(Token first, Token second) {
		super(first, second);
	}
	
	public PaireToken invert() {
		return new PaireToken(getSecond(), getFirst());
	}
	
	public boolean hasSameToken() {
		return first.equals(second);
	}
}
