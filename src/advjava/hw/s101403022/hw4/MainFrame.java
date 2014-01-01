/**
 * Advance JAVA Assignment
 * 
 * Student Name : 蔡崴丞
 * Student No.  : 101403022
 * Class : Information Management - 2A
 * 
 * Filename : MainFrame.java
 * 
 * 主視窗
 */
package advjava.hw.s101403022.hw4;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class MainFrame extends JFrame {
	int act;
	JButton addFish, addTurtle, removeSelected, removeAll;
	JLabel action, fishCount, turtleCount;
	JPanel display;
	public MainFrame() {
		super("Aquarium");
		
		this.act = 0;
		
		// GridBagLayout
		setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		
		// ================    control buttons    ================
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1.0;
		addFish = new JButton("新增魚");
		addFish.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				act = 0;
				action.setText("目前功能 : 新增魚");
			}
		});
		add(addFish, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		addTurtle = new JButton("新增烏龜");
		addTurtle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				act = 1;
				action.setText("目前功能 : 新增烏龜");
			}
		});
		add(addTurtle, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 0;
		removeSelected = new JButton("移除選取");
		removeSelected.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				act = 2;
				action.setText("目前功能 : 移除選取");
			}
		});
		add(removeSelected, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 1;
		removeAll = new JButton("移除全部");
		removeAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				for (Component ele : display.getComponents()) {
					if (ele instanceof Element) ((Element)ele).remove();
				}
				display.repaint();
				updateCount();
			}
		});
		add(removeAll, c);
		
		// ================     status panel      ================
		JPanel statusPanel = new JPanel();
		statusPanel.setLayout(new GridLayout(0, 3));
		statusPanel.setBackground(Color.DARK_GRAY);
		
		action = new JLabel("目前功能 : 新增魚");
		action.setForeground(Color.WHITE);
		statusPanel.add(action);
		
		fishCount = new JLabel("魚總數 : 0");
		fishCount.setForeground(Color.WHITE);
		statusPanel.add(fishCount);
		
		turtleCount = new JLabel("烏龜總數 : 0");
		turtleCount.setForeground(Color.WHITE);
		statusPanel.add(turtleCount);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		c.weightx = 0.0;
		add(statusPanel, c);
		
		// ================     main display      ================
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 3;
		c.weighty = 1.0;
		display = new JPanel();
		display.setBackground(new Color(0, 192, 255));
		display.setLayout(null);
		display.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				if (event.getButton() == MouseEvent.BUTTON1) {
					Element ele = null;
					switch(act) {
					case 0: // add fish
						ele = new Fish(display, event.getPoint());
						break;
					case 1: // add turtle
						ele = new Turtle(display, event.getPoint());
						break;
					}
					
					if (ele != null) {
						ele.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent event) {
								if (act == 2) {
									((Element)event.getSource()).remove();
									display.repaint();
									updateCount();
								}
							}
						});
						display.add(ele);
						updateCount();
					}
				}
			}
		});
		add(display, c);
	}
	
	public void updateCount() {
		fishCount.setText("魚總數 : " + Fish.count);
		turtleCount.setText("烏龜總數 : " + Turtle.count);
	}
}
