package com.njupt.tigerdog.seizeTigerChessGame;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class TigerOrDog {
	private static final int tigercenterX = 150;// 老虎中心
	private static final int tigerCenterY = 270;
	private static final double squareSize = 60;

	private static final int borderX1 = 50;
	private static final int borderX2 = 290;
	private static final int borderY1 = 170;
	private static final int borderY2 = 410;

	private int indexX;// 横坐标
	private int indexY;// 纵坐标

	private JLabel labelChessman;// 以标签的形式呈现棋子
	private ImageIcon lableImage;// 棋子图片

	private int signTigerOrDog;// 标识虎还是猎狗

	public TigerOrDog(String imageURL, int x, int y, int width, int height) {

		lableImage = new ImageIcon(imageURL);
		lableImage.setImage(lableImage.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
		labelChessman = new JLabel(lableImage);

		// 标志是虎还是猎狗，如果是虎则为1，如果是猎狗则为0，用于在棋盘数组中显示
		signTigerOrDog = (x == tigercenterX && y == tigerCenterY ? 1 : 0);

		// 棋子初始位置(设置的位置是棋子左上角的坐标，而不是中心点的坐标，因为组件在面板中的定位是组件左上角的定位，不是组件正中央的定位)
		labelChessman.setBounds(x, y, width, height);

		// 获取棋子中心坐标
		indexX = labelChessman.getX() + width / 2;
		indexY = labelChessman.getY() + height / 2;

		// 为每个棋子独自添加监听器
		labelChessman.addMouseListener(new MouseHandler());
	}

	// 获取标签的宽和高
	public int getWidth() {
		return labelChessman.getWidth();
	}

	public int getHeight() {
		return labelChessman.getHeight();
	}

	// 获取标签
	public JLabel getLabelChessman() {
		return labelChessman;
	}

	// 监听器
	class MouseHandler extends MouseAdapter {// 鼠标的按和点击、拖动、移动、释放
		int preX;// 按鼠标时，鼠标在棋子上的坐标
		int preY;
		int pressX;// 按鼠标时，鼠标在棋盘/面板上的坐标
		int pressY;
		int releaseX;// 释放鼠标时，鼠标在棋盘/面板上的坐标
		int releaseY;
		int centerX;// 释放鼠标时，棋子中心在棋盘/面板上的坐标
		int centerY;
		int countDrectionX;// 按鼠标和释放鼠标的单位方向差值
		int countDrectionY;

		@Override
		public void mousePressed(MouseEvent event) {// 鼠标按

			// 鼠标点击时，鼠标在棋子标签上的坐标（此时棋子的左上角为原点）
			preX = event.getX();
			preY = event.getY();

			// 鼠标点击时鼠标位于面板上的坐标（此时整个面板的左上角为原点）
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

			// 用于判断棋子是否出界
			centerX = indexX + (int) squareSize * countDrectionX;
			centerY = indexY + (int) squareSize * countDrectionY;

			// 横向或者纵向只能走一格，并且不能出界
			if (Math.abs(countDrectionX) + Math.abs(countDrectionY) == 1 && centerX >= borderX1 && centerX <= borderX2
					&& centerY >= borderY1 && centerY <= borderY2) {

				// 新坐标（棋子中心在面板上的坐标）
				indexX = centerX;
				indexY = centerY;

				// 设置标签图片的新位置,面板上的定位都是标签的左上角，不是标签的中心
				labelChessman.setLocation(indexX - getWidth() / 2, indexY - getHeight() / 2);
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
