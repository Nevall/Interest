package com.interest.myapplication.view.save;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.interest.myapplication.Entity.SaveEntity;
import com.interest.myapplication.Entity.bookEntity.BookDetailEntity;
import com.interest.myapplication.Entity.newsEntity.Content;
import com.interest.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 2016/4/7.
 */
public class SaveAdapter<T extends SaveEntity> extends BaseAdapter {
    private List<T> data;
    private LayoutInflater inflater;
    private Context context;

    public SaveAdapter(List<T> data,Context context) {
        if (data==null){
            this.data = new ArrayList<>();
        }else{
            this.data = data;
        }
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public T getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_save,null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.ivSave);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tvSave);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        T entity = data.get(position);
        Picasso.with(context).load(entity.getImage()).into(holder.imageView);
        holder.tvTitle.setText(entity.getTitle());
        return convertView;
    }
    class ViewHolder{
        TextView tvTitle;
        ImageView imageView;
    }
}
