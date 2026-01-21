package challenge1;

public class Customer {

    private String name;
    private String email;
    private String grade;

    public Customer(String name, String email, String grade) {
        this.name = name;
        this.email = email;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getGrade() {
        return grade;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}