
import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tunhnt
 */
public class Pratic extends Applet implements KeyListener, Runnable{
    
    List<Shot> shots;
    List<Alien> aliens;
    int level;
    boolean gameOver, shipActive;
    Image img;
    Graphics g;
    final double WIDTH = 50, HEIGHT = 50;
    final int SCREENWIDTH = 1000, SCREENHEIGHT = 650;
    final double GRAVITY = 0.96;
    final double SPEED = 0.3;
    boolean upAccel, downAccel, leftAccel, rightAccel;
    Thread t;
    double x, y, xVelocity, yVelocity, x2, y2, angle;
    final double xOrig2 = 30, yOrig2 = 0;
     
    
    public void init() {
        gameOver = false;
        shipActive = false;
        shots = new ArrayList<Shot>();
        aliens = new ArrayList<Alien>();
        level = 1;
        this.addRandomAliens(level);
        angle = 0;
        upAccel = false;
        downAccel = false;
        leftAccel = false;
        rightAccel = false;
        xVelocity = 0;
        yVelocity = 0;
        x = 200;
        y= 200;
        x2 = x + 45;
        y2 = y + 15;
        this.resize(SCREENWIDTH, SCREENHEIGHT);
        img = createImage(1000, 650);
        g = img.getGraphics();
        this.addKeyListener(this);
        t = new Thread(this);
        t.start();
    }
    
    public void update(Graphics g){
        paint(g);
    }
       
    
    public void paint(Graphics gfx){
        
        g.setColor(Color.black);
	g.fillRect(0, 0, 1000, 750);
	g.setColor(Color.orange);
	g.drawString("Level: " + level, 20, 20);
        
                if (gameOver) {
			g.setColor(Color.cyan);
			g.drawString("GAME OVER", 200, 200);
		} else if (shipActive) {
			g.setColor(Color.cyan);
			g.fillOval((int) x, (int) y, (int) WIDTH, (int) HEIGHT);
			updateAngle();
			g.drawLine((int) (x + WIDTH / 2), (int) (y + HEIGHT / 2), (int) x2, (int) y2);
			for (Shot s : shots)
				s.draw(g);
			for (Alien a : aliens)
				a.draw(g);
		} else {
			g.setColor(Color.gray);
			g.fillOval((int) x, (int) y, (int) WIDTH, (int) HEIGHT);
			updateAngle();
			g.drawLine((int) (x + WIDTH / 2), (int) (y + HEIGHT / 2), (int) x2, (int) y2);
			for (Shot s : shots)
				s.draw(g);
			for (Alien a : aliens)
				a.draw(g);
		}
		gfx.drawImage(img, 0, 0, this);
    }
    
    public void updateAngle() {
		if (angle >= (360)) // keep angle between 0 and 359
			angle -= 360;
		else if (angle < 0)
			angle += 360;

		if (angle == 0) {
			x2 = WIDTH + WIDTH / 2 + x;
			y2 = HEIGHT / 2 + y;
		} else if (angle == 45) {
			x2 = WIDTH / 2 + x + 35;
			y2 = HEIGHT / 2 + y - 35;
		} else if (angle == 90) {
			x2 = WIDTH / 2 + x;
			y2 = y - HEIGHT / 2;
		} else if (angle == 135) {
			x2 = WIDTH / 2 + x - 35;
			y2 = HEIGHT / 2 + y - 35;
		} else if (angle == 180) {
			x2 = x - WIDTH / 2;
			y2 = HEIGHT / 2 + y;
		} else if (angle == 225) {
			x2 = WIDTH / 2 + x - 35;
			y2 = HEIGHT / 2 + y + 35;
		} else if (angle == 270) {
			x2 = x + WIDTH / 2;
			y2 = y + HEIGHT + HEIGHT / 2;
		} else if (angle == 315) {
			x2 = WIDTH / 2 + x + 35;
			y2 = HEIGHT / 2 + y + 35;
		}
	}
    

   


    @Override
    public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DOWN && shipActive) {
			downAccel = true;
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT && shipActive) {
			rightAccel = true;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT && shipActive) {
			leftAccel = true;
		} else if (e.getKeyCode() == KeyEvent.VK_UP && shipActive) {
			upAccel = true;
		} else if (e.getKeyCode() == KeyEvent.VK_A && shipActive) {
			angle += 45;
		} else if (e.getKeyCode() == KeyEvent.VK_F && shipActive) {
			angle -= 45;
		} else if (e.getKeyCode() == KeyEvent.VK_SPACE && shipActive) {
			if (shots.size() < 6) {
				Shot s = new Shot(x2, y2, angle, xVelocity, yVelocity);
				shots.add(s);
			}
		} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			shipActive = true;
		}
       
    }
    
    public void run(){
        for(;;){
                        if (!leftAccel && !rightAccel)
				xVelocity *= GRAVITY;
			if (!upAccel && !downAccel)
				yVelocity *= GRAVITY;
			if (leftAccel)
				xVelocity -= SPEED;
			if (rightAccel)
				xVelocity += SPEED;
			if (upAccel)
				yVelocity -= SPEED;
			if (downAccel)
				yVelocity += SPEED;

			x += xVelocity;
			y += yVelocity;
                        x2 += xVelocity;
			y2 += yVelocity;

			
			for(int i = 0; i < shots.size(); i++){
                            if(shots.get(i).getLifeLeft() <= 0){
                                shots.remove(i);
                            }else{
                                shots.get(i).move();
                            }       
                        }
                        
                        for(Alien a : aliens){
                            a.move();
                        }
                        
                        checkOnEdge();
                        updateAlienBattle();
			repaint();
                        
                        try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
                           
			}
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			downAccel = false;
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			rightAccel = false;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			leftAccel = false;
		} else if (e.getKeyCode() == KeyEvent.VK_UP) {
			upAccel = false;
		}

    }
    
    public void checkOnEdge() {
		if (x >= SCREENWIDTH - 5) {
			x -= (SCREENWIDTH + 50);
			x2 -= (SCREENWIDTH + 50);
		} else if (x <= -45) {
			x += SCREENWIDTH + 50;
			x2 += SCREENWIDTH + 50;
		}
		if (y <= -45) {
			y += 690;
			y2 += 690;
		} else if (y >= 645) {
			y -= 645 + 45;
			y2 -= 645 + 45;
		}
    }
    
    private void deleteShot(int i) {
	shots.remove(i);
    }

    private void deleteAlien(int i) {
       	aliens.remove(i);
    }
    
    private void updateAlienBattle() {
		List<Integer> alIndexs = new ArrayList<Integer>();
		List<Integer> sIndexs = new ArrayList<Integer>();
		for (int i = 0; i < aliens.size(); i++) {
			for (int k = 0; k < shots.size(); k++) {
				if (aliens.get(i).shotCollision(shots.get(k))) {
					alIndexs.add(i);
					sIndexs.add(k);
				}
			}
		}
		for (Alien a : aliens)
			if (a.shipCollision((int) (x + 25), (int) (y + 25)) && shipActive) {
				gameOver = true;
			}

		for (int i : alIndexs)
			aliens.remove(i);
		for (int i : sIndexs)
			shots.remove(i);
		if (aliens.size() == 0) {
			level++;
			shipActive = false;
			this.addRandomAliens(level);
		}
	}
    
    public void addRandomAliens(int num) {
		for (int i = 0; i < num; i++) {
			int randX = (int) (Math.random() * 1000);
			int randY = (int) (Math.random() * 650);
			aliens.add(new Alien(randX, randY));
		}
	}

   
    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
}

