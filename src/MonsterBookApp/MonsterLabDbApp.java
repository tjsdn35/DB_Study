package MonsterBookApp;

import java.util.List;
import java.util.Scanner;

public class MonsterLabDbApp {
    private static final MonsterRepository repository = new MonsterRepository();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        List<Monster> initialMonsters = repository.findAll();

        System.out.println("==========================================");
        System.out.println("        MONSTER LAB - DB EDITION");
        System.out.println("==========================================");
        System.out.println("MySQL 연결 성공! 몬스터 " + initialMonsters.size() + "마리를 불러왔습니다.");

        boolean running = true;
        while (running) {
            showMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1": showAllMonsters(); break;
                case "2": showMonsterClassGuide(); break;
                case "3": registerMonster(); break;
                case "4": searchMonsterByName(); break;
                case "5": showStrongMonsters(); break;
                case "6": updateMonsterLevel(); break;
                case "7": deleteMonster(); break;
                case "8": showSortedMonsters(); break;
                case "9": showStatistics(); break;
                case "0":
                    running = false;
                    System.out.println("연구소를 종료합니다.");
                    break;
                default:
                    System.out.println("메뉴 번호를 확인해 주세요.");
            }

            // [추가할 부분] 0번(종료)이 아닐 때만 Enter를 누를 때까지 대기
            if (running) {
                System.out.print("\n>> 계속하려면 Enter 키를 누르세요...");
                scanner.nextLine();
                
            } 
            
        }
        scanner.close();
    }

    private static void showMenu() {
        System.out.println("\n============= 몬스터 연구소 메뉴 =============");
        System.out.println("1. 전체 몬스터 도감 보기");
        System.out.println("2. 몬스터 클래스 알아보기");
        System.out.println("3. 새 몬스터 등록하기");
        System.out.println("4. 이름으로 몬스터 검색하기");
        System.out.println("5. 강한 몬스터 찾기 (레벨 10 이상)");
        System.out.println("6. 몬스터 레벨 수정하기");
        System.out.println("7. 몬스터 삭제하기");
        System.out.println("8. 이름순 도감 보기");
        System.out.println("9. 연구 통계 보기");
        System.out.println("0. 연구소 종료");
        System.out.println("==========================================");
        System.out.print("원하는 메뉴 번호를 입력해 주세요: ");
    }

    // [메뉴 1] 전체 도감 보기 (JDBC + Stream forEach)
    private static void showAllMonsters() {
        System.out.println("\n[전체 몬스터 도감]");
        List<Monster> monsters = repository.findAll();
       /*
        monsters.stream()
                .forEach(monster -> System.out.println(monster.getInfo()));
    } */
        
        for (int i = 0; i < monsters.size(); i++) {
            Monster m = monsters.get(i);
            System.out.printf("%d번. [ID:%d] %s / %s / 레벨 %d / 경험치 %d / 속성 %s\n",
                    (i + 1), m.getId(), m.getName(), m.getGrade(), m.getLevel(), m.getExp(), m.getElement());
        }
    }

    // [메뉴 2] Monster 클래스 구조 설명
    private static void showMonsterClassGuide() {
        System.out.println("\n[Monster 클래스 알아보기]");
        System.out.println("- 필드: id(DB 고유번호), name(이름), grade(등급), level(레벨), exp(경험치), element(속성)");
        System.out.println("- 역할: DB 테이블의 한 행(Row)을 자바 객체 하나로 변환해 담는 설계도입니다.");
    }

    // [메뉴 3] 새 몬스터 등록 (INSERT)
    private static void registerMonster() {
        System.out.println("\n[새 몬스터 등록하기]");
        System.out.print("이름: ");
        String name = scanner.nextLine();
        System.out.print("등급(일반/희귀/전설): ");
        String grade = scanner.nextLine();
        System.out.print("레벨(숫자): ");
        int level = Integer.parseInt(scanner.nextLine());
        System.out.print("경험치(숫자): ");
        int exp = Integer.parseInt(scanner.nextLine());
        System.out.print("속성(물/불/풀/얼음/땅/어둠): ");
        String element = scanner.nextLine();

        int result = repository.insert(name, grade, level, exp, element);
        System.out.println(result + "건이 DB에 등록되었습니다.");
    }

    // [메뉴 4] 이름으로 검색 (SQL WHERE LIKE)
    private static void searchMonsterByName() {
        System.out.print("\n검색할 몬스터 이름: ");
        String keyword = scanner.nextLine();

        List<Monster> foundList = repository.findByName(keyword);
        if (foundList.isEmpty()) {
            System.out.println("검색된 몬스터가 없습니다.");
        } else {
            foundList.forEach(monster -> System.out.println(monster.getInfo()));
        }
    }

    // [메뉴 5] 강한 몬스터 찾기 (Stream filter)
    private static void showStrongMonsters() {
        System.out.println("\n[강한 몬스터 찾기 (레벨 10 이상)]");
        List<Monster> monsters = repository.findAll();

        monsters.stream()
                .filter(monster -> monster.getLevel() >= 10)
                .forEach(monster -> System.out.println(monster.getInfo()));
    }

    // [메뉴 6] 몬스터 레벨 수정 (UPDATE)
    private static void updateMonsterLevel() {
        System.out.print("\n수정할 몬스터의 ID 번호: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("변경할 새 레벨: ");
        int newLevel = Integer.parseInt(scanner.nextLine());

        int result = repository.updateLevel(id, newLevel);
        System.out.println(result + "건의 레벨이 수정되었습니다.");
    }

    // [메뉴 7] 몬스터 삭제 (DELETE)
    private static void deleteMonster() {
        System.out.print("\n삭제할 몬스터의 ID 번호: ");
        int id = Integer.parseInt(scanner.nextLine());

        int result = repository.deleteById(id);
        System.out.println(result + "건이 삭제되었습니다.");
    }

    // [메뉴 8] 이름순 도감 보기 (Stream map + sorted)
    private static void showSortedMonsters() {
        System.out.println("\n[이름순 도감 보기]");
        List<Monster> monsters = repository.findAll();

        monsters.stream()
                .map(monster -> monster.getName())
                .sorted()
                .forEach(name -> System.out.println(name));
    }

    // [메뉴 9] 연구 통계 보기 (Stream count, filter, mapToInt, sum)
    private static void showStatistics() {
        System.out.println("\n[연구 통계 보기]");
        List<Monster> monsters = repository.findAll();

        long totalCount = monsters.stream().count();
        long rareCount = monsters.stream()
                .filter(monster -> monster.getGrade().equals("희귀"))
                .count();
        int totalExp = monsters.stream()
                .mapToInt(monster -> monster.getExp())
                .sum();

        System.out.println("전체 몬스터: " + totalCount + "마리");
        System.out.println("희귀 몬스터: " + rareCount + "마리");
        System.out.println("총 경험치: " + totalExp);
    }
}
