package com.zhan.circularview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;


/**
 * Created by Zhan on 16-01-07.
 */
public class CircularView extends View {

    public enum IconSize {XSMALL, SMALL, MEDIUM, LARGE, XLARGE}

    //Default values
    private final static int DEFAULT_BG_RADIUS = 100;
    private final static int DEFAULT_BG_COLOR = R.color.colorPrimary;
    private final static int DEFAULT_STROKE_WIDTH = 0;
    private final static int DEFAULT_STROKE_COLOR = R.color.black;
    private final static int DEFAULT_STROKE_PADDING = 0;
    private final static int DEFAULT_ICON_SIZE  = 5;//LARGE
    private final static int DEFAULT_ICON_COLOR = R.color.white;


    private Context context;
    private int backgroundRadius;
    private int backgroundColor;
    private int strokeWidth;
    private int strokeColor;
    private int strokePadding;
    private IconSize eiconSize;
    private int iconSize;
    private int iconColor;
    private Drawable iconDrawable;
    private Paint paint;

    public CircularView(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public CircularView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public CircularView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    public CircularView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes){
        this.context = context;

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CircularView, 0, 0);
        try{
            backgroundRadius = a.getDimensionPixelSize(R.styleable.CircularView_cv_bgRadius, DEFAULT_BG_RADIUS);
            backgroundColor = a.getColor(R.styleable.CircularView_cv_bgColor, getResources().getColor(DEFAULT_BG_COLOR));
            strokeWidth = a.getDimensionPixelSize(R.styleable.CircularView_cv_strokeWidth, DEFAULT_STROKE_WIDTH);
            strokeColor = a.getColor(R.styleable.CircularView_cv_strokeColor, getResources().getColor(DEFAULT_STROKE_COLOR));
            strokePadding = a.getDimensionPixelSize(R.styleable.CircularView_cv_strokePadding, DEFAULT_STROKE_PADDING);
            iconDrawable = a.getDrawable(R.styleable.CircularView_cv_iconDrawable);
            iconSize = a.getInteger(R.styleable.CircularView_cv_iconSize, DEFAULT_ICON_SIZE);
            iconColor = a.getColor(R.styleable.CircularView_cv_iconColor, getResources().getColor(DEFAULT_ICON_COLOR));
        }finally {
            a.recycle();
        }

        paint = new Paint();
        paint.setAntiAlias(true);
    }

    public int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /*super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //Get the width measurement
        int widthSize = View.resolveSize(width, widthMeasureSpec);

        //Get the height measurement
        int heightSize = View.resolveSize(height, heightMeasureSpec);

        //MUST call this to store the measurements
        setMeasuredDimension(widthSize, heightSize);
        */

        /*
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int parentWidth = MeasureSpec.getSize(widthMeasureSpec); // receives parents width in pixel
        int parentHeight = MeasureSpec.getSize(heightMeasureSpec);

        //your desired sizes, converted from pixels to setMeasuredDimension's unit
        final int desiredWSpec = MeasureSpec.makeMeasureSpec(parentWidth, MeasureSpec.EXACTLY);
        final int desiredHSpec = MeasureSpec.makeMeasureSpec(parentHeight, MeasureSpec.EXACTLY);
        this.setMeasuredDimension(desiredWSpec, desiredHSpec);
        */

        int desiredWidth = 100;
        int desiredHeight = 100;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) { //specific value
            //Must be this size
            width = widthSize;
            backgroundColor = Color.RED;
        } else if (widthMode == MeasureSpec.AT_MOST) { //match parent
            //Can't be bigger than...
            width = Math.min(desiredWidth, widthSize);
            backgroundColor = Color.BLUE;
        } else { //wrap content
            //Be whatever you want
            width = desiredWidth;
        }

        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            //Must be this size
            height = heightSize;
            strokeColor = Color.RED;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            height = Math.min(desiredHeight, heightSize);
            strokeColor = Color.BLUE;
        } else {
            //Be whatever you want
            height = desiredHeight;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int viewWidthHalf = this.getWidth() / 2;
        int viewHeightHalf = this.getHeight() / 2;

        int radius;
        if (viewWidthHalf > viewHeightHalf) {
            radius = viewHeightHalf;
        } else {
            radius = viewWidthHalf;
        }

        drawCircle(canvas, radius, viewWidthHalf, viewHeightHalf);
        //drawCircle(canvas, backgroundRadius, this.getWidth(), this.getHeight());
        drawIcon(canvas);
        invalidate();
    }
/*
    private int getDesiredWidth() {
        return 50;
    }

    private int getDesiredHeight() {
        return 50;
    }*/

    private void drawCircle(Canvas canvas, int radius, int width, int height){
        if(strokeWidth > 0) {
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            paint.setStrokeWidth(strokeWidth);
            canvas.drawCircle(width, height, radius - (strokeWidth / 2), paint);

            paint.setStyle(Paint.Style.FILL);
            paint.setColor(backgroundColor);
            canvas.drawCircle(width, height, radius - strokeWidth - strokePadding, paint);
        }else{
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(backgroundColor);
            canvas.drawCircle(width, height, radius, paint);
        }
    }

    private void drawIcon(Canvas canvas){
        if(iconDrawable != null){
            Rect bounds = canvas.getClipBounds();

            int multiplier = 5;

            bounds.left += (iconSize * multiplier);
            bounds.right -= (iconSize * multiplier);
            bounds.top += (iconSize * multiplier);
            bounds.bottom -= (iconSize * multiplier);

            iconDrawable.setBounds(bounds);
            iconDrawable.mutate().setColorFilter(iconColor, PorterDuff.Mode.SRC_IN);
            iconDrawable.draw(canvas);
        }
    }

    public int getCircleColor() {
        return backgroundColor;
    }

    public void setCircleColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        invalidate();
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
        invalidate();
    }

    public int getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
        invalidate();
    }

    public int getStrokePadding() {
        return strokePadding;
    }

    public void setStrokePadding(int strokePadding) {
        this.strokePadding = strokePadding;
    }

    public IconSize getIconSize() {
        return eiconSize;
    }

    public void setIconSize(IconSize size) {
        this.eiconSize = size;
        this.iconSize = convertEnumToSize(this.eiconSize);
        invalidate();
    }

    private int convertEnumToSize(IconSize size){
        if(size == IconSize.XSMALL){
            return 17;
        }else if(size == IconSize.SMALL){
            return 13;
        }else if(size == IconSize.MEDIUM){
            return 9;
        }else if(size == IconSize.LARGE){
            return 5;
        }else{
            return 1;
        }
    }

    public int getIconColor() {
        return iconColor;
    }

    public void setIconColor(int iconColor) {
        this.iconColor = iconColor;
        invalidate();
    }

    public Drawable getIconDrawable() {
        return iconDrawable;
    }

    public void setIconDrawable(Drawable iconDrawable) {
        this.iconDrawable = iconDrawable;
        invalidate();
    }
/*
    public void setIcon(int drawableId){
        this.iconDrawable = ResourcesCompat.getDrawable(getResources(), drawableId, this.context.getTheme());
    }*/


}
