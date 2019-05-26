package com.njupt.tigerdog.seizeTigerChessGame;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class BackgroundPanel extends JPanel {// 在组件中显示图片

	// 静态资源
	private static final String tigerURL = "src/com/njupt/tigerdog/image/tiger.PNG";
	private static final String dogURL = "src/com/njupt/tigerdog/image/dog.PNG";
	private static final String backgroundURL = "src/com/njupt/tigerdog/image/catchTiger.PNG";

	// 老虎和猎狗棋子的大小（单位是像素）
	private static final int tigerSize = 40;
	private static final int dogSize = 36;

	// 整个棋盘面板的宽和高
	private static final int width = 360;
	private static final int height = 480;

	// 背景图片变量
	private Image image;

	// 猎狗、老虎变量
	private TigerOrDog tiger;
	private TigerOrDog dogs;

	// 构造方法
	public BackgroundPanel() {
		setLayout(null);
		this.image = new ImageIcon(backgroundURL).getImage();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// 设置背景，全屏显示
		g.drawImage(image, 0, 0, getWidth(), getHeight(), 0, 0, image.getWidth(null), image.getHeight(null), null);

	}

	/**
	 * 返回首选尺寸
	 */
	@Override
	public Dimension getPreferredSize() {
		// 组件告诉用户他应该有多大，覆盖getPreferrSize方法，返回一个有首选高度和宽度的Dimension对象
		return new Dimension(width, height);
	}

	/**
	 * 添加组件
	 */
	public void addChessComponents() {

		// 根据棋盘添加棋子
		for (int row = 0; row < 7; ++row)
			for (int col = 0; col < 5; ++col) {
				// 添加老虎
				if (ChessBoard.getPointByIndex(row, col) == 1) {
					tiger = new TigerOrDog(tigerURL, row, col, tigerSize, tigerSize, 1);
					add(tiger.getLabelChessman());
				}
				// 添加猎狗
				else if (ChessBoard.getPointByIndex(row, col) == 2) {
					dogs = new TigerOrDog(dogURL, row, col, dogSize, dogSize, 2);
					add(dogs.getLabelChessman());
				}
			}
		add(new Buttons("使用说明", 0, 0, 90, 30).getButton());
		add(new Buttons("悔棋", 90, 0, 90, 30).getButton());
		add(new Buttons("重新开始", 180, 0, 90, 30).getButton());
		add(new Buttons("退出", 270, 0, 90, 30).getButton());
	}

}