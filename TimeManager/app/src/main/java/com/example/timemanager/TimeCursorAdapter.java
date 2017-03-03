package com.example.timemanager;

import android.content.Context;
import android.database.Cursor;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by trinity.song on 2017-03-02.
 */

public class TimeCursorAdapter extends CursorAdapter {
    public TimeCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_time, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvName = (TextView) view.findViewById(R.id.name);
        TextView tvDate = (TextView) view.findViewById(R.id.date);
        TextView tvStart = (TextView) view.findViewById(R.id.start);
        TextView tvStop = (TextView) view.findViewById(R.id.stop);
        TextView tvTotal = (TextView) view.findViewById(R.id.total);

        String strName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        String strDate = cursor.getString(cursor.getColumnIndexOrThrow("date"));
        String strStart = cursor.getString(cursor.getColumnIndexOrThrow("start"));
        String strStop = cursor.getString(cursor.getColumnIndexOrThrow("stop"));
        String strTotal = cursor.getString(cursor.getColumnIndexOrThrow("total"));

        tvName.setText(strName);
        tvDate.setText(strDate);
        tvStart.setText(strStart);
        tvStop.setText(strStop);
        tvTotal.setText(strTotal);

        notifyDataSetChanged();
    }

}
