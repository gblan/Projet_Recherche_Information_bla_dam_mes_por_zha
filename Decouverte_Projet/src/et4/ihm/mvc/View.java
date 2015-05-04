package et4.ihm.mvc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import et4.ihm.mvc.component.KnowledgeComponent;
import et4.ihm.mvc.component.SearchComponent;
import et4.ihm.mvc.panel.body.BodyPanel;
import et4.ihm.mvc.panel.footer.FooterPanel;
import et4.ihm.mvc.panel.header.HeaderPanel;

/**
 * L'interface graphique en elle meme
 * @author All
 *
 */
public class View implements Observer{

	private String title = "";
	JPanel global;
	HeaderPanel header;
	BodyPanel body;
	FooterPanel footer;
	
	private JFrame frame = new JFrame();
	
	public final static Integer width = 420;
	public final static Integer height = 640;
	private static int POURCENTAGE_HEIGHT_HEADER = 15;
	private static int POURCENTAGE_HEIGHT_FOOTER = 10;
	
	
	public View(String title, Model model) {
		this.title = title;
		
		
		global = new JPanel();
		
		
		footer = new FooterPanel(model);
		body = new BodyPanel(model,footer);
		header = new HeaderPanel(body,footer);
		

		
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        if (JOptionPane.showConfirmDialog(frame, 
		            "Are you sure to close this window?", "Really Closing?", 
		            JOptionPane.YES_NO_OPTION,
		            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
		            System.exit(0);
		        }
		    }
		});
		
		init();
		
	}
	
	private void init() {
		
		/**
		 * Coloration
		 */
		global.setBackground(Color.decode("#e74c3c"));
		header.setBackground(Color.decode("#2980b9"));
		body.setBackground(Color.decode("#ecf0f1"));
		footer.setBackground(Color.decode("#2980b9"));

		/**
		 * Global
		 */
		
		global.setLayout(new BorderLayout());
		
		/**
		 * Header
		 */
		
		header.setPreferredSize(new Dimension(width,height*POURCENTAGE_HEIGHT_HEADER/100));
		
		
        //b.setFont(new Font("Helvetica-Neue", Font.BOLD, 12));//http://answers.yahoo.com/question/index?qid=20070906133202AAOvnIP
		        
		global.add(header, BorderLayout.PAGE_START);
		
		/**
		 * Body
		 */
		
		global.add(body, BorderLayout.CENTER);

		
		/**
		 * Footer
		 */
		
		global.add(footer, BorderLayout.PAGE_END);
		footer.setPreferredSize(new Dimension(width,height*POURCENTAGE_HEIGHT_FOOTER/100)); 
		
		frame.setSize(new Dimension(width,height));
		global.setSize(new Dimension(width,height)); 
		frame.add(global);
		
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
	}

	/**
	 * Fonction permettant de detecter les changements sur le model
	 */
	@Override
	public void update(Observable o, Object arg) {
		
		try {
			
			ArrayList<SearchComponent> components = (ArrayList<SearchComponent>)arg;
			SearchComponent tester = components.get(0); //sert a provoquer class cast exception
			
			body.getSearch().update(components);
			body.showSearch();
			footer.showLoading();
		} catch (ClassCastException e) {
		}
		
		try {
			ArrayList<KnowledgeComponent> components = (ArrayList<KnowledgeComponent>)arg;
			KnowledgeComponent tester = components.get(0); //sert a provoquer class cast exception
			body.getKnowledge().update(components);
			body.showKnowledge();
			footer.showBtn();
		} catch (ClassCastException e) {
		}
		
	}

	/**
	 * Ajout des different listner
	 * @param controller
	 */
	public void addController(Controller controller) {
		
		
	}

}
