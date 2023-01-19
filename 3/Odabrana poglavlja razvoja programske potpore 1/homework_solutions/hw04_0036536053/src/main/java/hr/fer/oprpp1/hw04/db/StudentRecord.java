package hr.fer.oprpp1.hw04.db;

import java.util.Objects;
/**
 * functional interface [StudentRecord] represents object representation of a student which is described in row of database.txt
 * 
 * @author Jure Rajcic
 */
public class StudentRecord {
    private String jmbag;
    private String lastName;
    private String firstName;
    private String finalGrade;


    public StudentRecord(String jmbag, String lastName, String firstName, String finalGrade) {
        this.jmbag = jmbag;
        this.lastName = lastName;
        this.firstName = firstName;
        this.finalGrade = finalGrade;
    }

    public StudentRecord(String[] atributes){
        this(atributes[0], atributes[1], atributes[2], atributes[3]);
    }



    public String getJmbag() {
        return this.jmbag;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getFinalGrade() {
        return this.finalGrade;
    }


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof StudentRecord)) {
            return false;
        }
        StudentRecord studentRecord = (StudentRecord) o;
        return this.jmbag.equals(studentRecord.jmbag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jmbag, lastName, firstName, finalGrade);
    }

    @Override
    public String toString() {
        return String.format("jmbag: %s, lastName: %s, firstName: %s", jmbag, lastName, firstName);
    }
    
}
