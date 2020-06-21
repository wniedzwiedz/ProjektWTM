package com.example.projektwtm.modele;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Date;

@DatabaseTable(tableName = "payment_entity")
public class Payment implements Serializable {

    @DatabaseField(id = true, canBeNull = false, columnName = "id")
    private int id;

    @DatabaseField(columnName = "deadline")
    private Date deadline;

    @DatabaseField(columnName = "price")
    private double price;

    @DatabaseField(columnName = "is_paid")
    private boolean isPaid;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    User user;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    Group group;

    public Payment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
