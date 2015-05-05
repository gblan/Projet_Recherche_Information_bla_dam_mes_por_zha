/* Copyright (c) 2007 Jordan Kiang
 * jordan-at-kiang.org
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.kiang.chinese.pinyin.im.app.main;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.im.InputContext;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import org.kiang.chinese.pinyin.im.PinyinInputTermSource.PinyinInputMethodControl;
import org.kiang.chinese.pinyin.im.app.Messages;
import org.kiang.chinese.pinyin.im.app.PinyinInputConfig;
import org.kiang.chinese.pinyin.im.swing.PinyinInputTextArea;
import org.kiang.swing.JFontChooser;


/**
 * A simple Java Swing text editor hard wired with a Pinyin input method.
 * Runnable through its main method.  Reads configuration properties through
 * a bundled resource if available, otherwise uses defaults.  Can read/write
 * files in a couple of character encodings.
 * 
 * @author Jordan Kiang
 */
public class PinyinInput extends JFrame {
	
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

	// the text area
	private PinyinInputTextArea textArea;
	
	// a Font bundled as a resource, if available
	private Font bundledFont;
	

	
	/**
	 * @param title title bar String
	 */
	public PinyinInput(String title) {
		super(title);

		this.textArea = this.initConfig();
		
		// put the text area into a 
		JScrollPane scrollPane = new JScrollPane(this.textArea);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(600, 300));
		
		this.getContentPane().add(scrollPane);
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

    /**
     * Main method runs the program.
     * @param args
     */
    static public void main(String[] args) {
    	PinyinInput input = new PinyinInput(Messages.Key.TITLE.getText());
    	
    	input.pack();
    	input.setVisible(true);
    }
}
