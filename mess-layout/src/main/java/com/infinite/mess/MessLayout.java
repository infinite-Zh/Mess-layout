package com.infinite.mess;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by inf on 2016/7/29.
 */
public class MessLayout extends LinearLayout{

    private static final int SPACE=10;
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

        for(int i=0;i<getChildCount();i++){
            IMess mess= (IMess) getChildAt(i);
            int column=mess.occupiedColumn();
            int ratio=1;
            if (column==1){
                ratio=2;
            }else {
                ratio=1;
            }
            measureChild(getChildAt(i),MeasureSpec.makeMeasureSpec((getWidth()-getPaddingLeft()-getPaddingRight()-SPACE)/ratio,MeasureSpec.EXACTLY),MeasureSpec.makeMeasureSpec((getHeight()-getPaddingTop()-getPaddingBottom())/4,MeasureSpec.EXACTLY));
        }
        setMeasuredDimension(widthSize,heightSize);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int left =0;
        int top=0;
        int right=0;
        int bottom=0;
        int lineWidth=0;
        int totalHeight=0;
        for(int i=0;i<getChildCount();i++){
            View view=getChildAt(i);
            int width=view.getMeasuredWidth()-getPaddingLeft()-getPaddingRight();
            int height=view.getMeasuredHeight()-getPaddingTop()-getPaddingBottom();
            //不换行
           if(lineWidth+width<=getWidth()){
               left=lineWidth;
               top=totalHeight;
               right=left+width;
               bottom=top+height;
               lineWidth+=width+SPACE;

           }else {//换行
               totalHeight+=height+SPACE;
               left=0;
               top=totalHeight;
               right=left+width;
               bottom=top+height;
               lineWidth=width+SPACE;
           }
            view.layout(left,top,right,bottom);
        }
    }


}
