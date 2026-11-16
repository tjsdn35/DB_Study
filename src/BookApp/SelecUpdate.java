package BookApp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SelecUpdate {
	public static void main(String[] args) {
		String sql = """
				insert into monsters (name, grade, level_no, exp, element)
				VALUES (?, ?, ?, ?, ?)
				""";

		try (Connection connection = DbConfig.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)

		) {
			statement.setString(1, "꼬마유령");
			statement.setString(2, "희귀");
			statement.setInt(3, 15);
			statement.setInt(4, 900);
			statement.setString(5, "어둠");

			int result = statement.executeUpdate();
			System.out.println(result + "건이 수정되었습니다.");

		} catch (Exception e) {
			System.out.println("MySQL 연결 또는 조회에 실패했습니다.");
			System.out.println("오류 내용: " + e.getMessage());
		}
	}
}
