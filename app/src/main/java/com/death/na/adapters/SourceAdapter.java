package com.death.na.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.death.na.MainActivity;
import com.death.na.R;
import com.death.na.models.SourceModel;
import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by rajora_sd on 4/19/2017.
 */

public class SourceAdapter extends RealmRecyclerViewAdapter<SourceModel, SourceAdapter.MyViewHolder> {

    Realm realm;
    RealmConfiguration realmConfiguration;
    private MainActivity activity;
    public SourceAdapter(RealmConfiguration configuration, MainActivity activity, OrderedRealmCollection<SourceModel> data) {
        super(activity ,data, true);
        this.activity = activity;
        this.realmConfiguration = configuration;
        Realm.setDefaultConfiguration(configuration);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.sources_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.sourceText.setText(getData().get(position).getCategory());
        holder.sourceText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(holder.isNotSelected)
                    {
                        holder.isNotSelected = false;
                        holder.sourceText.setTextColor(Color.WHITE);
                        holder.sourceText.setBackgroundResource(R.drawable.bg_textview);
                    }
                    else
                    {
                        holder.isNotSelected = true;
                        holder.sourceText.setTextColor(Color.parseColor("#ff0134"));
                        holder.sourceText.setBackgroundColor(Color.WHITE);
                    }

            }
        });
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        Boolean isNotSelected;
        TextView sourceText;
        MyViewHolder(View itemView) {
            super(itemView);
            setIsRecyclable(false);
            sourceText = (TextView) itemView.findViewById(R.id.s_text);
            isNotSelected = false;
        }
    }
}
