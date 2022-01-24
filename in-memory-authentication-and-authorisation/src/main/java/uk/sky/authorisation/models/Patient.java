package uk.sky.authorisation.models;

public class Patient {

    private int id;
    private String firstName;
    private int age;
    private String illness;

    public Patient(int id, String firstName, int age, String illness) {
        this.id = id;
        this.firstName = firstName;
        this.age = age;
        this.illness = illness;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getIllness() {
        return illness;
    }

    public void setIllness(String illness) {
        this.illness = illness;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", age=" + age +
                ", illness='" + illness + '\'' +
                '}';
    }
}
