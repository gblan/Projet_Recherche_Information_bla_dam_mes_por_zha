package et4.ihm.mvc.component;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import et4.ihm.mvc.panel.UIColor;

public class KnowledgeComponent extends Forme{

	private RectangleItem r;
	
	private String knownword = "";
	private String translation = "";
	private String phonetic = "";
	private String rate = "";
	
	public KnowledgeComponent(Point position,String knownword, String translation, String phonetic, double d1 ,int width, int height) {
		
		super(position, UIColor.APSHALT_LIGHT, UIColor.BLUE_LIGHT);
		
		this.knownword = knownword;
		this.translation = translation;
		this.phonetic = phonetic;
		this.rate = String.valueOf(d1);
		
		r = new RectangleItem(new Rectangle(position,new Dimension(width,height)), getBackground(),getBorder());
	}

	public RectangleItem getR() {
		return r;
	}

	public String getKnownword() {
		return knownword;
	}

	public String getRate() {
		return rate;
	}

	public String getTranslation() {
		return translation;
	}

	public String getPhonetic() {
		return phonetic;
	}

	
}
