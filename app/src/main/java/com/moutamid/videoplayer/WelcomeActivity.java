package com.moutamid.videoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Constant.checkApp(this);


        findViewById(R.id.button).setOnClickListener(v -> startActivity(new Intent(this, MainActivity.class)));

    }
}