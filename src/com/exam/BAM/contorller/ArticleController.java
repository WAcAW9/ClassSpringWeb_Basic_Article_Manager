package com.exam.BAM.contorller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.exam.BAM.container.Container;
import com.exam.BAM.dto.Article;
import com.exam.BAM.dto.Member;
import com.exam.BAM.util.Util;

public class ArticleController extends Controller{
	
	private List<Article> articles;
	
	public ArticleController(Scanner sc) {
		this.sc = sc;
		this.lastId = 0;
		this.articles = Container.articles;
	}

	@Override
	public void doAction(String cmd, String methodName) {
		this.cmd = cmd;
		switch(methodName) {
		case "write":
			doWrite();
			break;
		case "list":
			showList();
			break;
		case "detail":
			showDetail();
			break;
		case "modify":
			doModify();
			break;
		case "delete":
			doDelete();
			break;
		default:
			System.out.println("존재하지 않는 명령어 입니다");
			break;
		}
		
	}
	
	private void doWrite() {
		

		System.out.print("제목 : ");
		String title = sc.nextLine();
		System.out.print("내용 : ");
		String body = sc.nextLine();
		
		lastId++;
		
		Article article = new Article(lastId,Util.getDateStr(),loginedMember.getId(),title,body,0);
		articles.add(article);
		
		
		System.out.println(lastId + "번 글이 생성되었습니다");
		
	}
	
	private void showList() {
		// 게시물이 존재하지 않는 경우
		if(articles.size()==0) {
			System.out.println("게시글이 없습니다");		
			return;
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
				return;
			}
		}
		
	
		
		// 게시물이 존재하는 경우
		System.out.println("번호 |   제목    | 조회수 |작성자|   작성일");
		for(int i=printArticles.size()-1;i>=0;i--) {
			Article article = printArticles.get(i);
			
			// 순회하며 작성자 번호와 일치한 회원의 loginId 가져오기
			String writerName = getWriterName(article.getMemberId());
			
			System.out.printf("%d   |   %s   |  %s  |  %s  |%s\n",article.getId(),article.getTitle(),article.getViewCnt(),writerName,article.getRegDate());
		}
	}



	private void showDetail() {
			
		// 명령어 오류 검출
		int id = getIdByCmd(cmd);
		
		if(id==0) {
			System.out.println("명령어가 올바르지 않습니다.");
			return;
		}
		
		// 게시물 찾기(순회)
		Article foundArticle = getArticlebyId(id);
		
		if(foundArticle ==null) {
			return;
		}
	
		foundArticle.increaseViewCnt();
		
		
		
		// 순회하며 작성자 번호와 일치한 회원의 loginId 가져오기
		String writerName = getWriterName(foundArticle.getMemberId());
		
		
		
		System.out.printf("번호: %d\n",foundArticle.getId());
		System.out.printf("작성일: %s\n",foundArticle.getRegDate());
		System.out.printf("작성자: %s\n",writerName);
		System.out.printf("제목: %s\n",foundArticle.getTitle());
		System.out.printf("내용: %s\n",foundArticle.getBody());
		System.out.printf("조회수: %d\n",foundArticle.getViewCnt());
		
		
	}

	

	private void doModify() {

		// 명령어 오류 검출
		int id = getIdByCmd(cmd);
		
		if(id==0) {
			System.out.println("명령어가 올바르지 않습니다.");
			return;
		}
		
		// 게시물 찾기(순회)
		Article foundArticle = getArticlebyId(id);
		
		if(foundArticle ==null) {
			return;
		}
		
		// 권한 체크
		if(loginedMember.getId()!=foundArticle.getMemberId()) {
			System.out.println("해당 게시물에 대한 권한이 없습니다");
			return;
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

	private void doDelete() {

		// 명령어 오류 검출
		int id = getIdByCmd(cmd);
		
		if(id==0) {
			System.out.println("명령어가 올바르지 않습니다.");
			return;
		}
		
		// 게시물 찾기(순회)
		Article foundArticle = getArticlebyId(id);
		
		if(foundArticle ==null) {
			return;
		}
		
		// 권한 체크
		if(loginedMember.getId()!=foundArticle.getMemberId()) {
			System.out.println("해당 게시물에 대한 권한이 없습니다");
			return;
		}
		
		articles.remove(foundArticle);
		System.out.println(id+"번 게시물을 삭제했습니다.");
		
	}
	
	private String getWriterName(int memberId) {
		for(Member member : Container.members) {
			if(memberId == member.getId()) {
				return member.getName();
			}
		}
		return null;
	}
	
	private int getIdByCmd(String cmd) {
		
		String[] cmdBits = cmd.split(" "); //공백 마다 자르기 -> 단어별로 배열 원소로 저장
		
		// 예외처리
		try {
			int id=Integer.parseInt(cmdBits[2]);
			return id;
		}catch(NumberFormatException e) {
			return 0;
		}catch(Exception e) {
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
	
	@Override
	public void makeTestData() {
		// test Data
		System.out.println("태스트용 데이터 3개를 생성했습니다");
		for(int i=1;i<=3;i++) {
			articles.add(new Article(++lastId,Util.getDateStr(),(int)(Math.random()*3)+1,"제목"+i,"내용"+i,i*10));
		}
		
	}
}
