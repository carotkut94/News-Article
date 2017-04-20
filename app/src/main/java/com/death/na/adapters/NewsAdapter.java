package com.death.na.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.death.na.NewsModel;
import com.death.na.R;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {


    private List<NewsModel> models;
    private Context mContext;

    public NewsAdapter(Context context, List<NewsModel> models) {
        mContext = context;
        this.models = models;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_container_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final NewsModel model = models.get(position);
        holder.cardTitle.setText(model.getTitle());
        if(model.getDescription().equals("null")){
            holder.newsText.setVisibility(View.GONE);
        }else
        {
            holder.newsText.setText(model.getDescription());
        }
        if(model.getAuthor().equals("null")){
            holder.authorName.setVisibility(View.GONE);
        }else
        {
            holder.authorName.setText(model.getAuthor());
        }
        if(model.getUrlToImage().equals("null")){
            Log.e("IMG", "EMPTY LINK");
            holder.adImage.setImageResource(R.drawable.bg_textview);
        }
        else{
            Glide.with(mContext).load(model.getUrlToImage()).diskCacheStrategy(DiskCacheStrategy.NONE).into(holder.adImage);
        }
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView adImage;
        TextView cardTitle, authorName, newsText;

        MyViewHolder(View itemView) {
            super(itemView);
            adImage = (ImageView) itemView.findViewById(R.id.card_image);
            cardTitle = (TextView) itemView.findViewById(R.id.card_title);
            newsText = (TextView) itemView.findViewById(R.id.card_text);
            authorName = (TextView) itemView.findViewById(R.id.card_author);

        }
    }
}