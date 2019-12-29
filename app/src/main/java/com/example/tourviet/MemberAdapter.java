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

class MemberAdapter extends BaseAdapter {

    private Context context;
    private List<TourMember> tourMembers;

    public MemberAdapter(Context context, List<TourMember> tourMembers) {
        this.context = context;
        this.tourMembers = tourMembers;
    }

    @Override
    public int getCount() {
        return tourMembers.size();
    }

    @Override
    public Object getItem(int position) {
        return tourMembers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return tourMembers.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
            convertView = layoutInflater.inflate(R.layout.member_item, null);

            viewHolder = new ViewHolder();
            viewHolder.avatar = convertView.findViewById(R.id.member_avatar);
            viewHolder.id = convertView.findViewById(R.id.member_id);
            viewHolder.name = convertView.findViewById(R.id.member_name);
            viewHolder.phone = convertView.findViewById(R.id.member_phone);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        TourMember member = tourMembers.get(position);

        Picasso.get().load(member.getAvatar()).fit().into(viewHolder.avatar);
        viewHolder.id.setText(String.valueOf(member.getId()));
        viewHolder.name.setText(member.getName());
        viewHolder.phone.setText(member.getPhone());

        return convertView;
    }

    private static class ViewHolder {
        public TextView id;
        public TextView name;
        public ImageView avatar;
        public TextView phone;
    }
}
