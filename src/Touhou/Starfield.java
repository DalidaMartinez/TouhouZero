package Touhou;

import java.awt.Graphics;


public class Starfield {
	   private Star[] stars = new Star [1000];

	   public Starfield()
	   {
	      for (int i = 0; i < stars.length; i++)
	         stars[i] = new Star();
	   }

	   public void draw (Graphics g)
	   {
	      for (int i = 0; i < stars.length; i++)
	         stars[i].draw(g);
	   }
	   
	   public void setAsMenu(){
		   for(Star star: stars){
			   star.dx = -1;
			   star.dy = -1;
		   }
	   }
	}


