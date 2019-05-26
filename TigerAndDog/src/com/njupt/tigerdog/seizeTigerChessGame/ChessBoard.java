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

	public static int preChessman;// 用于记录上一步是狗还是虎走棋
	static BackgroundPanel chessPanel;

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

		// 初始时刻，没有上一步，故将记录上一步走棋设置为0
		preChessman = 0;

		// 初始时刻其余值为初始值0，表示没有棋子占据
	}

	/**
	 * 静态方法：判断棋子是否可以走动（越界不可走动、有棋子占据也不可走动）
	 * 
	 * @param centerX棋子中心在面板中的横坐标----与棋盘中的列相对应
	 * @param centerY棋子中心在面板中的纵坐标----与棋盘中的行相对应
	 * @return
	 */
	public static Boolean stepToPoint(int centerX, int centerY, int sign) {
		int col = (centerX - borderX) / 60;
		int row = (centerY - borderY) / 60;
		return (col >= 0 && row >= 0 && col < maxCol && row < maxRow) && chessBoard[row][col] == 0
				&& sign != preChessman;
	}

	/**
	 * 静态方法：设置棋子在棋盘中的位置
	 * 
	 * @param centerX棋子中心在面板中的横坐标----与棋盘中的列相对应
	 * @param centerY棋子中心在面板中的纵坐标----与棋盘中的行相对应
	 * @param sign标志棋子是老虎还是猎狗，在棋盘中显示
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
	 * @param col
	 * @return
	 */
	public static int getPointByIndex(int row, int col) {
		return chessBoard[row][col];
	}

	/**
	 * 将棋盘上某点的棋子消除（将其设置为0，重绘棋盘时便可将其消除），并重绘棋盘面板
	 * 
	 * @param row
	 * @param col
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
