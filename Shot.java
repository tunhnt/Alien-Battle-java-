
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
public class Shot {
    final int SHOT_SPEED = 12;
    final int SHOT_LIFE = 25;
    double x, y, xVel, yVel;
    int lifeLeft;

    public Shot(double x, double y, double angle, double shipXVel, double shipYVel) {
        this.x = x;
        this.y = y;
        
        double radians = (2 * Math.PI)-(angle / 180.0)*Math.PI;
        xVel = shipXVel + Math.cos(radians) * SHOT_SPEED;
        yVel = shipYVel + Math.sin(radians) * SHOT_SPEED;
        lifeLeft = SHOT_LIFE;
    }
    
    public void move(){
        lifeLeft--;
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
    
    public void draw(Graphics g){
        g.setColor(Color.orange);
        g.fillOval((int)(x-2), (int)(y-2), 4, 4);
    }
    
    public int getLifeLeft(){
        return lifeLeft;
    }
    
    public double getX(){
        return x;
    } 
    
    public double getY(){
        return y; 
    }
}
