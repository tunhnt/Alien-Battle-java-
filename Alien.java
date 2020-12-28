
import java.awt.Color;
import java.awt.Graphics;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author win8_
 */
public class Alien {
    double x, y, xVel, yVel;

    public Alien(double x, double y) {
        this.x = x;
        this.y = y;
        double randSpeed = (int) (Math.random() * 8 + 1);
        double randAngle = Math.random() * Math.PI * 2;
        xVel = randSpeed * Math.cos(randAngle);
        yVel = randSpeed * Math.sin(randAngle);
    }
    
    public void move(){
        x += xVel;
        y += yVel;
        
        if (x < -1) {
            x += 1001;
        }
        else if (x > 1001) {
            x -= 1001;
        }
        if (y < -1) {
            y += 651;
        }
        else if (y > 651) {
            y -= 651;
        }
    }
    
    public void draw(Graphics g) {
	g.setColor(Color.orange);
	g.drawOval((int) (x) - 17, (int) (y) - 15, 34, 30);
	g.drawLine((int) (x), (int) (y + 15), (int) (x), (int) (y + 25)); // center leg
	g.drawLine((int) (x - 10), (int) (y + 12), (int) (x - 12), (int) (y + 23)); // left leg
	g.drawLine((int) (x + 10), (int) (y + 12), (int) (x + 12), (int) (y + 24)); // right leg
    }
    
    public boolean shotCollision(Shot shot) {
		// Same idea as shipCollision, but using shotRadius = 0
		if (Math.pow(17, 2) > Math.pow(shot.getX() - x, 2) + Math.pow(shot.getY() - y, 2))
			return true;
		return false;
    }
    
    // shipX and shipY are center of ship
	public boolean shipCollision(int shipX, int shipY) {
		// Same idea as shipCollision, but using shotRadius = 0
		if (Math.pow(40, 2) > Math.pow((shipX) - x, 2) + Math.pow((shipY) - y, 2))
			return true;
		return false;
	}
    
    
    
}
