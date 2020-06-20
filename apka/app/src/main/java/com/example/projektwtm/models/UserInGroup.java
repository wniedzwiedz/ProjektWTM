package com.example.projektwtm.models;


public class UserInGroup {

    private UserInGroupId id;

    private User user;

    private Group group;

    private boolean isConfirmed;

    public UserInGroupId getId() {
        return id;
    }

    public void setId(UserInGroupId id) {
        this.id = id;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }
}
