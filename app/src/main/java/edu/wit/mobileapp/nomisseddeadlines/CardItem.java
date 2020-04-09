package edu.wit.mobileapp.nomisseddeadlines;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.view.View;

import java.util.Locale;

public class CardItem {
    public String title;
    public String date;
    public String assignmentType;
    public String classes;
    public String priority;
    public View.OnClickListener clickListener;
    TextToSpeech toSpeech;

    public CardItem(Context context) {
        toSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR)
                    toSpeech.setLanguage(Locale.US);
            }
        });
        clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak(toSpeech);
            }};
    }

    @Override
    public String toString() {
        return title + ", " + assignmentType + ", " +
                " due on " + date + " for " + classes +
                ". Priority is " + priority;
    }

    public void speak(TextToSpeech toSpeech) {
        toSpeech.speak(this.toString(), TextToSpeech.QUEUE_FLUSH, null, null);
    }
}
