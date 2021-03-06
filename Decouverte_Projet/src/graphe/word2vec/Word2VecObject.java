package graphe.word2vec;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.nio.charset.Charset;

import org.deeplearning4j.models.word2vec.VocabWord;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.models.word2vec.wordstore.VocabCache;
import org.deeplearning4j.models.word2vec.wordstore.inmemory.InMemoryLookupCache;
import org.deeplearning4j.text.sentenceiterator.LineSentenceIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.deeplearning4j.util.SerializationUtils;

import et4.index.TokenizationChinese2;

public class Word2VecObject {
	private SentenceIterator iter;
    private TokenizerFactory tokenizer;
    private Word2Vec vec;
    VocabCache cache;
    public final static String VEC_PATH = "vec2.ser";
    public final static String CACHE_SER = "cache.ser";

    public Word2VecObject(File file) throws Exception {
        this.iter = new LineSentenceIterator(new File(file.getAbsolutePath()));
        tokenizer =  new DefaultTokenizerFactory();
    }

    /*public static void main(String[] args) {
    	TokenizationChinese2 tokenizationchinese2sav = new TokenizationChinese2();;
		
		try {
			tokenizationchinese2sav.load();
			System.out.println("Tokenization chargee");
		} catch (ClassNotFoundException | IOException e1) {
			// TODO Auto-generated catch block
			
			try {
				tokenizationchinese2sav.getTokensDeFichierEtoile("chCorpusUTFBis.txt");
				tokenizationchinese2sav.save();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//System.out.println(tokenizationchinese2sav.getSentences());
		FileOutputStream fos1 = null;
		try {
			fos1 = new FileOutputStream("chCorpusUTFBis.txt");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos1,Charset.forName("UTF-8")));
	    
		for(String sentence : tokenizationchinese2sav.getSentences()) {
			String[] array = sentence.split(" ");
			
			try {
				

			
				for(String s : array) {
					writer.write(s);
					writer.newLine();
				}
				
				
				
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}*/
    

    public double similarity(String first, String second) {
		
    	if(cache.indexOf(first) < 0) {
            System.err.println("Word " + first + " not in vocab");
            return 0;
        }
        if(cache.indexOf(second) < 0) {
            System.err.println("Word " + second + " not in vocab");
            return 0;
        }
    	return vec.similarity(first, second);
		
	}

	public void launch(String token) throws Exception {
    	
        train(token);
    }

    public void train(String token) throws Exception {
        
 
    	
        if(vec == null && !new File(VEC_PATH).exists()) {
            cache = new InMemoryLookupCache.Builder()
                    .lr(2e-3).vectorLength(100).build();
            //minWordFrequency(5)
            vec = new Word2Vec.Builder().vocabCache(cache)
                    .windowSize(10)
                    .layerSize(100).iterate(iter).tokenizerFactory(tokenizer)
                    .build();
            vec.setCache(cache);
            vec.fit();
            
            
            SerializationUtils.saveObject(vec, new File(VEC_PATH));
            SerializationUtils.saveObject(cache,new File(CACHE_SER));

        }


        else {
            vec = SerializationUtils.readObject(new File(VEC_PATH));
            cache = SerializationUtils.readObject(new File(CACHE_SER));
            vec.setCache(cache);
            
            System.out.println("Read success");
         
            
            

            
            /*for(String s : vec.wordsNearest(token, 10)) {
            	System.out.println(token+" - "+s+" -> "+vec.similarity(s, token));
            }*/
     
            
            /*while((line = reader.readLine()) != null) {
            	String[] split = line.split(",");
            	
            	
                if(cache.indexOf(split[0]) < 0) {
                    System.err.println("Word " + split[0] + " not in vocab");
                    continue;
                }
                if(cache.indexOf(split[01]) < 0) {
                    System.err.println("Word " + split[01] + " not in vocab");
                    continue;
                }
                System.out.println("_____");
                System.out.println(vec.similarity(split[0],split[1]));
                System.out.println("_____");
            }*/
        }
        
     // Create a stream to hold the output
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		// IMPORTANT: Save the old System.out!
		PrintStream old = System.out;
		// Tell Java to use your special stream
		System.setOut(ps); // Print some output: goes to your special stream
		
        
        System.out.println("_________VOCAB__________");
        
        for(String s : cache.words()) {
        	System.out.println(s);
        }
        
        System.out.println("_________VOCAB__________");

		System.out.flush();
		System.setOut(old); // Show what happened
		//System.out.println("Here: " + baos.toString());
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        System.out.println("Print similarity");
        
        try (PrintStream out = new PrintStream(new FileOutputStream("filename.txt"))) {
            out.print(baos.toString());
        }
    }
}
