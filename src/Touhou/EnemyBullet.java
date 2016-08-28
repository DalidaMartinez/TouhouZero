package Touhou;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class EnemyBullet {



	private int x,y;
	private int dy = 10;
	private int type;
	Image img;


	public EnemyBullet(int x, int y, int type){
		this.x = x;
		this.y = y;
		this.type = type;


	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void update(){
		y+=dy;

	}

	public void draw(Graphics g ){
		if(type == 1 ){
			img = Toolkit.getDefaultToolkit().getImage("sprites/bullets/enemyBullet.png");
		}

		if(type == 2){
			img = Toolkit.getDefaultToolkit().getImage("sprites/bullets/blueBullet.png");
		}

		if(type == 3){
			img = Toolkit.getDefaultToolkit().getImage("sprites/bullets/yellowDot.png");
		}
		if(type == 4){
			img = Toolkit.getDefaultToolkit().getImage("sprites/bullets/greendot.png");
		}
		g.drawImage(img, x, y, null);
	}
	
}


