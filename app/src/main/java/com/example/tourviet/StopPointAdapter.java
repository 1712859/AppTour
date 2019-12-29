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

        StopPoint stopPoint = stopPoints.get(position);

        Picasso.get().load(stopPoint.getAvatar()).fit().into(viewHolder.avatar);
        viewHolder.name.setText(stopPoint.getName());
        viewHolder.minCost.setText(stopPoint.getMinCost());
        viewHolder.maxCost.setText(stopPoint.getMaxCost());
        viewHolder.arrivalAt.setText(stopPoint.getArrivalAt());
        viewHolder.leaveAt.setText(stopPoint.getLeaveAt());

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
