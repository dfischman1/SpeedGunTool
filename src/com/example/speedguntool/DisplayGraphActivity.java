package com.example.speedguntool;

import android.app.Activity;
import android.os.Bundle;

import android.view.*;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;
import android.annotation.SuppressLint;
import android.support.v4.app.NavUtils;
import android.os.Build;
import android.widget.ImageView;

public class DisplayGraphActivity extends Activity {

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);

        ImageView image = new ImageView(this);
        image.setImageResource(R.drawable.ic_launcher);
        setContentView(image);
        
    }
    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    */
}
