package com.example.ilya.robocontroller;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ConsoleActivity extends AppCompatActivity implements View.OnClickListener{

    public static Handler handler;
    String message;
    LinearLayout messageLayout;
    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_console);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        messageLayout = findViewById(R.id.messageslayout);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        handler = new Handler(){
            @Override
            public void handleMessage(android.os.Message msg){
                addMsg(msg.what,msg.obj);
            }
        };
    }

    @SuppressLint("ResourceAsColor")
    public void addMsg(int color, Object NewMsgObj){
        TextView newMsgTxr = new TextView(this);
        if (color==1){
            newMsgTxr.setHintTextColor(R.color.colorConsoleMsgArduino);
        }
        if (color==0){
            newMsgTxr.setHintTextColor(R.color.colorConsoleMsgPhone);
        }
        newMsgTxr.setText(NewMsgObj.toString());
        messageLayout.addView(newMsgTxr);
    }
    @Override
    public void onClick(View v) {

    }
}
