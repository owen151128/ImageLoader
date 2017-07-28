package com.hpcnt.imageloader.contract;

import com.android.volley.RequestQueue;

/**
 * Created by 0wen151128 on 2017. 7. 28..
 */

public interface MainContract {

    interface View {
        void setTextView(String text);
    }

    interface Presenter {
        void attachView(MainContract.View view);

        void detachView();

        void setVolleyQueue(RequestQueue requestQueue);

        void getHtml(String targetUrl);

        void parseImageUrl();
    }
}
