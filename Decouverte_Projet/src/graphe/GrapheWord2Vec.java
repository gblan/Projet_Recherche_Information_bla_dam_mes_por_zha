package graphe;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class GrapheWord2Vec {

	
	private HashMap<String,Double> dico;
	/*public GrapheWord2Vec(ArrayList<String> tokenConnu) {
		dico = new HashMap<String, Double>();
		for(int i = 0; i<tokenConnu.size();i++){
			add(tokenConnu.get(i));
		}
	}*/
	
	public GrapheWord2Vec() {
		try {
			load();
			System.out.println("L'ouverture de votre dictionnaire a reussi");
		} catch (ClassNotFoundException | IOException e) {
			System.out.println("Creation d'un nouveau dictionnaire");
			dico = new HashMap<String, Double>();
			
		}
	}
	
	public void add(String token) {
		if(!dico.containsKey(token)) {
			dico.put(token, (double) (1.0));
		}
	}
	
	public boolean contains(String token) {
		return dico.containsKey(token);
	}
	
	@Override
	public String toString() {
		String display = "";
		for(Entry<String, Double> entry : dico.entrySet()) {
			display+=" "+entry.getKey()+" apprentissage = "+entry.getValue()+"\n";
		}
		return display;
	}
	

	/*public static void main(String[] args) {
		
		ArrayList<String> tokenConnu = new ArrayList<String>();
			tokenConnu.add("Test");
			tokenConnu.add("Test");
			tokenConnu.add("Bis");
		GrapheWord2Vec graphe = new GrapheWord2Vec(tokenConnu);
		System.out.print("Graphe "+"\n"+graphe);
	}*/

	public void addDico(ArrayList<String> tokennew) {
		
		for(String token : tokennew) {
			add(token);
		}
		try {
			save();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void save() throws IOException {
		FileOutputStream fos = new FileOutputStream("dico.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(dico);
        oos.close();
        fos.close();
        System.out.printf("Serialized HashMap data is saved in dico.ser");
	}
	
	public void load() throws IOException, ClassNotFoundException {
		FileInputStream fis = new FileInputStream("dico.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        dico = (HashMap<String,Double>) ois.readObject();
        ois.close();
        fis.close();
        
        System.out.println("------DESERIALIZE------");
        
        System.out.println(this);
	}

	public HashMap<String,Double> getDico() {
		return dico;
	}
	
}
