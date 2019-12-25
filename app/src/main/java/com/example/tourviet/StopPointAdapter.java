package com.example.tourviet;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class StopPointAdapter extends BaseAdapter {

    private Context context;
    private List<StopPoint> stopPoints;

    public StopPointAdapter(Context context, List<StopPoint> stopPoints) {
        this.context = context;
        this.stopPoints = stopPoints;
    }

    @Override
    public int getCount() {
        return stopPoints.size();
    }

    @Override
    public Object getItem(int position) {
        return stopPoints.get(position);
    }

    @Override
    public long getItemId(int position) {
        return stopPoints.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
            convertView = layoutInflater.inflate(R.layout.stop_point_item, null);

            viewHolder = new ViewHolder();
            viewHolder.avatar = convertView.findViewById(R.id.stopPoint_avatar);
            viewHolder.name = convertView.findViewById(R.id.stopPoint_name);
            viewHolder.minCost = convertView.findViewById(R.id.stopPoint_minCost);
            viewHolder.maxCost = convertView.findViewById(R.id.stopPoint_maxCost);
            viewHolder.arrivalAt = convertView.findViewById(R.id.stopPoint_arrivalAt);
            viewHolder.leaveAt = convertView.findViewById(R.id.stopPoint_leaveAt);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Picasso.get().load(stopPoints.get(position).getAvatar()).fit().into(viewHolder.avatar);
        viewHolder.name.setText(stopPoints.get(position).getName());
        viewHolder.minCost.setText(stopPoints.get(position).getMinCost());
        viewHolder.maxCost.setText(stopPoints.get(position).getMaxCost());
        viewHolder.arrivalAt.setText(stopPoints.get(position).getArrivalAt());
        viewHolder.leaveAt.setText(stopPoints.get(position).getLeaveAt());


        return convertView;
    }

    private static class ViewHolder {
        ImageView avatar;
        TextView name;
        TextView minCost;
        TextView maxCost;
        TextView arrivalAt;
        TextView leaveAt;
    }
}
