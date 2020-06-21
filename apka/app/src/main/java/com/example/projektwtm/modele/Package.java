package com.example.projektwtm.modele;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.List;

@DatabaseTable(tableName = "package_entity")
public class Package implements Serializable {

    @DatabaseField(id = true, canBeNull = false, columnName = "id")
    private int id;

    @DatabaseField(columnName = "name")
    private String name;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Application application;

    private List<Group> groups;

    public Package() {
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

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}
