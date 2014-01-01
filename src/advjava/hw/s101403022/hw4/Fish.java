/**
 * Advance JAVA Assignment
 * 
 * Student Name : 蔡崴丞
 * Student No.  : 101403022
 * Class : Information Management - 2A
 * 
 * Filename : Fish.java
 * 
 * 這是魚
 */
package advjava.hw.s101403022.hw4;

import java.awt.Point;

import javax.swing.JPanel;

public class Fish extends Element {
	static int count = 0;
	private int type;
	public Fish(JPanel _display, Point _loc) { super(_display, _loc); }
	
	@Override protected void addCount() { count++; }
	@Override protected void removeCount() { count--; }
	
	@Override
	protected void childInit() {
		this.type = rnd.nextInt(3);
		left = getScaledIcon(Integer.toString(type * 2 + 2));
		right = getScaledIcon(Integer.toString(type * 2 + 1));
	}
	
	@Override
	protected void genSpeed() {
		speedX = rnd.nextInt(10);
		speedX = speedX < 5 ? speedX - 5 : speedX - 4; // +- 1~5
		speedY = Math.abs(speedX);
		speedY = rnd.nextInt(2 * speedY + 1) - speedY; // 0  ~  +- speedX
	}
}
