package facultypkg;

public class Faculty {
    private String name;
    private int facultyId;
    private String subject;

    public Faculty(String name, int facultyId, String subject) {
        this.name = name;
        this.facultyId = facultyId;
        this.subject = subject;
    }

    public void showDetails() {
        System.out.println("---- Faculty Details ----");
        System.out.println("Name       : " + name);
        System.out.println("Faculty ID : " + facultyId);
        System.out.println("Subject    : " + subject);
    }
}
