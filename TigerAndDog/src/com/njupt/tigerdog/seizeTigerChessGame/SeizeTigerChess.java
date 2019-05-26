package com.njupt.tigerdog.seizeTigerChessGame;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class SeizeTigerChess extends JFrame {
	private static final String backgroundURL = "src/com/njupt/tigerdog/image/catchTiger.PNG";
	BackgroundPanel backgroundPanel;

	/**
	 * 游戏界面设置
	 * 
	 * @param title界面标题
	 */
	public SeizeTigerChess(String title) {
		// 初始化游戏界面
		initicalChessGrame(title);

		// 为游戏界面添加背景面板
		backgroundPanel = new BackgroundPanel();
		// 方法返回这个JFrame的内容窗口对象，在内容窗口中添加所要设置的背景图片
		setContentPane(backgroundPanel);

		// 初始化棋盘，该棋盘的所有方法和域都设置为静态，因为只有一个棋盘
		new ChessBoard(backgroundPanel);

		// 为面板添加各个组件
		// 由于在面板上的棋子，是根据棋盘上的点来添加的（是1，则为虎，是2，则为猎狗），所以必须要先初始化棋盘
		backgroundPanel.addChessComponents();

		// 按照组件的首选大小，设置窗口大小
		pack();
		// 设置框架为可见，这天语句必须放在最后，否则运行时，界面会黑屏一下
		setVisible(true);
	}

	/**
	 * 开始前，游戏窗口的设置
	 */
	public void initicalChessGrame(String title) {
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(new ImageIcon(backgroundURL).getImage());
		setResizable(false);
	}
}