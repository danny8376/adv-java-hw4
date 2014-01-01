/**
 * Advance JAVA Assignment
 * 
 * Student Name : 蔡崴丞
 * Student No.  : 101403022
 * Class : Information Management - 2A
 * 
 * Filename : Element.java
 * 
 * 畫面上會動的東西(?)的共通父類別
 */
package advjava.hw.s101403022.hw4;

import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import java.util.Random;
import java.util.TimerTask;
import java.util.Timer;

import javax.imageio.ImageIO;
import javax.swing.*;

abstract public class Element extends JLabel {
	protected int speedX, speedY;
	protected 	boolean alive;
	protected Icon left, right;
	protected double sizeRatio;
	protected Point loc;
	protected JPanel display;
	protected Thread proc;
	protected Timer rndTimer;
	protected Random rnd;
	public Element(JPanel _display, Point _loc) {
		super();
		
		this.display = _display;
		this.loc = _loc;
		this.alive = true;
		
		addCount();
		
		this.rndTimer = new Timer();
		this.rnd = new Random();
		this.speedX = this.speedY = 0;
		
		// common randoms
		this.sizeRatio = 0.6 + rnd.nextInt(9) * 0.05; // 0.6~1.0
		
		childInit();
		newSpeed();
		
		// main thread
		this.proc = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while(alive) {
						update();
						Thread.sleep(20); // 50 FPS?
					}
				} catch (InterruptedException e) {
				}
			}
		});
		
		// center with init pos
		loc.x -= getIcon().getIconWidth() / 2;
		loc.y -= getIcon().getIconHeight() / 2;
		
		// set label size
		setSize(getIcon().getIconWidth(), getIcon().getIconHeight());
		
		this.proc.start();
		
		scheduleRandomSpeed();
	}
	
	private void scheduleRandomSpeed() {
		int period = 2 + rnd.nextInt(4); // 2~5s
		this.rndTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				newSpeed();
				scheduleRandomSpeed();
			}
		}, period * 1000);
	}
	
	abstract protected void addCount();
	abstract protected void removeCount();
	
	// load image & something else
	abstract protected void childInit();
	
	public void remove() {
		this.display.remove(this);
		this.rndTimer.cancel();
		this.alive = false;
		removeCount();
	}
	
	protected Icon getScaledIcon(String fn) {
		try {
			Image img = ImageIO.read(getClass().getResource("images/" + fn + ".png"));
			return new ImageIcon(img.getScaledInstance((int)(img.getWidth(null) * this.sizeRatio),
														(int)(img.getHeight(null) * this.sizeRatio),
														Image.SCALE_SMOOTH));
		} catch (IOException e) {
			return null;
		}
	}
	
	protected void update() {
		int w = getIcon().getIconWidth(), h = getIcon().getIconHeight();
		if (loc.x < 0 || loc.x + w > display.getWidth()) {
			speedX = -speedX;
			// change picture
			updateIcon();
			// 強制驅離
			loc.x = loc.x < 0 ? 0 : (display.getWidth() - w);
		}
		if (loc.y < 0 || loc.y + h > display.getHeight()) {
			speedY = -speedY;
			// 強制驅離
			loc.y = loc.y < 0 ? 0 : (display.getHeight() - h);
		}
		loc.x += speedX;
		loc.y += speedY;
		
		setLocation(loc);
	}
	
	protected void newSpeed() {
		genSpeed();
		updateIcon();
	}
	
	protected void updateIcon() {
		setIcon(speedX > 0 ? right : left);
	}
	
	abstract protected void genSpeed();
}
