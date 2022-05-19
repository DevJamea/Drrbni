package com.example.drrbni.Models;

public class Student {
    private String email, img, name, major, university, governorate, address, linkWhatsApp, userId;
    private int typeUser;

    public Student(String email, String img, String name, String specialization, String university, String governorate, String address, String linkWhatsApp, String userId, int typeUser) {
        this.email = email;
        this.img = img;
        this.name = name;
        this.major = specialization;
        this.university = university;
        this.governorate = governorate;
        this.address = address;
        this.linkWhatsApp = linkWhatsApp;
        this.userId = userId;
        this.typeUser = typeUser;
    }

    public Student() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getGovernorate() {
        return governorate;
    }

    public void setGovernorate(String governorate) {
        this.governorate = governorate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLinkWhatsApp() {
        return linkWhatsApp;
    }

    public void setLinkWhatsApp(String linkWhatsApp) {
        this.linkWhatsApp = linkWhatsApp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(int typeUser) {
        this.typeUser = typeUser;
    }
}
