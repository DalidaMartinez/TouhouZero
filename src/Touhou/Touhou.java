package Touhou;
//----------------------------------------------------------------------------//
// The main class of the Asteroids Game.                                      //
// Instantiates the game's objects and orients them properly.                 //
// Sets up a seperate thread for the game's main loop.                        //
// Handles keyboard input.                                                    //
//----------------------------------------------------------------------------//
// Copyright 2004 - Brian Murphy                                              //
//----------------------------------------------------------------------------//

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

public class Touhou extends Applet implements KeyListener, MouseListener
{

	// Initialize the on screen game objects
	UserShip userShip = new UserShip();
	EnemyShip[] enemies = new EnemyShip[30];
	Boss boss = new Boss();
	int enemyIndex = 0;
	Starfield stars = new Starfield();
	Starfield menuStars = new Starfield();
	PlayerProjectiles userProj  = new PlayerProjectiles();
	EnemyProjectiles enemProj = new EnemyProjectiles();
	BossProjectiles bossProj = new BossProjectiles();
	
	//end game
	Animation fireAnim = new Animation("sprites/fire/fire", 5, 5);
	int[] fireX;
	int[] fireY;
	
	int projType = 1;

	//Different rates of fire, spawning
	int enemyROF = 10;

	//Are we drawing the menu right now?
	boolean menu = true;
	Image menuShip = Toolkit.getDefaultToolkit().getImage("sprites/menu/ship.png");
	Image start1 = Toolkit.getDefaultToolkit().getImage("sprites/menu/start1.png");
	Image start2 = Toolkit.getDefaultToolkit().getImage("sprites/menu/start2.png");
	Image start = start1;

	// input
	boolean left      = false;
	boolean right     = false;
	boolean forward = false;
	boolean reverse = false;
	boolean fire      = false;

	// Make the thread for the main loop accessable to multiple methods
	Thread game;

	//double buffering
	Graphics buffer;
	Image offscreen;
	// Number of miliseconds between frames of the animation
	private static final int waittime = 30;
	int timer = 0;
	int score = 0;
	int health = 100;
	int bossHealth = 1000;
	//-------------------------------------------------------------------------//

	public void init (){
		setLayout (null);
		resize(800,800);
		setBackground(Color.black);
		offscreen = createImage(getSize().width, getSize().height);
		buffer = offscreen.getGraphics();

		//Modify the menu starfield before drawing
		menuStars.setAsMenu();

		// Aquire the focus for the Applet so keypresses are accepted
		requestFocus();
		// Attach the KeyListener to the Applet in order to monitor keypresses
		addKeyListener(this);
		addMouseListener(this);
		game = new Thread(){
			public void run()
			{
				fireX = new int[30];
				fireY = new int[30];
				for(int c = 0; c < 30; c++){
					fireX[c] = (int)(Math.random()*350) + 200;
					fireY[c] = (int)(Math.random()*250) + 250;
				}
				while (true)
				{
					applyPlayerInput();
					moveObjects();
					handleCollisions();
					if(timer % enemyROF == 0)
						enemyShooting();
					if(timer % 30 == 0){
						spawnEnemy();
					}
					try
					{
						game.sleep(waittime);
					}
					catch (InterruptedException ie)
					{
					}
					timer++;
					repaint();
				}
			}

		};
		game.start();
	}
	//-------------------------------------------------------------------------//

	public boolean isFocusTraversable()
	{
		return true;
	}
	
	int time = 0;
	private void spawnEnemy() {
		int type = ((int)(Math.random()*4)) + 1;
		enemies[enemyIndex] = new EnemyShip(type);
		enemyIndex++;
		if(enemyIndex >= enemies.length)
			enemyIndex = 0;
	}
	private void applyPlayerInput()
	{
		if (left)
			userShip.moveLeft();
		if (right)
			userShip.moveRight();
		if (reverse)
			userShip.reverse();
		if (forward)
			userShip.moveForward();
		if(fire)
			playerShooting();
	}

	//move 
	private void moveObjects()
	{
		userProj.update();
		enemProj.update();
		bossProj.update();
		for(EnemyShip enem: enemies){
			if(enem != null)
				enem.move();
		}

	}
	//shooting happens here
	private void playerShooting()
	{
		if(timer % (projType*10) == 0)
			userProj.addBullet(new PlayerBullet(userShip.getX()+35, userShip.getY(), projType));
	}
	public void enemyShooting(){
		for(EnemyShip enemy: enemies){
			if(enemy != null){
				int x = enemy.getX();
				int y = enemy.getY();
				int type = enemy.getType();
				System.out.println(x + " " + y + " " + type);
				enemProj.addBullet(new EnemyBullet(x+5, y+5, type));
			}
		}
		bossProj.addBullet(boss.getX(), boss.getY());
	}

	public void handleCollisions()
	{
		EnemyBullet[] bullets = enemProj.getProjectiles();
		for(int x = 0; x < bullets.length; x++){
			EnemyBullet bullet = bullets[x];
			if(bullet != null && userShip.hitBy(bullet)){
				System.out.println("Player hit");
				health -= 10;
				bullets[x] = null;
			}
		}
		enemProj.setProjectiles(bullets);
		
		BossBullet[] boss_bullets = bossProj.getProjectiles();
		for(int x = 0; x < boss_bullets.length; x++){
			BossBullet boss_bullet = boss_bullets[x];
			if(boss_bullet != null && userShip.hitBy(boss_bullet)){
				System.out.println("Player hit");
				health -= 10;
				boss_bullets[x] = null;
			}
		}
		bossProj.setProjectiles(boss_bullets);

		PlayerBullet[] userBullets = userProj.getProjectiles();
		for(int x = 0; x < userBullets.length; x++){
			PlayerBullet bullet = userBullets[x];
			for(int c = 0; c < enemies.length; c++){
				EnemyShip enemy = enemies[c];
				if(bullet != null && enemy != null && enemy.hitBy(bullet)){
					System.out.println("Enemy Hit");
					score += enemies[c].getType()*100;
					
					userBullets[x] = null;
					enemies[c] = null;
				}
				if(bullet!= null && boss.hitBy(bullet))
					bossHealth--;
					
			}
		}

	}

	//-------------------------------------------------------------------------//
	// The job of the KeyListener which runs in the GUI thread of the A.W.T.   //
	// is to record the state of the V and B keys which are used to move the   //
	// paddle left and right respectively.  That state is stored in the        //
	// paddleleft and paddleright variables which are then read each time the  //
	// main loop iterates.  While this is not exactly asynchronous input, it   //
	// servers as a close enough approximation for Breakout.                   //
	//-------------------------------------------------------------------------//
	public static int VK_LEFT    = KeyEvent.VK_LEFT;
	public static int VK_RIGHT   = KeyEvent.VK_RIGHT;
	public static int VK_UP      = KeyEvent.VK_UP;
	public static int VK_SPACE = KeyEvent.VK_SPACE;
	public static int VK_DOWN    = KeyEvent.VK_DOWN;
	//-------------------------------------------------------------------------//
	// Make keyReleased info available to main loop thread.                    //
	//-------------------------------------------------------------------------//
	@Override
	public void keyReleased (KeyEvent e)
	{
		int keycode = e.getKeyCode();
		if (keycode == VK_LEFT)
			left = false;
		if (keycode == VK_RIGHT)
			right = false;
		if (keycode == VK_UP)
			forward = false;
		if (keycode == VK_DOWN)
			reverse = false;
		if (keycode == VK_SPACE)
		{
			fire    = false;
		}
	}

	//-------------------------------------------------------------------------//
	// Make keyPressed info available to main loop thread.                     //
	//-------------------------------------------------------------------------//
	@Override
	public void keyPressed (KeyEvent e)
	{
		int keycode = e.getKeyCode();

		if (keycode == VK_LEFT)     
			left = true;
		if (keycode == VK_RIGHT)
			right = true;
		if (keycode == VK_UP)
			forward = true;
		if (keycode == VK_DOWN)
			reverse = true;
		if (keycode == VK_SPACE)
		{
			fire    = true;
		}
	}

	//-------------------------------------------------------------------------//
	// keyTyped not needed, but defined to satisfy KeyListener interface.      //
	//-------------------------------------------------------------------------//
	@Override
	public void keyTyped (KeyEvent e) {  }
	//-------------------------------------------------------------------------//
	// ALL DRAWING GOES HERE      //
	//-------------------------------------------------------------------------//
	public void paint(Graphics g){
		buffer.clearRect(0, 0, getSize().width, getSize().height);
		if(menu){
			menuStars.draw(buffer);
			buffer.drawImage(menuShip, 75, 200,null);
			buffer.drawImage(start, 100, 500, null);
		}else if(health >0){
			stars.draw(buffer);
			userShip.draw(buffer);
			for(EnemyShip enemy: enemies)
				if(enemy != null)
					enemy.draw(buffer);
			userProj.draw(buffer);
			enemProj.draw(buffer);
			buffer.drawString("Score: " + score, 50, 50);
			buffer.drawString("Health: " + health, 50, 75);
			buffer.drawString("Boss Health:" + bossHealth, 50, 100);
			boss.draw(buffer);
			bossProj.draw(buffer);
		}else{
			menuStars.draw(buffer);
			buffer.drawImage(menuShip, 75, 200,null);
			for(int c = 0; c < 30; c++){
				buffer.drawImage(fireAnim.getImage(), fireX[c], fireY[c], null);
			}
			buffer.drawString("Failure", 50,50);
		}
		g.drawImage(offscreen, 0,0, this);
	}
	//allows for the double buffering
	public void update(Graphics g) { 
		paint(g);
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		menu = false;
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		start = start2;
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		start = start1;
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	} 
}
