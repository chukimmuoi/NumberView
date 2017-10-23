package com.example.chukimmuoi.numberview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

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

    private int mNumber;

    public NumberView(Context context) {
        super(context);
    }

    public NumberView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NumberView);

        final int startNumber = typedArray.getInteger(R.styleable.NumberView_number_start_number, 0);
        final int endNumber   = typedArray.getInteger(R.styleable.NumberView_number_end_number, 0);
        final boolean isScore = typedArray.getBoolean(R.styleable.NumberView_number_is_score, false);
        final int timeSecond  = typedArray.getInteger(R.styleable.NumberView_number_time_second, 0);

        mNumberObject = new NumberObject(context.getResources(), isScore, startNumber, 0F, 0F);

        start(startNumber, endNumber, timeSecond * 1000);
    }

    public NumberView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // Width & height nhỏ nhất của number.
        final int desiredWidth = mNumberObject.getWidth();
        final int desireHeight = mNumberObject.getHeight();

        // Tính toán, thỏa thuận với viewGroup để xác định kích thước cho view.
        int width = reconcileSize(desiredWidth, widthMeasureSpec);
        int height = reconcileSize(desireHeight, heightMeasureSpec);

        setMeasuredDimension(width, height);
    }

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

        // Chính giữa màn hình.
        int xCenter = getWidth() / 2;
        int yCenter = getHeight() / 2;

        // Lấy ra left & top dựa trên width, height, xCenter, yCenter.
        int leftNumber = xCenter - (mNumberObject.getWidth() / 2);
        int topNumber = yCenter - (mNumberObject.getHeight() / 2);

        mNumberObject.setLeft(Float.valueOf(leftNumber));
        mNumberObject.setTop(Float.valueOf(topNumber));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Lưu lại trạng thái ban đầu của canvas.
        canvas.save();

        mNumberObject.onDraw(canvas);

        // Khôi phuc lại trạng thái lúc đầu của canvas sau khi thực hiện các phép biến đổi:
        // translate,scale,rotate,skew,concat or clipRect, clipPath.
        canvas.restore();
    }

    public void setNumber(int number) {
        boolean isChangeLength = String.valueOf(number).length() > String.valueOf(mNumber).length();
        this.mNumber = number;

        mNumberObject.setNumber(number);

        if (isChangeLength) {
            requestLayout();
        } else {
            invalidate();
        }
    }

    public void start(int start, int end, long time) {
        ObjectAnimator animator = ObjectAnimator.ofInt(this, "number", start, end);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(time);
        animator.start();
    }
}
