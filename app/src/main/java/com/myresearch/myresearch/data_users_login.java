package com.myresearch.myresearch;

public class data_users_login {
    String email;
    String password;
    String time;
    String key;
    String name;
    String phone;
    String TheUniversity;
    String studyGroup;

    public data_users_login() {
    }

    public data_users_login(String email, String password, String time, String key, String name, String phone, String theUniversity, String studyGroup) {
        this.email = email;
        this.password = password;
        this.time = time;
        this.key = key;
        this.name = name;
        this.phone = phone;
        TheUniversity = theUniversity;
        this.studyGroup = studyGroup;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTheUniversity() {
        return TheUniversity;
    }

    public void setTheUniversity(String theUniversity) {
        TheUniversity = theUniversity;
    }

    public String getStudyGroup() {
        return studyGroup;
    }

    public void setStudyGroup(String studyGroup) {
        this.studyGroup = studyGroup;
    }
}