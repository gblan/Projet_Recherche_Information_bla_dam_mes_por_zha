package et4.index;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;

public class TokenizationChinese2 extends Tokenization {

	private static final String basedir = System.getProperty(
			"TokenizationChinese", "data");

	
	public static void main(String[] args) throws Exception {
		TokenizationChinese2 tc = new TokenizationChinese2();
		System.out.println(tc.TokenizeDeFichier("texteChinois.txt").toString());
	}
	 
	public CorpusIndex TokenizeDeFichier(String fileName) throws FileNotFoundException {
		CorpusIndex index = new CorpusIndex();
		Set<String> tokensSet;
		try {
			tokensSet = getTokens(fileName);
			Iterator<String> it = tokensSet.iterator();
			while (it.hasNext()) {
				String token = it.next();
				ArrayList<Integer> positions = getAllPostions(
						getContenuDeFichier(fileName), token);
				index.getListTokens().put(token,
						new Token(fileName, positions, token));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Exit du system dans TokenizationChinese.java");
		// System.exit(0);
		return index;
	}

	public String getContenuDeFichier(String filePath) {
		String contenu = "";
		File filename = new File(filePath); // 要读取以上路径的input。txt文件
		InputStreamReader reader;
		try {
			reader = new InputStreamReader(new FileInputStream(filename));
			BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
			String line = "";
			try {
				line = br.readLine();
				while (line != null) {
					contenu += line;
					contenu += '\n';
					line = br.readLine(); // 一次读入一行数据
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 建立一个输入流对象reader
		return contenu;
	}

	/**
	 * This is a very simple demo of calling the Chinese Word Segmenter
	 * programmatically. It assumes an input file in UTF8.
	 * <p/>
	 * <code>
	 *  Usage: java -mx1g -cp seg.jar SegDemo fileName
	 *  </code> This will run correctly in the distribution home directory. To
	 * run in general, the properties for where to find dictionaries or
	 * normalizations have to be set.
	 *
	 * @author Christopher Manning
	 */
	public Set<String> getTokens(String filepath) throws Exception {
		System.setOut(new PrintStream(System.out, true, "utf-8"));
		Properties props = new Properties();
		props.setProperty("sighanCorporaDict", basedir); //
		props.setProperty("NormalizationTable", "data/norm.simp.utf8"); //
		props.setProperty("normTableEncoding", "UTF-8"); // below is needed
		// because
		// CTBSegDocumentIteratorFactory
		// accesses it
		props.setProperty("serDictionary", basedir + "/dict-chris6.ser.gz");
		/*
		 * if (args.length > 0) { props.setProperty("testFile", args[0]); }
		 */
		props.setProperty("inputEncoding", "UTF-8");
		props.setProperty("sighanPostProcessing", "true");

		CRFClassifier<CoreLabel> segmenter = new CRFClassifier<CoreLabel>(props);
		segmenter.loadClassifierNoExceptions(basedir + "/ctb.gz", props);
		/*
		 * for (String filename : args) { System.out.println("filename : " +
		 * filename); segmenter.classifyAndWriteAnswers(filename); }
		 */

		/*
		 * Tokenization pour une phrase String sample = contenu; List<String>
		 * segmented = segmenter.segmentString(sample);
		 * System.out.println("haha" + segmented);
		 */

		// Create a stream to hold the output
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		// IMPORTANT: Save the old System.out!
		PrintStream old = System.out;
		// Tell Java to use your special stream
		System.setOut(ps); // Print some output: goes to your special stream
		segmenter.classifyAndWriteAnswers(filepath); // Put things back
		System.out.flush();
		System.setOut(old); // Show what happened
		// System.out.println("Here: " + baos.toString());

		String tokens = baos.toString();
		String tokensList[] = new String[10000];
		/*
		 * tokens = tokens.replaceAll("，"," ").replaceAll("、", //
		 * "").replaceAll("。", "").replaceAll("：", "").replaceAll("；", //
		 * "").replaceAll("《", "").replaceAll("》", "").replaceAll("“", //
		 * "").replaceAll("”", "").replaceAll("（", "").replaceAll("）", //
		 * "").replaceAll("(", " ").replaceAll(")", "");
		 */
		tokens = tokens.replaceAll("，", " ").replaceAll("、", "")
				.replaceAll("。", "").replaceAll("：", "").replaceAll("；", "")
				.replaceAll("《", "").replaceAll("》", "").replaceAll("“", "")
				.replaceAll("”", "").replaceAll("（", "").replaceAll("）", "")
				.replaceAll("～", "").replaceAll("~", "").replaceAll("\\(", "")
				.replaceAll("\\)", "").replaceAll("-", "").replaceAll("\n", "");
		tokensList = tokens.split(" ", -1);
		Set<String> tokensSet = new HashSet<String>();
		for (int i = 0; i < tokensList.length; i++) {
			// System.out.println(tokensList[i] + " ");
			// System.out.println("empty : "+tokensList[i].isEmpty());
			if (!tokensList[i].isEmpty()) {
				tokensSet.add(tokensList[i]);
			}
		}
		return tokensSet;
	}

	public ArrayList<Integer> getAllPostions(String contenu, String token) {
		ArrayList<Integer> positions = new ArrayList<Integer>();
		String s = contenu;
		int positionAbsolu = 0;
		for (;;) {
			int positionRelatif = s.indexOf(token);
			positionAbsolu = positionAbsolu + positionRelatif;
			// System.out.println("pos : " + positionAbsolu);
			if (positionRelatif >= 0) {
				positions.add(positionAbsolu);
				positionRelatif++;
				positionAbsolu++;
				s = s.substring(positionRelatif);
			} else {
				break;
			}
		}
		return positions;
	}

	@Override
	public ArrayList<CorpusIndex> Tokenize(String fileName) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
