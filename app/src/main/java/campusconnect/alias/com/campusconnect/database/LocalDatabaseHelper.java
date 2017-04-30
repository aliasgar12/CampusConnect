package campusconnect.alias.com.campusconnect.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import campusconnect.alias.com.campusconnect.cupboardDB.MyModules;
import campusconnect.alias.com.campusconnect.cupboardDB.MyRequests;
import campusconnect.alias.com.campusconnect.cupboardDB.MySubjects;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by alias on 4/28/2017.
 */


public class LocalDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "localDatabase.db";
    private static final int DATABASE_VERSION = 1;


    public LocalDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    static {
        // register our models
        cupboard().register(MySubjects.class);
        cupboard().register(MyModules.class);
        cupboard().register(MyRequests.class);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // this will ensure that all tables are created
        cupboard().withDatabase(db).createTables();
        // add indexes and other database tweaks in this method if you want

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this will upgrade tables, adding columns and new tables.
        // Note that existing columns will not be converted
        cupboard().withDatabase(db).createTables();
        // do migration work if you have an alteration to make to your schema here

    }

//    public String getModuleNameById(int moduleId){}
//
//    public String getSubjectNameById(int subjectId){}
//
//    public boolean doesSubjectExist(int subjectId){}
//
//    public boolean doesRequestExist(int moduleId, int userId){}
//
//    public boolean doesModuleExist(int moduleId){}


}