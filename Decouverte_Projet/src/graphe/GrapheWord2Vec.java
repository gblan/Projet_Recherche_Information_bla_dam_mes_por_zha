package graphe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import et4.index.Token;

public class GrapheWord2Vec {

	private HashMap<PaireToken,Double> noeud;
	private ArrayList<Token> dico;
	public GrapheWord2Vec(ArrayList<Token> tokenConnu) {
		noeud = new HashMap<PaireToken, Double>();
		dico = new ArrayList<Token>();
		for(int i = 0; i<tokenConnu.size();i++){
			dico.add(tokenConnu.get(i));
			for(int j = i+1; j<tokenConnu.size(); j++ ) {
				add(new PaireToken(tokenConnu.get(i), tokenConnu.get(j)));
			}
		}
		
	}
	
	public void add(PaireToken paire) {
		if(noeud.containsKey(paire)) {
		}
		else {
			noeud.put(paire, (double) (1.0));
		}
	}
	
	public boolean contains(PaireToken paire) {
		return noeud.containsKey(paire);
	}
	
	@Override
	public String toString() {
		String display = "";
		for(Entry<PaireToken, Double> entry : noeud.entrySet()) {
			display+=" "+entry.getKey()+" similarite = "+entry.getValue()+"\n";
		}
		return display;
	}
	
	
	
	public HashMap<PaireToken, Double> getNoeud() {
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

	public boolean contains(String token) {
		// TODO Auto-generated method stub
		return dico.contains(new Token("", 0, token));
	}
	
	
	
}
