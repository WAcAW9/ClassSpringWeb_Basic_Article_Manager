import java.util.*;

public class Main {
	
//	전역변수
	static int lastArticleId = 0;
	static List<Article> articles = new ArrayList<>();
	
//	static 초기화 블럭
	static {
		lastArticleId = 0;
		articles = new ArrayList<>();
	}
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("== 프로그램 시작 ==");		
		
		maskeTestData();  // 접근 제한자 private static void makeTestData(){}
		
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
			
			// 2) 게시글 입력 명령어
			if(cmd.equals("article write")) {

				System.out.print("제목 : ");
				String title = sc.nextLine();
				System.out.print("내용 : ");
				String body = sc.nextLine();
				
				lastArticleId++;
				
				Article article = new Article(lastArticleId,Util.getDateStr(),title,body,0);
				articles.add(article);
				
				System.out.println(lastArticleId + "번 글이 생성되었습니다");
			}
			// 3) 게시글 출력 명령어
			else if(cmd.startsWith("article list")) {
				// 게시물이 존재하지 않는 경우
				if(articles.size()==0) {
					System.out.println("게시글이 없습니다");		
					continue;
				}
				
				// 3-1) 게시글 검색
				// 백업
				List<Article> printArticles = articles;
				
				String searchKeyword=cmd.substring("article list".length()).trim();
	
				// 검색어가 있는 경우
				if(searchKeyword.length()>0) {
					System.out.println("검색어 : "+ searchKeyword);					
					printArticles = new ArrayList<>();
					// 검색어 포함 게시물 찾기
					for(Article article : articles) {
						if(article.title.contains(searchKeyword)) {
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
					System.out.printf("%d   |   %s   |  %d  |%s\n",article.id,article.title,article.viewCnt,article.regDate);
				
				}
				
			}
			// 4) 게시물 상새보기
			else if(cmd.startsWith("article detail ")) { // 뒤에 띄어쓰기 필수
				// 명령어 분해
				String[] cmdBits = cmd.split(" "); //공백 마다 자르기 -> 단어별로 배열 원소로 저장
				
				int id=0;
				
				// 예외처리
				try {
					id=Integer.parseInt(cmdBits[2]);
				}catch(NumberFormatException e) {
					System.out.println("명령어가 올바르지 않습니다.");
					continue;
				}
				catch(Exception e) {
					System.out.println("error : "+ e);
				}
				
				// 게시물 찾기(순회)
				
				Article foundArticle = null;
				
				for(Article article : articles) {
					if(id == article.id) { // 데이터 무결성 -> 타입변환 주의
						foundArticle = article;
						break;
					}
				}
				
				if(foundArticle ==null) {
					System.out.println(id+"번 게시물은 존재하지 않습니다.");
					continue;
				}
				
				foundArticle.viewCnt++;
				
				System.out.printf("번호: %d\n",foundArticle.id);
				System.out.printf("작성일: %s\n",foundArticle.regDate);
				System.out.printf("제목: %s\n",foundArticle.title);
				System.out.printf("내용: %s\n",foundArticle.body);
				System.out.printf("조회수: %d\n",foundArticle.viewCnt);
				
			}
			// 5) 게시물 수정하기
			else if(cmd.startsWith("article modify ")) { // 뒤에 띄어쓰기 필수
				// 명령어 분해
				String[] cmdBits = cmd.split(" "); //공백 마다 자르기 -> 단어별로 배열 원소로 저장
				
				int id=0;
				
				// 예외처리
				try {
					id=Integer.parseInt(cmdBits[2]);
				}catch(NumberFormatException e) {
					System.out.println("명령어가 올바르지 않습니다.");
					continue;
				}
				catch(Exception e) {
					System.out.println("error : "+ e);
				}
				
				// 게시물 찾기(순회)
				Article foundArticle = null;
				for(Article article : articles) {
					if(id == article.id) { // 데이터 무결성 -> 타입변환 주의
						foundArticle = article;
						break;
					}
				}
				if(foundArticle ==null) {
					System.out.println(id+"번 게시물은 존재하지 않습니다.");
					continue;
				}
				
				// 수정할 제목과 내용 사용자에게 입력받기
				System.out.print("수정할 제목 : ");
				String title = sc.nextLine();
				System.out.print("수정할 내용 : ");
				String body = sc.nextLine();
				
				// 입력받은 내용으로 값 수정
				foundArticle.title=title;
				foundArticle.body=body;
				
				System.out.println(id+"번 게시물을 수정했습니다.");
			}
			
			// 6) 게시물 삭제하기
			else if(cmd.startsWith("article delete ")) { // 뒤에 띄어쓰기 필수
				// 명령어 분해
				String[] cmdBits = cmd.split(" "); //공백 마다 자르기 -> 단어별로 배열 원소로 저장
				
				int id=0;
				
				// 예외처리
				try {
					id=Integer.parseInt(cmdBits[2]);
				}catch(NumberFormatException e) {
					System.out.println("명령어가 올바르지 않습니다.");
					continue;
				}
				catch(Exception e) {
					System.out.println("error : "+ e);
				}
				
				// 게시물 찾기(순회)
				Article foundArticle = null;
				for(Article article : articles) {
					if(id == article.id) { // 데이터 무결성 -> 타입변환 주의
						foundArticle = article;
						break;
					}
				}
				if(foundArticle ==null) {
					System.out.println(id+"번 게시물은 존재하지 않습니다.");
					continue;
				}
				articles.remove(foundArticle);
				System.out.println(id+"번 게시물을 삭제했습니다.");
			}
			
			// 7) 정의되지 않은 명령어
			else {
				System.out.println("존재하지 않는 명령어입니다.");
			}
			
		} //while
		
		sc.close();
		System.out.println("== 프로그램 종료 ==");
		
		
	}

	private static void maskeTestData() {
		// TODO Auto-generated method stub
		// test Data
		System.out.println("태스트용 대이터 3개를 생성했습니다");
		for(int i=1;i<=3;i++) {
			articles.add(new Article(++lastArticleId,Util.getDateStr(),"제목"+i,"내용"+i,i*10));
		}
		
	}
}

class Article{
	int id;
	String regDate;
	String title;
	String body;
	int viewCnt;
	
	public Article(int id,String regDate, String title, String body,int viewCnt) {
		this.id=id;
		this.regDate = regDate;
		this.title=title;
		this.body=body;
		this.viewCnt=viewCnt;
	}
}