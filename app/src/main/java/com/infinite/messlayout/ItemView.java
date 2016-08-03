package com.infinite.messlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.infinite.mess.IMess;

/**
 * Created by inf on 2016/8/2.
 */
public class ItemView extends LinearLayout implements IMess{

    private int mColumnCount=2;
    private TextView mTxt;
    private String mStr;
    public ItemView(Context context) {
        super(context);
    }

    public ItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        init();
        mTxt.setText(mStr);
    }

    private void init(){
        mTxt= (TextView) findViewById(R.id.txt);
    }

    @Override
    public int occupiedColumn() {
        return mColumnCount;
    }

    public void setOccupiedColumn(int count){
        mColumnCount=count;
    }

    public void setText(String txt){
//        mTxt.setText(txt);
        mStr=txt;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),MeasureSpec.getSize(heightMeasureSpec));
    }
}
