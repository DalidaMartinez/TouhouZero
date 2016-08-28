package Touhou;

import java.awt.Graphics;

public class BossProjectiles {
	//hardcoded
	int[] dxs = {-10,-9,-8,-7,-6,-5,-4,-3,-2,-1, 0,2,3,4,5,6,7,8,9, 10, 9, 8,7,6,5,4,3,2,1, 0};
	int dx_in = 0;
	private BossBullet[] bullets = new BossBullet[1000];
	private int bulletIndex = 0;
	
	public void update(){
		for(BossBullet bullet: bullets)
			if(bullet != null)
				bullet.update();
	}

	public void draw(Graphics g){
		for(BossBullet bullet: bullets)
			if(bullet != null && bullet.getY() < 900) 
				bullet.draw(g);
	}
	public void addBullet(int x, int y){
		BossBullet bull = new BossBullet(x, y, 1, dxs[dx_in]);
		dx_in++;
		if(dx_in >= dxs.length)
			dx_in = 0;
			
		bullets[bulletIndex] = bull;
		bulletIndex++;
		if(bulletIndex >= bullets.length)
			bulletIndex = 0;
	}
	public BossBullet[] getProjectiles(){
		return bullets;
	}
	public void setProjectiles(BossBullet[] projectiles){
		bullets = projectiles;
	}

}
