package edu.wit.mobileapp.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
private String TAG = "MAIN";
EditText yearText;
EditText schoolText;
Button quarterlyButton;
Button semesterlyButton;
Button trimesterlyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information_entry_layout);
        //Set DB PATH
        String path = "/data/data/" + getPackageName() + "/database.db";
        SQLiteDatabase db;
        //Open or Create DB
        db = SQLiteDatabase.openOrCreateDatabase(path, null);
        //Get Application context then use it in SQLlite helper class
        Context context = getApplicationContext();
        final SQLlite dbHelper = new SQLlite(context);
        dbHelper.onCreate(db);

        yearText = findViewById(R.id.editText1);

        schoolText = findViewById(R.id.editText2);

        quarterlyButton = findViewById(R.id.button);

        semesterlyButton = findViewById(R.id.button2);

        trimesterlyButton = findViewById(R.id.button3);

        Button button= (Button) findViewById(R.id.button4);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get String values of UI elements after done button has been pressed
                String year = yearText.getText().toString();
                String school = schoolText.getText().toString();
                int semesters;
                if (quarterlyButton.isActivated()){
                    semesters = 4;
                }
                else if (semesterlyButton.isActivated()) {
                    semesters = 2;
                }
                else {
                    semesters = 3;
                }
                dbHelper.insertYear(semesters, school, year);
                Log.d(TAG, dbHelper.getYear().toString());
                /** Change this when ready*/
                Intent myIntent = new Intent(view.getContext(), CalendarView.class);
               // startActivityForResult(myIntent, 0);
                Log.d(TAG, "switched to userinfo form");
            }
        });
    }
}
