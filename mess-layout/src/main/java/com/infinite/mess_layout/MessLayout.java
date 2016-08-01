package com.infinite.mess_layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by inf on 2016/7/29.
 */
public class MessLayout extends ViewGroup{

    public MessLayout(Context context) {
        super(context);
    }

    public MessLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MessLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);

        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(widthSize,heightSize);

    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        for(int i=0;i<getChildCount();i++){
            View view=getChildAt(i);
        }

    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(getContext(),null);
    }

}
