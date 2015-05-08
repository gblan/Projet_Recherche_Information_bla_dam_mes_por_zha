package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

public class Test3 {

	public static void main(String[] args) throws IOException {
		File t = new File("");
		//System.out.println(""+t.getAbsolutePath());
		InputStream ch = new FileInputStream("src/chCorpus.txt");
	    InputStreamReader frR = new InputStreamReader(ch,
	            Charset.forName("UTF-8"));
	    BufferedReader frB = new BufferedReader(frR);

	    FileOutputStream fos1 = new FileOutputStream("src/chCorpusUTF.txt");
	    BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter( fos1,Charset.forName("UTF-8")));
	    
	    String line ="";
	        while ((line = frB.readLine()) != null) {
	            //String[] tmp = line.split("    ");
	            bw1.write(line);
	            bw1.newLine();
	        }

	        //System.out.println("Done");

	}
}
