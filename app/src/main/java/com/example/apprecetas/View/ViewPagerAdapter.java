package com.example.apprecetas.View;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.apprecetas.R;
import com.squareup.picasso.Picasso;

public class ViewPagerAdapter extends PagerAdapter {
    Activity activity;
    String[] images;
    LayoutInflater inflater;

    public ViewPagerAdapter(Activity activity,String[] images){
        this.activity = activity;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.viewpager_item,container,false);

        ImageView image;
        image = (ImageView)itemView.findViewById(R.id.image_view1);
        DisplayMetrics dis = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dis);
        int height = dis.heightPixels;
        int width = dis.widthPixels;
        image.setMinimumHeight(height);
        image.setMinimumWidth(width);

        try{
            System.out.println("---------------------------------------"+images[position]);
            Picasso.get().load(MenuActivity.conversorDirecciones(images[position])).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(image);
        }catch (Exception ex){

        }
        container.addView(itemView);
        return itemView;
    }

    public void destroyItem(ViewGroup container, int position, Object object){
        ((ViewPager) container).removeView((View)object);
    }
}
