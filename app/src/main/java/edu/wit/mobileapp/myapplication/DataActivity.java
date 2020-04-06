package edu.wit.mobileapp.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DataActivity extends AppCompatActivity {
    private static final String DATABASE_NAME = "/database";

    private BottomNavigationView bottomNavigationView;
    private CountAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_layout);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.data);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.calendar:
                        startActivity(new Intent(getApplicationContext(), CalendarActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.today:
                        startActivity(new Intent(getApplicationContext(), CardViewAssignmentActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.data:
                        startActivity(new Intent(getApplicationContext(), DataActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        //Set DB PATH
        String path = "/data/data/" + getPackageName() + DATABASE_NAME + ".db";
        Context context = getApplicationContext();
        //Use context and path to create SQLlite helper class object
        SQLlite dbHelper = SQLlite.dbHelper(context, path);
        ArrayList<String> courses = dbHelper.getCourseNames();
        ArrayList<String> assignemnts = dbHelper.getAllAssignments();
        ArrayList<Counter> counters = new ArrayList<>();
        ArrayList<String> schooling = dbHelper.getYear();

        TextView schoolText = (TextView) findViewById(R.id.schoolName);
        schoolText.setText(schooling.get(0) + " ");

        TextView yearText = (TextView) findViewById(R.id.year);
        yearText.setText(schooling.get(1));

        for(int i = 0; i < courses.size(); i++) {
                Counter counter = new Counter();
                counter.setCourse(courses.get(i));
                counter.setCount(0);
                counters.add(counter);
        }
        for (int i =0; i < assignemnts.size(); i++) {
            for (int j = 0; j < counters.size(); j++) {
                if (assignemnts.get(i).equals(counters.get(j).getCourse())) {
                    Integer count = counters.get(j).getCount();
                    count++;
                    counters.get(j).setCount(count);
                }
            }
        }
        adapter = new CountAdapter(this, 0, counters);
        ListView cardView = (ListView) findViewById(R.id.CountView);
        cardView.setAdapter(adapter);


    }


}
