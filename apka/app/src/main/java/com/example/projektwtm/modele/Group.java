package com.example.projektwtm.modele;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.List;

@DatabaseTable(tableName = "group_entity")
public class Group implements Serializable {

    @DatabaseField(id = true, canBeNull = false, columnName = "id")
    private int id;

    @DatabaseField(columnName = "name")
    private String name;

    @DatabaseField(columnName = "bank_account_number")
    private String bankAccountNumber;

    @DatabaseField(columnName = "max_number_of_members")
    private int maxNumberOfMembers;

    @DatabaseField(columnName = "login_hash")
    private String loginHash;

    @DatabaseField(columnName = "password_hash")
    private String passwordHash;

    @DatabaseField(columnName = "group_hash")
    private String groupHash;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Package aPackage;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private User owner;

    private List<Payment> payments;

    private List<UserInGroup> userInGroups;

    public Group() {
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

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public int getMaxNumberOfMembers() {
        return maxNumberOfMembers;
    }

    public void setMaxNumberOfMembers(int maxNumberOfMembers) {
        this.maxNumberOfMembers = maxNumberOfMembers;
    }

    public String getLoginHash() {
        return loginHash;
    }

    public void setLoginHash(String loginHash) {
        this.loginHash = loginHash;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getGroupHash() {
        return groupHash;
    }

    public void setGroupHash(String groupHash) {
        this.groupHash = groupHash;
    }

    public Package getaPackage() {
        return aPackage;
    }

    public void setaPackage(Package aPackage) {
        this.aPackage = aPackage;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public List<UserInGroup> getUserInGroups() {
        return userInGroups;
    }

    public void setUserInGroups(List<UserInGroup> userInGroups) {
        this.userInGroups = userInGroups;
    }
}
