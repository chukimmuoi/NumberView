package com.example.chukimmuoi.numberview;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author : Hanet Electronics
 * @Skype : chukimmuoi
 * @Mobile : +84 167 367 2505
 * @Email : muoick@hanet.com
 * @Website: http://hanet.com/
 * @Project: NumberView
 * Created by CHUKIMMUOI on 10/23/2017.
 */
public class NumberView extends View {

    private NumberObject mNumberObject;

    public NumberView(Context context) {
        super(context);
    }

    public NumberView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mNumberObject = new NumberObject(context.getResources(), 98, 0F, 0F);
    }

    public NumberView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // Width & height nho nhat cua number.
        final int desiredWidth = mNumberObject.getWidth();
        final int desireHeight = mNumberObject.getHeight();

        // Tinh toan, thoa thuan voi viewGroup de xac dinh kich thuoc cho view.
        int width = reconcileSize(desiredWidth, widthMeasureSpec);
        int height = reconcileSize(desireHeight, heightMeasureSpec);

        setMeasuredDimension(width, height);
    }

    /**
     * Reconcile a desired size for the view contents with a {@link android.view.View.MeasureSpec}
     * constraint passed by the parent.
     *
     * This is a simplified version of {@link View#resolveSize(int, int)}
     *
     * @param contentSize Size of the view's contents.
     * @param measureSpec A {@link android.view.View.MeasureSpec} passed by the parent.
     * @return A size that best fits {@code contentSize} while respecting the parent's constraints.
     */
    private int reconcileSize(int contentSize, int measureSpec) {
        final int mode = MeasureSpec.getMode(measureSpec);
        final int specSize = MeasureSpec.getSize(measureSpec);

        switch (mode) {
            // match_parent | 300dp.
            case MeasureSpec.EXACTLY:
                return specSize;
            // wrap_content | match_parent.
            case MeasureSpec.AT_MOST:
                return Math.min(contentSize, specSize);
            case MeasureSpec.UNSPECIFIED:
            default:
                return contentSize;
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        // Chinh giua man hinh.
        int xCenter = getWidth() / 2;
        int yCenter = getHeight() / 2;

        // Lay ra left & top dua tren width, height, xCenter, yCenter.
        int leftNumber = xCenter - (mNumberObject.getWidth() / 2);
        int topNumber = yCenter - (mNumberObject.getHeight() / 2);

        mNumberObject.setLeft(Float.valueOf(leftNumber));
        mNumberObject.setTop(Float.valueOf(topNumber));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mNumberObject.onDraw(canvas);
    }
}
