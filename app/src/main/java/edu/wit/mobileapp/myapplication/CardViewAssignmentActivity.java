package edu.wit.mobileapp.myapplication;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class CardViewAssignmentActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_view_layout);

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

        CardItemAdapter adapter;
        adapter = new CardItemAdapter(this, 0, list);
        ListView cardView = (ListView) findViewById(R.id.CardView);
        cardView.setAdapter(adapter);
    }
}
