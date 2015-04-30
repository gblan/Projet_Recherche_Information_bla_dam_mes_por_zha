package et4.ihm.mvc.panel.body;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.im.InputContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.kiang.chinese.pinyin.im.PinyinInputTermSource.PinyinInputMethodControl;
import org.kiang.chinese.pinyin.im.app.PinyinInputConfig;
import org.kiang.chinese.pinyin.im.app.main.PinyinInputAppConfig;
import org.kiang.chinese.pinyin.im.swing.PinyinInputTextArea;
import org.kiang.swing.JFontChooser;

import et4.ihm.mvc.Model;
import et4.ihm.mvc.View;
import et4.ihm.mvc.component.SearchComponent;
import et4.ihm.mvc.controller.SearchBarController;

public class SearchPanel extends JPanel{

	PinyinInputTextArea textArea;
	private ArrayList<SearchComponent> components;
	private final String FONT = "Helvetica-Neue";
	private final int FONT_SIZE = 20;
	public final static int heightComponent = 100;
	public final static int space = 20;
	// a Font bundled as a resource, if available
	private Font bundledFont;
	// resource path of the configuration
	static private final String PROPERTIES_RESOURCE = "input.properties";
	
	static private final String[] SUPPORTED_CHARSETS = new String[] {
		// FileFilters get added to a JFileChooser in the order
		// they appear in the array.  seems like JFileChooser
		// will use the last as the selected no matter what,
		// so make the last the one that should be selected.
		"GB2312",
		"Big5",
		"UTF-8",
	};

	public SearchPanel(Model model) {
		super();
		
		components = new ArrayList<SearchComponent>();
		
		
		textArea = this.initConfig();
		textArea.setPreferredSize(new Dimension(View.width-20,50));
		textArea.setFont(new Font(FONT, Font.PLAIN, FONT_SIZE));
		textArea.setCaretColor(Color.WHITE);
		int r = 241, g = 196, b = 15, a = 255;
		textArea.setBackground(new Color(r,g,b,a));
		textArea.setForeground(Color.white);
		//
		
		JLabel label = new JLabel("Que recherchez vous ?");
		label.setFont(new Font(FONT, Font.PLAIN, FONT_SIZE));
		label.setForeground(Color.WHITE);
		
		add(label);
		
		SearchBarController c = new SearchBarController(model);
		textArea.addKeyListener(c);
		//textArea.addActionListener(c);
		
		add(textArea);

	}
	
	public void update(ArrayList<SearchComponent> components) {
		
		this.components.clear();
		this.components.addAll(components);
		
		repaint();
	}
	
	@Override
	public void paint(Graphics graphics) {
		
		super.paint(graphics);

		Graphics2D g = (Graphics2D) graphics;
		RenderingHints rh = new RenderingHints(
	             RenderingHints.KEY_TEXT_ANTIALIASING,
	             RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	    g.setRenderingHints(rh);
		
		int ysentence = 0;
		int ynewword = 0;
		int ytranslation = 0;
		int yphonetic = 0;
		int yglobal = heightComponent;
		
		for(SearchComponent sc : components) {
			
			ysentence = yglobal+25;
			ytranslation = ysentence+20;
			yphonetic = ytranslation+20;
			ynewword = yphonetic+20;
			
			g.setColor(sc.getBackground());
			g.fill(sc.getR().r);
			g.setColor(sc.getBorder());
			g.draw(sc.getR().r);
			
			g.setColor(Color.WHITE);

			g.setFont(new Font("Helvetica-Neue", Font.PLAIN, 18));
			g.drawString("Sentence : "+sc.getSentence(), 20, ysentence);
			
			g.setFont(new Font("Helvetica-Neue", Font.PLAIN, 16));
			g.drawString("Translation : "+sc.getTranslation(), 20, ytranslation);
			
			g.setFont(new Font("Helvetica-Neue", Font.PLAIN, 16));
			g.drawString("Phonetic : "+sc.getPhonetic(), 20, yphonetic);
			
			g.setFont(new Font("Helvetica-Neue", Font.PLAIN, 12));
			g.drawString("New Words : "+sc.getNewWords(), 20, ynewword);
			
			yglobal+=heightComponent+space;
			
		}
		
		
	}

	public ArrayList<SearchComponent> getSearchComponent() {
		return components;
	}
	
	/**
	 * Initialize the text area using the resource configuration.
	 * @return the configured text area
	 */
	private PinyinInputTextArea initConfig() {
		PinyinInputTextArea textArea = this.buildTextArea();
		
		Properties props = new Properties();
		try {
			props.load(PinyinInputAppConfig.class.getResourceAsStream(PROPERTIES_RESOURCE));
		} catch(IOException ioe) {
			// couldn't read props, will just resort to defaults.
			// not necessarily wrong, since the properties are optional
		}
		
		// use the properties to initialize a configuration
		PinyinInputConfig config = new PinyinInputAppConfig(props);
		
		// obtain the InputContext from the text area, and use
		// its control object in the configuration.
		InputContext inputContext = textArea.getInputContext();
		PinyinInputMethodControl control = (PinyinInputMethodControl)inputContext.getInputMethodControlObject();
		control.setCharacterMode(config.getCharacterMode());
		control.setChooserOrientation(config.getChooserOrientation());
		control.setUsingRawWindow(config.getRawMode());
		

		Font currentFont = textArea.getFont();
		Font font = config.getFont(currentFont);
		// check if the font is one that exists on the system already.
		// if not, then we store it as an instance variable so we
		// can re-present it as an option later on the font chooser.
		boolean fontBundled = true;
		Font[] systemFonts = JFontChooser.getSystemFonts(font.getStyle(), font.getSize());
		for(Font systemFont : systemFonts) {
			if(font.getFamily().equals(systemFont.getFamily())) {
				fontBundled = false;
				break;
			}
		}
		if(fontBundled) {
			this.bundledFont = font;
		}
	
		textArea.setFont(font);
		control.setFont(font);
		
		return textArea;
	}
	
	/**
	 * @return build the JTextArea component in which text is composed
	 */
	private PinyinInputTextArea buildTextArea() {
		PinyinInputTextArea textArea = new PinyinInputTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		return textArea;
	}
	
}
