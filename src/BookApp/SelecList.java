package BookApp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SelecList {
	public static void main(String[] args) {
		String sql = "SELECT * FROM monsters WHERE grade = ?";

		try (Connection connection = DbConfig.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)

		) {
			statement.setString(1, "희귀");

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					System.out.println(resultSet.getString("name"));
				}
			}
		} catch (Exception e) {
			System.out.println("MySQL 연결 또는 조회에 실패했습니다.");
			System.out.println("오류 내용: " + e.getMessage());
		}
	}
}
