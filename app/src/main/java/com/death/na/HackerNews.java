package com.death.na;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.death.na.adapters.NewsAdapter;
import com.death.na.models.SourceModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.Realm;

/**
 * Created by rajora_sd on 4/20/2017.
 */

public class HackerNews extends Fragment{

    NewsAdapter mAdapter;
    ArrayList<NewsModel> newsModels;
    RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.frgament_recycler, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.news);
        newsModels = new ArrayList<>();
        mAdapter = new NewsAdapter(getActivity(), newsModels);

        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.HORIZONTAL));
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        addSources();
    }
    private void addSources() {
        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                "http://newsapi.org/v1/articles?source=hacker-news&sortBy=latest&apiKey=44283b117d784997a4edd8875c900f5d", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    newsModels.clear();
                    JSONArray jsonArray = response.getJSONArray("articles");
                    for (int i=0;i<jsonArray.length();++i)
                    {
                        JSONObject tObject = jsonArray.getJSONObject(i);
                        NewsModel newsModel = new NewsModel();
                        newsModel.setAuthor(tObject.getString("author"));
                        newsModel.setTitle(tObject.getString("title"));
                        newsModel.setDescription(tObject.getString("description"));
                        newsModel.setUrl(tObject.getString("url"));
                        newsModel.setUrlToImage(tObject.getString("urlToImage"));
                        newsModels.add(newsModel);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG", "Error: " + error.getMessage());
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        ApplicationController.getInstance().addToRequestQueue(jsonObjReq);
    }
}
