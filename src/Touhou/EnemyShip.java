package Touhou;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class EnemyShip{
	//Movement types
	public static final int DIAG_LR = 1;
	public static final int DIAG_RL = 2;
	public static final int SIDE =3;
	public static final int WAVE = 4;
	private int height = 20;
	private int width = 20;
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


	private int movementType;
	private int x;
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
	public int getType(){
		return movementType;
	}


	private int y;
	private int vx = 5;
	private int vy = 5;
	Animation animation;

	int[] waveX;
	int[] waveY;
	int waveCount = 0;
	public EnemyShip(int move){
		movementType = move;
		System.out.println(movementType);
		//Start pos for wave
		if(movementType == this.DIAG_RL){
			y = -10;
			x = 0;
		}
		if(movementType == this.DIAG_LR){
			y = -10;
			x = 800;
		}
		if(movementType == this.WAVE){
			y = 0;
			x = (int)(Math.random()*800);
		}
		if(movementType == this.SIDE){
			if(Math.random() > .5){
				y = (int)(Math.random()*500);;
				x = -10;
				vx = 5;
			}
			else{
				y = (int)(Math.random()*800);;
				x = 900;
				vx = -5;
			}
		}

		String name = "sprites/enemy" + movementType + "/spaceship" + movementType;
		animation = new Animation(name, 3, 6);
		fillWave();
	}
	private void fillWave() {
		waveX = new int[50];
		waveY = new int[50];
		for(int c = 0; c < 25; c++)
			waveX [c] = c;
		for(int c = 25; c > 0; c--)
			waveX [50-c] = -c;
		for(int c = 0; c < 25; c++)
			waveY [c] = c;
		for(int c = 25; c > 0; c--){
			waveY [50-c] = c;
		}
		System.out.println(waveX.toString());
	}
	public void moveDiagonally() {
		x += vx;
		y += vy;
	}

	public void moveNegativeDiagonally() {
		x -= vx;
		y += vy;
	}
	public void moveSideways(){
		x += vx;
	}

	public void move() {
		if(movementType == DIAG_RL)
			moveDiagonally();
		if(movementType == DIAG_LR)
			moveNegativeDiagonally();
		if(movementType == SIDE)
			moveSideways();
		if(movementType == WAVE)
			moveWave();

		//place back on screen, flip direction if sidways
		if(y > 800){
			y = 0;
			vx = -vx;
		}
	}
	public void moveWave(){
		x += waveX[49-waveCount];
		y += vy;
				//waveY[waveCount];
		waveCount++;
		if(waveCount >= waveX.length){
			waveCount = 0;
		}
	}


	public void draw (Graphics g)
	{	
		g.drawImage(animation.getImage(), x, y, null);
		//g.fillRect(x, y, 20, 20);
	}
	public boolean hitBy(PlayerBullet bullet) {
		int x1min = x;
		int y1min = y;
		int x1max = x + getWidth();
		int y1max = y + getHeight();

		int x2min = bullet.getX();
		int y2min = bullet.getY();
		int x2max = bullet.getX() + 30;
		int y2max = bullet.getY() + 30;

		return
				(
						(x1min <= x2max) &&
						(x1max >= x2min) &&
						(y1min <= y2max) &&
						(y1max >= y2min)
						);
	}
}
