package edu.wit.mobileapp.nomisseddeadlines;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CardViewAssignmentActivity extends AppCompatActivity{
    private FloatingActionButton mAddFab;
    private BottomNavigationView bottomNavigationView;
    Button deleteBtn;
    private static final String DATABASE_NAME = "/database";
    private List<CardItem> list;
    private PopupMenu popup;
    private Integer number = 0;
    private CardItemAdapter adapter;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_view_layout);
        deleteBtn = findViewById(R.id.deleteBtn);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.today);
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

        Log.v("CardView", "start");
        //Set DB PATH
        String path = "/data/data/" + getPackageName() + DATABASE_NAME + ".db";
        Context context = getApplicationContext();
        //Use context and path to create SQLlite helper class object
        SQLlite dbHelper = SQLlite.dbHelper(context, path);
        Log.v("CardView", "here");

        // Floating Action Button
        mAddFab = findViewById(R.id.floatingActionButton);
        mAddFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(CardViewAssignmentActivity.this, AddAssignmentActivity.class);
                startActivity(intent);
            }
        });

        list = new ArrayList<>();
        ArrayList<String> allAssignments = dbHelper.getAllAssignments();
        //Log.v("cardLoop", allAssignments.get(0));
        for (int i = 0; i < allAssignments.size(); i+=5) {
            CardItem cardItem = new CardItem(context);
            cardItem.title = allAssignments.get(i);
            cardItem.classes = allAssignments.get(i+1);
            cardItem.date = allAssignments.get(i+2);
            cardItem.assignmentType = allAssignments.get(i+3);;
            cardItem.priority = allAssignments.get(i+4);
            list.add(cardItem);
            Log.v("cardLoop", cardItem.toString());
        }
        dbHelper.close();

        adapter = new CardItemAdapter(this, 0, list);
        ListView cardView = (ListView) findViewById(R.id.CardView);
        cardView.setAdapter(adapter);

        //deleteBtn.setVisibility(View.INVISIBLE);
        deleteBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                remove(number);
                //deleteBtn.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void showPopupMenu(View view, final int position) {
        // Inflate Menu
        popup = new PopupMenu(view.getContext(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_card, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.delete:
                        Log.v("myApp", String.valueOf(position));

                        //deleteBtn.setVisibility(View.VISIBLE);
                        number = position;
                        Log.v("myApp", String.valueOf(number));

                        return true;
                }
                return false;
            }
        });
        popup.show();
    }

    public void remove(int position){
        adapter.remove(adapter.getItem(position));
        adapter.notifyDataSetChanged();
    }
}
