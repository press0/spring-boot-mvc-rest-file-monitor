package bmw.model;

public class Registration {

    private Claass claass;
    private String professor;
    private int studentId;

    public Registration() {}

    public Registration(Claass claass, String professor, int studentId) {
        this.claass = claass;
        this.professor = professor;
        this.studentId = studentId;
    }

    public Claass getClaass() {
        return claass;
    }

    public void setClaass(Claass aclass) {
        this.claass = aclass;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    @Override
    public String toString() {
        return "Registration{" +
                "class=" + claass +
                ", professor='" + professor + '\'' +
                ", studentId=" + studentId +
                '}';
    }
}

