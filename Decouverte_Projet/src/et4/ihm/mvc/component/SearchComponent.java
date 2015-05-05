package et4.ihm.mvc.component;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import et4.ihm.mvc.panel.UIColor;

public class SearchComponent extends Forme {

	private RectangleItem r;

	private String sentence = "";
	private String translation = "";
	private String phonetic = "";
	private String newWords = "";
	private int occurence;

	public SearchComponent(Point position, String sentence, String translation, String phonetic, String newWords,
			int occurence, int width, int height) {

		super(position, UIColor.APSHALT_LIGHT, UIColor.BLUE_LIGHT);

		this.sentence = sentence;
		this.translation = translation;
		this.phonetic = phonetic;
		this.newWords = newWords;
		this.occurence = occurence;

		r = new RectangleItem(new Rectangle(position, new Dimension(width, height)), getBackground(), getBorder());
	}

	public RectangleItem getR() {
		return r;
	}

	public String getSentence() {
		return sentence;
	}

	public String getNewWords() {
		return newWords;
	}

	public String getTranslation() {
		return translation;
	}

	public String getPhonetic() {
		return phonetic;
	}

	public int getOccurence() {
		return occurence;
	}
	

}
