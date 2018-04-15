package com.example.administrator.animatecircleimageview;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.administrator.animatecircleimageview.views.DragImageView;
/**
 * Created by CeuiLiSA.
 */
public class MainActivity extends AppCompatActivity {

    private DragImageView mDragImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDragImageView = findViewById(R.id.my_image_view);
        /*加载本地图片：
        Bitmap localBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.head);
        mDragImageView.setImageResource(localBitmap);
        */
        //加载网络图片
        String url = "https://i.pximg.net/c/480x960/img-master/img/2018/03/18/00/00/03/67786060_p0_master1200.jpg";
        Glide.with(this).load(url).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                mDragImageView.setImageResource(resource);
            }
        });
    }
}