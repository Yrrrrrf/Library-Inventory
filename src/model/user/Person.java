package model.user;

public abstract class Person {

    private int id;
    private String name, surname, address, phoneNumber, mail, password;
    private String birthday;


    // Constructor method
    Person(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    // Constructor method
    Person(String name, String surname, String mail) {
        this.name = name;
        this.surname = surname;
    }

    // abstract method
    // An abstract method is  obligaroty to be implementated in the daugther class
    public abstract String getUserValues();


    public static boolean verifyPhoneLenght(String phoneNumber) {


        return true;
    }


    //* Getters
    public int getId() {return this.id;}

    public String getName() {return this.name;}

    public String getSurname() {return this.surname;}

    public String getAddress() {return this.address;}

    public String getBirthday() {return this.birthday;}

    public String getPhoneNumber() {return this.phoneNumber;}

    public String getMail() {return this.mail;}

    public String getPassword() {return this.password;}


    //* Setters
    public void setId(int id) {this.id = id;}

    public void setName(String name) {this.name = name;}

    public void setSurname(String surname) {this.surname = surname;}

    public void setAddress(String address) {this.address = address;}

    public void setBirthday(String birthday) {this.birthday = birthday;}

    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}

    public void setMail(String mail) {this.mail = mail;}

    public void setPassword(String password) {this.password = password;}

}