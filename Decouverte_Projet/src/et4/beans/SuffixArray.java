package et4.beans;

import java.util.ArrayList;
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
		tabSuffixes = new int[corpus.getIndexSize()+1];
	}
	
	public int[] getTabSuffix(){
		return tabSuffixes;
	}

	public void initTabSuffix() {
		for (int i = 0; i < corpus.getIndex().getListTokens().size(); i++) {
			tabSuffixes[i] = corpus.getIndex().getListTokens().get(i).getPosition();
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
	}

	public int[] getLCPVector() {
		int[] result = new int[tabSuffixes.length + 1];
		ArrayList<Token> listToken = corpus.getIndex().getListTokens();
		result[0] = 0;
		for (int i = 1; i < tabSuffixes.length ; i++) {
			result[i] = getLCP2String(listToken.get(tabSuffixes[i]), listToken.get(tabSuffixes[i - 1]));
		}
		
		result[tabSuffixes.length] = 0;

		return result;
	}

	/**
	 * Work with JRE 1.8 only
	 * @param token1
	 * @param token2
	 * @return
	 */
	private int getLCP2String(Token token1, Token token2) {
		int result = 0;
		String s1 = token1.getStringToken();
		String s2 = token2.getStringToken();

		for (int i = 0; i < Integer.min(s1.length(), s2.length()); i++) {
			if (s1.charAt(i) == s2.charAt(i)) {
				result++;
			} else {
				break;
			}
		}

		return result;
	}
}
