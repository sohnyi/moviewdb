@file:JvmName("ImageExt")

package com.sohnyi.moviedb.extionsion

import android.widget.ImageView
import com.bumptech.glide.Glide
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
    // 设置加载选项
    val requestOptions =
        RequestOptions()
            .transform(RoundedCorners(roundingRadius))
            .centerCrop()


    Glide.with(this.context)
        .load(url)
        .apply(requestOptions)
        .thumbnail(
            Glide.with(this.context)
                .load(url)
                .apply(RequestOptions().sizeMultiplier(0.2f))
        )
        .into(this)
}