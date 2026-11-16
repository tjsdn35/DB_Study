package MonsterBookApp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MonsterRepository {

    // 1. 전체 몬스터 조회 (SELECT) -> List<Monster>로 반환
    public List<Monster> findAll() {
        List<Monster> monsters = new ArrayList<>();
        String sql = "SELECT id, name, grade, level_no, exp, element FROM monsters";

        try (Connection connection = DbConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

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
        } catch (SQLException e) {
            System.out.println("전체 조회 중 오류 발생: " + e.getMessage());
        }
        return monsters;
    }

    // 2. 새 몬스터 등록 (INSERT)
    public int insert(String name, String grade, int level, int exp, String element) {
        String sql = "INSERT INTO monsters (name, grade, level_no, exp, element) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DbConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, name);
            statement.setString(2, grade);
            statement.setInt(3, level);
            statement.setInt(4, exp);
            statement.setString(5, element);

            return statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("등록 중 오류 발생: " + e.getMessage());
            return 0;
        }
    }

    // 3. 이름으로 몬스터 검색 (WHERE + LIKE)
    public List<Monster> findByName(String keyword) {
        List<Monster> monsters = new ArrayList<>();
        String sql = "SELECT id, name, grade, level_no, exp, element FROM monsters WHERE name LIKE ?";

        try (Connection connection = DbConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, "%" + keyword + "%");

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    monsters.add(new Monster(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("grade"),
                            resultSet.getInt("level_no"),
                            resultSet.getInt("exp"),
                            resultSet.getString("element")
                    ));
                }
            }
        } catch (SQLException e) {
            System.out.println("이름 검색 중 오류 발생: " + e.getMessage());
        }
        return monsters;
    }

    // 4. 몬스터 레벨 수정 (UPDATE)
    public int updateLevel(int id, int newLevel) {
        String sql = "UPDATE monsters SET level_no = ? WHERE id = ?";

        try (Connection connection = DbConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, newLevel);
            statement.setInt(2, id);

            return statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("레벨 수정 중 오류 발생: " + e.getMessage());
            return 0;
        }
    }

    // 5. 몬스터 삭제 (DELETE)
    public int deleteById(int id) {
        String sql = "DELETE FROM monsters WHERE id = ?";

        try (Connection connection = DbConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            return statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("삭제 중 오류 발생: " + e.getMessage());
            return 0;
        }
    }
}
