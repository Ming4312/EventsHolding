package com.example.kailun.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by kailun on 2016/5/27.
 */
public class MyAdapter2 extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<CustomEventList> eventLists;

    public MyAdapter2(Context context, List<CustomEventList> list) {
        layoutInflater = LayoutInflater.from(context);
        eventLists = list;
    }



    private class ViewHolder {
        TextView title, date, location;
        public ViewHolder(TextView title,TextView location, TextView date) {
            this.title = title;
            this.location = location;
            this.date = date;

        }

    }
    @Override
    public int getCount() {
        return eventLists.size();
    }

    @Override
    public Object getItem(int position) {
        return eventLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return eventLists.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.list_events, null);
            holder = new ViewHolder(
                    (TextView)convertView.findViewById(R.id.eventName),
                    (TextView)convertView.findViewById(R.id.eventLocation),
                    (TextView)convertView.findViewById(R.id.eventDate)
            );
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        CustomEventList events = (CustomEventList)getItem(position);

        holder.title.setText(events.getEvent_name());
        holder.location.setText(events.getEvent_location());
        holder.date.setText(events.getEvent_date());

        return convertView;
    }
}
