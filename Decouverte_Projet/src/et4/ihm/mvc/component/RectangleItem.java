package et4.ihm.mvc.component;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;


public class RectangleItem extends Forme{
	
	public Rectangle r;
	
	public RectangleItem()
	{
		super(new Point(0,0),Color.red,Color.black);
		r = new Rectangle();
	}
	
	public RectangleItem(Rectangle r, Color background, Color border)
	{
		super(new Point(r.x, r.y),background,border);
		this.r = r;
	}

	public Rectangle getR() {
		return r;
	}
	
	

}

