package com.android.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class RoundFrameLayout extends FrameLayout {
    /**
     * 图片的类型，圆形or圆角
     */
    private int type;
    public static final int TYPE_CIRCLE = 0;
    public static final int TYPE_ROUND = 1;
    public static final int TYPE_OVAL = 2;
    private final RectF roundRect = new RectF();
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    /**
     * 圆角的半径
     */
    private float mRadius;
    private int mBackgroundColor;
    public RoundFrameLayout(@NonNull Context context) {
        super(context);
    }

    public RoundFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundFrameLayout, defStyleAttr, 0);
        type = a.getInt(R.styleable.RoundFrameLayout_rll_type, TYPE_ROUND);
        mRadius = a.getDimension(R.styleable.RoundFrameLayout_rll_radius, 0);
        mBackgroundColor = a.getColor(R.styleable.RoundFrameLayout_rll_backgroundColor, Color.TRANSPARENT);
        a.recycle();
        paint.setColor(mBackgroundColor);
        paint.setAntiAlias(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        setWillNotDraw(false);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        roundRect.set(0, 0, getWidth(), getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(mBackgroundColor);
        if (type == TYPE_ROUND) {
            canvas.drawRoundRect(roundRect, mRadius, mRadius, paint);
        } else if (type == TYPE_CIRCLE) {
            canvas.drawCircle(roundRect.centerX(), roundRect.centerY(), mRadius, paint);
        } else {
            canvas.drawOval(roundRect, paint);
        }
    }
}
