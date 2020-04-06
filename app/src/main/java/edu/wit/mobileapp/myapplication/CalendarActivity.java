package edu.wit.mobileapp.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class CalendarActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_layout);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.calendar);
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

        CalendarView simpleCalendarView = (CalendarView) findViewById(R.id.calendarView); // get the reference of CalendarView
        simpleCalendarView.setOnDateChangeListener( new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                //Set DB PATH
                String path = "/data/data/" + getPackageName() + "/database.db";
                Context context = getApplicationContext();
                //Use context and path to create SQLlite helper class object
                SQLlite dbHelper = SQLlite.dbHelper(context, path);
                String selectedDate = String.valueOf(month+1) + "/" + String.valueOf(dayOfMonth) + "/" + String.valueOf(year);
                ArrayList<String> allAssignments = dbHelper.getAssignmentByDueDate(selectedDate);

                dbHelper.close();
                if(allAssignments.size() > 0) {
                    Log.d("CalendarActivity", "enter loop");
                    ArrayList<CardItem> list = new ArrayList<>();
                    for (int i = 0; i < allAssignments.size(); i += 5) {
                        CardItem cardItem = new CardItem();
                        cardItem.title = allAssignments.get(i);
                        cardItem.priority = allAssignments.get(i + 1);
                        cardItem.classes = allAssignments.get(i + 2);
                        cardItem.date = allAssignments.get(i + 3);
                        cardItem.assignmentType = allAssignments.get(i+4);
                        list.add(cardItem);

                    }

                    CardItemAdapter adapter;
                    adapter = new CardItemAdapter(context, 0, list);
                    ListView cardView = (ListView) findViewById(R.id.CardView1);
                    cardView.setAdapter(adapter);
                    cardView.setVisibility(View.VISIBLE);


                }
                else {
                    ListView cardView = (ListView) findViewById(R.id.CardView1);
                    cardView.setVisibility(View.INVISIBLE);
                }
           }
        });

    }
}
