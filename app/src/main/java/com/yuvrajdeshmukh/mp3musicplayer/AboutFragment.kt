package com.yuvrajdeshmukh.mp3musicplayer

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout


class AboutFragment : Fragment() {
    lateinit var sentEmail :ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view =  inflater.inflate(R.layout.fragment_about, container, false)
        sentEmail = view.findViewById(R.id.sentEmail) as ImageView
        sentEmail.setOnClickListener {


            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:") // only email apps should handle this
                putExtra(Intent.EXTRA_EMAIL, arrayOf("yuvrajdeshmukh994@gmail.com")) // recipients
                putExtra(Intent.EXTRA_SUBJECT, "Subject")
            }
            val chooser = Intent.createChooser(intent, "Send Email")
            startActivity(chooser)

        }
        return view

    }


    }
