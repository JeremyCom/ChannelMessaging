package com.example.commanje.channelmessaging2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MessageAdapter extends ArrayAdapter<Message> {

    private final Context context;
    private final ArrayList<Message> values;

    public MessageAdapter(Context context, ArrayList<Message> values) {
        super(context, android.R.layout.simple_list_item_1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_row_channel, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.titleChannel);
        textView.setText(values.get(position).message);
        return rowView;
    }
}
