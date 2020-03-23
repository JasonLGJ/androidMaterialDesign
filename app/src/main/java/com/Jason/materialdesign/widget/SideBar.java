package com.Jason.materialdesign.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.Jason.materialdesign.R;

/**
 * Copyright (c) 2003,成都天奥信息科技有限公司
 * All rights reserved.
 *
 * 功能描述：左边字母栏
 * 编写人：李广金
 * 开始日期：2020.03.17
 */
public class SideBar extends AppCompatTextView {

    private String[] letters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#"};

    private Paint textPaint;

    private Paint bigTextPaint;

    private Paint scaleTextPaint;

    private Canvas canvas;

    private int itemH;

    private int w;

    private int h;

    /**
     * 普通情况下字体大小
     */
    float singleTextH;

    /**
     * 缩放离原始的宽度
     */
    private float scaleWidth;

    /**
     * 滑动的Y
     */
    private float eventY = 0;

    /**
     * 缩放的倍数
     */
    private int scaleSize = 1;

    /**
     * 缩放个数item，即开口大小
     */
    private int scaleItemCount = 6;

    private ISideBarSelectCallBack callBack;

    public SideBar(Context context) {
        this(context, null);
    }

    public SideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    /**
     * 初始化函数
     *
     * @param attrs attr
     */
    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.SideBar);
            scaleSize = ta.getInteger(R.styleable.SideBar_scaleSize, 1);
            scaleItemCount = ta.getInteger(R.styleable.SideBar_scaleItemCount, 6);
            scaleWidth = ta.getDimensionPixelSize(R.styleable.SideBar_scaleWidth, dp(100));
            ta.recycle();
        }
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(getCurrentTextColor());
        textPaint.setTextSize(getTextSize());
        textPaint.setTextAlign(Paint.Align.CENTER);
        bigTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bigTextPaint.setColor(getCurrentTextColor());
        bigTextPaint.setTextSize(getTextSize() * (scaleSize + 3));
        bigTextPaint.setTextAlign(Paint.Align.CENTER);
        scaleTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        scaleTextPaint.setColor(getCurrentTextColor());
        scaleTextPaint.setTextSize(getTextSize() * (scaleSize + 1));
        scaleTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    public void setDataResource(String[] data) {
        letters = data;
        invalidate();
    }

    public void setOnStrSelectCallBack(ISideBarSelectCallBack callBack) {
        this.callBack = callBack;
    }

    /**
     * 设置字体缩放比例
     */
    public void setScaleSize(int scale) {
        scaleSize = scale;
        invalidate();
    }

    /**
     * 设置缩放字体的个数，即开口大小
     */
    public void setScaleItemCount(int scaleItemCount) {
        this.scaleItemCount = scaleItemCount;
        invalidate();
    }

    private int dp(int px) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (px * scale + 0.5f);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                if (event.getX() > (w - getPaddingRight() - singleTextH - 10)) {
                    eventY = event.getY();
                    invalidate();
                    return true;
                }
                else {
                    eventY = 0;
                    invalidate();
                    break;
                }
            case MotionEvent.ACTION_CANCEL:
                eventY = 0;
                invalidate();
                return true;
            case MotionEvent.ACTION_UP:
                if (event.getX() > (w - getPaddingRight() - singleTextH - 10)) {
                    eventY = 0;
                    invalidate();
                    return true;
                }
                else
                    break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        this.canvas = canvas;
        DrawView(eventY);
    }

    private void DrawView(float y) {
        int currentSelectIndex = -1;
        if (y != 0) {
            for (int i = 0; i < letters.length; i++) {
                float currentItemY = itemH * i;
                float nextItemY = itemH * (i + 1);
                if (y >= currentItemY && y < nextItemY) {
                    currentSelectIndex = i;
                    if (callBack != null) {
                        callBack.onSelectStr(currentSelectIndex, letters[i]);
                    }
                    //画大的字母
                    Paint.FontMetrics fontMetrics = bigTextPaint.getFontMetrics();
                    float bigTextSize = fontMetrics.descent - fontMetrics.ascent;
                    canvas.drawText(letters[i], w - getPaddingRight() - scaleWidth - bigTextSize, singleTextH + itemH * i, bigTextPaint);
                }
            }
        }
        drawLetters(y, currentSelectIndex);
    }

    private void drawLetters(float y, int index) {
        w = getMeasuredWidth();
        h = getMeasuredHeight();
        itemH = h / letters.length;
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        singleTextH = fontMetrics.descent - fontMetrics.ascent;
        for (int i = 0; i < letters.length; i++) {
            canvas.drawText(letters[i], w - getPaddingRight(), singleTextH + itemH * i, textPaint);
        }
    }

    /**
     * 回调接口
     */
    public interface ISideBarSelectCallBack {
        void onSelectStr(int index, String selectStr);
    }
}
