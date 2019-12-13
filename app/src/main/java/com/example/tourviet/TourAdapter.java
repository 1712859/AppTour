package com.example.tourviet;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TourAdapter extends BaseAdapter {

    private Context context;
    private List<TourItem> tourItems;

    public TourAdapter(Context context, List<TourItem> tourItems) {
        this.context=context;
        this.tourItems=tourItems;
    }
    @Override
    public int getCount() {
        return tourItems.size();
    }

    @Override
    public Object getItem(int position) {
        return tourItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return tourItems.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView==null){
            LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
            convertView = layoutInflater.inflate(R.layout.main2_tour_item, null);

            viewHolder=new ViewHolder();
            viewHolder.tourAvatar=convertView.findViewById(R.id.tourItem_avatar);
            viewHolder.tourTitle=convertView.findViewById(R.id.tourItem_title);
            viewHolder.tourPriceMin=convertView.findViewById(R.id.tourItem_priceMin);
            viewHolder.tourPriceMax=convertView.findViewById(R.id.tourItem_priceMax);
            viewHolder.tourTimeMin=convertView.findViewById(R.id.tourItem_timeMin);
            viewHolder.tourTimeMax=convertView.findViewById(R.id.tourItem_timeMax);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder=(ViewHolder) convertView.getTag();
        }

        String image = tourItems.get(position).getAvatar();
        Picasso.get().load(image).fit().into(viewHolder.tourAvatar);

        viewHolder.tourTitle.setText(tourItems.get(position).getName());
        viewHolder.tourPriceMin.setText(String.valueOf(tourItems.get(position).getMinCost()));
        viewHolder.tourPriceMax.setText(String.valueOf(tourItems.get(position).getMaxCost()));
        viewHolder.tourTimeMin.setText(tourItems.get(position).getStartDate());
        viewHolder.tourTimeMax.setText(tourItems.get(position).getEndDate());

        return convertView;
    }

    static class ViewHolder{
        ImageView tourAvatar;
        TextView tourTitle;
        TextView tourPriceMin;
        TextView tourPriceMax;
        TextView tourTimeMin;
        TextView tourTimeMax;
    }
}
