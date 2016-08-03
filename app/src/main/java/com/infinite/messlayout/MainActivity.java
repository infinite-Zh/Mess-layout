package com.infinite.messlayout;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.infinite.mess.MessLayout;
import com.infinite.mess.SortActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MessLayout mLayout;
    private ViewPager mPager;
    private List<MessLayout> mList = new ArrayList<>();
    private MyAdapter mAdapter;
    private ImageView imgSort;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPager = (ViewPager) findViewById(R.id.vp);
        imgSort= (ImageView) findViewById(R.id.img_sort);
        PATH = getExternalCacheDir() + File.separator + "metroScrolldata.txt";
        mAdapter = new MyAdapter(mList, this);

        mPager.setAdapter(mAdapter);

        imgSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, SortActivity.class);
                startActivity(intent);
            }
        });
    }

    private String PATH = "";

    private void saveData(String str) {
        File file = new File(PATH);
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
            FileOutputStream os = new FileOutputStream(file);
            os.write(str.getBytes());
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String getData() {
        File file = new File(PATH);
        StringBuffer sb = new StringBuffer();
        if (file.exists() && false) {
            try {
                FileInputStream fis = new FileInputStream(file);
                byte[] bbuf = new byte[1024];
                int hasRead = 0;
                while ((hasRead = fis.read(bbuf)) > 0) {
                    System.out.println(new String(bbuf, 0, hasRead));
                    sb.append(new String(bbuf));
                }
                fis.close();
                return sb.toString();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            AssetManager am = getAssets();
            try {
                InputStream is = am.open("metroScrolldata.txt");
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuffer sb2 = new StringBuffer();
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb2.append(line);
                }
                String json = sb2.toString();
                Log.e("content", json);

                saveData(sb2.toString());
                return json;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();

        inflateData();
        mAdapter.notifyDataSetChanged();

    }

    private void inflateData() {
        mList.clear();
        String json = getData();
        try {
            JSONArray array = new JSONArray(json);
            //行数
            int row = 0;
            //占用的列数
            int occupiedColumn = 0;
            //上一个view是否占用两列
            boolean isLastBig = false;

            //每页最多4行，生成一个长度是最大页数的数组
            List<ItemView>[] lists = new List[(int) Math.ceil(array.length() / 4)];
            //给数组里面的每一个元素赋值
            for (int i = 0; i < lists.length; i++) {
                lists[i] = new ArrayList<>();
            }
            for (int i = 0; i < array.length(); i++) {

                //当前数据
                JSONObject object = array.getJSONObject(i);
                //下一组数据
                JSONObject nextObj = null;
                //上上一组数据
                JSONObject last2Obj = null;
                if (i != array.length() - 1) {
                    nextObj = array.optJSONObject(i + 1);
                }
                if (i >= 2) {
                    last2Obj = array.optJSONObject(i - 2);
                }

                ItemView view = (ItemView) LayoutInflater.from(this).inflate(R.layout.layout_item_view, mLayout, false);

                view.setText(object.getString("title"));
                view.setBackgroundColor(Color.GRAY);
                //如果当前数据占两列
                if (object.getString("size").equals("max")) {
                    view.setOccupiedColumn(2);
//                    row++;
                    occupiedColumn = 2;
                    isLastBig = true;
                } else {//当前数据占一列
                    view.setOccupiedColumn(1);
                    occupiedColumn++;
                    //如果上一组占两列，并且下一组占两列，行数+1；
                    if (isLastBig && nextObj != null && nextObj.getString("size").equals("max"))
                        row++;
                    //如果上一组占一列，上上一组占一列，下一组占两列，行数+1
                    if (!isLastBig && last2Obj != null && last2Obj.getString("size")
                                                                  .equals("min") && nextObj != null && nextObj.getString(
                            "size").equals("max"))
                        row++;
                    //如果是第一组数据，并且占一列，并且下一组占两列，行数+1
                    if (i == 0 && nextObj != null && nextObj.getString("size").equals("max"))
                        row++;
                    isLastBig = false;
                }
                //如果占用的列数是2，行数+1，占用列数清零
                if (occupiedColumn == 2) {
                    row++;
                    occupiedColumn = 0;
                }
                lists[row / 5].add(view);

            }
            Log.e("row", row + "");
            for (List<ItemView> l : lists) {
                if (l.size() > 0) {
                    mLayout = (MessLayout) LayoutInflater.from(this)
                                                         .inflate(R.layout.mess_layout, null)
                                                         .findViewById(R.id.messLayout);
                    for (ItemView view : l) {
                        mLayout.addView(view);
                    }
                    mList.add(mLayout);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
