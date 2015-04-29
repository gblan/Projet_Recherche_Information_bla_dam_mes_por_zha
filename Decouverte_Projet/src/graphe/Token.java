package graphe;
/**
 * Represente les token donc un mot.
 * 
 * Classe heritant de TypeIndex
 * @author aminmessaoudi
 *
 */
public class Token extends TypeIndex{

	/**
	 * Constructeur par defaut
	 * Prend un mot en parametre
	 * @param word
	 */
	public Token(String word)
	{
		super(word);
	}
	
	/**
	 * toString -> affichage du token
	 * appel du super.toString()
	 */
	@Override
	public String toString() {
		return super.toString();
	}

	/**
	 * Permet de comparer un token donne (en parametre) et le token implicite
	 */
	@Override
	public int compareTo(TypeIndex o) {
		// TODO Auto-generated method stub
		return this.getMot().compareTo(o.getMot());
	}

	/**
	 * Appel du super.equals()
	 */
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}

	/**
	 * Appel du super.hashCode()
	 */
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
	
}
