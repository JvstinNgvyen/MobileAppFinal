package edu.wit.mobileapp.nomisseddeadlines;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CountAdapter extends ArrayAdapter<Counter> {

    private LayoutInflater mInflater;
    public String cardTitle;
    public Integer cardCount;

    public CountAdapter(Context context, int rid, List<Counter> list) {
        super(context, rid, list);
        mInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }
    public View getView(final int position, View convertView, ViewGroup parent){
        Counter item = (Counter)getItem(position);
        View view = mInflater.inflate(R.layout.count_item, null);

        TextView course;
        course = (TextView) view.findViewById(R.id.title);
        cardTitle = item.course;
        course.setText(cardTitle);

        TextView count;
        count = (TextView) view.findViewById(R.id.count);
        cardCount = item.count;
        count.setText(cardCount.toString());

        return view;
    }
}
