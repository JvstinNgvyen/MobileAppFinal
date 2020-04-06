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

public class CardItemAdapter extends ArrayAdapter<CardItem> {
    private LayoutInflater mInflater;

    public CardItemAdapter(Context context, int rid, List<CardItem> list) {
        super(context, rid, list);
        mInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        CardItem item = (CardItem)getItem(position);

        View view = mInflater.inflate(R.layout.card_item, null);

        TextView title;
        title = (TextView) view.findViewById(R.id.title);
        title.setText(item.title);

        TextView date;
        date = (TextView) view.findViewById(R.id.date);
        date.setText(item.date);

        TextView assignment;
        assignment = (TextView) view.findViewById(R.id.assignment);
        assignment.setText(item.assignmentType);

        TextView classes;
        classes = (TextView) view.findViewById(R.id.classes);
        classes.setText(item.classes);

        TextView priority;
        priority = (TextView) view.findViewById(R.id.priority);
        priority.setText(item.priority);

        ImageButton speechButton;
        speechButton = (ImageButton) view.findViewById(R.id.talkButton);
        speechButton.setOnClickListener(item.clickListener);

        final ImageButton menuButton;
        menuButton = (ImageButton) view.findViewById((R.id.img_menu));
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CardViewAssignmentActivity click = new CardViewAssignmentActivity();
                click.showPopupMenu(menuButton, position);
            }
        });

        return view;
    }


}
