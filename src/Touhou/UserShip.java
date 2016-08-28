package Touhou;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class UserShip{
	int dx = 10;
	int dy = 10;
	int x = 0;
	int y = 0;
	int width = 40;
	int height = 40;
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getDx() {
		return dx;
	}
	public void setDx(int dx) {
		this.dx = dx;
	}
	public int getDy() {
		return dy;
	}
	public void setDy(int dy) {
		this.dy = dy;
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
	public Animation getAnimation() {
		return animation;
	}
	public void setAnimation(Animation animation) {
		this.animation = animation;
	}
	Animation animation;

	public UserShip(){
		animation = new Animation("sprites/usership/usership", 3, 7);
		setX(400);
		setY(600);
	}
	public void moveLeft() {
		setX(getX()-dx);
	}

	public void moveRight() {
		setX(getX()+dx);
	}

	public void moveForward() {
		setY(getY()-dy);
	}
	public void reverse(){
		setY(getY()+dy);
	}
	public void draw (Graphics g)
	{
		g.drawImage(animation.getImage(), getX(), getY(), null);
	}
	public boolean hitBy(EnemyBullet bullet) {
		int x1min = x;
		int y1min = y;
		int x1max = x + getWidth();
		int y1max = y + getHeight();

		int x2min = bullet.getX();
		int y2min = bullet.getY();
		int x2max = bullet.getX() + 5;
		int y2max = bullet.getY() + 5;

		return
				(
						(x1min <= x2max) &&
						(x1max >= x2min) &&
						(y1min <= y2max) &&
						(y1max >= y2min)
						);
	}
	public boolean hitBy(BossBullet bullet) {
		int x1min = x;
		int y1min = y;
		int x1max = x + getWidth();
		int y1max = y + getHeight();

		int x2min = bullet.getX();
		int y2min = bullet.getY();
		int x2max = bullet.getX() + 5;
		int y2max = bullet.getY() + 5;

		return
				(
						(x1min <= x2max) &&
						(x1max >= x2min) &&
						(y1min <= y2max) &&
						(y1max >= y2min)
						);
	}
}

