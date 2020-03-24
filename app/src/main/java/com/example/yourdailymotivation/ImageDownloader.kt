package com.example.yourdailymotivation

import android.widget.ImageView
import com.squareup.picasso.Picasso

class ImageDownloader(url: String) {
    private val image = Picasso.get().load(url)

    fun setImage(view: ImageView) {
        image.into(view)
    }
}