package com.hpcnt.imageloader.main;

import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.HttpURLConnection;
import java.util.ArrayList;

/**
 * Created by 0wen151128 on 2017. 7. 28..
 */

public class MainPresenter implements MainContract.Presenter {
    private MainContract.View view;
    private ArrayList<String> imageUrlList;
    private String htmlDocument;
    private RequestQueue requestQueue;
    private StringRequest stringRequest;

    @Override
    public void attachView(MainContract.View view) {
        this.view = view;
        imageUrlList = new ArrayList<>();
    }

    @Override
    public void setVolleyQueue(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
    }

    @Override
    public void getHtml(String targetUrl) {
        stringRequest = new StringRequest(targetUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                htmlDocument = response;
                if (htmlDocument != null)
                    parseImageUrl();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                int statusCode = error.networkResponse.statusCode;
                Log.v("httpResult", "Http response code " + statusCode + " return");
                if (HttpURLConnection.HTTP_MOVED_PERM == statusCode || HttpURLConnection.HTTP_MOVED_TEMP == statusCode || HttpURLConnection.HTTP_SEE_OTHER == statusCode) {
                    String location = error.networkResponse.headers.get("Location");
                    Log.v("httpRedirect", "Redirect to " + location);
                    getHtml(location);
                }
            }
        });
        requestQueue.add(stringRequest);
    }

    @Override
    public void parseImageUrl() {
        Document document = Jsoup.parse(htmlDocument);
        for (Element e : document.getElementsByTag("img")) {
            imageUrlList.add(e.absUrl("src"));
        }
    }
}
