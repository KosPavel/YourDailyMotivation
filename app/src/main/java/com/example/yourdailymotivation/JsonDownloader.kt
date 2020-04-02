package com.example.yourdailymotivation

import android.app.Activity
import android.os.AsyncTask
import android.util.Log
import android.widget.LinearLayout
import androidx.core.view.marginBottom
import androidx.fragment.app.FragmentActivity
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.Reader
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.Charset
import java.util.*

class JsonDownloader(private val activity: Activity) :
    AsyncTask<Void, Void, MutableList<String>>() {

    private val getMotivatedHotPostsUrl = "https://www.reddit.com/r/GetMotivated/hot/.json"

    private fun streamToString(inputStream: InputStream): String {
        val text = Scanner(inputStream, "UTF-8").useDelimiter("\\Z").next()
        return text
    }


    override fun doInBackground(vararg params: Void?): MutableList<String> {
        val apiResponse = URL(getMotivatedHotPostsUrl).readText()
        val result = mutableListOf<String>()
        var hotPosts = JSONObject(apiResponse)
        hotPosts = hotPosts.getJSONObject("data")
        val hotPostsChildren = hotPosts.getJSONArray("children")
        for (i in (0 until hotPostsChildren.length())) {
            val post = hotPostsChildren.getJSONObject(i).getJSONObject("data")
            if (post.getString("title").contains("[IMAGE]", ignoreCase = true) || post.getString("title").contains("[PIC]", ignoreCase = true)) {
                result.add(post.getString("url"))
            }
        }
        return result
    }

    override fun onPostExecute(result: MutableList<String>) {
        super.onPostExecute(result)

        val fm = (activity as FragmentActivity).supportFragmentManager
        val ft = fm.beginTransaction()
        for (url in result) {
            val fragment = PostFragment()
            fragment.setUrl(url)
            ft.add(R.id.linearLayout, fragment, url)
        }
        ft.commit()
    }

}