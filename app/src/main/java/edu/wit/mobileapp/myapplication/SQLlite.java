package edu.wit.mobileapp.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
        db.execSQL("create table assignments" +" (id integer primary key, assignmentName text, dueDate date, courseName text)");
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
        contentValues.put("phone", classType);;
        db.insert("courses", null, contentValues);
        return true;
    }
    public boolean insertAssignment (String name, String courseName, Date date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", courseName);
        contentValues.put("date", date.toString());
        db.insert("assignment", null, contentValues);
        return true;
    }
    public Cursor getCourses(String courseName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from courses where courseName="+courseName+"", null );
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

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from assignments", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex("courseName")));
            res.moveToNext();
        }
        return array_list;
    }
}

