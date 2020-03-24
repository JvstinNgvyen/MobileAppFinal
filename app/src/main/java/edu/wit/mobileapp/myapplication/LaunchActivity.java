package edu.wit.mobileapp.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Empty launch activity, this will determine which activity to launch
 *      1. Splash activity
 *      2. Home activity
 *
 * @author erangaeb@gmail.com (eranga herath)
 */
public class LaunchActivity extends AppCompatActivity {
    private static final String DATABASE_NAME = "/database";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launcher_activity);
        String path = "/data/data/" + getPackageName() + DATABASE_NAME + ".db";
        Context context = getApplicationContext();
        //Use context and path to create SQLlite helper class object
        SQLlite dbHelper = SQLlite.dbHelper(context, path);

        // determine where to go
        if(dbHelper.doesExist(context) == false) {
            Intent intent = new Intent(this, AddInfoActivity.class);
            Log.v("LAUNCHER:", "addinfoactivity");
            dbHelper.close();
            finish();
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, CardViewAssignmentActivity.class);
            Log.v("LAUNCHER:", "cardViewActivity");
            finish();
            startActivity(intent);
        }
    }
}
