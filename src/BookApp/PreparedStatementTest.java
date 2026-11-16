package BookApp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PreparedStatementTest {
    public static void main(String[] args) {
        String sql = "SELECT id, name, grade, level_no, exp, element FROM monsters";

        try (
            Connection connection = DbConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()) {
        	List<Monster> monsters = new ArrayList<>();
        	
            while (resultSet.next()) {
            	Monster monster = new Monster(
            			resultSet.getInt("id"),
            			resultSet.getString("name"),
            			resultSet.getString("grade"),
            			resultSet.getInt("level_no"),
            			resultSet.getInt("exp"),
            			resultSet.getString("element")
            		);

            			monsters.add(monster);
            	
        		}   
                
            } catch (Exception e) {
    			System.out.println("MySQL 연결에 실패했습니다.");
    			System.out.println("오류 내용: " + e.getMessage());
    	}
    }
}
