# AnimateCircleImageViewLibrary

可以拖拽的CircleImageView

从屏幕松手图片位置自动复原，带回弹效果

![截图](https://github.com/CeuiLiSA/images/blob/master/ScreenRecord_2018-04-12-15-53-23.gif)

# How to use

Step 1. Add the JitPack repository to your build file
~~~
allprojects {
    repositories {
	...
	maven { url 'https://jitpack.io' }
    }
}
~~~

Step 2. Add the dependency
~~~
dependencies {
    implementation 'com.github.CeuiLiSA:AnimateCircleImageViewLibrary:1.0.1'
}
~~~

Step 3. use it in xml
~~~
<?xml version="1.0" encoding="utf-8"?>
<com.example.administrator.mycircleimgview.views.DragImageView
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/my_image_view"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:circleImgBorderColor="@color/colorAccent"
    app:circleImgRadius="110"
    app:circleImgBorder="3"
    app:topMargin="200"
    app:circleViewsCount="5">


</com.example.administrator.mycircleimgview.views.DragImageView>
~~~

Step 4. in xxx.class

加载本地图片：
~~~
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
~~~

加载网络图片：

~~~
public class MainActivity extends AppCompatActivity {

    private DragImageView mDragImageView;
    private static final String url =
            "https://i.pximg.net/c/480x960/img-master/img/2018/03/18/00/00/03/67786060_p0_master1200.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDragImageView = findViewById(R.id.my_image_view);
        Glide.with(this).load(url).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                mDragImageView.setImageResource(resource);
            }
        });
    }
}
~~~

使用到的开源库：
~~~
implementation 'de.hdodenhof:circleimageview:2.2.0'
implementation 'com.facebook.rebound:rebound:0.3.8'
implementation 'com.github.bumptech.glide:glide:3.8.0'
~~~
