package com.richdataco.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.richdataco.commonlib.R;


/**
 * Created by OlaWang on 2017/5/3.
 */
public class RoundImageView extends android.support.v7.widget.AppCompatImageView {

    private Drawable mDstDrawable;
    private Bitmap mDstBitmap;
    private Paint mPaint;
    private PorterDuffXfermode mXfermode;


    public RoundImageView(Context context) {
        this(context, null, 0);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView);
        mDstDrawable = typedArray.getDrawable(R.styleable.RoundImageView_frameSrc);
        typedArray.recycle();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLUE);

        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mDstDrawable != null) {
            if (getMeasuredWidth() != 0 && getMeasuredHeight() != 0) {
                mDstBitmap = drawableToBitmap(mDstDrawable, getMeasuredWidth(), getMeasuredHeight());
            }
        }
    }

    public static Bitmap drawableToBitmap(Drawable drawable, int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.saveLayer(0, 0, getWidth(), getHeight(), null,
                Canvas.MATRIX_SAVE_FLAG
                        | Canvas.CLIP_SAVE_FLAG
                        | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG
                        | Canvas.FULL_COLOR_LAYER_SAVE_FLAG
                        | Canvas.CLIP_TO_LAYER_SAVE_FLAG);

        super.onDraw(canvas);

        if (mDstBitmap != null && mDstBitmap.getWidth() != 0) {
            mPaint.setXfermode(mXfermode);
            mPaint.setColor(Color.BLUE);
            canvas.drawBitmap(mDstBitmap, 0, 0, mPaint);
            mPaint.setXfermode(null);
        }
    }
}
