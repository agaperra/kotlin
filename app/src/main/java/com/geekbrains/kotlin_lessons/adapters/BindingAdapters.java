package com.geekbrains.kotlin_lessons.adapters;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.geekbrains.kotlin_lessons.Constants;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class BindingAdapters {

    @BindingAdapter("android:imageURL")
    public static void setImageURL(ImageView imageView, String URL){
        try {
            imageView.setAlpha(0f);
            Picasso.get().load(Constants.imageURL+URL).noFade().into(imageView, new Callback() {
                @Override
                public void onSuccess() {
                    imageView.animate().setDuration(300).alpha(1f).start();
                }

                @Override
                public void onError(Exception e) {

                }
            });
        }
        catch (Exception ignored){

        }
    }
}
