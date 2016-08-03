package com.infinite.messlayout;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.infinite.mess.MessLayout;

import java.util.List;

/**
 * Created by inf on 2016/8/2.
 */
public class MyAdapter extends PagerAdapter{

    private List<MessLayout> list;
    private Context mCtx;
    public MyAdapter(List<MessLayout> lists, Context context){
        list=lists;
        mCtx=context;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(list.get(position));
        return list.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) list.get(position));
    }
}
