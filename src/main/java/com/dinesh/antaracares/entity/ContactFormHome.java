package com.dinesh.antaracares.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "contact_form_home")
public class ContactFormHome {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "gender")
    private String gender;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "message")
    private String message;

    @Column(name = "referral")
    private String referral;

    public ContactFormHome() {
    }

    public ContactFormHome(String name, String email, String mobile, String gender, String country, String city, String nationality, String message, String referral) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.gender = gender;
        this.country = country;
        this.city = city;
        this.nationality = nationality;
        this.message = message;
        this.referral = referral;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReferral() {
        return referral;
    }

    public void setReferral(String referral) {
        this.referral = referral;
    }

    @Override
    public String toString() {
        return "ContactFormHome{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", gender='" + gender + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", nationality='" + nationality + '\'' +
                ", message='" + message + '\'' +
                ", referral='" + referral + '\'' +
                '}';
    }
}
