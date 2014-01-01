/**
 * Advance JAVA Assignment
 * 
 * Student Name : 蔡崴丞
 * Student No.  : 101403022
 * Class : Information Management - 2A
 * 
 * Filename : Turtle.java
 * 
 * 它是烏龜
 */
package advjava.hw.s101403022.hw4;

import java.awt.Point;

import javax.swing.JPanel;

public class Turtle extends Element {
	static int count = 0;
	public Turtle(JPanel _display, Point _loc) { super(_display, _loc); }
	
	@Override protected void addCount() { count++; }
	@Override protected void removeCount() { count--; }
	
	@Override
	protected void childInit() {
		left = getScaledIcon("w2");
		right = getScaledIcon("w");
	}
	
	@Override
	protected void update() {
		if (loc.y + getIcon().getIconHeight() == display.getHeight()) super.update();
		else {
			loc.y += 2;
			if (loc.y + getIcon().getIconHeight() >= display.getHeight()) {
				loc.y = display.getHeight() - getIcon().getIconHeight();
				newSpeed();
			}
			setLocation(loc);
		}
	}
	
	@Override
	protected void genSpeed() {
		speedX = rnd.nextInt(6);
		speedX = speedX < 3 ? speedX - 3 : speedX - 2;
	}
}
