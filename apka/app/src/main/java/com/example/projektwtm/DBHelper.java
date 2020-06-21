package com.example.projektwtm;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.projektwtm.modele.Application;
import com.example.projektwtm.modele.Group;
import com.example.projektwtm.modele.Package;
import com.example.projektwtm.modele.Payment;
import com.example.projektwtm.modele.User;
import com.example.projektwtm.modele.UserInGroup;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

public class DBHelper extends OrmLiteSqliteOpenHelper {

    static String DB_NAME = "user_manager.db";
//    static String USER_TABLE_NAME = "User";
//    static String USER_TABLE_COLUMN_ID = "id";
//    static String USER_TABLE_COLUMN_FIRSTNAME = "firstname";
//    static String USER_TABLE_COLUMN_SURNAME = "surname";
//    static String USER_TABLE_COLUMN_EMAIL = "email";
//    static String USER_TABLE_COLUMN_PASSWORD_HASH = "password_hash";

//    static String SQL_CREATE_TABLE = "CREATE TABLE " + USER_TABLE_NAME + "(" +
//            USER_TABLE_COLUMN_ID + " INTEGER PRIMARY KEY, " +
//            USER_TABLE_COLUMN_FIRSTNAME + " TEXT NOT NULL, " +
//            USER_TABLE_COLUMN_SURNAME + " TEXT NOT NULL, " +
//            USER_TABLE_COLUMN_EMAIL + " TEXT NOT NULL, " +
//            USER_TABLE_COLUMN_PASSWORD_HASH + " TEXT NOT NULL)";

//    static String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + USER_TABLE_NAME;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource cs) {
        try {
            TableUtils.createTable(cs, User.class);
            TableUtils.createTable(cs, Application.class);
            TableUtils.createTable(cs, Package.class);
            TableUtils.createTable(cs, Group.class);
            TableUtils.createTable(cs, Payment.class);
            TableUtils.createTable(cs, UserInGroup.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource cs, int oldVersion, int newVersion) {

    }

    public void addUser(int id, String firstname, String surname, String email, String passwordHash) {

    }

    public List<User> getUser(Class<User> userClass) throws SQLException {
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
