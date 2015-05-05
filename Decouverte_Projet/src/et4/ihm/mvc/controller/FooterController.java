package et4.ihm.mvc.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.JFileChooser;

import et4.ihm.mvc.FooterOpenFileBtn;
import et4.ihm.mvc.Model;

public class FooterController implements MouseListener{

	private FooterOpenFileBtn btn;
	private Model model;
	public FooterController(Model model, FooterOpenFileBtn btn) {
		this.btn = btn;
		this.btn.addMouseListener(this);
		this.model = model;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		if(e.getSource()==btn) {
			JFileChooser dialogue = new JFileChooser(new File("ressources"));
			File file;
			
			if (dialogue.showOpenDialog(null)== 
			    JFileChooser.APPROVE_OPTION) {
			    file = dialogue.getSelectedFile();
			    
			    if(file!=null)
			    {
			    	System.out.println("Open de : "+file.getName());			    	
			    	
			    	try {
						model.updateKnowledge(file);
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			    	/*if(fichier.getName().endsWith(".xml"))
			    	{
			    		System.out.println("Ouverture en cours ... : "+fichier.getAbsolutePath());
			    		
			    	}
			    	else
			    	{
			    		System.out.println("Le fichier choisi n'est pas sous le bon format !");
			    	}*/
			    }
			    else
			    {
			    	System.out.println("Vous n'avez pas choisi de fichier");
			    }
			}
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

}
