package gpacalc;

import java.util.ArrayList;
import camp.nextstep.edu.missionutils.Console;

public class Application {
    ArrayList<Subject> majorList = new ArrayList<>();
    ArrayList<Subject> liberalArtsList = new ArrayList<>();
    double totalMajorAverageRating = 0;
    double totalLiberalArtsAverageRating = 0;
    int totalMajorCredit = 0;
    int totalLiberalArtsCredit = 0;
    int passCredit = 0;
    int failCredit = 0;

    public static void main(String[] args) {
        Application app = new Application();
        app. run(app);
    }

    public void run(Application app) {
        System.out.println("전공 과목명과 이수학점, 평점을 입력해주세요(예시: 프로그래밍언어론-3-A+,소프트웨어공학-3-B+):");
        app.input(app, app.majorList, true);

        System.out.println("교양 과목명과 이수학점, 평점을 입력해주세요(예시: 선형대수학-3-C0,인간관계와자기성장-3-P):");
        app.input(app, app.liberalArtsList, false);

        System.out.println("\n<과목 목록>");
        app.printSubjects("[전공] ", app.majorList);
        app.printSubjects("[교양] ", app.liberalArtsList);

        app.printCalcResult(app);
    }

    public void input(Application app, ArrayList<Subject> subjectList, boolean majorSubjectStatus) {
        String[] beforeSplitInput = Console.readLine().split(",");

        app.creatingObject(app, beforeSplitInput, subjectList, majorSubjectStatus);
    }

    // input 나누는 것이 메소드를 최소 단위로 잘 나눈 것인지 의문
    public void creatingObject(Application app, String[] beforeSplitInput, ArrayList<Subject> subjectList, boolean majorSubjectStatus) {
        String[] subjectComponent;

        for (String eachSubject : beforeSplitInput) {
            subjectComponent = eachSubject.trim().split("-");
            Subject subject = new Subject(subjectComponent[0],Integer.parseInt(subjectComponent[1]),subjectComponent[2]);
            subjectComponentChecker(app, subject, majorSubjectStatus);

            subjectList.add(subject);
        }
    }

    public void subjectComponentChecker(Application app, Subject subject, boolean majorSubjectStatus){
        app.titleCheck(subject.getTitle(), subject);
        app.creditCheck(subject.getCredit(),subject);
        app.gradeCheck(app,subject, majorSubjectStatus);
    }

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

    public void creditCheck(int digit, Subject subject) {
        try{
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

    public void gradeCheck(Application app, Subject subject, boolean majorSubject) {
        try {
            if (subject.getGrade().equals("P") || subject.getGrade().equals("NP")) {
                String grade = app.calcPAndFGradeStringToDouble(subject.getGrade());
                if (grade.equals("wrong value")) {
                    throw new IllegalArgumentException("과목성적을 잘못 입력하셨습니다.");
                }
                if (grade.equals("Pass")){
                    app.passCredit += subject.getCredit();
                }
            } else {
                double grade = app.calcGradeStringToDouble(subject.getGrade());
                if (grade == -1) {
                    System.out.println(subject.getGrade() + "grade == " + grade);
                    throw new IllegalArgumentException("과목성적을 잘못 입력하셨습니다.");
                }
                if (grade == 0){
                    app.failCredit += subject.getCredit();
                }
                else if (majorSubject){
                    app.totalMajorCredit += subject.getCredit();
                    app.totalMajorAverageRating += subject.getCredit() * grade;
                }else{
                    app.totalLiberalArtsCredit += subject.getCredit();
                    app.totalLiberalArtsAverageRating += subject.getCredit() * grade;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
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

    public String calcPAndFGradeStringToDouble(String grade) {
        switch (grade) {
            case "P":
                return "Pass";
            case "NP":
                return "Not Passed";
            default:
                return "wrong value";
        }
    }

    public void printSubjects(String subjectType, ArrayList<Subject> subjectList) {
        for(Subject eachSubject : subjectList){
            System.out.println(subjectType + " " + eachSubject.getTitle() + "," + eachSubject.getCredit() + "," + eachSubject.getGrade());
        }
    }

    public void printCalcResult(Application app) {
        System.out.println("\n<취득학점>");
        System.out.println(app.totalMajorCredit + app.totalLiberalArtsCredit + app.passCredit + "학점");

        System.out.println("\n<평점평균>");
        System.out.println(Math.round((app.totalMajorAverageRating + app.totalLiberalArtsAverageRating) / (app.totalMajorCredit + app.totalLiberalArtsCredit + app.failCredit)*100)/100.0 + " / 4.5");

        System.out.println("\n<전공 평점평균>");
        System.out.println(Math.round((app.totalMajorAverageRating / app.totalMajorCredit)*100)/100.0  + " / 4.5");
    }
}