package com.mbarcelos.avenuecode.kotlintest.util

import android.databinding.BindingAdapter
import android.text.TextUtils
import android.widget.ImageView
import com.bumptech.glide.Glide

object DataBindAdapters {

    @JvmStatic
    @BindingAdapter("src")
    fun setImageSource(imageView: ImageView, imageURL: String) {
        if (!TextUtils.isEmpty(imageURL)) {
            Glide.with(imageView).load(imageURL).into(imageView)
        }
    }
}