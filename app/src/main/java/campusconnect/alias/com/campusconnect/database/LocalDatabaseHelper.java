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


    // Subject Helper Methods (Add , Delete and Check if exists)

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


    // Module Helper Methods (Add , Delete and Check if exists)

    public void addModule(int moduleId, String moduleName, int subjectId) {
        MyModules modTemp = new MyModules();
        modTemp.setModuleId(moduleId);
        modTemp.setModuleName(moduleName);
        modTemp.setSubjectCRN(subjectId);
        cupboard().withDatabase(db).put(modTemp);
    }

    public void deleteModule(int moduleId) {
        cupboard().withDatabase(db).delete(MyModules.class, "moduleId = ?", String.valueOf(moduleId));

    }

    public boolean doesModuleExist(int moduleId) {
        MyModules module;
        module = cupboard().withDatabase(db)
                .query(MyModules.class)
                .withSelection("moduleId = ?", String.valueOf(moduleId))
                .get();
        if (module != null)
            return true;
        return false;
    }

    public void moduleCompleted(int moduleId){
        MyModules module;
        module = cupboard().withDatabase(db)
                .query(MyModules.class)
                .withSelection("moduleId = ?", String.valueOf(moduleId))
                .get();
        module.setComplete(true);
    }


    // Request Helper Methods (Add , Delete and check if exists)

    public void addRequest(int subjectId, String subjectName) {
        MySubjects subTemp = new MySubjects();
        subTemp.setSubjectCRN(subjectId);
        subTemp.setSubjectName(subjectName);
        cupboard().withDatabase(db).put(subTemp);
    }


    public void deleteRequest(int subjectId, String subjectName) {
        MySubjects subTemp = new MySubjects();
        subTemp.setSubjectCRN(subjectId);
        subTemp.setSubjectName(subjectName);
        cupboard().withDatabase(db).put(subTemp);
    }


//    public boolean doesRequestExist(int moduleId, int userId){}
//

    public boolean doesModulesExist(int subjectId) {
        MyModules module = cupboard().withDatabase(db)
                .query(MyModules.class)
                .withSelection("subjectCRN = ?", String.valueOf(subjectId))
                .get();
        if (module != null)
            return true;
        return false;
    }

    public void deleteModulesBySubjectId(int subjectId){
        cupboard().withDatabase(db).delete(MyModules.class, "subjectCRN = ?", String.valueOf(subjectId));

    }
//    public String getModuleNameById(int moduleId){}

//  public String getModuleNameById(int moduleId){}
//
//    public String getSubjectNameById(int subjectId){}
}