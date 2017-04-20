package com.death.na;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.death.na.models.Favs;
import com.death.na.models.SourceModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.xml.transform.Source;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by rajora_sd on 4/19/2017.
 */

public class URLProcessor {

    Realm realm, realCategories;
    RealmConfiguration realmConfiguration,realmConfigurationCateogries;
    private Context context;
    private String url;

    URLProcessor(Context context, String url) {
        this.context = context;
        this.url = url;
        Realm.init(context);
        realmConfiguration = new RealmConfiguration.Builder().name("SOURCES").build();
        realmConfigurationCateogries = new RealmConfiguration.Builder().name("CATEGORIES").build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    public boolean checkIfIdExists(String id) {
        realm = Realm.getDefaultInstance();
        RealmQuery<SourceModel> query = realm.where(SourceModel.class)
                .equalTo("id", id);
        return query.count() != 0;
    }
    void addSources() {
        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.e("RES", response.toString());
                realm = Realm.getDefaultInstance();
                try {
                    JSONArray sourcesArray = response.getJSONArray("sources");
                    for(int i=0;i<sourcesArray.length();++i)
                    {
                        JSONObject temporaryObject = sourcesArray.getJSONObject(i);
                        if(!checkIfIdExists(temporaryObject.getString("id"))){
                        realm.beginTransaction();
                        SourceModel sourceModel = realm.createObject(SourceModel.class);
                        sourceModel.setId(temporaryObject.getString("id"));
                        sourceModel.setName(temporaryObject.getString("name"));
                        sourceModel.setDescription(temporaryObject.getString("description"));
                        sourceModel.setUrl(temporaryObject.getString("url"));
                        sourceModel.setCategory(temporaryObject.getString("category"));
                        sourceModel.setLanguage(temporaryObject.getString("language"));
                        sourceModel.setChecked(false);
                        sourceModel.setCountry(temporaryObject.getString("country"));
                        realm.commitTransaction();
                        Log.e("SUCCESS", "DATA INSERTED");}
                        else
                        {
                            Log.e("SUCCESS", "DATA ALREADY INSERTED");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG", "Error: " + error.getMessage());
                Toast.makeText(context,
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        ApplicationController.getInstance().addToRequestQueue(jsonObjReq);
    }
}
