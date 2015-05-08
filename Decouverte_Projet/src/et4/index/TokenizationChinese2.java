package et4.index;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;

public class TokenizationChinese2 extends Tokenization implements Serializable{

	private static final String basedir = System.getProperty(
			"TokenizationChinese", "data");
	
	private Set<String> tokensSet;
	private CorpusIndex index;
	private String contenu;
	private String contenubis;
	private ArrayList<Integer> positions;
	private String tokens = "";
	ArrayList<String> sentences = new ArrayList<String>();
	/*public static void main(String[] args) throws Exception {
		
		TokenizationChinese2 tc = new TokenizationChinese2();
		List<String> sentences = tc.getTokensDeFichierEtoile("chCorpusUTF.txt");

		System.out.println("List.size "+sentences.size());
		
		for (int i = 0; i < sentences.size(); i++) {
			System.out.println(sentences.get(i).replaceFirst(" ", ""));
		}
		
		//tc.getContenuDeFichierEtoile("test.txt");
		//System.out.println(tc.getTokensDeFichierEtoile("test.txt"));
		//System.out.println(tc.TokenizeDeFichier("test.txt").toString());
		//System.out.println(tc.getTokensDePhrase("今天天气好,太好啦!"));
	}*/
	
	 
	public String getContenuDeFichierEtoile(String filePath){
		contenubis = "";
		File filename = new File(filePath);
		InputStreamReader reader;
		try {
			reader = new InputStreamReader(new FileInputStream(filename));
			BufferedReader br = new BufferedReader(reader);
			String line = "";
			try {
				line = br.readLine();
				while (line != null) {
					contenubis += line;
					//contenu += '\n';
					contenubis += " *** ";
					line = br.readLine();
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
		} 
		//System.out.println("contenu : " + contenu);
		return contenubis;
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
	public Set<String> getTokensDeFichier(String filepath) throws Exception {
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
		tokensSet = new HashSet<String>();
		for (int i = 0; i < tokensList.length; i++) {
			// System.out.println(tokensList[i] + " ");
			// System.out.println("empty : "+tokensList[i].isEmpty());
			if (!tokensList[i].isEmpty()) {
				tokensSet.add(tokensList[i]);
			}
		}
		return tokensSet;
	}
	
	public List<String> getTokensDeFichierEtoile(String filepath) throws Exception {
		contenu = getTokensDePhraseEtoile(getContenuDeFichierEtoile(filepath));
		contenu = contenu.replaceAll("\\* \\* \\*", "***").replaceAll("\\* \\*\\*", "***").replaceAll("\\*\\* \\*", "***").replaceAll("\\* \\* \\*\\* \\* \\*", "***").replace("\\* \\* \\* \\* \\* \\*", "***");
		
		String[] array = contenu.split("\\*\\*\\*");
		sentences = new ArrayList<String>();
		sentences.addAll(Arrays.asList(array));
		return Arrays.asList(array);
	}
	
	public ArrayList<String> getTokensDePhrase(String phrase) throws Exception {
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
		 //Tokenization pour une phrase
		String tokensList[] = new String[1000]; 
		List<String> segmented = segmenter.segmentString(phrase);
		
		String tokens = "";
		for(int i=0; i<segmented.size();i++){
			tokens = tokens+segmented.get(i)+" ";
		}
		tokens = tokens.replaceAll("，", " ").replaceAll("、", "")
				.replaceAll("。", "").replaceAll("：", "").replaceAll("；", "")
				.replaceAll("《", "").replaceAll("》", "").replaceAll("“", "")
				.replaceAll("”", "").replaceAll("（", "").replaceAll("）", "")
				.replaceAll("～", "").replaceAll("~", "").replaceAll("\\(", "")
				.replaceAll("\\)", "").replaceAll("-", "").replaceAll("\n", "").replaceAll(",","");
		System.out.println("segmented : " + tokens);
		
		tokensList = tokens.split(" ", -1);
		ArrayList<String> resultat = new ArrayList<String>();
		for(int i=0; i<tokensList.length;i++){
			if(!tokensList[i].isEmpty()){
				resultat.add(tokensList[i]);
			}
		}
		return resultat;
	}
	
	public String getTokensDePhraseEtoile(String phrase) throws Exception {
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
		 //Tokenization pour une phrase
		String tokensList[] = new String[1000]; 
		List<String> segmented = segmenter.segmentString(phrase);
		
		tokens = "";
		for(int i=0; i<segmented.size();i++){
			tokens = tokens+segmented.get(i)+" ";
		}
		tokens = tokens.replaceAll("，", " ").replaceAll("、", "")
				.replaceAll("。", "").replaceAll("：", "").replaceAll("；", "")
				.replaceAll("《", "").replaceAll("》", "").replaceAll("“", "")
				.replaceAll("”", "").replaceAll("（", "").replaceAll("）", "")
				.replaceAll("～", "").replaceAll("~", "").replaceAll("\\(", "")
				.replaceAll("\\)", "").replaceAll("-", "").replaceAll("\n", "").replaceAll(",","");
		//System.out.println("segmented : " + tokens);
		return tokens;
	}

	public ArrayList<Integer> getAllPostions(String contenu, String token) {
		positions = new ArrayList<Integer>();
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

	public Set<String> getTokensSet() {
		return tokensSet;
	}

	public CorpusIndex getIndex() {
		return index;
	}

	public String getContenu() {
		return contenu;
	}

	public ArrayList<Integer> getPositions() {
		return positions;
	}

	public String getTokens() {
		return tokens;
	}
	
	public void save() throws IOException {
		FileOutputStream fos = new FileOutputStream("tokenizationchines.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
        oos.close();
        fos.close();
        System.out.printf("Serialized HashMap data is saved in dico.ser");
	}
	
	public void load() throws IOException, ClassNotFoundException {
		FileInputStream fis = new FileInputStream("tokenizationchines.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        TokenizationChinese2 tmp = (TokenizationChinese2) ois.readObject();
        
        tokensSet = tmp.tokensSet;
        contenu = tmp.contenu;
        contenubis = tmp.contenubis;
        positions = tmp.positions;
        tokens = tmp.tokens;
        sentences = tmp.sentences;
        /*
         * private Set<String> tokensSet;
	private CorpusIndex index;
	private String contenu;
	private String contenubis;
	private ArrayList<Integer> positions;
	private String tokens = "";
         */
        
        ois.close();
        fis.close();
        
        System.out.println("------DESERIALIZE------");
        
        System.out.println(this);
	}



	public String getContenubis() {
		return contenubis;
	}



	public ArrayList<String> getSentences() {
		return sentences;
	}
	
	

}
