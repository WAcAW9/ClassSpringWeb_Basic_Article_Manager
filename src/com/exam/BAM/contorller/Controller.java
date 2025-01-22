package com.exam.BAM.contorller;

import java.util.Scanner;

public abstract class Controller {
	
	public Scanner sc;
	public int lastId;
	public String cmd;
	
	// 추상 메서드
	public abstract void makeTestData();

	public abstract void doAction(String cmd, String methodName);
}
