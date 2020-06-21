package com.example.projektwtm.modele;

import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Objects;

public class UserInGroupId implements Serializable {

    private User user;

    private Group group;

    public UserInGroupId() {
    }

    public UserInGroupId(User user, Group group) {
        this.user = user;
        this.group = group;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserInGroupId)) return false;
        UserInGroupId that = (UserInGroupId) o;
        return Objects.equals(getUser(), that.getUser()) &&
                Objects.equals(getGroup(), that.getGroup());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser(), getGroup());
    }

}
