package com.interest.myapplication.view.book;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.interest.myapplication.Entity.bookEntity.BookDetailEntity;
import com.interest.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 2016/3/26.
 */
public class BookAdapter extends BaseAdapter{

    private Context context;
    private List<BookDetailEntity> books;
    private LayoutInflater inflater;
    private int dataCount;

    /**
     * 有新的数据，加载到数据源中
     * @param books
     * @param isFirst
     * @return
     */
    public List<BookDetailEntity> updateData(List<BookDetailEntity> books,boolean isFirst){
        if (isFirst){   //第一次刷新，把之前的数据清空，第二次刷新把新数据添加到旧数据中
            this.books.clear();
            dataCount = 8;
        }
        this.books.addAll(0,books);
        notifyDataSetChanged();
        return this.books;
    }
    /**
     * 加载已有的资源
     */
    public boolean loadMoreData() {
        dataCount+=8;
        boolean hasMore = true;
        if (dataCount>books.size()){
            dataCount = books.size();
            hasMore = false;       //已经没有更多
        }
        notifyDataSetChanged();
        return hasMore;            //还有更多
    }
    public BookAdapter(List books, Context context) {
        if (books!=null){
            this.books = books;
        }else{
            this.books = new ArrayList<BookDetailEntity>();
        }
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dataCount;
    }

    @Override
    public BookDetailEntity getItem(int position) {
        return books.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView==null){
            convertView = inflater.inflate(R.layout.item_books,null);
            holder = new ViewHolder();
            holder.tvBookAuthor = (TextView) convertView.findViewById(R.id.tvBookAuthor);
            holder.ivBookIcon = (ImageView) convertView.findViewById(R.id.ivBookIcon);
            holder.tvBookName = (TextView) convertView.findViewById(R.id.tvBookName);
            holder.tvBookPrice = (TextView) convertView.findViewById(R.id.tvBookPrice);
            holder.tvBookPages= (TextView) convertView.findViewById(R.id.tvPagas);
            holder.tvBookDate = (TextView) convertView.findViewById(R.id.tvDate);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        try {
            BookDetailEntity entity = books.get(position);
            holder.tvBookName.setText(entity.getTitle());
            Picasso.with(context).load(entity.getImage()).into(holder.ivBookIcon);
            holder.tvBookAuthor.setText("作者：" + entity.getAuthor().toString().substring(1,entity.getAuthor().toString().length()-1));
            holder.tvBookPrice.setText("定价：" + entity.getPrice());
            holder.tvBookPages.setText("页数：" + entity.getPages());
            holder.tvBookDate.setText("出版日期：" + entity.getPubdate());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }
    class ViewHolder{
        ImageView ivBookIcon;
        TextView tvBookName;
        TextView tvBookAuthor;
        TextView tvBookPrice;
        TextView tvBookPages;
        TextView tvBookDate;
    }
}
