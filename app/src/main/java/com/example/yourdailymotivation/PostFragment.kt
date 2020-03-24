package com.example.yourdailymotivation


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class PostFragment : Fragment() {

        private var postUrl: String = ""

        fun setUrl(url: String) {
            postUrl = url
        }

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val view = inflater.inflate(R.layout.fragment_post, container, false)
            val postTitle: TextView = view.findViewById(R.id.postTitle)
            postTitle.text = postUrl
            // Inflate the layout for this fragment
            return view
        }
    }