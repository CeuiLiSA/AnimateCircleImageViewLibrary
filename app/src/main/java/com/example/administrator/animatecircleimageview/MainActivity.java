package com.example.administrator.animatecircleimageview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.animatecircleimageview.views.DragImageView;

/**
 * Created by CeuiLiSA.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.head);
        DragImageView dragImageView = findViewById(R.id.my_image_view);
        dragImageView.setImageResource(bitmap);
    }
}
