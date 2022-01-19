package com.yuvrajdeshmukh.mp3musicplayer

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.jar.Manifest


class DashboardFragment : Fragment() {
companion object
{
    var musicListMA:ArrayList<Music>? = null
}

    var recyclearView: RecyclerView? = null

    //    var recyclearAdapter : DashboardAdapter? = null
    var layoutManager: RecyclerView.LayoutManager? = null


    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        recyclearView = view?.findViewById(R.id.recyclearView)

        layoutManager = LinearLayoutManager(activity as Context)
        musicListMA = getAllAudioFromDevice(context!!)
        //creating our adapter
        val adapter = DashboardAdapter(activity as Context, musicListMA!!)

        //now adding the adapter to recyclerview
        recyclearView?.adapter = adapter
        recyclearView?.layoutManager = layoutManager






        return view
    }
    @RequiresApi(Build.VERSION_CODES.R)
    fun getAllAudioFromDevice(context: Context): ArrayList<Music> {
        var arrayList = ArrayList<Music>()
        val selection = MediaStore.Audio.Media.IS_MUSIC + "!=0"
        val projection = arrayOf(MediaStore.Audio.Media._ID,MediaStore.Audio.Media.TITLE,MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DURATION,MediaStore.Audio.Media.DATE_ADDED,
            MediaStore.Audio.Media.DATA)
        var songURI = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        var songCursor = context.contentResolver?.query(songURI, projection, selection, null,MediaStore.Audio.Media.DATE_ADDED
        + " DESC",null)
////
//
//
        if (songCursor != null && songCursor.moveToFirst()) {
            val songID = songCursor.getColumnIndex(MediaStore.Audio.Media._ID)
            val songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
            val songArtist = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
            val pathC = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA)
            val duration = songCursor.getColumnIndex(MediaStore.Audio.Media.DURATION)
////            val dateAdded = songCursor.getColumnIndex(MediaStore.Audio.Media.DATE_ADDED)
            while (songCursor.moveToNext()) {
                var currentID = songCursor.getString(songID)
                var currentTitle = songCursor.getString(songTitle)
                var currentArtist = songCursor.getString(songArtist)
                var currentData = songCursor.getString(pathC)
            var     currentDuration =songCursor.getLong(duration)
////                var currentDate = songCursor.getLong(dateAdded)

                arrayList.add(
                    Music(
                        currentID,
                        currentTitle,
                        currentArtist,
                        currentDuration,
                        currentData



                    )
                )
            }
            songCursor.close()
        }
        return arrayList
    }
}
