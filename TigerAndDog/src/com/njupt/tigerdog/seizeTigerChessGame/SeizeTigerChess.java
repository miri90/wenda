package com.njupt.tigerdog.seizeTigerChessGame;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class SeizeTigerChess extends JFrame {

	private static final String tigerURL = "src/com/njupt/tigerdog/image/tiger.PNG";
	private static final String dogURL = "src/com/njupt/tigerdog/image/dog.PNG";
	private static final String backgroundURL = "src/com/njupt/tigerdog/image/catchTiger.PNG";

	private static final int tigerSize = 40;
	private static final int dogSize = 36;

	TigerOrDog tiger;
	TigerOrDog dogs;

	public SeizeTigerChess(String title) {
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(new ImageIcon(backgroundURL).getImage());
		setResizable(false);
		readyGo();
		setVisible(true);
		pack();
	}

	public void readyGo() {
		// 将布局管理设置为空
		// setLayout(null);

		BackgroundPanel backgroundPanel = new BackgroundPanel(backgroundURL);
		setContentPane(backgroundPanel);// 方法返回这个JFrame的内容窗口对象，在内容窗口中添加所要设置的背景图片

		// 添加老虎和猎狗
		tiger = new TigerOrDog(tigerURL, 150, 270, tigerSize, tigerSize);
		backgroundPanel.add(tiger.getLabelChessman());
		dogs = new TigerOrDog(dogURL, 50 - 18, 170 - 18, dogSize, dogSize);
		backgroundPanel.add(dogs.getLabelChessman());

		// 添加按钮
		backgroundPanel.add(new Buttons("使用说明", 0, 0, 90, 30).getButton());
		backgroundPanel.add(new Buttons("悔棋", 90, 0, 90, 30).getButton());
		backgroundPanel.add(new Buttons("重新开始", 180, 0, 90, 30).getButton());
		backgroundPanel.add(new Buttons("退出", 270, 0, 90, 30).getButton());
	}
}