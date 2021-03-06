package com.example.chukimmuoi.numberview;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Hanet Electronics
 * @Skype : chukimmuoi
 * @Mobile : +84 167 367 2505
 * @Email : muoick@hanet.com
 * @Website: http://hanet.com/
 * @Project: NumberView
 * Created by CHUKIMMUOI on 10/23/2017.
 */
public class NumberObject {

    private static final int[] RES_ID_BITMAP_SCORE = {
            R.drawable.ic_score_0,
            R.drawable.ic_score_1,
            R.drawable.ic_score_2,
            R.drawable.ic_score_3,
            R.drawable.ic_score_4,
            R.drawable.ic_score_5,
            R.drawable.ic_score_6,
            R.drawable.ic_score_7,
            R.drawable.ic_score_8,
            R.drawable.ic_score_9
    };

    private static final int[] RES_ID_BITMAP_NUMBER = {
            R.drawable.ic_number_0,
            R.drawable.ic_number_1,
            R.drawable.ic_number_2,
            R.drawable.ic_number_3,
            R.drawable.ic_number_4,
            R.drawable.ic_number_5,
            R.drawable.ic_number_6,
            R.drawable.ic_number_7,
            R.drawable.ic_number_8,
            R.drawable.ic_number_9,
    };

    private List<Bitmap> mBitmapArray;
    private Paint mPaint;
    private Float mLeft;
    private Float mTop;

    private Resources mResources;
    private String mNumber = "00";

    private int mWidth;
    private int mHeight;

    private char[] mNumberArray;

    public NumberObject(Resources resources, boolean isScore, int number, Float left, Float top) {
        this.mResources = resources;

        this.mLeft = left;
        this.mTop  = top;

        this.mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.mPaint.setDither(true);
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStyle(Paint.Style.FILL);

        mBitmapArray = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(
                    mResources,
                    isScore ? RES_ID_BITMAP_SCORE[i] : RES_ID_BITMAP_NUMBER[i]
            );

            mBitmapArray.add(bitmap);
        }

        setNumber(number);
    }

    public void setNumber(int number) {
        if (number < 0) number = 0;

        this.mNumber = number < 10 ? "0".concat(String.valueOf(number)) : String.valueOf(number);
        mNumberArray = this.mNumber.trim().toCharArray();

        mWidth  = 0;
        mHeight = 0;
        for (char numberChar : mNumberArray) {
            String numberString = String.valueOf(numberChar);
            if (isNumber(numberString)) {
                int numberInt = Integer.parseInt(numberString);

                mWidth = mWidth + mBitmapArray.get(numberInt).getWidth();
                Bitmap bitmap = mBitmapArray.get(numberInt);
                if (bitmap.getHeight() > mHeight) {
                    mHeight = bitmap.getHeight();
                }
            }
        }
    }

    private boolean isNumber(String string) {
        if (TextUtils.isEmpty(string)) return false;

        return string.matches("[+-]?\\d*(\\.\\d+)?");
    }

    public void setLeft(Float left) {
        this.mLeft = left;
    }

    public void setTop(Float top) {
        this.mTop = top;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public void onDraw(Canvas canvas) {
        int i = 0;
        for (char numberChar : mNumberArray) {
            String numberString = String.valueOf(numberChar);
            if (isNumber(numberString)) {
                int numberInt = Integer.parseInt(numberString);
                if (mBitmapArray == null || mBitmapArray.isEmpty()) return;
                Bitmap bitmap = mBitmapArray.get(numberInt);
                canvas.drawBitmap(bitmap, mLeft + bitmap.getWidth() * i, mTop, mPaint);
                i++;
            }
        }
    }

    private void clearPaint() {
        if (mPaint != null) {
            mPaint.reset();
            mPaint = null;
        }
    }

    private void clearListBitmap() {
        if (mBitmapArray != null) {
            for (Bitmap bitmap : mBitmapArray) {
                bitmap.recycle();
            }
            mBitmapArray.clear();
            mBitmapArray = null;
        }

        System.gc();
    }

    public void onDestroy() {
        clearPaint();
        clearListBitmap();
    }
}
