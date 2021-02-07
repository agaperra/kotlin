package com.geekbrains.kotlin_lessons.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.geekbrains.kotlin_lessons.Constants
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("android:imageURL")
    fun setImageURL(imageView: ImageView, URL: String) {
        try {
            imageView.alpha = 0f
            Picasso.get().load(Constants.imageURL + URL).noFade()
                .into(imageView, object : Callback {
                    override fun onSuccess() {
                        imageView.animate().setDuration(300).alpha(1f).start()
                    }

                    override fun onError(e: Exception) {}
                })
        } catch (ignored: Exception) {
        }
    }
}