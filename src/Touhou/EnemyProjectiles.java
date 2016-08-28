package Touhou;

import java.awt.Graphics;
import java.util.LinkedList;

public class EnemyProjectiles {

	private EnemyBullet[] bullets = new EnemyBullet[1000];
	private int bulletIndex = 0;
	
	public void update(){
		for(EnemyBullet bullet: bullets)
			if(bullet != null)
				bullet.update();
	}

	public void draw(Graphics g){
		for(EnemyBullet bullet: bullets)
			if(bullet != null && bullet.getY() < 900) 
				bullet.draw(g);
	}
	public void addBullet(EnemyBullet bull){
		bullets[bulletIndex] = bull;
		bulletIndex++;
		if(bulletIndex >= bullets.length)
			bulletIndex = 0;
	}
	public EnemyBullet[] getProjectiles(){
		return bullets;
	}
	public void setProjectiles(EnemyBullet[] projectiles){
		bullets = projectiles;
	}

}
