package me.donlis.vreader.util;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import androidx.databinding.BindingAdapter;

public class GlideUtil {

    private GlideUtil(){}

    public static GlideUtil getInstance(){
        return GlideHolder.instance;
    }

    private static class GlideHolder{
        private static GlideUtil instance = new GlideUtil();
    }

    @BindingAdapter("android:displayImg")
    public static void displayImg(ImageView view,String url){
        Glide.with(view.getContext())
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade(300))
                .into(view);
    }

}
