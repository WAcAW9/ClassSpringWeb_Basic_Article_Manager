package com.exam.BAM.app;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.exam.BAM.util.Util;
import com.exam.BAM.dto.Article;
import com.exam.BAM.dto.Member;

public class App {
	
	//	전역변수
	private int lastArticleId;
	private List<Article> articles;
	private int lastMemberId;
	private List<Member> members;
	
	public App(){
		this.lastArticleId = 0;
		this.articles = new ArrayList<>();
		this.lastMemberId = 0;
		this.members = new ArrayList<>();
	}
	
	public void run() {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("== 프로그램 시작 ==");		
		
		makeTestData();  // 접근 제한자 private static void makeTestData(){}
		
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
			
			// 3) 게시글 입력 명령어
			else if(cmd.equals("article write")) {

				System.out.print("제목 : ");
				String title = sc.nextLine();
				System.out.print("내용 : ");
				String body = sc.nextLine();
				
				lastArticleId++;
				
				Article article = new Article(lastArticleId,Util.getDateStr(),title,body,0);
				articles.add(article);
				
				System.out.println(lastArticleId + "번 글이 생성되었습니다");
			}
			// 4) 게시글 출력 명령어
			else if(cmd.startsWith("article list")) {
				// 게시물이 존재하지 않는 경우
				if(articles.size()==0) {
					System.out.println("게시글이 없습니다");		
					continue;
				}
				
				// 4-1) 게시글 검색
				// 백업
				List<Article> printArticles = articles;
				
				String searchKeyword=cmd.substring("article list".length()).trim();
	
				// 검색어가 있는 경우
				if(searchKeyword.length()>0) {
					System.out.println("검색어 : "+ searchKeyword);					
					printArticles = new ArrayList<>();
					// 검색어 포함 게시물 찾기
					for(Article article : articles) {
						if(article.getTitle().contains(searchKeyword)) {
							printArticles.add(article);
						}
					}
					// 검색어 포함 게시물 없는 경우
					if(printArticles.size()==0) {
						System.out.println("검색 결과가 없습니다.");
						continue;
					}
				}
				
				// 게시물이 존재하는 경우
				System.out.println("번호 |    제목    | 조회수 |   작성일");
				for(int i=printArticles.size()-1;i>=0;i--) {
					Article article = printArticles.get(i);
					System.out.printf("%d   |   %s   |  %d  |%s\n",article.getId(),article.getTitle(),article.getViewCnt(),article.getRegDate());
				
				}
				
			}
			// 5) 게시물 상새보기
			else if(cmd.startsWith("article detail ")) { // 뒤에 띄어쓰기 필수
				
				// 명령어 오류 검출
				int id = getIdByCmd(cmd);
				
				if(id==0) {
					System.out.println("명령어가 올바르지 않습니다.");
					continue;
				}
				
				// 게시물 찾기(순회)
				Article foundArticle = getArticlebyId(id);
				
				if(foundArticle ==null) {
					continue;
				}
			
				foundArticle.increaseViewCnt();
				
				System.out.printf("번호: %d\n",foundArticle.getId());
				System.out.printf("작성일: %s\n",foundArticle.getRegDate());
				System.out.printf("제목: %s\n",foundArticle.getTitle());
				System.out.printf("내용: %s\n",foundArticle.getBody());
				System.out.printf("조회수: %d\n",foundArticle.getViewCnt());
				
			}
			// 6) 게시물 수정하기
			else if(cmd.startsWith("article modify ")) { // 뒤에 띄어쓰기 필수
				
				// 명령어 오류 검출
				int id = getIdByCmd(cmd);
				
				if(id==0) {
					System.out.println("명령어가 올바르지 않습니다.");
					continue;
				}
				
				// 게시물 찾기(순회)
				Article foundArticle = getArticlebyId(id);
				
				if(foundArticle ==null) {
					continue;
				}
				
				// 수정할 제목과 내용 사용자에게 입력받기
				System.out.print("수정할 제목 : ");
				String title = sc.nextLine();
				System.out.print("수정할 내용 : ");
				String body = sc.nextLine();
				
				// 입력받은 내용으로 값 수정
				foundArticle.setTitle(title);
				foundArticle.setBody(body);
				
				System.out.println(id+"번 게시물을 수정했습니다.");
			}
			
			// 7) 게시물 삭제하기
			else if(cmd.startsWith("article delete ")) { // 뒤에 띄어쓰기 필수
				
				// 명령어 오류 검출
				int id = getIdByCmd(cmd);
				
				if(id==0) {
					System.out.println("명령어가 올바르지 않습니다.");
					continue;
				}
				
				// 게시물 찾기(순회)
				Article foundArticle = getArticlebyId(id);
				
				if(foundArticle ==null) {
					continue;
				}
				
				articles.remove(foundArticle);
				System.out.println(id+"번 게시물을 삭제했습니다.");
			}
			
			// 8) 정의되지 않은 명령어
			else {
				System.out.println("존재하지 않는 명령어입니다.");
			}
			
		} //while
		
		sc.close();
		System.out.println("== 프로그램 종료 ==");
	}
	
	private int getIdByCmd(String cmd) {
		
		String[] cmdBits = cmd.split(" "); //공백 마다 자르기 -> 단어별로 배열 원소로 저장
		
		// 예외처리
		try {
			int id=Integer.parseInt(cmdBits[2]);
			return id;
		}catch(NumberFormatException e) {
			return 0;
		}
	}

	private Article getArticlebyId(int id) {

		for(Article article : articles) {
			if(id == article.getId()) { // 데이터 무결성 -> 타입변환 주의
				return article;
			}
		}	
		System.out.println(id+"번 게시물은 존재하지 않습니다.");
		return null;
		
	}

	private void makeTestData() {
		// test Data
		System.out.println("태스트용 데이터 3개를 생성했습니다");
		System.out.println("태스트용 회원 3개를 생성했습니다");
		for(int i=1;i<=3;i++) {
			articles.add(new Article(++lastArticleId,Util.getDateStr(),"제목"+i,"내용"+i,i*10));
			members.add(new Member(++lastMemberId,Util.getDateStr(),"test"+i,"test"+i,"user"+i));
		}
		
	}
}
