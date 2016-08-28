package Touhou;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class Star {
	private int x;
	private int y;
	private int z;
	public int dx = 0;
	public int dy = 1;
	private Image star;
	public Star(){
		x = (int)(Math.random()*800);
		y = (int)(Math.random()*800);
		z = (int)(Math.random()*10)+1;
	    star = Toolkit.getDefaultToolkit().getImage("star.png");
	}
	public void draw(Graphics g){
		y += dy *z;
		x += dx *z;
		g.setColor(Color.WHITE);
		g.fillRect(x, y, z/5, z/5);
		g.drawImage(star, x, y, null);
		
		placeInBounds();
		
	}
	private void placeInBounds() {
		if(y > 800)
			y = 0;
		if(y < 0)
			y = 800;
		if(x < 0)
			x = 800;
		if(x > 800)
			x = 0;
	}
}