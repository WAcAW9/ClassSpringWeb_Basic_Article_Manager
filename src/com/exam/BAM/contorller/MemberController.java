package com.exam.BAM.contorller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.exam.BAM.dto.Article;
import com.exam.BAM.dto.Member;
import com.exam.BAM.util.Util;

public class MemberController {
	
	private Scanner sc;
	private int lastMemberId;
	private List<Member> members;
	
	public MemberController(Scanner sc) {
		this.sc = sc;
		this.lastMemberId = 0;
		this.members = new ArrayList<>();
	}

	public void doJoin() {
		String loginId=null;
		String loginPw=null;
		String name = null;
		
		// 아이디 중복 확인 검증
		while(true) {
			
			System.out.print("아이디 : ");
			loginId = sc.nextLine().trim();
			
			boolean isLoginIdDup = false;
			
			if(loginId.length()==0) {
				System.out.println("아이디는 필수 입력 정보입니다.");
				continue;
			}
			
			for(Member member : members) {
				if(loginId.equals(member.getLoginId())) {
					isLoginIdDup = true;
					break;
				}
			}
			if(isLoginIdDup) {
				System.out.printf("[ %s ]은(는) 이미 사용중인 아이디입니다.\n",loginId);
				continue;
			}
			System.out.println("이 아이디는 사용 가능합니다.");
			break;
			
		}
		
		
		// 비밀번호 확인 검증
		while(true) {
			System.out.print("비밀번호 : ");
			loginPw = sc.nextLine().trim();
			
			if(loginPw.length()==0) {
				System.out.println("비밀번호는 필수 입력 정보입니다.");
				continue;
			}
			
			System.out.print("비밀번호 확인 : ");
			String loginPwChk = sc.nextLine();
			
			if(loginPw.equals(loginPwChk)==false) {
				System.out.println("비밀번호가 일치하지 않습니다.");
				continue;
			}	
			break;
			
		}
		
		// 필수 입력 정보(공백 검증)
		while(true) {
			System.out.print("이름 : ");
			name = sc.nextLine().trim();
			
			if(name.length()==0) {
				System.out.println("이름은 필수 입력 정보입니다.");
				continue;
			}
			break;
		}
		
		lastMemberId++;
		
		Member member = new Member(lastMemberId,Util.getDateStr(),loginId,loginPw,name);
		members.add(member);
		
		System.out.println(name+"님이 가입되었습니다.");
	}

	public void makeTestData() {
		// test Data
		System.out.println("태스트용 회원 3개를 생성했습니다");
		for(int i=1;i<=3;i++) {	
			members.add(new Member(++lastMemberId,Util.getDateStr(),"test"+i,"test"+i,"user"+i));
		}
	}
}
