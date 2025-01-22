package com.exam.BAM.app;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.exam.BAM.util.Util;
import com.exam.BAM.contorller.ArticleController;
import com.exam.BAM.contorller.Controller;
import com.exam.BAM.contorller.MemberController;
import com.exam.BAM.dto.Article;
import com.exam.BAM.dto.Member;

public class App {
	
	
	public void run() {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("== 프로그램 시작 ==");		
		
		MemberController memberController = new MemberController(sc);
		ArticleController articleController = new ArticleController(sc);
		
		memberController.makeTestData();
		articleController.makeTestData();
		
		while(true) {
			System.out.print("명령어)");
			String cmd = sc.nextLine().trim();
			
			// 0) 종료 명령어
			if(cmd.equals("exit")) break;
			// 1) 공백 입력
			if(cmd.equals("")) {
				System.out.println("명령어를 입력해주세요");
				continue;
			}
			
			// 명령어 분리
			String[] cmdBits = cmd.split(" ");
			
			// ex) 명령어 : articledetail
			if(cmdBits.length<2) {
				System.out.println("존재하지 않는 명령어입니다");
				continue;
			}
			
			// member join -> [0]member, [1]join
			String controllerName = cmd.split(" ")[0];
			String methodName = cmd.split(" ")[1];

			// 명령어 판단 (로그인 필수, 로그아웃 필수)
			switch(methodName) {
				case "write":
				case "delete":
				case "modify":
				case "logout":
					if(Controller.loginedMember==null) {
						System.out.println("로그인이 필요합니다");
						continue;
					}
					break;	
				case "join":
				case "login":
					if(Controller.loginedMember!=null) {
						System.out.println("로그아웃이 필요합니다");
						continue;
					}
				default:
					break;
			}
			
			Controller controller = null;
			
			if(controllerName.equals("member")) {
				controller = memberController;
			}else if(controllerName.equals("article")) {
				controller=articleController;
			}else {
				System.out.println("존재하지 않는 명령어입니다");
				continue;
			}
			
			controller.doAction(cmd, methodName);
			
		} //while
		
		sc.close();
		System.out.println("== 프로그램 종료 ==");
	}
	


	
}
