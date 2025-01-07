import java.util.*;

public class Main {
	public static void main(String[] args) {
		
		// 1. 입력 대기 -> 입력시 즉시 종료
		// 데이터 타입 변수이름 = 객체 만들기 생성자(인자(클래스.변수))
		/*Scanner input = new Scanner(System.in);
		
		System.out.println("== 프로그램 시작 ==");
		System.out.print("명령어)");
		
		String cmd=input.nextLine();
		
		System.out.println("입력된 명령어 : " + cmd);
		System.out.println("== 프로그램 끝 ==");*/
		
		
		
		// 2. 종료 의사 표시 -> 종료
		
		/*Scanner input = new Scanner(System.in);
		// 경고 : 자원이 영원히 켜져있음 
		
		System.out.println("== 프로그램 시작 ==");
		
		while(true) {
			System.out.print("명령어)");
			String cmd=input.nextLine();
			System.out.println("입력된 명령어 : " + cmd);
			if(cmd.equals("exit")) break; 
			
			//why .equals()? -> 부등호(==)는 주소 비교 결과,
			// 순수한 값만 비교하기 위해 문자열 비교는 equals 사용
			
			// String a = "a"; 1차원적 값을 직접 넣을 수 있는 특별한 String 자료형
			// String b = new String("a"); 객체 형태 데이터를 넣어야 함
			
			// a==b : false
			// a.equals(b) : true
			
			// 변수 : stack에 저장,같다면 동일 주소 사용
			// instance : heap에 저장, 주소 별도 사용
			
		}
		
		// input 자원 끝내기
		input.close();

		System.out.println("== 프로그램 끝 ==");
		*/
		
		//3. 게시글 작성과 입출력
		/*Scanner sc = new Scanner(System.in);
		int lastArticleId = 0;
		
		System.out.println("== 프로그램 시작 ==");
		
		while(true) {
			System.out.print("명령어)");
			String cmd = sc.nextLine().trim(); //앞뒤 공백 제거
			
			// 0) 종료 명령어
			if(cmd.equals("exit")) break;
			
			// 1) 공백 입력
			// nextLine() return 타입 검증 필요(null,"")
			if(cmd.equals("")) { //cmd.length()==0 => 공백 확인 가능
				System.out.println("명령어를 입력해주세요");
				continue;
			}
			
			// 2) 게시글 입력 명령어
			if(cmd.equals("article write")) {
				System.out.print("제목 : ");
				sc.nextLine();
				System.out.print("내용 : ");
				sc.nextLine();
				
				System.out.println(++lastArticleId + "번 글이 생성되었습니다");
			}
			// 3) 게시글 출력 명령어
			else if(cmd.equals("article list")) {
				System.out.println("게시글이 없습니다");
			}
			// 4) 정의되지 않은 명령어
			else {
				System.out.println("존재하지 않는 명령어입니다.");
			}
			
		} //while
		
		sc.close();
		System.out.println("== 프로그램 종료 ==");
		*/
		
		//4. 게시글 작성 저장
		// 복수개의 데이터 처리를 위한 List
		// 게시물 데이터 타입 생성, 번호/제목/내용 데이터 저장
		Scanner sc = new Scanner(System.in);
		
		int lastArticleId = 0;
		// 게시물 List 생성
		List<Article> articles = new ArrayList<>();
		
		System.out.println("== 프로그램 시작 ==");
		
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
				// 내용 입력받기
				System.out.print("제목 : ");
				String title = sc.nextLine();
				System.out.print("내용 : ");
				String body = sc.nextLine();
				
				lastArticleId++;
				
				// 객체 생성
				Article article = new Article(lastArticleId,title,body);
				
				// 리스트에 저장
				articles.add(article);
				
				System.out.println(lastArticleId + "번 글이 생성되었습니다");
			}
			// 3) 게시글 출력 명령어
			else if(cmd.equals("article list")) {
				// 게시물이 존재하지 않는 경우
				if(articles.size()==0) {
					System.out.println("게시글이 없습니다");		
					continue;
				}
				// 게시물이 존재하는 경우
				System.out.println("번호 | 제목");
				for(int i=articles.size()-1;i>=0;i--) {
					System.out.printf("%d   |   %s\n",articles.get(i).id,articles.get(i).title);
				
				}
				
			}
			// 4) 정의되지 않은 명령어
			else {
				System.out.println("존재하지 않는 명령어입니다.");
			}
			
		} //while
		
		sc.close();
		System.out.println("== 프로그램 종료 ==");
		
		
	}
}

class Article{
	int id;
	String title;
	String body;
	
	// 생성자
	public Article(int id, String title, String body) {
		this.id=id;
		this.title=title;
		this.body=body;
	}
}