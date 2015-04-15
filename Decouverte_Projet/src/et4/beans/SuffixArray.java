package et4.beans;

import java.util.Random;

import et4.corpus.MonolingualCorpus;

public class SuffixArray {

	private MonolingualCorpus corpus;

	// the main data structure
	private int suffixes[];
	// random number generator used for quick sorting
	private static final Random RAND = new Random();

	// call to sort the entire suffix array
	// qsort( suffixes , 0 , suffixes.length - 1);
	/**
	 * Quick sort algorithm of the suffixes
	 *
	 * Uses : corpus . compareSuffixes ( i , j ) to sort values
	 *
	 * @param array
	 * @param begin
	 * @param end
	 */
	private void qsort(int[] array, int begin, int end) {
		if (end > begin) {
			int index = begin + RAND.nextInt(end - begin + 1);
			int pivot = array[index];
			{
				int tmp = array[index];
				array[index] = array[end];
				array[end] = tmp;
			}
			for (int i = index = begin; i < end; ++i) {
				if (corpus.compareSuffixes(array[i], pivot) <= 0) {
					int tmp = array[index];
					array[index] = array[i];
					array[i] = tmp;
					index++;
				}
			}
			{
				int tmp = array[index];
				array[index] = array[end];
				array[end] = tmp;
			}
			qsort(array, begin, index - 1);
			qsort(array, index + 1, end);
		}
	}
}
