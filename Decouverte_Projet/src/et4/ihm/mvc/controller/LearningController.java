package et4.ihm.mvc.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JTextArea;

import org.apache.commons.io.FileUtils;

import et4.ihm.mvc.Model;
import et4.ihm.mvc.component.LearningSubmitBtn;

public class LearningController implements KeyListener, MouseListener{

	private Model model;
	private LearningSubmitBtn btn;
	private JTextArea input, output;
	public LearningController(Model model, LearningSubmitBtn btn, JTextArea input, JTextArea output) {
		this.model = model;
		this.btn = btn;
		this.input = input;
		this.output = output;
		this.btn.addMouseListener(this);
		this.input.addKeyListener(this);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==btn) {
			
			String[] tradCh = null;
			String[] tradLink = null;
			String[] tradFr = null;
			String result = "";


			try {
				tradCh = FileUtils.readFileToString(new File("resources/ch.txt"), "UTF-8").split("\n");
				tradLink = FileUtils.readFileToString(new File("resources/linksChineseFrench.txt"), "UTF-8")
							.split("\n");
				tradFr =  FileUtils.readFileToString(new File("resources/fr.txt"), "UTF-8").split("\n");
			} catch (IOException e1) {
//				 TODO Auto-generated catch block
				e1.printStackTrace();
			}
				
			//System.out.println("input.get" + input.getText());
			HashMap<String, ArrayList<String>> hashmapNewWords = model.learn(input.getText());
			
			for(Entry<String,ArrayList<String>> entry : hashmapNewWords.entrySet()){
				String newWord = entry.getKey();
				ArrayList<String> listSentences = entry.getValue();
				
				if(listSentences.size()!= 0){
					result += "______________________\n";
					result += "New word to learn : \""+newWord+"\"\n\n";
					for (String sentence : listSentences) {
						
						String trad = Model.findTranslationChineseFrench(sentence, 1, tradCh, tradFr, tradLink, false);
						String piying = Model.getPinyin(sentence);
	
						result+="Sentence with this word :\n";
						result+= sentence+"\n";
						result+="Piying :\n";
						result+= piying+"\n";
						result+="Traduction : \n";
						result+= trad+"\n";
	
						result += "______________________\n";
	
					}
					result += "\n";
				}
			}
			output.setText(result);
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
