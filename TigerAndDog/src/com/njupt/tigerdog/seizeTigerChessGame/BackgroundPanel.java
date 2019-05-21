package com.njupt.tigerdog.seizeTigerChessGame;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class BackgroundPanel extends JPanel {// 在组件中显示图片
	private static final int width = 360;
	private static final int height = 480;
	private Image image;

	public BackgroundPanel(String backgroundURL) {
		setLayout(null);
		this.image = new ImageIcon(backgroundURL).getImage();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// 设置背景，全屏显示
		g.drawImage(image, 0, 0, getWidth(), getHeight(), 0, 0, image.getWidth(null), image.getHeight(null), null);

	}

	@Override
	public Dimension getPreferredSize() {
		// 组件告诉用户他应该有多大，覆盖getPreferrSize方法，返回一个有首选高度和宽度的Dimension对象
		return new Dimension(width, height);
	}

}