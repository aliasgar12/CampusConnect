package campusconnect.alias.com.campusconnect.database;

import android.content.ContentValues;
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
    private SQLiteDatabase db;


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

    public void open() {
        db = this.getWritableDatabase();
    }


    // Subject Helper Methods
    // (Add , Delete and Check if exists)

    public void addSubject(int subjectId, String subjectName) {
        MySubjects subTemp = new MySubjects();
        subTemp.setSubjectCRN(subjectId);
        subTemp.setSubjectName(subjectName);
        cupboard().withDatabase(db).put(subTemp);
    }

    public void deleteSubject(int subjectId) {
        cupboard().withDatabase(db).delete(MySubjects.class, "subjectCRN = ?", String.valueOf(subjectId));
    }

    public boolean doesSubjectExist(int subjectId) {
        MySubjects subject;
        subject = cupboard().withDatabase(db)
                .query(MySubjects.class)
                .withSelection("subjectCRN = ?", String.valueOf(subjectId))
                .get();
        if (subject != null)
            return true;
        return false;
    }

    public String getSubjectNameByModuleId(int moduleId) {
        int subId = getSubjectCrnByModuleId(moduleId);
        String subName = getSubjectNameByCrn(subId);
        if (subName != null)
            return subName;
        return "";
    }

    public int getSubjectCrnByModuleId(int moduleId) {
        MyModules module = cupboard().withDatabase(db)
                .query(MyModules.class)
                .withSelection("moduleId = ?", String.valueOf(moduleId))
                .get();
        if (module != null)
            return module.getSubjectCRN();
        return 0;
    }

    public String getSubjectNameByCrn(int subjectId) {
        MySubjects subject;
        subject = cupboard().withDatabase(db)
                .query(MySubjects.class)
                .withSelection("subjectCRN = ?", String.valueOf(subjectId))
                .get();
        if (subject != null)
            return subject.getSubjectName();
        return "";
    }


    // Module Helper Methods
    // (Add , Delete and other helper methods)

    public void addModule(int moduleId, String moduleName, int subjectId) {
        MyModules modTemp = new MyModules();
        modTemp.setModuleId(moduleId);
        modTemp.setModuleName(moduleName);
        modTemp.setSubjectCRN(subjectId);
        cupboard().withDatabase(db).put(modTemp);
    }


    public void moduleCompleted(int moduleId) {
        ContentValues values = new ContentValues(1);
        values.put("isComplete", 1);
        cupboard().withDatabase(db).update(MyModules.class, values, "moduleId = ?", String.valueOf(moduleId));
    }

    public void moduleIncomplete(int moduleId) {
        ContentValues values = new ContentValues(1);
        values.put("isComplete", 0);
        cupboard().withDatabase(db).update(MyModules.class, values, "moduleId = ?", String.valueOf(moduleId));
    }

    public boolean doesModulesExist(int subjectId) {
        MyModules module = cupboard().withDatabase(db)
                .query(MyModules.class)
                .withSelection("subjectCRN = ?", String.valueOf(subjectId))
                .get();
        if (module != null)
            return true;
        return false;
    }

    public void deleteModulesBySubjectId(int subjectId) {
        cupboard().withDatabase(db).delete(MyModules.class, "subjectCRN = ?", String.valueOf(subjectId));
    }

    public String getModuleNameById(int moduleId) {
        MyModules module = cupboard().withDatabase(db)
                .query(MyModules.class)
                .withSelection("moduleId = ?", String.valueOf(moduleId))
                .get();
        if (module != null)
            return module.getModuleName();
        return "";
    }

    public MyModules getModule(int moduleId) {
        MyModules module = cupboard().withDatabase(db)
                .query(MyModules.class)
                .withSelection("moduleId = ?", String.valueOf(moduleId))
                .get();
        if (module != null)
            return module;
        return null;
    }


    // Request Helper Methods
    // (Add , Delete and other helper methods)

    public void addRequest(int moduleId, int toUserId) {
        MyRequests reqTemp = new MyRequests();
        reqTemp.setModuleId(moduleId);
        reqTemp.setToUserId(toUserId);
        reqTemp.setModuleName(getModuleNameById(moduleId));
        cupboard().withDatabase(db).put(reqTemp);
    }

    public void deleteRequest(int moduleId, int toUserId) {
        cupboard().withDatabase(db).delete(MyRequests.class, "moduleId = ? and toUserId = ?", String.valueOf(moduleId), String.valueOf(toUserId));

    }

    public boolean doesRequestExist(int moduleId, int toUserId) {
        MyRequests request = cupboard().withDatabase(db)
                .query(MyRequests.class)
                .withSelection("moduleId = ? and toUserId = ?", String.valueOf(moduleId), String.valueOf(toUserId))
                .get();
        if (request != null)
            return true;
        return false;
    }

}