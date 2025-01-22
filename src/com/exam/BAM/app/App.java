package com.exam.BAM.app;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.exam.BAM.util.Util;
import com.exam.BAM.contorller.ArticleController;
import com.exam.BAM.contorller.MemberController;
import com.exam.BAM.dto.Article;
import com.exam.BAM.dto.Member;

public class App {
	
	
	public void run() {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("== 프로그램 시작 ==");		
		
		MemberController memberController = new MemberController(sc);
		memberController.makeTestData();
		
		ArticleController articleController = new ArticleController(sc);
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
			// 2) 회원가입 기능
			if(cmd.equals("member join")) {
				memberController.doJoin();
			}
			// 3) 게시글 입력 명령어
			else if(cmd.equals("article write")) {
				articleController.doWrite();
			}
			// 4) 게시글 출력 명령어
			else if(cmd.startsWith("article list")) {
				articleController.showList(cmd);
			}
			// 5) 게시물 상새보기
			else if(cmd.startsWith("article detail ")) { // 뒤에 띄어쓰기 필수
				articleController.showDetail(cmd);
			}
			// 6) 게시물 수정하기
			else if(cmd.startsWith("article modify ")) { // 뒤에 띄어쓰기 필수
				articleController.doModify(cmd);
			}
			// 7) 게시물 삭제하기
			else if(cmd.startsWith("article delete ")) { // 뒤에 띄어쓰기 필수
				articleController.doDelete(cmd);
			}
			// 8) 정의되지 않은 명령어
			else {
				System.out.println("존재하지 않는 명령어입니다.");
			}
		} //while
		
		sc.close();
		System.out.println("== 프로그램 종료 ==");
	}
	


	
}
