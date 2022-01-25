package com.yuvrajdeshmukh.mp3musicplayer.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yuvrajdeshmukh.mp3musicplayer.adapter.FavoriteAdapter
import com.yuvrajdeshmukh.mp3musicplayer.data.Music
import com.yuvrajdeshmukh.mp3musicplayer.R


class FavoritesFragment : Fragment() {
    companion object
    {
        var FmusicListMA:ArrayList<Music> = ArrayList()
    }

    var FrecyclearView: RecyclerView? = null


    var FlayoutManager: RecyclerView.LayoutManager? = null
    var x : Music? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_favorites, container, false)
        FrecyclearView = view?.findViewById(R.id.fav_recycler_view)
//
        FlayoutManager = LinearLayoutManager(activity as Context)

//         FmusicListMA.add(Music("","","",0,""))

        //creating our adapter
        val adapter = FavoriteAdapter(activity as Context, FmusicListMA!!)

//        //now adding the adapter to recyclerview
        FrecyclearView?.adapter = adapter
        FrecyclearView?.layoutManager = FlayoutManager
        return view
    }


    }
