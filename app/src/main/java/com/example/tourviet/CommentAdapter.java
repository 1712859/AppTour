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

class CommentAdapter extends BaseAdapter {

    private Context context;
    private List<TourComment> tourComments;

    public CommentAdapter(Context context, List<TourComment> tourComments) {
        this.context = context;
        this.tourComments = tourComments;
    }

    @Override
    public int getCount() {
        return tourComments.size();
    }

    @Override
    public Object getItem(int position) {
        return tourComments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return tourComments.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
            convertView = layoutInflater.inflate(R.layout.comment_item, null);

            viewHolder = new ViewHolder();

            viewHolder.avatar = convertView.findViewById(R.id.comment_avatar);
            viewHolder.id = convertView.findViewById(R.id.comment_id);
            viewHolder.name = convertView.findViewById(R.id.member_name);
            viewHolder.comment = convertView.findViewById(R.id.comment_comment);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        TourComment comment = tourComments.get(position);

        Picasso.get().load(comment.getAvatar()).fit().into(viewHolder.avatar);
        viewHolder.id.setText(String.valueOf(comment.getId()));
        viewHolder.name.setText(comment.getName());
        viewHolder.comment.setText(comment.getComment());

        return convertView;
    }

    private static class ViewHolder {
        public ImageView avatar;
        public TextView id;
        public TextView name;
        public TextView comment;
    }
}
