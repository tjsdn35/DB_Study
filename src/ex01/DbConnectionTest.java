package ex01;

import java.sql.Connection;

public class DbConnectionTest {
	public static void main(String[] args) {
			//mysql서버와 연결 테스트
			// 워크벤치의 Test_Connection과 같은 기능 만들기
		try (Connection connection = DbConfig.getConnection()) {
			// DbConfig.getConnection() : mysql 서버에 연결시도
			// 성공하면 connection 객체를 돌려줌
			System.out.println("MySQL 연결에 성공했습니다!");
			
			//getCatalog: 연결된 mysql서버의 데이터베이스 알려줌
			System.out.println("연결된 DB: " + connection.getCatalog());
		} catch (Exception e) {
			//사용자 id password 오류 또는 mydql서버가 바쁠때
			System.out.println("MySQL 연결에 실패했습니다.");
			System.out.println("오류 내용: " + e.getMessage());
		}

	}

}