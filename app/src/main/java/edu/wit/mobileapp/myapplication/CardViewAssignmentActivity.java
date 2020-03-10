package edu.wit.mobileapp.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CardViewAssignmentActivity extends AppCompatActivity {
    private FloatingActionButton mAddFab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_view_layout);

        // Linked views to their ID
        mAddFab = findViewById(R.id.floatingActionButton);


        Bundle bundle = this.getIntent().getExtras();
        String title = bundle.getString("title");
        String due = bundle.getString("due");
        String classes = bundle.getString("class");
        String assignment = bundle.getString("assignment");
        String priority = bundle.getString("priority");

        List<CardItem> list = new ArrayList<>();
        CardItem item1 = new CardItem();
        item1.title = title;
        item1.date = due;
        item1.assignment = assignment;
        item1.classes = classes;
        item1.priority = priority;
        list.add(item1);

        mAddFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(CardViewAssignmentActivity.this, AddAssignmentActivity.class);
                startActivity(intent);
            }
        });

        CardItemAdapter adapter;
        adapter = new CardItemAdapter(this, 0, list);
        ListView cardView = (ListView) findViewById(R.id.CardView);
        cardView.setAdapter(adapter);
    }
}