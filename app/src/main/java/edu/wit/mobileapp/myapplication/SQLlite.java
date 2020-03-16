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

    public SQLlite(Context context){
        super(context,DATABASE_NAME,null,1);
    }

    public void onCreate(SQLiteDatabase db) {
            db.execSQL(
                    "create table courses " +
                            "(courseName text primary key, classType text)"
            );
            db.execSQL("create table assignments" +
                    "(assignmentName text primary key, priority text, courseName text, dueDate date, assignmentType text)");
            db.execSQL("create table year" + " (id integer primary key, yearSplit integer, schoolName text,yearOfSchool integer)");

    }
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS courses");
        database.execSQL("DROP TABLE IF EXISTS assignments");
        onCreate(database);
    }
    public boolean insertCourse (String name, String classType) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("classType", classType);;
        db.insert("courses", null, contentValues);
        return true;
    }
    public boolean insertAssignment (String name, String courseName, String date, String assignmentType, String priority) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("assignmentName", name);
        contentValues.put("courseName", courseName);
        contentValues.put("date", date.toString());
        contentValues.put("assignmentType", assignmentType);
        contentValues.put("priority", priority);

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
    public Cursor getCourses(String courseName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from courses where courseName="+courseName+"", null );
        return res;
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
            array_list.add(res.getString(res.getColumnIndex("date")));
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
        File f = context.getDatabasePath(DATABASE_NAME);
        long dbSize = f.length();
        if (dbSize == 0) {
            dbHelper.onCreate(db);
        }

        return dbHelper;
    }
}