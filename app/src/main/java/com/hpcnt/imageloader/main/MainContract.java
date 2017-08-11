package com.hpcnt.imageloader.main;

import com.android.volley.RequestQueue;

/**
 * Created by 0wen151128 on 2017. 7. 28..
 */

public interface MainContract {

    interface View {
    }

    interface Presenter {
        void attachView(MainContract.View view);

        void setVolleyQueue(RequestQueue requestQueue);

        void getHtml(String targetUrl);
    }
}
