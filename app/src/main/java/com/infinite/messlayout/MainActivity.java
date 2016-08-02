package com.infinite.messlayout;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;

import com.infinite.mess.MessLayout;

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
    private List<MessLayout> mList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        mLayout= (MessLayout) findViewById(R.id.messLayout);
        mPager= (ViewPager) findViewById(R.id.vp);
        PATH = getExternalCacheDir() + File.separator + "metroScrolldata.txt";
        String json=getData();
        try {
            JSONArray array=new JSONArray(json);
            int row=0;
            boolean goNextLine=false;
            boolean isLastSizeSmall=false;
            List<ItemView>[] lists=new List[(int) Math.ceil(array.length()/4)];

            for(int i=0;i<lists.length;i++){
                lists[i]=new ArrayList<>();
            }
            for(int i=0;i<array.length();i++){

                JSONObject object=array.getJSONObject(i);
                ItemView view= (ItemView) LayoutInflater.from(this).inflate(R.layout.layout_item_view, mLayout, false);

                view.setText(object.getString("title"));
                view.setBackgroundColor(Color.GRAY);
                if (object.getString("size").equals("max")){
                    view.setOccupiedColumn(2);
                    row++;
                    goNextLine=true;
                    isLastSizeSmall=false;
                }else {
                    view.setOccupiedColumn(1);
                    if (goNextLine){
                        row++;
                    }else {
                        if (isLastSizeSmall){
                            goNextLine=true;
                        }else {
                            row++;
                        }
                    }

                    isLastSizeSmall=true;
                    lists[row/3].add(view);

                }

            }

            for(List<ItemView> l:lists){
                if (l.size()>0){
                    mLayout=new MessLayout(this);
                    for(ItemView view:l){
                        mLayout.addView(view);
                    }
                    mList.add(mLayout);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        MyAdapter adapter=new MyAdapter(mList,this);
        mPager.setAdapter(adapter);
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
        if (file.exists()) {
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
    }
}
