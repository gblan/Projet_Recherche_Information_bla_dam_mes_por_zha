package et4.beans;

import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.Random;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import et4.corpus.MonolingualCorpus;
import et4.index.Token;

public class SuffixArray {

	private MonolingualCorpus corpus;
	// the main data structure
	private int tabSuffixes[];
	// random number generator used for quick sorting
	private static final Random RAND = new Random();

	public SuffixArray(MonolingualCorpus corpus) {
		this.corpus = corpus;
		this.tabSuffixes = new int[corpus.getNbMots()];
	}

	public int[] getTabSuffix() {
		return tabSuffixes;
	}

	/**
	 * Initialise le tableau de suffixes en donnant les positions des
	 * diff�rents tokens du fichier
	 */
	public void initTabSuffix() {
		int i = 0;
		for (Entry<String, Token> entry : corpus.getIndex().getListTokens().entrySet()) {
			for (int position : entry.getValue().getPositions()) {
				tabSuffixes[i] = position;
				i++;
			}
		}
		System.out.println("init OK");
	}

	// call to sort the entire suffix array
	// qsort( suffixes , 0 , suffixes.length - 1);
	/**
	 * Quick sort algorithm of the suffixes
	 *
	 * Uses : corpus . compareSuffixes ( i , j ) to sort values
	 *
	 * @param tabSuffixes
	 * @param begin
	 * @param end
	 */
	public void qsort(int[] tabSuffixes, int begin, int end) {
		// System.out.println("qsort");
		if (end > begin) {
			int index = begin + RAND.nextInt(end - begin + 1);
			int pivot = tabSuffixes[index];
			{
				int tmp = tabSuffixes[index];
				tabSuffixes[index] = tabSuffixes[end];
				tabSuffixes[end] = tmp;
			}
			for (int i = index = begin; i < end; ++i) {
				if (corpus.compareSuffixes(tabSuffixes[i], pivot) <= 0) {
					int tmp = tabSuffixes[index];
					tabSuffixes[index] = tabSuffixes[i];
					tabSuffixes[i] = tmp;
					index++;
				}
			}
			{
				int tmp = tabSuffixes[index];
				tabSuffixes[index] = tabSuffixes[end];
				tabSuffixes[end] = tmp;
			}
			qsort(tabSuffixes, begin, index - 1);
			qsort(tabSuffixes, index + 1, end);
		}
		// System.out.println("QSort OK");
	}

	public int[] getLCPVector() throws Exception {
		int[] result = new int[tabSuffixes.length +1];
		// ArrayList<Token> listToken = corpus.getIndex().getListTokens();
		result[0] = 0;
		for (int i = 1; i < tabSuffixes.length ; i++) {
			result[i] = getLCPLongPrefixeBetween(tabSuffixes[i-1], tabSuffixes[i]);
			System.out.println("Result : "+result[i]);
		}

		result[tabSuffixes.length] = 0;

		return result;
	}

	/**
	 * @param posFirstToken
	 * @param posSecondToken
	 * @return le plus long prefixe commun entre les deux tokens aux positions
	 *         firstToken et secondToken
	 * @throws Exception
	 */
	public Integer getLCPLongPrefixeBetween(Integer posFirstToken, Integer posSecondToken) throws Exception {
		Integer result = 0;
		// String content = "";

		/**
		 * TODO Rajouter > taille max
		 */
		if (posFirstToken < 0 || posSecondToken < 0) {
			throw new Exception("getLCP2String in SuffixArray : posFirstToken < 0 || posSecondToken < 0");
		}

		String corpusSentence = corpus.getCorpus();

		// System.out.println("Corpus "+corpusSentence);

		System.out.println("Token a la position : " + posFirstToken + " = |"
				+ corpusSentence.substring(posFirstToken, corpusSentence.length()) + "|");
		System.out.println("Token a la position : " + posSecondToken + " = |"
				+ corpusSentence.substring(posSecondToken, corpusSentence.length()) + "|");

		String firstToken = corpusSentence.substring(posFirstToken, corpusSentence.length());
		String secondToken = corpusSentence.substring(posSecondToken, corpusSentence.length());

		int i = 0;
		boolean match = true;
		while (i < Math.min(firstToken.length(), secondToken.length()) && match) {

			if (firstToken.charAt(i) == secondToken.charAt(i)) {
				// content += firstToken.charAt(i);
				result++;
			} else
				match = false;

			i++;
		}

		/**
		 * 0 2 4 6 10 11 to be or not to be
		 * 
		 * 
		 * 
		 */

		return result;
	}
	
	
	
	public static int dichotomieRecursive(ArrayList<String> array, String search, int starting, int ending) {
		
		if(ending<starting) return -1;
		int mid = (int)((starting+ending)/2);
		System.out.println("array.get(mid)= " +array.get(mid));
		int result = compare2Tokens(array.get(mid),search);
		
		if(result==0)
			return mid;
		else if(result<0) {
			starting = mid+1;
		}
		else {
			
			ending = mid-1;
		}
	
		
		return dichotomieRecursive(array,search, starting, ending);
	}
	
	public static int compare2Tokens(String fromArray, String search) {
		// Si la string a la position 'mid' a une taille inferieur a celle du 'search' on va juste faire un compareto
		// => aucune chance que les deux strings se ressemblent
		if(fromArray.length()<search.length()) {
			return fromArray.compareTo(search);
		}
		// Si la string a la position 'mid' a une taille superieur a celle de 'search', alors elle est un candiat potentiel
		// => il y a une chance qu'elle se ressemble : par exemple mid="to be" search="to" on va decouper mid pour qu'elle ai
		// la meme taille que 'search'
		else {
			return fromArray.substring(0, search.length()).compareTo(search);
		}
	}
	
	public static void main(String[] args) {
		
		ArrayList<String> exemple = new ArrayList<String>();
			exemple.add("Anacondaaaaaa");
			exemple.add("Anticonsti-qjsqdqdh");
			exemple.add("Cerise");
			exemple.add("Java");
			exemple.add("Orange");
			exemple.add("Pastèque");
			exemple.add("Pastèque");
			exemple.add("Pastèque");
			exemple.add("Pastèque");
			exemple.add("Pastèque");
			exemple.add("Pastèque");
			exemple.add("Pastèque");
			exemple.add("Pastèque");
			exemple.add("Pastèque");
			exemple.add("Pastèque");
			exemple.add("Pastèque");
			exemple.add("Pastèque");
			exemple.add("Pastèque");
			exemple.add("Pastèque");
			exemple.add("Pastèques");
			exemple.add("Pastèque");
			exemple.add("Pastèque");
			exemple.add("Pastèque");
			exemple.add("Pastèque Ro");
			exemple.add("Pastèque");
			exemple.add("Pastèque");
			exemple.add("Pastèque");
			exemple.add("Pommes Ro");
			exemple.add("Vert");
			exemple.add("Zèbre");
			
		String search = "Pastèque";
		int position = SuffixArray.dichotomieRecursive(exemple,search,0,exemple.size()-1);
		System.out.println("Position = "+position);

		ArrayList<Integer> positions = new ArrayList<Integer>();
		
		
		for(int i = position-1; i>=0; i--) {
			
			if(SuffixArray.compare2Tokens(exemple.get(i), search)==0) {
				positions.add(0, i);
			}
			//TODO transforme en while
			else 
				break;
			
		}
		
		positions.add(position);
		
		for (int i = position+1; i < exemple.size(); i++) {
						
			if(SuffixArray.compare2Tokens(exemple.get(i), search)==0) {
				positions.add(positions.size(), i);
			}
			//TODO transforme en while
			else 
				break;
		}
		
		System.out.println("Positions : "+positions);
	}
	
	
	int[] getAllPositionsOfPhrase(String search){
		/* tokenisation pour tester si tous les tokens de la phrase sont dans l'index*/
		
		System.out.println("HEYYY-------");
		/* recherche dichotomique*/
		
		
		/* liste de secours*/
		
		return null;		
	}
}
