package com.github.abraga.fuzetest.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.github.abraga.fuzetest.R

fun ImageView.loadUrl(url: String?) {
    url?.let {
        Glide.with(context)
            .load(it)
            .apply(RequestOptions().placeholder(R.drawable.img_csgo_logo)
            .error(R.drawable.img_csgo_logo))
            .placeholder(R.drawable.img_csgo_logo)
            .into(this)
    }
}

fun ImageView.loadUrlRoundedCorners(url: String?) {
    Glide.with(context)
        .load(url)
        .transform(CenterCrop(), RoundedCorners(16))
        .apply(RequestOptions().placeholder(R.drawable.img_avatar_placeholder)
        .error(R.drawable.img_avatar_placeholder))
        .placeholder(R.drawable.img_avatar_placeholder)
        .into(this)
}