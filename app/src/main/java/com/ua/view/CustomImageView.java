package com.ua.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.TypedValue;
import android.widget.ImageView;

import com.ua.R;

/**
 * Created by justs_000 on 2014/12/20.
 */


/**
 * 自定义View，实现圆角，圆形等效果

 */
public class CustomImageView extends ImageView
{

    /**
     * TYPE_CIRCLE / TYPE_ROUND
     */
    private int type;
    private static final int TYPE_CIRCLE = 0;
    private static final int TYPE_ROUND = 1;
    private static final int TYPE_SQUARE_ROUND = 2;

    /**
     * 图片
     */
    private Bitmap mSrc;

    /**
     * 圆角的大小
     */
    private int mRadius;

    /**
     * 控件的宽度
     */
    private int mWidth;
    /**
     * 控件的高度
     */
    private int mHeight;

    public CustomImageView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public CustomImageView(Context context)
    {
        this(context, null);
    }

    /**
     * 初始化一些自定义的参数
     *
     * @param context
     * @param attrs
     * @param defStyle
     */
    public CustomImageView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.CustomImageView, defStyle, 0);

        int n = a.getIndexCount();
        for (int i = 0; i < n; i++)
        {
            int attr = a.getIndex(i);
            switch (attr)
            {
                case R.styleable.CustomImageView_src:
                    mSrc = BitmapFactory.decodeResource(getResources(),
                            a.getResourceId(attr, 0));
                    break;
                case R.styleable.CustomImageView_type:
                    type = a.getInt(attr, 0);// 默认为Circle
                    break;
                case R.styleable.CustomImageView_borderRadius:
                    mRadius = a.getDimensionPixelSize(attr, (int) TypedValue
                            .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f,
                                    getResources().getDisplayMetrics()));// 默认为10DP
                    break;
            }
        }
        a.recycle();
    }

    public void SetPic(Bitmap bitmap)
    {
        mSrc = bitmap;
        //this.postInvalidate();
        CustomImageView.this.invalidate();
    }

    @Override
    public void setImageBitmap(Bitmap bm)
    {
        //super.setImageBitmap(bm);
        SetPic(bm);
    }
    @Override
    public void setImageDrawable(Drawable drawable)
    {
        BitmapDrawable bd = (BitmapDrawable)drawable;
        SetPic(bd.getBitmap());
    }


    /**
     * 计算控件的高度和宽度
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /**
         * 设置宽度
         */
        if(mSrc==null)
        {
            setMeasuredDimension(mWidth, mHeight);
            return;
        }

        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);

        if (specMode == MeasureSpec.EXACTLY)// match_parent , accurate
        {
            mWidth = specSize;
        } else
        {
            // 由图片决定的宽
            int desireByImg = getPaddingLeft() + getPaddingRight()
                    + ((mSrc!=null)?mSrc.getWidth():0);
            if (specMode == MeasureSpec.AT_MOST)// wrap_content
            {
                mWidth = Math.min(desireByImg, specSize);
            } else

                mWidth = desireByImg;
        }

        /***
         * 设置高度
         */

        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY)// match_parent , accurate
        {
            mHeight = specSize;
        } else
        {
            int desire = getPaddingTop() + getPaddingBottom()
                    + mSrc.getHeight();

            if (specMode == MeasureSpec.AT_MOST)// wrap_content
            {
                mHeight = Math.min(desire, specSize);
            } else
                mHeight = desire;
        }

        setMeasuredDimension(mWidth, mHeight);

    }

    /**
     * 绘制
     */
    @Override
    protected void onDraw(Canvas canvas)
    {
        if((mSrc==null)|(!(mWidth>0))|(!(mHeight>0)))
            return;
        switch (type)
        {
            // 如果是TYPE_CIRCLE绘制圆形
            case TYPE_CIRCLE:
                int min = Math.min(mWidth, mHeight);
                /**
                 * 长度如果不一致，按小的值进行压缩
                 */
                mSrc = Bitmap.createScaledBitmap(mSrc, min, min, false);
                canvas.drawBitmap(createCircleImage(mSrc, min), 0, 0, null);
                break;
            case TYPE_SQUARE_ROUND:
                mSrc = Bitmap.createScaledBitmap(mSrc, mWidth, mHeight, false);
                canvas.drawBitmap(createSquareRoundConerImage(mSrc), 0, 0, null);
                break;
            case TYPE_ROUND:
                mSrc = Bitmap.createScaledBitmap(mSrc, mWidth, mHeight, false);
                canvas.drawBitmap(createRoundConerImage(createSquareWithTitleSpace(mSrc,getResources().getColor(R.color.lightorange))), 0, 0, null);
                break;

        }

    }

    /**
     * 根据原图和变长绘制圆形图片
     *
     * @param source
     * @param min
     * @return
     */
    private Bitmap createCircleImage(Bitmap source, int min)
    {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(min, min, Config.ARGB_8888);
        /**
         * 产生一个同样大小的画布
         */
        Canvas canvas = new Canvas(target);
        /**
         * 首先绘制圆形
         */
        canvas.drawCircle(min / 2, min / 2, min / 2, paint);
        /**
         * 使用SRC_IN，参考上面的说明
         */
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        /**
         * 绘制图片
         */
        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }

    /**
     * 根据原图添加圆角
     *
     * @param source
     * @return
     */
    private Bitmap createRoundConerImage(Bitmap source)
    {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(mWidth, mHeight, Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
        RectF rect = new RectF(0, 0, source.getWidth(), source.getHeight());
        canvas.drawRoundRect(rect, mRadius, mRadius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }

    private Bitmap createSquareWithTitleSpace(Bitmap source , int color)
    {
        final Paint paint =new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
        paint.setAlpha(255);
        Bitmap target = Bitmap.createBitmap(mWidth, mHeight, Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
        canvas.drawBitmap(source, 0, 0, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        RectF titleSpace =new RectF(0, 0, source.getWidth(), source.getHeight()/6);
        canvas.drawRect(titleSpace,paint);
        return target;
    }
    /**
     * 根据原图下角添加圆角
     *
     * @param source
     * @return
     */
    private Bitmap createSquareRoundConerImage(Bitmap source)
    {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(mWidth, mHeight, Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
        RectF rect = new RectF(0, 0, source.getWidth(), source.getHeight());
        RectF rect2 = new RectF(0,0,source.getWidth(), source.getHeight()/2);
        canvas.drawRect(rect2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));
        canvas.drawRoundRect(rect, mRadius, mRadius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }
}


