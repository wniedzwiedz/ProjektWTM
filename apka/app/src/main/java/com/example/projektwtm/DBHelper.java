package com.example.projektwtm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.projektwtm.modele.User;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

public class DBHelper extends OrmLiteSqliteOpenHelper {

    static String DB_NAME = "user_manager.db";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource cs) {
        try {
            TableUtils.createTable(cs, User.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource cs, int oldVersion, int newVersion) {

    }

    public void addUser(int id, String firstname, String surname, String email, String passwordHash) {

    }

    public List<User> getUser() throws SQLException {
        Dao<User, ?> dao = getDao(User.class);
        return dao.queryForAll();
    }

    public User getUserById(Object aId) throws SQLException {
        Dao<User, Object> dao = getDao(User.class);
        return dao.queryForId(aId);
    }

    public Dao.CreateOrUpdateStatus createOrUpdateUser(User obj) throws SQLException {
        Dao<User, ?> dao = getDao(User.class);
        return dao.createOrUpdate(obj);
    }

    public int deleteUserById(Object aId) throws SQLException {
        Dao<User, Object> dao = getDao(User.class);
        return dao.deleteById(aId);
    }

}
