package com.hpcnt.imageloader.contract;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

/**
 * Created by 0wen151128 on 2017. 7. 28..
 */

public interface MainContract {

    interface View {
        void vollyCallback();

        void textAlertDialog();
    }

    interface Presenter {
        void attachView(MainContract.View view);

        void detachView();

        void setVolleyQueue(RequestQueue requestQueue);

        void getHtml(String targetUrl);

        ArrayList<String> getParseImageUrl();
    }
}
