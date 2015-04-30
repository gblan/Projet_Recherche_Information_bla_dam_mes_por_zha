package et4.beans;

import java.util.Map.Entry;
import java.util.Random;

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
	
	int[] getAllPositionsOfPhrase(String phrase){
		/* tokenisation pour tester si tous les tokens de la phrase sont dans l'index*/
		
		
		/* recherche dichotomique*/
		
		
		/* liste de secours*/
		
		return null;		
	}
}
