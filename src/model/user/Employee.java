package model.user;

import java.util.Date;

public class Employee extends Person {

    //? Add Enum features, employment(String) can only be one of the options from employment(enum)
    public enum employment {CLEANER, LOGISTIC, CUSTOMER_SERVICE, BOOKLISTER};
    private String employment;
    private Date recluitmentDate;


    public Employee(String name, String surname, String employment) {
        super(name, surname);
        this.employment = employment;
        this.recluitmentDate = new Date();
    }


    @Override
    public String getUserValues() {
        return this.getMail() + ", "
             + this.getPassword() + ", "
             + this.getName() + ", "
             + this.getSurname() + ", "
             + this.getEmployment() + ", "
             + this.getAddress() + ", "
             + this.getPhoneNumber() + ", "
             + this.getBirthday();
    }


    //* Getters
    public String getEmployment() {return this.employment;}

    public Date getRecluitmentDate() {return this.recluitmentDate;}


    //* Setters
    public void setEmployment(String employment) {this.employment = employment;}

    public void setRecluitmentDate(Date recluitmentDate) {this.recluitmentDate = recluitmentDate;}

}