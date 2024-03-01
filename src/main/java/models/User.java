package models;

import toolkit.PasswordEncryptor;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 *
 *   id
 *  first name
 * last name
 *  password
 *  email
 *  phone nbr
 * area in tunisia (Ariana,Tunis..)
 * role (participant, donor, volunteer)
 */
public class User {

    private int id ;
    private String firstName;
    private String lName;

    private String pwd;
    private String email;

    private int phone ;

    private String area ;

    private String role;



    private int code;


    /**
     * Constructor for User
     *
     * @param fname first name
     * @param lName last name
     * @param pwd password
     * @param email email
     * @param number phone nbr
     * @param area area in tunisia (Ariana,Tunis..)
     * @param role role (participant, donor, volunteer)
     */
    public User( String fname, String lName, String pwd, String email, int number, String area, String role) {
        this.firstName = fname;
        this.lName = lName;
        this.pwd = pwd;
        this.email = email;
        this.phone = number;
        this.area = area;
        this.role = role;
    }

//métier ban user: ts les users sont actif mais qd ban devient inactif
    public User(){}






    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User)  o;
        return getId() == user.getId() &&
                getPhone() == user.getPhone() &&
                Objects.equals(getFirstName(), user.getFirstName()) &&
                Objects.equals(getLName(), user.getLName()) &&
                Objects.equals(getPwd(), user.getPwd()) && Objects.equals(getEmail(), user.getEmail()) && Objects.equals(getArea(), user.getArea()) && Objects.equals(getRole(), user.getRole());
    }



    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lName + '\'' +
                ", pwd='" + pwd + '\'' +
                ", email='" + email + '\'' +
                ", number=" + phone +
                ", area=" + area +
                ", role='" + role + '\'' +
                '}';
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

    public String getLName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int number) {
        this.phone = number;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }





}

