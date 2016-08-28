package Touhou;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.LinkedList;
import java.util.Random;

public class Boss {

	int x = 0;
	int y = 0;
	int height = 0;
	int width = 50;





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
		this.y  = y;
	}

	public Animation getAnimation() {
		return animation;
	}

	public void setAnimation(Animation animation) {
		this.animation = animation;
	}

	Animation animation;

	public Boss() {
		animation = new Animation("sprites/boss/mothership", 10, 10);
		setX(300);
		setY(0);

	}

	public void draw(Graphics g) {

		g.drawImage(animation.getImage(), getX(), getY(), null);

	}

	public boolean hitBy(PlayerBullet bullet) {
		int x1min = x;
		int y1min = y;
		int x1max = x + width;
		int y1max = y + height;

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
