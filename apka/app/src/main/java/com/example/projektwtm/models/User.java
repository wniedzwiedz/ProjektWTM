package com.example.projektwtm.models;

import java.util.ArrayList;
import java.util.List;

public class User {

    private int id;

    private String firstName;

    private String lastName;

    private String passwordHash;

    private String email;

    List<Payment> payments;

    List<Group> myGroups;

    List<UserInGroup> userInGroups;

    public User(String firstName, String lastName, String email, String passwordHash) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passwordHash = passwordHash;
        payments = new ArrayList<>();
        myGroups = new ArrayList<>();
        userInGroups = new ArrayList<>();
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public List<Group> getMyGroups() {
        return myGroups;
    }

    public void setMyGroups(List<Group> myGroups) {
        this.myGroups = myGroups;
    }

    public List<UserInGroup> getUserInGroups() {
        return userInGroups;
    }

    public void setUserInGroups(List<UserInGroup> userInGroups) {
        this.userInGroups = userInGroups;
    }
}
