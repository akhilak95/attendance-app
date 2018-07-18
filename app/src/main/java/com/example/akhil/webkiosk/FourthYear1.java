package com.example.akhil.webkiosk;


import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
/**
 * Created by akhil on 14/3/18.
 */

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class FourthYear1 extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datesheet);

        TouchImageView img = new TouchImageView(this);
        img.setImageResource(R.drawable.pic4);
        img.setMaxZoom(4f);
        setContentView(img);
    }
}