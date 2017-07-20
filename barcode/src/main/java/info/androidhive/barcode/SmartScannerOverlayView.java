package info.androidhive.barcode;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.ViewGroup;

/**
 * Created by ravi on 04/05/17.
 */

public class SmartScannerOverlayView extends ViewGroup {
    float left, top, endY;
    int rectWidth;
    int heightOffset, frames = 5;
    boolean reverseAnimation;
    Context context;

    public SmartScannerOverlayView(Context context) {
        super(context);
        this.context = context;
    }

    public SmartScannerOverlayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.context = context;
    }

    public SmartScannerOverlayView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.SmartScannerOverlayView,
                0, 0);

        heightOffset = a.getInteger(R.styleable.SmartScannerOverlayView_height_offset, 0);
        rectWidth = getResources().getInteger(R.integer.scanner_rect_width);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        left = (w - dpToPx(rectWidth)) / 2;
        top = (h - dpToPx(rectWidth)) / 2;
        endY = top;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // draw transparent rect
        int cornerRadius = 0;
        Paint eraser = new Paint();
        eraser.setAntiAlias(true);
        eraser.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));


        RectF rect = new RectF(left, top, dpToPx(rectWidth) + left, dpToPx(rectWidth) + top);
        canvas.drawRoundRect(rect, (float) cornerRadius, (float) cornerRadius, eraser);

        // draw horizontal line
        Paint line = new Paint();
        line.setColor(ContextCompat.getColor(getContext(), R.color.scanner_line));
        line.setStrokeWidth(4f);

        if (endY >= top + dpToPx(rectWidth) + frames) {
            reverseAnimation = true;
        } else if (endY == top + frames) {
            reverseAnimation = false;
        }

        if (reverseAnimation) {
            endY -= frames;
        } else {
            endY += frames;
        }

        canvas.drawLine(left, endY, left + dpToPx(rectWidth), endY, line);

        invalidate();
    }
}