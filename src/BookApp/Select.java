package BookApp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Select {
    public static void main(String[] args) {
        String sql = "SELECT id, name, grade, level_no, exp, element FROM monsters";

        try (
            Connection connection = DbConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()) {
        	
            while (resultSet.next()) {
            	
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String grade = resultSet.getString("grade");
                int level = resultSet.getInt("level_no");
                int exp = resultSet.getInt("exp");
                String element = resultSet.getString("element");

                Monster monster = new Monster(
                        id, name, grade, level, exp, element);

                System.out.println(monster.getName());
        	}   
                
            } catch (Exception e) {
    			System.out.println("MySQL 연결에 실패했습니다.");
    			System.out.println("오류 내용: " + e.getMessage());
    	}
    }
}
