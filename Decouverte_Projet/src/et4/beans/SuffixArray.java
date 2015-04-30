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
	}
	
	public int[] getTabSuffix(){
		return tabSuffixes;
	}

	public void initTabSuffix() {
		for(Entry<String, Token> entry : corpus.getIndex().getListTokens().entrySet()){
//			for(Integer :entry.)
		}
		for (int i = 0; i < corpus.getIndex().getListTokens().size(); i++) {
//			tabSuffixes[i] = corpus.getIndex().getListTokens().get(i).getPositions();
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
//		ArrayList<Token> listToken = corpus.getIndex().getListTokens();
		result[0] = 0;
		for (int i = 1; i < tabSuffixes.length ; i++) {
//			result[i] = getLCP2String(listToken.get(tabSuffixes[i]), listToken.get(tabSuffixes[i - 1]));
		}
		
		result[tabSuffixes.length] = 0;

		return result;
	}
	
	/**
	 * @param posFirstToken
	 * @param posSecondToken
	 * @return le plus long prefixe commun entre les deux tokens aux positions firstToken et secondToken
	 * @throws Exception
	 */
	public String getLCPLongPrefixeBetween(Integer posFirstToken, Integer posSecondToken) throws Exception {
		String result = "";
		
		
		if(posFirstToken<0 || posSecondToken<0) {
			throw new Exception("getLCP2String in SuffixArray : posFirstToken < 0 || posSecondToken < 0");
		}
		
		//String corpusSentence = corpus.getCorpus();
		String corpusSentence = "to be or not to be";

		
		
		

		return result;
	}
}
