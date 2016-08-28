package Touhou;

import java.awt.Image;
import java.awt.Toolkit;

public class Animation {
	
	Image[] image;
	
	int current = 0; 
	
	int duration;
	
	int countdown;
	
	
	
	public Animation(String name,int duration,  int n){
		
		this.duration = duration;
		
		countdown = duration; 
		
		image = new Image[n];
		
		for (int i = 0; i< n; i++)
			image[i] = Toolkit.getDefaultToolkit().getImage(name + "_"+ i + ".png");
	}
	
	public Image getStaticImage(){
		return image[0];	
	}
	
	public Image getImage(){
		
		countdown--;
		
		if(countdown == 0){
			countdown = duration;			

			current ++;	
		
			if(current == image.length)
				current = 0;
		
		}
		return image[current];
	}
}
