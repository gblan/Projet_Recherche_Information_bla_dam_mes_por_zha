package graphe.word2vec;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.models.word2vec.wordstore.VocabCache;
import org.deeplearning4j.models.word2vec.wordstore.inmemory.InMemoryLookupCache;
import org.deeplearning4j.text.sentenceiterator.LineSentenceIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.deeplearning4j.util.SerializationUtils;
import org.springframework.core.io.ClassPathResource;

public class TestWord2Vec {
	private SentenceIterator iter;
    private TokenizerFactory tokenizer;
    private Word2Vec vec;

    public final static String VEC_PATH = "vec.ser";
    public final static String CACHE_SER = "cache.ser";

    public TestWord2Vec(String path) throws Exception {
        this.iter = new LineSentenceIterator(new File(path));
        tokenizer =  new DefaultTokenizerFactory();
    }

    public static void main(String[] args) throws Exception {
    	if(args.length >= 1)
            new TestWord2Vec(args[0]).train();
        else {
        	
        	Path currentRelativePath = Paths.get("");
        	String s = currentRelativePath.toAbsolutePath().toString();
        	System.out.println("Current relative path is: " + s);
        	
            ClassPathResource resource = new ClassPathResource("frCorpus.txt");
            System.out.println("ClassPathRessource");
            File f = resource.getFile();

            new TestWord2Vec(f.getAbsolutePath()).train();

        }
    	System.out.println("Fin");
    }


    public void train() throws Exception {
        VocabCache cache;
        if(vec == null && !new File(VEC_PATH).exists()) {
            cache = new InMemoryLookupCache.Builder()
                    .lr(2e-3).vectorLength(100).build();
            
            vec = new Word2Vec.Builder().minWordFrequency(5).vocabCache(cache)
                    .windowSize(5)
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

            for(String s : cache.words()) {
                System.out.println(s);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String line;
            System.out.println("Print similarity");
            
            System.out.println(" --> "+vec.wordsNearest("guerre", 10));
            
            while((line = reader.readLine()) != null) {
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
            }
        }
    }
}
