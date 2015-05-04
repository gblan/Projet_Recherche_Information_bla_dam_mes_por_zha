package graphe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import et4.index.Token;

public class GrapheWord2Vec {

	private HashMap<PaireToken,Float> noeud;
	
	public GrapheWord2Vec(ArrayList<Token> tokenConnu) {
		noeud = new HashMap<PaireToken, Float>();
		for(int i = 0; i<tokenConnu.size();i++){
			for(int j = i+1; j<tokenConnu.size(); j++ ) {
				add(new PaireToken(tokenConnu.get(i), tokenConnu.get(j)));
			}
		}
		
	}
	
	public void add(PaireToken paire) {
		if(noeud.containsKey(new PaireToken(paire.getFirst(), paire.getSecond()))) {
		}
		else {
			noeud.put(paire, (float) (1.0));
		}
	}
	
	@Override
	public String toString() {
		String display = "";
		for(Entry<PaireToken,Float> entry : noeud.entrySet()) {
			display+=" "+entry.getKey()+" similarite = "+entry.getValue()+"\n";
		}
		return display;
	}
	
	
	
	public HashMap<PaireToken, Float> getNoeud() {
		return noeud;
	}

	public static void main(String[] args) {
		
		ArrayList<Token> tokenConnu = new ArrayList<Token>();
			tokenConnu.add(new Token("Doc", 1, "Test"));
			tokenConnu.add(new Token("Doc", 18, "Test"));
			tokenConnu.add(new Token("Doc", 18, "Bis"));
		GrapheWord2Vec graphe = new GrapheWord2Vec(tokenConnu);
		System.out.print("Graphe "+"\n"+graphe);
		System.out.println(graphe.getNoeud().containsKey(new PaireToken(new Token("Doc", 1, "Test"), new Token("Doc", 1, "Test"))));
	}
	
}
