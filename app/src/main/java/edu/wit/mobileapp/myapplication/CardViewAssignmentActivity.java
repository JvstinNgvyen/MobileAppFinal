package edu.wit.mobileapp.myapplication;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CardViewAssignmentActivity extends AppCompatActivity implements View.OnClickListener{
    private FloatingActionButton mAddFab;
    private static final String DATABASE_NAME = "/database";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_view_layout);
        Log.v("CardView", "start");
        //Set DB PATH
        String path = "/data/data/" + getPackageName() + DATABASE_NAME + ".db";
        Context context = getApplicationContext();
        //Use context and path to create SQLlite helper class object
        SQLlite dbHelper = SQLlite.dbHelper(context, path);
        Log.v("CardView", "here");
        // Linked views to their ID
        mAddFab = findViewById(R.id.floatingActionButton);

        mAddFab.setOnClickListener(this);

        List<CardItem> list = new ArrayList<>();
        ArrayList<String> allAssignments = dbHelper.getAllAssignments();
        Log.v("cardLoop", allAssignments.get(0));
        for (int i = 0; i < allAssignments.size(); i+=5) {
            CardItem cardItem = new CardItem();
            cardItem.title = allAssignments.get(i);
            cardItem.classes = allAssignments.get(i+1);
            cardItem.date = allAssignments.get(i+2);
            cardItem.assignmentType = allAssignments.get(i+3);;
            cardItem.priority = allAssignments.get(i+4);
            list.add(cardItem);
            Log.v("cardLoop", cardItem.toString());
        }
        //dbHelper.close();

        CardItemAdapter adapter;
        adapter = new CardItemAdapter(this, 0, list);
        ListView cardView = (ListView) findViewById(R.id.CardView);
        cardView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if(v == mAddFab) {
            Intent intent = new Intent();
            intent.setClass(CardViewAssignmentActivity.this, AddAssignmentActivity.class);
            startActivity(intent);
        }
    }
}
