package ex01;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DbDeleteTest {
	public static void main(String[] args) {

		String sql = """ 
					DELETE FROM monsters 
					WHERE
					id = ?
					""";
		
		try (
				Connection connection = DbConfig.getConnection();
				PreparedStatement statement =
						connection.prepareStatement(sql)
		){
			statement.setInt(1, 7);
			
			int result = statement.executeUpdate(); // sql 실행하면 업데이트 갯수
											// 업데이트 = insert, updatel, delete
			System.out.println("delete 카운트 : " + result);

		} catch (Exception e) {
			// 사용자 id password 오류 또는 mydql서버가 바쁠때
			System.out.println("MySQL 연결에 실패했습니다.");
			System.out.println("오류 내용: " + e.getMessage());
		}
	}
}