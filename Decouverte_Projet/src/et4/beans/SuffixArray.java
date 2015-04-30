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
	 * Initialise le tableau de suffixes en donnant les positions des différents
	 * tokens du fichier
	 */
	public void initTabSuffix() {
		int i = 0;
		for (Entry<String, Token> entry : corpus.getIndex().getListTokens().entrySet()) {
			for (int list : entry.getValue().getPositions()) {
				tabSuffixes[i] = list;
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

	public int[] getLCPVector() {
		int[] result = new int[tabSuffixes.length + 1];
		// ArrayList<Token> listToken = corpus.getIndex().getListTokens();
		result[0] = 0;
		for (int i = 1; i < tabSuffixes.length; i++) {
			 result[i] = getLCP2String(tabSuffixes[i],tabSuffixes[i+1]);
			// listToken.get(tabSuffixes[i - 1]));
		}

		result[tabSuffixes.length] = 0;

		return result;
	}

	/**
	 * @param tabSuffixes2
	 * @param tabSuffixes3
	 * @return
	 */
	private int getLCP2String(int tabSuffixes2, int tabSuffixes3) {
		int result = 0;
////		String s1 = tabSuffixes2.getStringToken();
////		String s2 = tabSuffixes3.getStringToken();
//
//		for (int i = 0; i < Math.min(s1.length(), s2.length()); i++) {
//			if (s1.charAt(i) == s2.charAt(i)) {
//				result++;
//			} else {
//				break;
//			}
//		}
//
		return result;
	}
}