package com.smart.journal.customview.imageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.makeramen.roundedimageview.RoundedImageView;
import com.smart.journal.R;

import static android.view.View.MeasureSpec.EXACTLY;

/**
 * @author guandongchen
 * @date 2018/9/29
 */
public class AspectRatioRoundedImageView extends RoundedImageView{

    private  int widthRatio;
    private  int heightRatio;

    public AspectRatioRoundedImageView(Context context) {
        super(context);
    }

    public AspectRatioRoundedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttr(context,attrs);
    }

    public AspectRatioRoundedImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context,attrs);
    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == EXACTLY) {
            if (heightMode != EXACTLY) {
                heightSize = (int) (widthSize * 1f / widthRatio * heightRatio);
            }
        } else if (heightMode == EXACTLY) {
            widthSize = (int) (heightSize * 1f / heightRatio * widthRatio);
        } else {
            throw new IllegalStateException("Either width or height must be EXACTLY.");
        }

        widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(widthSize, EXACTLY);
        heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(heightSize, EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void initAttr(Context context, AttributeSet attrs){
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AspectRatioViewPager);
        widthRatio = a.getInteger(R.styleable.AspectRatioViewPager_widthRatio, 1);
        heightRatio = a.getInteger(R.styleable.AspectRatioViewPager_heightRatio, 1);
        a.recycle();
    }
}
