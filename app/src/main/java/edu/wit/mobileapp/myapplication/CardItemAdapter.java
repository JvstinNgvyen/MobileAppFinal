package edu.wit.mobileapp.myapplication;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

public class CardItemAdapter extends ArrayAdapter<CardItem> {
    private LayoutInflater mInflater;
    public TextToSpeech toSpeech;
    public String cardTitle;
    public String cardPriority;
    public String cardClasses;
    public String cardDate;
    public String cardAssignmentType;

    public CardItemAdapter(Context context, int rid, List<CardItem> list) {
        super(context, rid, list);
        mInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        CardItem item = (CardItem)getItem(position);

        View view = mInflater.inflate(R.layout.card_item, null);

        TextView title;
        title = (TextView) view.findViewById(R.id.title);
        cardTitle = item.title;
        title.setText(cardTitle);

        TextView date;
        date = (TextView) view.findViewById(R.id.date);
        cardDate = item.date;
        date.setText(cardDate);

        TextView assignment;
        assignment = (TextView) view.findViewById(R.id.assignment);
        cardAssignmentType = item.assignmentType;
        assignment.setText(cardAssignmentType);

        TextView classes;
        classes = (TextView) view.findViewById(R.id.classes);
        cardClasses = item.classes;
        classes.setText(cardClasses);

        TextView priority;
        priority = (TextView) view.findViewById(R.id.priority);
        cardPriority = item.priority;
        priority.setText(cardPriority);

        ImageButton speechButton;
        speechButton = (ImageButton) view.findViewById(R.id.talkButton);
        toSpeech = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR)
                    toSpeech.setLanguage(Locale.US);
            }
        });
        speechButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String speechString = cardTitle + ", " + cardAssignmentType + ", " +
                        " due on " + cardDate + " for " + cardClasses +
                        ". Priority is " + cardPriority;
                toSpeech.speak(speechString, TextToSpeech.QUEUE_FLUSH, null, null);
            }
        });

        return view;
    }

}
