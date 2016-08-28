package Touhou;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
public class BossBullet {

	    double angleY = 0;

		private int x,y;
		private int dx;
		private int dy = 10;
		private int type;
		Image img;

		
		public BossBullet(int x, int y, int type, int dx){
			this.x = x;
			this.y = y;
			this.type = type;
			this.dx = dx;
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
		
	/*	public void rotate(angle){
			 -Math.cos(angle);
	*/	

		public void update(){
			y+=  dy ;
			x+=  dx*2;
		}

		public void draw(Graphics g ){
			if(type == 1 ){
				img = Toolkit.getDefaultToolkit().getImage("sprites/bullets/greenx.png");
			}

			if(type == 2){
				img = Toolkit.getDefaultToolkit().getImage("sprites/bullets/blueBullet.png");
			}
			
			if(type == 3){
				img = Toolkit.getDefaultToolkit().getImage("sprites/bullets/yellowDot.png");
			}



			g.drawImage(img, x, y, null);

		
	}
	}


