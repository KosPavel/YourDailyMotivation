package com.example.yourdailymotivation

import android.os.AsyncTask
import android.util.Log
import org.json.JSONObject
import java.net.URL


//долгий процесс, делать через thread

class JsonDownloader : AsyncTask<Void, Void, MutableList<String>?>() {

    private val getMotivatedHotPostsUrl = "https://www.reddit.com/r/GetMotivated/hot/.json"
    private var hotPosts: JSONObject? = null

    override fun doInBackground(vararg params: Void?): MutableList<String>? {
        val apiResponse = URL(getMotivatedHotPostsUrl).readText()
        hotPosts = JSONObject(apiResponse)
        return getImagesUrls()
    }

    private fun getImagesUrls() : MutableList<String>? {
        val result = mutableListOf<String>()
        val data = hotPosts!!.getJSONObject("data").getJSONObject("children")
        for (key in data.keys()) {
            val post = data.getJSONObject(key).getJSONObject("data")
            if (post.getString("title").contains("[IMAGE]")) {
                result.add(post.getString("url"))
            }
        }
        Log.i("YDM", "number of urls found: ${result.size}")
        return result
    }
}