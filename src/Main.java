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
		
		Scanner input = new Scanner(System.in);
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
		
	}
}
