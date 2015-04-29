package et4.ihm.mvc.component;

import java.awt.Color;
import java.awt.Point;

public class Forme {
	
	private Point position;
	private Color background,border;
	
	public Forme(Point position, Color background, Color border) {
		// TODO Auto-generated constructor stub
		this.position = position;
		this.background = background;
		this.border = border;
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public Color getBackground() {
		return background;
	}

	public void setBackground(Color background) {
		this.background = background;
	}

	public Color getBorder() {
		return border;
	}

	public void setBorder(Color border) {
		this.border = border;
	}

	

}
