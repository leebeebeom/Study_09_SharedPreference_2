package com.beebeom.a09_sharedpreference_2;

import android.content.pm.ApplicationInfo;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AppListAdapter extends BaseAdapter {
    private List<ApplicationInfo> mItems = new ArrayList<>();

    public void setItems(List<ApplicationInfo> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder viewHolder = new MyViewHolder();
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_app_list, parent, false);
            viewHolder.appName = convertView.findViewById(R.id.app_name_text);
            viewHolder.appIcon = convertView.findViewById(R.id.app_icon_image);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MyViewHolder) convertView.getTag();
        }
        ApplicationInfo info = mItems.get(position);
        //앱 이름
        String name = (String) info.loadLabel(parent.getContext().getPackageManager());
        viewHolder.appName.setText(name);
        //앱 아이콘
        Drawable icon =info.loadIcon(parent.getContext().getPackageManager());
        viewHolder.appIcon.setImageDrawable(icon);

        return convertView;
    }

    private static class MyViewHolder {
        ImageView appIcon;
        TextView appName;
    }
}

