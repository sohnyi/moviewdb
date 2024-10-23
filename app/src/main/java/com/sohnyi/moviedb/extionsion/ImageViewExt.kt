@file:JvmName("ImageExt")

package com.sohnyi.moviedb.extionsion

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

/**
 * 加载图片
 *
 * @param url         图片地址
 */
fun ImageView.load(
    url: String?,
    roundingRadius: Int = 0,
) {

    if (url.isNullOrEmpty()) {
        return
    }

    val newUrl = if (!url.startsWith("http")) {
        "https://image.tmdb.org/t/p/w500$url"
    } else {
        url
    }

    val transformations = arrayOf(CenterCrop(), RoundedCorners(roundingRadius))

    // 设置加载选项
    val requestOptions =
        RequestOptions()
            .transform(*transformations)


    Glide.with(this.context)
        .load(newUrl)
        .apply(requestOptions)
        .thumbnail(
            Glide.with(this.context)
                .load(newUrl)
                .apply(
                    RequestOptions()
                        .sizeMultiplier(0.2f)
                        .transform(CenterCrop())
                )

        )
        .into(this)
}