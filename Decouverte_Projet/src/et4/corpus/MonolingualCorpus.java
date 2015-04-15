package et4.corpus;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MonolingualCorpus {
	private static String corpusContent;

	/**
	 * @param fileName
	 * @param encoding
	 * @return String contains file input
	 * @throws IOException
	 */
	public static boolean loadFromFile(String fileName, Charset encoding) {
		try {
			byte[] encoded = Files.readAllBytes(Paths.get(fileName));
			corpusContent = new String(encoded, encoding);
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public String getTokenAtPosition(int position) {
		return null;
	}

	public String getSuffixFromPosition(int position) {
		return null;
	}

	public int compareSuffixes(int position1, int position2) {
		return 0;
	}
}
