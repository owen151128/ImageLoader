package com.hpcnt.imageloader.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.android.volley.toolbox.Volley;
import com.hpcnt.imageloader.R;
import com.hpcnt.imageloader.contract.MainContract;
import com.hpcnt.imageloader.presenter.MainPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainContract.View {
    private MainPresenter mainPresenter;
    @BindView(R.id.textView)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mainPresenter = new MainPresenter();
        mainPresenter.attachView(this);
        mainPresenter.setVolleyQueue(Volley.newRequestQueue(getApplicationContext()));
        mainPresenter.getHtml("http://www.naver.com");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.detachView();
        System.exit(0);
    }

    @Override
    public void setTextView(String text) {
        textView.setText(text);
    }
}
