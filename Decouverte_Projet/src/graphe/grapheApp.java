package graphe;

import it.unimi.dsi.fastutil.floats.FloatBigArrays;

import java.util.ArrayList;
import java.util.HashMap;


public class grapheApp {
	
	private HashMap<String,Float> noeud;
	private FloatBigArrays arc;
	
	public grapheApp(ArrayList<String> tokenConnu){
		for(int i = 0; i<tokenConnu.size();i++){
			noeud.put(tokenConnu.get(i), (float) 1.0);
		}
	}
}
