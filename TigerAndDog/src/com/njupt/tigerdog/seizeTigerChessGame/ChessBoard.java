package com.njupt.tigerdog.seizeTigerChessGame;

/**
 * 棋盘类，内置二维数组表示棋盘
 * 
 * @author 10652
 */
public class ChessBoard {
	private static final int borderX = 50;// 棋盘左上角在面板中的的坐标
	private static final int borderY = 50;
	private static final int maxCol = 5;// 棋盘的最大行列数
	private static final int maxRow = 7;
	private static int[][] chessBoard;// 二维数组表示棋盘
	private static final int[] inclineCount = { 0, 2, 4, 6 };

	public static int preChessman;// 用于记录上一步是狗还是虎走棋(老虎则为1，猎狗则为2)
	static BackgroundPanel chessPanel;// 获取上层面板变量，用于重绘整个面板

	public ChessBoard(BackgroundPanel backgroundPanel) {
		chessPanel = backgroundPanel;
		chessBoard = new int[maxRow][maxCol];

		// -1表示矩形棋盘中不可走棋的点
		chessBoard[0][0] = -1;
		chessBoard[0][1] = -1;
		chessBoard[0][3] = -1;
		chessBoard[0][4] = -1;
		chessBoard[1][0] = -1;
		chessBoard[1][4] = -1;

		// 设置猎狗棋子位置
		for (int rowOrCol = 2; rowOrCol < 6; ++rowOrCol) {
			chessBoard[rowOrCol][0] = 2;
			chessBoard[rowOrCol + 1][4] = 2;
			chessBoard[2][rowOrCol - 1] = 2;
			chessBoard[6][rowOrCol - 2] = 2;
		}

		// 设置老虎位置
		chessBoard[4][2] = 1;

		// 开局老虎先走，所以初始化上一步走棋标识时，应该设置为猎狗
		preChessman = 2;

		// 初始时刻其余值为初始值0，表示没有棋子占据
	}

	/**
	 * 判断能否走棋
	 * 
	 * @param centerX
	 *            鼠标释放时，面板上定位的中心坐标（即棋子即将落子的时，棋子的中心坐标）
	 * @param centerY
	 * @param signTigerOrDog
	 *            标识这一步是老虎还是猎狗走棋
	 * @param countDrectionX
	 * @param countDrectionY
	 * @return
	 */
	public static Boolean StepChess(int centerX, int centerY, int signTigerOrDog, int countDrectionX,
			int countDrectionY) {
		int stepLength = Math.abs(countDrectionX) + Math.abs(countDrectionY);
		if (stepLength == 1 && ChessBoard.stepToPoint(centerX, centerY, signTigerOrDog))
			return true;
		else if (Math.abs(countDrectionX) == 1 && Math.abs(countDrectionY) == 1
				&& ChessBoard.stepToInclinePoint(centerX, centerY, signTigerOrDog))
			return true;
		return false;
	}

	/**
	 * 有四步判断走棋需要特殊比较
	 * 
	 * @param centerX
	 * @param centerY
	 * @param preCenterX
	 * @param preCenterY
	 * @return
	 */
	public static Boolean specialJudge(int centerX, int centerY, int preCenterX, int preCenterY) {
		int col = (centerX - borderX) / 60;
		int row = (centerY - borderY) / 60;
		int preCol = (preCenterX - borderX) / 60;
		int preRow = (preCenterY - borderY) / 60;
		if (row == 1 && col == 1 && preRow == 2 && (preCol == 0 || preCol == 1))
			return false;
		if (row == 1 && col == 3 && preRow == 2 && (preCol == 3 || preCol == 4))
			return false;
		if (preRow == 1 && preCol == 1 && row == 2 && (col == 0 || col == 1))
			return false;
		if (preRow == 1 && preCol == 3 && row == 2 && (col == 3 || col == 4))
			return false;
		return true;
	}

	/**
	 * 静态方法：判断是否能横竖走棋（越界不可走动、有棋子占据也不可走动）
	 * 
	 * @param centerX
	 *            棋子中心在面板中的横坐标与棋盘中的列相对应
	 * @param centerY
	 *            棋子中心在面板中的纵坐标与棋盘中的行相对应
	 * @param sign
	 *            标记这一步走棋的是狗还是老虎
	 * @return
	 */
	public static Boolean stepToPoint(int centerX, int centerY, int sign) {
		int col = (centerX - borderX) / 60;
		int row = (centerY - borderY) / 60;
		return (col >= 0 && row >= 0 && col < maxCol && row < maxRow) && chessBoard[row][col] == 0
				&& sign != preChessman;
	}

	/**
	 * 静态方法 ：判断是否能斜着走棋（有棋子占据不可走动）
	 * 
	 * @param centerX
	 *            棋子中心在面板中的横坐标与棋盘中的列相对应
	 * @param centerY
	 *            棋子中心在面板中的纵坐标与棋盘中的行相对应
	 * @param sign
	 *            标记这一步走棋的是狗还是老虎
	 * @return
	 */
	public static Boolean stepToInclinePoint(int centerX, int centerY, int sign) {
		int col = (centerX - borderX) / 60;
		int row = (centerY - borderY) / 60;
		if (sign == preChessman || chessBoard[row][col] > 0)
			return false;
		for (int i = 0; i < 4; ++i)
			if ((inclineCount[i] == row - col) || (inclineCount[i] == row + col))
				return true;
		return false;
	}

	/**
	 * 静态方法：设置棋子在棋盘中的位置
	 * 
	 * @param centerX
	 *            棋子中心在面板中的横坐标与棋盘中的列相对应
	 * @param centerY
	 *            棋子中心在面板中的纵坐标与棋盘中的行相对应
	 * @param sign
	 *            标志棋子是老虎还是猎狗，在棋盘中显示
	 */
	public static void setPoint(int centerX, int centerY, int sign) {
		try {
			chessBoard[(centerY - borderY) / 60][(centerX - borderX) / 60] = sign;
		} catch (Exception e) {
		}
	}

	/**
	 * 获取棋盘上的某点的棋子
	 * 
	 * @param row
	 *            棋盘上的行
	 * @param col
	 *            棋盘上的列
	 * @return
	 */
	public static int getPointByIndex(int row, int col) {
		return chessBoard[row][col];
	}

	/**
	 * 将棋盘上某点的棋子消除（将其设置为0，重绘棋盘时便可将其消除），并重绘棋盘面板
	 * 
	 * @param row
	 *            棋盘上的行
	 * @param col
	 *            棋盘上的列
	 */
	public static void resetPointByIndex(int row, int col) {
		// 将棋子重置为0
		chessBoard[row][col] = 0;

		// 重绘棋盘面板
		chessPanel.removeAll();
		chessPanel.setVisible(false);
		chessPanel.addChessComponents();
		chessPanel.setVisible(true);
	}
}
