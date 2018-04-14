package com.example.administrator.animatecircleimageview.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.administrator.animatecircleimageview.R;
import com.example.administrator.animatecircleimageview.utils.ScreenUtil;
import com.example.administrator.animatecircleimageview.utils.ViewTrackController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CeuiLiSA.
 */
public class DragImageView extends RelativeLayout {

    List<AnimateImageViewItem> mIvList;
    AnimateImageViewItem circleImageView;
    Context mContext;
    Bitmap imageResource;
    ViewDragHelper mDragHelper;
    ViewTrackController mViewTrackController;
    LayoutParams params;
    int circleImgRadius, topMargin, circleImgBorder, circleViewsCount, borderColor;
    public DragImageView(Context context) {
        super(context);
    }

    public DragImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.DragImageView);
        circleImgRadius = a.getInteger(R.styleable.DragImageView_circleImgRadius, 110);
        topMargin = a.getInteger(R.styleable.DragImageView_topMargin, 45);
        circleImgBorder = a.getInteger(R.styleable.DragImageView_circleImgBorder, 3);
        circleViewsCount = a.getInteger(R.styleable.DragImageView_circleViewsCount, 5);
        borderColor = a.getColor(R.styleable.DragImageView_circleImgBorderColor, getResources().getColor(R.color.colorPrimary));
        a.recycle();

        mDragHelper = ViewDragHelper.create(this, 1.0f, new MyViewDragHelper());
        mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_TOP);
        mViewTrackController = new ViewTrackController();
    }

    public void setImageResource(Bitmap bitmap)
    {
        imageResource = bitmap;

        mIvList = new ArrayList<>();
        for (int i = 0; i < circleViewsCount; i++) {
            AnimateImageViewItem circleImageView = new AnimateImageViewItem(mContext);
            circleImageView.setImageBitmap(imageResource);
            circleImageView.setBorderWidth(ScreenUtil.dip2px(mContext, circleImgBorder));
            circleImageView.setBorderColor(borderColor);
            circleImageView.setLayoutParams(params);
            addView(circleImageView);
            if (i == circleViewsCount-1) {
                this.circleImageView = circleImageView;
            } else {
                circleImageView.setAlpha(0.3f);
            }
            mIvList.add(circleImageView);
        }

        mViewTrackController.init(mIvList);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        params = new LayoutParams(
                ScreenUtil.dip2px(mContext, circleImgRadius), ScreenUtil.dip2px(mContext, circleImgRadius));
        params.topMargin = ScreenUtil.dip2px(mContext, topMargin);
        params.leftMargin = ScreenUtil.getScreenWidth(mContext) / 2 - ScreenUtil.dip2px(mContext, circleImgRadius) / 2;
    }

    private class MyViewDragHelper extends ViewDragHelper.Callback {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            if (child == circleImageView) {
                circleImageView.stopAnimation();
                return true;
            }
            return false;
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return 1;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return top;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            mViewTrackController.onRelease();
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            mViewTrackController.setOtherVisiable(true);
            mViewTrackController.onTopViewPosChanged(left, top);
        }
    }

    @Override
    public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && clickInAvatarView(event)) {
            return true;
        }
        return mDragHelper.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mViewTrackController.setOriginPos(circleImageView.getLeft(), circleImageView.getTop());
    }

    /**
     * 判断点击位置是否位于头像圆形的里面
     */
    private boolean clickInAvatarView(MotionEvent event) {
        boolean isInCircle = true;
        int clickX = (int) event.getRawX();
        int clickY = (int) event.getRawY();

        //获取控件在屏幕的位置
        int[] location = new int[2];
        circleImageView.getLocationOnScreen(location);

        //控件相对于屏幕的x与y坐标
        int x = location[0];
        int y = location[1];

        //圆半径 通过左右坐标计算获得getLeft
        int r = (circleImageView.getRight() - circleImageView.getLeft()) / 2;

        //圆心坐标
        int vCenterX = x + r;
        int vCenterY = y + r;

        //点击位置x坐标与圆心的x坐标的距离
        int distanceX = Math.abs(vCenterX - clickX);
        //点击位置y坐标与圆心的y坐标的距离
        int distanceY = Math.abs(vCenterY - clickY);
        //点击位置与圆心的直线距离
        int distanceZ = (int) Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
        if (distanceZ > r) {
            return false;
        }
        return isInCircle;
    }

}
