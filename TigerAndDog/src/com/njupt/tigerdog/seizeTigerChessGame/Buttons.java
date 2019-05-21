package com.njupt.tigerdog.seizeTigerChessGame;

import javax.swing.JButton;

public class Buttons {
	private JButton button;
	public Buttons(String buttonName,int buttonX,int buttonY,int width,int height) {
		button=new JButton(buttonName);
		button.setBounds(buttonX, buttonY, width, height);
	}
	public JButton getButton() {
		return button;
	}
	
	//添加监听器
}
