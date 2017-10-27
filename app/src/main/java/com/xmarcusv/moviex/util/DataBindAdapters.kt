package com.xmarcusv.moviex.util

import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.TransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions


object DataBindAdapters {

    @JvmStatic
    @BindingAdapter("src", "imageOptions", "imageCallback", "imageTransitions", requireAll = false)
    fun setImageSource(imageView: ImageView, imageURL: String, options: RequestOptions?, callback: RequestListener<Drawable>?, transitionOptions: TransitionOptions<*, in Drawable>?) {
        if (!TextUtils.isEmpty(imageURL)) {

            val glide = Glide.with(imageView).load(imageURL)

            options?.let { glide.apply(it) }

            callback?.let { glide.listener(it) }

            transitionOptions?.let { glide.transition(it) }

            glide.into(imageView)
        }
    }
}