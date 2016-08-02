package com.infinite.mess;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by inf on 2016/8/2.
 */
public class EzViewPager extends ViewPager {

    private String mJson;
    public EzViewPager(Context context) {
        super(context);
    }

    public EzViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setData(String json){
        mJson=json;
    }

    private void show(){
        try {
            JSONArray array=new JSONArray(mJson);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
