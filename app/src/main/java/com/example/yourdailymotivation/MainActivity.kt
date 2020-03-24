package com.example.yourdailymotivation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.fragment.app.FragmentManager
import org.json.JSONObject
import java.net.URL
import kotlin.reflect.typeOf

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
        next 3 lines = download and set image to imageView
        val imageView: ImageView = findViewById(R.id.imageView)
        val imageDownloader = ImageDownloader("https://i.redd.it/tvwstui5jnu31.png")
        imageDownloader.setImage(imageView)
         */

        //jsonDownloader.kt - AsyncTask
//        val jsonDownloader = JsonDownloader()
//        val urls = jsonDownloader.execute()

//        val fm = supportFragmentManager
//        val ft = fm.beginTransaction()
//        for ((i, x) in (0..10).withIndex()) {
//            val fragment = PostFragment()
//            val tag = "test_$i"
//            fragment.setUrl(tag)
//            ft.add(R.id.linearLayout, fragment, tag)
//        }
//        ft.commit()
        val fm = supportFragmentManager
        val jsonDownloader = JsonDownloaderThread(fm)
        jsonDownloader.start()
    }
}

class JsonDownloaderThread(private val fragmentManager: FragmentManager) : Thread() {

    private val getMotivatedHotPostsUrl = "https://www.reddit.com/r/GetMotivated/hot/.json"
    private var hotPosts: JSONObject? = null

    override fun run() {
        super.run()
        val apiResponse = URL(getMotivatedHotPostsUrl).readText()
        hotPosts = JSONObject(apiResponse)
        getImagesUrls()
        TODO("observer pattern?" +
                "or maybe simply get json and load img by Picasso?")
    }

    private fun getImagesUrls() {
        val data = hotPosts!!.getJSONObject("data").getJSONObject("children")
        for (key in data.keys()) {
            val post = data.getJSONObject(key).getJSONObject("data")
            if (post.getString("title").contains("[IMAGE]")) {
                val handler = MyHandler(fragmentManager)
                val msg = handler.obtainMessage()
                val bundle = Bundle()
                bundle.putString("url", post.getString("url"))
//                bundle.putString("")
                msg.data = bundle
                handler.sendMessage(msg)
            }
        }
    }
}

class MyHandler(private val fm: FragmentManager): Handler() {

    override fun handleMessage(msg: Message) {
        super.handleMessage(msg)
        val bundle = msg.data
        val url = bundle.getString("url")

        val ft = fm.beginTransaction()
        for ((i, x) in (0..10).withIndex()) {
            val fragment = PostFragment()
            val tag = "test_$i"
            fragment.setUrl(tag)
            ft.add(R.id.linearLayout, fragment, tag)
        }
        ft.commit()
    }
}