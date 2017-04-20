package com.death.na;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.death.na.adapters.SourceAdapter;
import com.death.na.models.SourceModel;
import io.realm.Realm;
import io.realm.RealmConfiguration;


public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Realm realm;
    LinearLayoutManager mLayoutManager;
    RealmConfiguration realmConfiguration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Choose Categories");
        setContentView(R.layout.activity_main);
        Realm.init(this);
        recyclerView = (RecyclerView) findViewById(R.id.choose_source);
        String url = Utils.getSourceURL();
        URLProcessor urlProcessor = new URLProcessor(this, url);
        urlProcessor.addSources();
        realmConfiguration = new RealmConfiguration.Builder().name("SOURCES").build();
        Realm.setDefaultConfiguration(realmConfiguration);
        realm = Realm.getDefaultInstance();
        Log.e("COUNT", ""+realm.where(SourceModel.class).distinctAsync("category").size());
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new SourceAdapter(realmConfiguration,this, realm.where(SourceModel.class).distinctAsync("category")));
    }
}
