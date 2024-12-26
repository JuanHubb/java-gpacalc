package gpacalc;

public class Subject {
    private String title;
    private int credit;
    private String grade;

    public Subject(){
    }
    public Subject(String title, int credit, String grade) {
        this.title = title;
        this.credit = credit;
        this.grade = grade;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
