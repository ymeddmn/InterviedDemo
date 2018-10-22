package com.example.horse.interview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import interview.horse.example.com.common.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        getWindow().setBackgroundDrawableResource(R.color.transparent);
//        startActivity(new Intent(this,CatalogActivity.class));
        Intent intent = new Intent();
        intent.setAction(BaseActivity.ACTION_ACTIVITY);
        intent.addCategory("android.intent.category.DEFAULT");
        startActivity(intent);


        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
