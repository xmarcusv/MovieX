package com.xmarcusv.moviex.util

import android.databinding.BindingAdapter
import android.text.TextUtils
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

object DataBindAdapters {

    @JvmStatic
    @BindingAdapter("src", "imageOptions", requireAll = false)
    fun setImageSource(imageView: ImageView, imageURL: String, options: RequestOptions?) {
        if (!TextUtils.isEmpty(imageURL)) {

            val glide = Glide.with(imageView).load(imageURL)

            options?.let { glide.apply(it) }

            glide.into(imageView)
        }
    }
}