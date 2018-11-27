package com.example.tiamo.oneday;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.tiamo.oneday.BaseAdapter.SlideshowAdapter;
import com.example.tiamo.oneday.Bean.ContentBean;
import com.example.tiamo.oneday.NetUtil.Netutil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager pager;
    private List<String> list;
    private SlideshowAdapter adapter;
    private TextView text_title,text_price,text_discounts;
    private final int UPDATE_UI=1;
    private String paht = "http://www.zhaoapi.cn/product/getProductDetail?pid=1";
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            pager.setCurrentItem(pager.getCurrentItem()+1);
            handler.sendEmptyMessageDelayed(0,1000);

            switch (msg.what){
                case UPDATE_UI:
                    ContentBean.DataBean request= (ContentBean.DataBean) msg.obj;
                    text_title.setText(request.getTitle());
                    text_price.setText("原价"+request.getPrice());
                    text_discounts.setText("现价"+request.getBargainPrice());
                    break;

            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = new ArrayList<>();
        //获取资源ID
        pager = findViewById(R.id.pager);
        text_title = findViewById(R.id.title);
        text_price = findViewById(R.id.price);
        text_discounts = findViewById(R.id.d_discounts);
        text_price.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
        //添加适配器
        adapter = new SlideshowAdapter(this);
        pager.setAdapter(adapter);

        initData();

        pager.setCurrentItem(5000);
        int i = pager.getCurrentItem();
//        handler.sendEmptyMessageDelayed(i,5000);

    }
    //gson解析
    public void initData(){
        Netutil.getJson(paht, ContentBean.class, new Netutil.CallBack<ContentBean>() {
            @Override
            public void successs(ContentBean o) {

                String images = o.getData().getImages();



                adapter.setData(sub(o.getData().getImages()));
                handler.sendMessage(handler.obtainMessage(UPDATE_UI,o.getData()));
            }
        });

    }

    public List<String> sub(String str){
        int index = str.indexOf("|");
        if(index>0){
            String substring = str.substring(0, index);
            list.add(substring);
            sub(str.substring(index+1,str.length()));
        }else{
            list.add(str);
        }
        return list;
    }
}
