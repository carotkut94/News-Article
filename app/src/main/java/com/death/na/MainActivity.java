package com.death.na;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.death.na.adapters.SourceAdapter;
import com.death.na.models.SourceModel;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Realm realm;
    RealmConfiguration realmConfiguration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.choose_source);
        String url = Utils.getSourceURL();
        URLProcessor urlProcessor = new URLProcessor(this, url);
        urlProcessor.addSources(url);
        Realm.init(this);
        realmConfiguration = new RealmConfiguration.Builder().name("SOURCES").build();
        Realm.setDefaultConfiguration(realmConfiguration);
        realm = Realm.getDefaultInstance();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new SourceAdapter(realmConfiguration,this, realm.where(SourceModel.class).findAllAsync()));
    }
}
