package gpacalc;

import java.util.ArrayList;

import camp.nextstep.edu.missionutils.Console;

public class Application {
    ArrayList<Subject> majorList = new ArrayList<>();
    ArrayList<Subject> liberalArtsList = new ArrayList<>();
    double totalMajorAverageRating = 0;
    double totalLiberalArtsAverageRating = 0;
    int total_major_Credit = 0;
    int total_liberalArts_Credit = 0;
    int passCredit = 0;
    int failCredit = 0;

    public static void main(String[] args) {
        Application app = new Application();

        System.out.println("전공 과목명과 이수학점, 평점을 입력해주세요(예시: 프로그래밍언어론-3-A+,소프트웨어공학-3-B+):");
        app.input(app, app.majorList, true);

        System.out.println("교양 과목명과 이수학점, 평점을 입력해주세요(예시: 선형대수학-3-C0,인간관계와자기성장-3-P):");
        app.input(app, app.liberalArtsList, false);

        System.out.println("\n<과목 목록>");
        for(Subject s : app.majorList){
            System.out.println("[전공] " + s.getTitle() + "," + s.getCredit() + "," + s.getGrade());
        }
        for(Subject s : app.liberalArtsList){
            System.out.println("[교양] " + s.getTitle() + "," + s.getCredit() + "," + s.getGrade());
        }

        app.printAll(app);
    }


    public void input(Application app, ArrayList<Subject> subjectList, boolean majorSubject) {
        String[] temp;
        String[] component;
        temp = Console.readLine().split(",");
        for (String eachSubject : temp) {
            component = eachSubject.trim().split("-");
            Subject subject = new Subject();

            // component[0]: title, component[1]: credit, component[2]: grade
            app.titleCheck(component[0], subject);
            app.creditCheck(component[1],subject);
            app.gradeCheck(app, component,subject, majorSubject);

            subjectList.add(subject);
        }
    }

    // 과목명(component[0]) 검사
    public void titleCheck(String component, Subject subject) {
        try{
            if (component.length() > 10) {
                throw new IllegalArgumentException("과목명은 공백 포함 10자 이내로 입력해야 합니다.");
            }else if (component.isBlank()) {
                throw new IllegalArgumentException("과목명은 공백만으로 구성될 수 없습니다.");
            }else{
                subject.setTitle(component);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }

    // 과목학점(component[1]) 검사
    public void creditCheck(String component, Subject subject) {
        try{
            int digit = Integer.parseInt(component);
            if (digit == 1 || digit == 2 || digit == 3 || digit == 4){
                subject.setCredit(digit);
            }else{
                throw new IllegalArgumentException("과목학점을 잘못 입력하셨습니다.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }

    // 과목성적(component[2]) 검사
    public void gradeCheck(Application app, String[] component, Subject subject, boolean majorSubject) {
        try {
            if (component[2].equals("P") || component[2].equals("NP")) {
                String grade = app.calc_PF_GradeStringToDouble(component[2]);
                if (grade.equals("wrong value")) {
                    throw new IllegalArgumentException("과목성적을 잘못 입력하셨습니다.");
                }
                if (grade.equals("Pass")){
                    app.passCredit += Integer.parseInt(component[1]);
                }
            } else {
                double grade = app.calcGradeStringToDouble(component[2]);
                if (grade == -1) {
                    System.out.println(component[2] + "grade == " + grade);
                    throw new IllegalArgumentException("과목성적을 잘못 입력하셨습니다.");
                }
                if (grade == 0){
                    app.failCredit += Integer.parseInt(component[1]);
                }
                else if (majorSubject){
                    app.total_major_Credit += Integer.parseInt(component[1]);
                    app.totalMajorAverageRating += subject.getCredit() * grade;
                }else{
                    app.total_liberalArts_Credit += Integer.parseInt(component[1]);
                    app.totalLiberalArtsAverageRating += subject.getCredit() * grade;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        subject.setGrade(component[2]);
    }

    public double calcGradeStringToDouble(String grade) {
        switch (grade){
            case "A+":
                return 4.5;
            case "A0":
                return 4.0;
            case "B+":
                return 3.5;
            case "B0":
                return 3.0;
            case "C+":
                return 2.5;
            case "C0":
                return 2.0;
            case "D+":
                return 1.5;
            case "D0":
                return 1.0;
            case "F":
                return 0;
            default:
                return -1;
        }
    }

    public String calc_PF_GradeStringToDouble(String grade) {
        switch (grade) {
            case "P":
                return "Pass";
            case "NP":
                return "Not Passed";
            default:
                return "wrong value";
        }
    }

    public void printAll(Application app) {
        System.out.println("\n<취득학점>");
        System.out.println(app.total_major_Credit + app.total_liberalArts_Credit + app.passCredit + "학점");

        System.out.println("\n<평점평균>");
        System.out.println(Math.round((app.totalMajorAverageRating + app.totalLiberalArtsAverageRating) / (app.total_major_Credit + app.total_liberalArts_Credit + app.failCredit)*100)/100.0 + " / 4.5");

        System.out.println("\n<전공 평점평균>");
        System.out.println(Math.round((app.totalMajorAverageRating / app.total_major_Credit)*100)/100.0  + " / 4.5");
    }
}