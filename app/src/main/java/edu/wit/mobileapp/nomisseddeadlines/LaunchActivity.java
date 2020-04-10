package edu.wit.mobileapp.nomisseddeadlines;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.ListenableWorker;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static java.sql.Types.TIME;

/**
 * Empty launch activity, this will determine which activity to launch
 *      1. AddInfo activity
 *      2. CardView activity
 *      3. Original template done by author below, modified for our use-case
 * @author erangaeb@gmail.com (eranga herath)
 * https://coderwall.com/p/n4qaaa/programatically-change-main-launch-activity-in-android
 * @author Ryan Gould
 */
public class LaunchActivity extends AppCompatActivity {
    private static final String DATABASE_NAME = "/database";
    public static String deadlines ;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launcher_activity);
        String path = "/data/data/" + getPackageName() + DATABASE_NAME + ".db";
        Context context = getApplicationContext();
        //Use context and path to create SQLlite helper class object
        SQLlite dbHelper = SQLlite.dbHelper(context, path);
        ArrayList<String> dueDates = dbHelper.getAllAssignmentDueDates();
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String formattedDate = dateFormat.format(date);
        int count = 0;
        for (int i =0; i <dueDates.size(); i++) {
            String dueDate = dateFormatter(dueDates.get(i));
            if (formattedDate.equals(dueDate)) {
                count++;
            }
        }
        if (count > 0) {
            PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(DeadlineTracker.class, 30, TimeUnit.SECONDS).build();
            WorkManager.getInstance().enqueue(periodicWorkRequest);
            showNotification();
        }

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
            dbHelper.close();
            finish();
            startActivity(intent);
        }
    }
public String dateFormatter(String date) {
       String[] parts = date.split("/");
       if (parts[0].length() < 2) {
           parts[0] = "0" + parts[0];
       }
       if (parts[1].length() < 2) {
           parts[1] = "0" + parts[1];
       }
       date = parts[0] + "/" + parts[1] + "/" + parts[2];
        return date;
}
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void showNotification() {
        NotificationManager manager = (NotificationManager)
                getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        String channelId = "deadlineTask";
        String channelName = "deadline";
        String task = "deadline";
        String description = "approaching";
        NotificationChannel channel = new NotificationChannel(channelId,channelName,
                    NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);


        Intent acceptNextIntent = new Intent(this, CardViewAssignmentActivity.class);
        PendingIntent acceptNextPendingIntent = PendingIntent.getBroadcast(this, 0, acceptNextIntent, 0);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                acceptNextIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Action acceptAction = new NotificationCompat.Action
                .Builder(R.drawable.notification, "Grant Request", acceptNextPendingIntent).build();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channelId)
                .setContentTitle(task)
                .setContentText(description)
                .setContentIntent(contentIntent)
                .setSmallIcon(R.drawable.ic_volume_up)
                .addAction(acceptAction);

        manager.notify(1, builder.build());
    }
}
