package Touhou;

import java.awt.Graphics;
import java.util.LinkedList;

public class PlayerProjectiles {

	private PlayerBullet[] bullets = new PlayerBullet[200];
	private int bulletIndex = 0;
	
	public void update(){
		for(PlayerBullet bullet: bullets)
			if(bullet != null)
				bullet.update();
	}

	public void draw(Graphics g){
		for(PlayerBullet bullet: bullets)
			if(bullet != null && bullet.getY() > 0) 
				bullet.draw(g);
	}
	public void addBullet(PlayerBullet bull ){
		bullets[bulletIndex] = bull;
		bulletIndex++;
		if(bulletIndex >= bullets.length)
			bulletIndex = 0;
	}
	public PlayerBullet[] getProjectiles(){
		return bullets;
	}
}

