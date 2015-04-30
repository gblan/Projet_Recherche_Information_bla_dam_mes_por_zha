package et4.ihm.mvc.panel.body;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.im.InputContext;
import java.io.IOException;
import java.util.Properties;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.kiang.chinese.pinyin.im.PinyinInputTermSource.PinyinInputMethodControl;
import org.kiang.chinese.pinyin.im.app.PinyinInputConfig;
import org.kiang.chinese.pinyin.im.app.main.PinyinInputAppConfig;
import org.kiang.chinese.pinyin.im.swing.PinyinInputTextArea;
import org.kiang.swing.JFontChooser;

import et4.ihm.mvc.Model;
import et4.ihm.mvc.View;
import et4.ihm.mvc.component.LearningSubmitBtn;
import et4.ihm.mvc.controller.LearningController;
import et4.ihm.mvc.panel.UIColor;

public class LearningPanel extends JPanel{

	private final String FONT = "Helvetica-Neue";
	private final int FONT_SIZE = 20;
	private Model model;
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
	public LearningPanel(Model model) {
		this.model = model;
		
		PinyinInputTextArea inputarea = initConfig(); inputarea.setBackground(UIColor.BLUE_DARK);
			inputarea.setForeground(Color.WHITE);
			inputarea.setFont(new Font(FONT, Font.PLAIN, FONT_SIZE));
			inputarea.setCaretColor(Color.WHITE);
		JLabel inputlabel = new JLabel("Input");
			inputlabel.setBackground(UIColor.BLUE_DARK); 
			inputlabel.setOpaque(true);
			inputlabel.setForeground(Color.WHITE);
			inputlabel.setFont(new Font(FONT, Font.PLAIN, FONT_SIZE));
			
		JPanel input = new JPanel();
		input.setLayout(new BorderLayout());
		
		input.add(inputlabel, BorderLayout.PAGE_START);
		input.add(inputarea, BorderLayout.CENTER);
		
		/*
		 * knowledgeBtn.add(Box.createHorizontalGlue());
		JLabel knowledge = new JLabel("Knowledge");
		knowledge.setFont(new Font(FONT, Font.PLAIN, FONT_SIZE));
		knowledge.setForeground(Color.WHITE);
		knowledgeBtn.add(knowledge);
		knowledgeBtn.add(Box.createHorizontalGlue());
		 */

		
		JTextArea outputarea = new JTextArea(); outputarea.setBackground(UIColor.BLUE_DARK);
			outputarea.setForeground(Color.WHITE);
			outputarea.setFont(new Font(FONT, Font.PLAIN, FONT_SIZE));
			outputarea.setEditable(false);
			outputarea.setCaretColor(Color.WHITE);
		JLabel outputlabel = new JLabel("Output"); 
			outputlabel.setBackground(UIColor.BLUE_DARK); 
			outputlabel.setOpaque(true);
			outputlabel.setForeground(Color.WHITE);
			outputlabel.setFont(new Font(FONT, Font.PLAIN, FONT_SIZE));
		
		JPanel output = new JPanel();
		output.setLayout(new BorderLayout());
		output.add(outputlabel, BorderLayout.PAGE_START);
		output.add(outputarea, BorderLayout.CENTER);
		
		LearningSubmitBtn btn = new LearningSubmitBtn("Submit");
		output.add(btn, BorderLayout.PAGE_END);
		
		setLayout(new GridLayout(2, 1));
		
		add(input);
		add(output);
		
		
		LearningController c = new LearningController(model, btn, inputarea, outputarea);
	}
	
	@Override
	public void paint(Graphics graphics) {
		
		super.paint(graphics);
		
		Graphics2D g = (Graphics2D) graphics;
		RenderingHints rh = new RenderingHints(
	             RenderingHints.KEY_TEXT_ANTIALIASING,
	             RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	    g.setRenderingHints(rh);
		
		
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
