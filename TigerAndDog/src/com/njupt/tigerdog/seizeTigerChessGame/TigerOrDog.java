package com.njupt.tigerdog.seizeTigerChessGame;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class TigerOrDog {
	private static final int borderX = 50;
	private static final int borderY = 50;
	private static final double squareSize = 60;

	private int indexX;// 横坐标
	private int indexY;// 纵坐标

	private JLabel labelChessman;// 以标签的形式呈现棋子
	private ImageIcon lableImage;// 棋子图片

	private int signTigerOrDog;// 标识虎还是猎狗

	/**
	 * 
	 * @param imageURL棋子图片
	 * @param row棋子在棋盘上的行数----对应于面板上的纵坐标
	 * @param col棋子在棋盘上的列数----对应于面板上的横坐标
	 * @param width棋子的宽
	 * @param height棋子的高
	 */
	public TigerOrDog(String imageURL, int row, int col, int width, int height, int sign) {

		lableImage = new ImageIcon(imageURL);
		lableImage.setImage(lableImage.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
		labelChessman = new JLabel(lableImage);

		// 标志是虎还是猎狗，如果是虎则为1，如果是猎狗则为2，用于在棋盘数组中显示
		signTigerOrDog = sign;

		// 获取棋子在面板中心的坐标（棋盘上的列对应于面板上的横坐标，棋盘上的行对应于面板上的纵坐标）
		indexX = col * (int) squareSize + borderX;
		indexY = row * (int) squareSize + borderY;

		// 棋子在面板上的初始位置(设置的位置是棋子左上角的坐标，而不是中心点的坐标，因为组件在面板中的定位是组件左上角的定位，不是组件正中央的定位)
		labelChessman.setBounds(indexX - width / 2, indexY - height / 2, width, height);

		// 为每个棋子独自添加监听器
		labelChessman.addMouseListener(new MouseHandler());
	}

	/**
	 * 获取标签的宽
	 * 
	 * @return
	 */
	public int getWidth() {
		return labelChessman.getWidth();
	}

	/**
	 * 获取标签的高
	 * 
	 * @return
	 */
	public int getHeight() {
		return labelChessman.getHeight();
	}

	/**
	 * 获取标签
	 * 
	 * @return
	 */
	public JLabel getLabelChessman() {
		return labelChessman;
	}

	/**
	 * 监听器类
	 * 
	 * @author 10652
	 */
	class MouseHandler extends MouseAdapter {// 鼠标的按和点击、拖动、移动、释放

		// 这些变量可放在外部类中声明，否则会产生许多临时变量
		int preX;// 按鼠标时，鼠标在棋子上的坐标
		int preY;
		int pressX;// 按鼠标时，鼠标在面板上的坐标
		int pressY;
		int releaseX;// 释放鼠标时，鼠标在面板上的坐标
		int releaseY;
		int centerX;// 释放鼠标时，棋子中心在面板上的坐标
		int centerY;
		int countDrectionX;// 按鼠标和释放鼠标的单位方向差值
		int countDrectionY;

		@Override
		public void mousePressed(MouseEvent event) {// 鼠标按

			// 鼠标点击时，鼠标在棋子标签上的坐标（此时棋子的左上角为原点）
			preX = event.getX();
			preY = event.getY();

			// 鼠标点击时，鼠标位于面板上的坐标（此时整个面板的左上角为原点）
			pressX = indexX - getWidth() / 2 + preX;
			pressY = indexY - getHeight() / 2 + preY;
		}

		@Override
		public void mouseReleased(MouseEvent event) {// 鼠标释放
			// 鼠标释放时鼠标位于面板上的坐标（整个面板的左上角为原点）
			// 释放鼠标时鼠标的坐标=按鼠标时鼠标在面板上的坐标+鼠标走过的距离差
			releaseX = event.getX() - preX + pressX;
			releaseY = event.getY() - preY + pressY;

			// 进行双目运算符操作时，如果其中有一个数为double类型，则会自动将另一个数自动转换为double
			// 计算按下鼠标和释放鼠标之间的单位方向距离(四舍五入)
			countDrectionX = (int) Math.round((releaseX - indexX) / squareSize);
			countDrectionY = (int) Math.round((releaseY - indexY) / squareSize);

			// 用于判断棋子处于可走动区域外
			centerX = indexX + (int) squareSize * countDrectionX;
			centerY = indexY + (int) squareSize * countDrectionY;

			// 横向或者纵向只能走一格，并且不能出界
			if (ChessBoard.StepChess(centerX, centerY, signTigerOrDog, countDrectionX, countDrectionY)
					&& ChessBoard.specialJudge(centerX, centerY, indexX, indexY)) {
				// 将棋子在棋盘上的旧位置更新为0
				ChessBoard.setPoint(indexX, indexY, 0);
				// 更新上一步走棋标识,走棋做准备
				ChessBoard.preChessman = signTigerOrDog;

				// 更新棋子中心在面板上的坐标
				indexX = centerX;
				indexY = centerY;
				// 更新棋盘上棋子的位置
				ChessBoard.setPoint(indexX, indexY, signTigerOrDog);

				// 设置标签图片的新位置,面板上的定位都是标签的左上角，不是标签的中心
				labelChessman.setLocation(indexX - getWidth() / 2, indexY - getHeight() / 2);

				/**
				 * 判断狗被吃 提供参数：棋盘
				 */
				if (true) {
					// 被吃的棋子在棋盘上重置为0，并重绘棋盘面板，消除棋盘上为0的棋子
					// ChessBoard.resetPointByIndex(row, col);
				}
				/**
				 * 判断获胜与否 提供参数：棋盘
				 */
				if (true) {
					// 弹出对话框，显示获胜方，游戏终止，弹出对话框，是否再来一局
				}
				// 否则继续
			}

		}

		@Override
		public void mouseDragged(MouseEvent event) {// 拖动
			// 目前用不到
		}

		@Override
		public void mouseMoved(MouseEvent event) {// 移动
			// 目前用不到
		}

		@Override
		public void mouseClicked(MouseEvent event) {// 该方法适用于单击、双击、三击等（这里用不到）
			// 目前用不到
		}
	}
}
