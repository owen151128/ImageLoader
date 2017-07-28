package com.hpcnt.imageloader.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.android.volley.toolbox.Volley;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.hpcnt.imageloader.R;
import com.hpcnt.imageloader.adapter.ImageRecyclerViewAdapter;
import com.hpcnt.imageloader.contract.MainContract;
import com.hpcnt.imageloader.presenter.MainPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainContract.View {
    private MainPresenter mainPresenter;
    @BindView(R.id.imageRecyclerView)
    RecyclerView imageRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mainPresenter = new MainPresenter();
        mainPresenter.attachView(this);
        mainPresenter.setVolleyQueue(Volley.newRequestQueue(getApplicationContext()));
        textAlertDialog();
    }

    @Override
    public void vollyCallback() {
        imageRecyclerView.setAdapter(new ImageRecyclerViewAdapter(mainPresenter.getParseImageUrl()));
        imageRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        imageRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void textAlertDialog() {
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.text_alert_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_title);
        builder.setView(view);

        final EditText editText = (EditText) view.findViewById(R.id.inputText);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mainPresenter.getHtml(editText.getText().toString());
            }
        });
        builder.setCancelable(false);
        builder.create().show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.detachView();
        System.exit(0);
    }

}
