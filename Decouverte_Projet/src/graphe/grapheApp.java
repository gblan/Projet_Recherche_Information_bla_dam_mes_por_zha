package graphe;

import java.util.ArrayList;
import java.util.HashMap;

import et4.index.Token;


public class grapheApp {
	
	private HashMap<Token,Float> noeud;
	//private FloatBigArrays arc;
	
	public grapheApp(ArrayList<Token> tokenConnu){
		for(int i = 0; i<tokenConnu.size();i++){
			noeud.put(tokenConnu.get(i), (float) 1.0);
		}
	}
}
