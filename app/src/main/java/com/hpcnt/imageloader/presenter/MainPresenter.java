package com.hpcnt.imageloader.presenter;


import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hpcnt.imageloader.contract.MainContract;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.HttpURLConnection;
import java.util.ArrayList;

/**
 * Created by 0wen151128 on 2017. 7. 28..
 */

public class MainPresenter implements MainContract.Presenter {
    private static final String TAG = MainPresenter.class.getCanonicalName();
    private MainContract.View view;
    private String htmlDocument;
    private RequestQueue requestQueue;
    private StringRequest stringRequest;

    @Override
    public void attachView(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
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
                view.vollyCallback();
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
        stringRequest.setTag(TAG);
        requestQueue.add(stringRequest);
    }

    @Override
    public ArrayList<String> getParseImageUrl() {
        ArrayList<String> imageUrlList = new ArrayList<>();
        Document document = Jsoup.parse(htmlDocument);
        for (Element e : document.getElementsByTag("img")) {
            if (!e.absUrl("src").equals(""))
                imageUrlList.add(e.absUrl("src"));
        }
        return imageUrlList;
    }
}
