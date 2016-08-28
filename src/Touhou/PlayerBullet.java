package Touhou;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class PlayerBullet {
		//bullet types
		public static final int FIRST = 1;
		public static final int SECOND = 2;
		public static final int THIRD = 3;
		
		private int x;
		private int y;
		private int type;
		private int height = 5;
		private int width = 5;
		
		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
		}

		public int getWidth() {
			return width;
		}

		public void setWidth(int width) {
			this.width = width;
		}

		Image img;
		
		public PlayerBullet(int x, int y, int type){
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
			y-=10;	
		}
		
		public void draw(Graphics g ){
			if(type == FIRST){
				img = Toolkit.getDefaultToolkit().getImage("sprites/bullets/userBullet.png");
			}
			
			if(type == SECOND){
				img = Toolkit.getDefaultToolkit().getImage("sprites/bullets/rocket.png");
			}
			
			if(type == THIRD){
				img = Toolkit.getDefaultToolkit().getImage("sprites/bullets/greenTT.png");
			}
			g.drawImage(img, x, y, null);
		}
		public boolean collidingWithEnemy(EnemyShip enemy){
			int x1min = getX();
			int y1min = getY();
			int x1max = x1min + this.width;
			int y1max = y1min + this.height;

			int x2min = enemy.getX();
			int y2min = enemy.getY();
			int x2max = x2min + enemy.getHeight();
			int y2max = y2min + enemy.getWidth();

			return	(x1min <= x2max) &&
					(x1max >= x2min) &&
					(y1min <= y2max) &&
					(y1max >= y2min);
		}
}
