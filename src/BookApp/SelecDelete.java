package BookApp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SelecDelete {
	public static void main(String[] args) {
		String sql = "DELETE FROM monsters WHERE id = ?";
		
		try (Connection connection = DbConfig.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)

		) {
			statement.setInt(1, 12);

			int result = statement.executeUpdate();
			System.out.println(result + "건이 삭제되었습니다.");

		} catch (Exception e) {
			System.out.println("MySQL 연결 또는 조회에 실패했습니다.");
			System.out.println("오류 내용: " + e.getMessage());
		}
	}
}
