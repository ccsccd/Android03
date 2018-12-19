package com.example.ccs.redrock3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Data> datas=new ArrayList<Data>();
    RecyclerAdapter recyclerAdapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        recyclerView=findViewById(R.id.recyclerview);
        recyclerAdapter=new RecyclerAdapter(datas,this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(manager);
    }

    private void init() {
        HttpConnect httpConnect=new HttpConnect("https://www.apiopen.top/novelSearchApi?" +
                "name=%E7%9B%97%E5%A2%93%E7%AC%94%E8%AE%B0");
        httpConnect.sendRequestWithHttpURLConnection(new HttpConnect.Callback() {
            @Override
            public void finish(String respone) {
                   parseJSON(respone);
            }
        });
    }
    private void parseJSON(String respone) {
        try {
            JSONObject jsonObject=new JSONObject(respone);
            JSONArray jsonArray=new JSONArray(jsonObject.getString("data"));
            for (int i = 0; i < jsonArray.length(); i++) {
                Data data=new Data(jsonArray.getString(i));
                datas.add(data);
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    recyclerAdapter.notifyDataSetChanged();
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
