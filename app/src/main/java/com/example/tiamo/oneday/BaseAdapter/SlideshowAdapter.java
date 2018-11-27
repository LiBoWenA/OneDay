package com.example.tiamo.oneday.BaseAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.tiamo.oneday.Bean.ContentBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class SlideshowAdapter extends PagerAdapter {
    private Context context;
    private List<String> list;

    public SlideshowAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void setData(List<String> lists){
        list = lists;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size()>0?5000:0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        container.addView(imageView);
        ImageLoader.getInstance().displayImage(list.get(position%list.size()),imageView);
        return imageView;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}

