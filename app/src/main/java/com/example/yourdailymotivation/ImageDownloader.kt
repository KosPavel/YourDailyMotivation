package com.example.yourdailymotivation

import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import com.squareup.picasso.Picasso


class ImageDownloader(url: String, private val activity: FragmentActivity) {
    private val image = Picasso.get().load(url)

    private fun resize() {
        val width: Int = activity.applicationContext.resources.displayMetrics.widthPixels
        val height: Int = activity.applicationContext.resources.displayMetrics.heightPixels
        image.resize(width, height).centerInside()
    }

    fun setImage(view: ImageView) {
        resize()
        image.into(view)
    }
}
