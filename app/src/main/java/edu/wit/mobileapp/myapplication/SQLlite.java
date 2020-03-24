package edu.wit.mobileapp.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

public class SQLlite extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "database";
    private static int DATABASE_VERSION = 12;


    public SQLlite(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
            db.execSQL(
                    "create table IF NOT EXISTS courses " +
                            "(courseName text primary key, classType text)"
            );
            db.execSQL("create table IF NOT EXISTS assignments" +
                    "(assignmentName text primary key, priority text, courseName text, dueDate date, assignmentType text)");
            db.execSQL("create table IF NOT EXISTS year" + " (id integer primary key, yearSplit integer, schoolName text,yearOfSchool integer)");
    }

    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS courses");
        database.execSQL("DROP TABLE IF EXISTS assignments");
        database.execSQL("DROP TABLE IF EXISTS year");
        onCreate(database);
    }
    public boolean insertCourse(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("courseName", name);
        db.insert("courses", null, contentValues);
        return true;
    }
    public boolean insertAssignment (String name, String courseName, String date, String assignmentType, String priority) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("assignmentName", name);
        contentValues.put("priority", priority);
        contentValues.put("courseName", courseName);
        contentValues.put("dueDate", date);
        contentValues.put("assignmentType", assignmentType);


        db.insert("assignments", null, contentValues);
        return true;
    }
    public boolean insertYear(Integer yearSplit, String schoolName, Integer yearOfSchool) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("yearSplit", yearSplit);
        contentValues.put("schoolName", schoolName);
        contentValues.put("yearOfSchool", yearOfSchool);
        db.insert("year", null, contentValues);
        return true;
    }
    public Cursor getCourse(String courseName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from courses where courseName="+courseName+"", null );
        return res;
    }
    public ArrayList getCourseNames() {
        ArrayList<String> array_list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select courseName from courses", null);

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex("courseName")));
            res.moveToNext();
        }
        return array_list;
    }
    public Cursor getYear() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from year", null );
        return res;
    }
    public Integer deleteCourse (String courseName) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("courses",
                "courseName = ? ",
                new String[] { courseName });
    }
    public Integer deleteAssignment (String assignmentName) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("assignments",
                "assignmentName = ? ",
                new String[] { assignmentName });
    }
    public ArrayList<String> getAllAssignments() {
        ArrayList<String> array_list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from assignments", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex("assignmentName")));
            array_list.add(res.getString(res.getColumnIndex("courseName")));
            array_list.add(res.getString(res.getColumnIndex("dueDate")));
            array_list.add(res.getString(res.getColumnIndex("assignmentType")));
            array_list.add(res.getString(res.getColumnIndex("priority")));
            res.moveToNext();
        }
        return array_list;
    }
    public static SQLlite dbHelper(Context context, String path) {
        SQLiteDatabase db;
        db = SQLiteDatabase.openOrCreateDatabase(path, null);
        final SQLlite dbHelper = new SQLlite(context);
        if (!doesExist(context)) {
            dbHelper.onCreate(db);
        }
        else {
            //dbHelper.onUpgrade(db, 1, DATABASE_VERSION);
            //DATABASE_VERSION++;
        }
        return dbHelper;
    }

    public static boolean doesExist(Context context){
        boolean ifExists;
        File f = context.getDatabasePath(DATABASE_NAME);
        if (f.exists()) {
            ifExists = true;
        }
        else {
            ifExists = false;
        }
        return ifExists;
    }
}