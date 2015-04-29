package graphe;


/**
 * Classe representant un TypeIndex de document
 * Cette classe est abstraite
 * @author aminmessaoudi
 *
 */
public abstract class TypeIndex implements Comparable<TypeIndex>{
	
	//HashMap<Document,Integer = occurence>
	//private HashMap<Document,Integer> docs = new HashMap<Document,Integer>();
	private String mot; // ..... ???
	
	/**
	 * Constructeur par defaut
	 * @param mot -> un TypeIndex est represente par un mot
	 */
	public TypeIndex(String mot)
	{
		this.mot = mot;
	}
	
	/**
	 * Getter
	 * @return le mot
	 */
	public String getMot() {
		return mot;
	}
	
	
	
	public void setMot(String mot) {
		this.mot = mot;
	}

	/**
	 * Affichage directe du mot
	 */
	@Override
	public String toString()
	{
		return mot;
	}

	/**
	 * Appel le compareTo de String deja defini
	 */
	@Override
	public int compareTo(TypeIndex o) {
		// TODO Auto-generated method stub
		return this.getMot().compareTo(o.getMot());
	}

	/**
	 * Compare 2 typeIndex par leur string (mot)
	 */
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		
		if(this==obj) return true;
		if(obj==null) return false;
		
		try {
			TypeIndex other = (TypeIndex)obj;
			return this.getMot().equals(other.getMot());
		}
		catch (ClassCastException e){
			
		
			
			return false;
		}
		
	}

	/**
	 * Fait appel au hashCode de String
	 */
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return mot.hashCode();
	};
	
	
	
	
}
