package edu.kvcc.cis298.workwithclasses.teamproject;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by John on 11/17/2015.
 */
public class RssModifier extends BaseAdapter {

    //Gets the parameters of the articles in the rss feed and puts them into the correct order
    //on the list view

    private final List<RssItem> items;
    private final Context context;

    public RssModifier(Context context, List<RssItem> items) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.feed_item, null);
            holder = new ViewHolder();
            holder.itemTitle = (TextView) convertView.findViewById(R.id.item_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.itemTitle.setText(items.get(position).getTitle());
        return convertView;
    }

    static class ViewHolder {
        TextView itemTitle;
    }
}
