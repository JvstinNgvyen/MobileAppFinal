package edu.wit.mobileapp.nomisseddeadlines;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;


public class DeadlineTracker extends Worker {
    public DeadlineTracker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context,params);

    }

    public Result doWork() {
        return Result.success();
    }

}
