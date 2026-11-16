package BookApp;

import java.sql.Connection;

public class JdbcConnectionTest {
	public static void main(String[] args) {
		try (Connection connection = DbConfig.getConnection()) {
			System.out.println("MySQL 연결에 성공했습니다!");
			System.out.println("연결된 DB: " + connection.getCatalog());
		} catch (Exception e) {
			System.out.println("MySQL 연결에 실패했습니다.");
			System.out.println("오류 내용: " + e.getMessage());
		}
	}
}
