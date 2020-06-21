package com.example.projektwtm.modele;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "user_in_group")
public class UserInGroup implements Serializable {

//    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private UserInGroupId id;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private User user;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
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
