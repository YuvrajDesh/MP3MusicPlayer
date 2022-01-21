package com.yuvrajdeshmukh.mp3musicplayer

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton


class NowPlayingFragment : Fragment() {
    companion object{
        var PlayPauseBtnNp: ImageButton? = null


    }
    var SongNameNp: TextView? = null
    var NextBtnNp : ImageButton? = null
    var nowPlayingF : RelativeLayout? = null






    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        PlayPauseBtnNp = view?.findViewById(R.id.playPauseBtnNP)
        SongNameNp = view?.findViewById(R.id.songNameNP)
        NextBtnNp = view?.findViewById(R.id.nextBtnNP)
        nowPlayingF = view?.findViewById(R.id.nowPlayingF)

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_now_playing, container, false)
        view.visibility = View.INVISIBLE






        return view

    }

    override fun onResume() {
        SongNameNp = view?.findViewById(R.id.songNameNP)
        PlayPauseBtnNp = view?.findViewById(R.id.playPauseBtnNP)
        NextBtnNp = view?.findViewById(R.id.nextBtnNP)
        nowPlayingF = view?.findViewById(R.id.nowPlayingF)


        super.onResume()
        if (MusicActivity.musicService != null) {
            view!!.visibility = View.VISIBLE
            SongNameNp!!.text = MusicActivity.myMusicList[MusicActivity.songPosition].songName

            NextBtnNp?.setOnClickListener {
                if(MusicActivity.songPosition == MusicActivity.myMusicList.size - 1)
                {
                    MusicActivity.songPosition = 0
                    SongNameNp!!.text = MusicActivity.myMusicList[MusicActivity.songPosition].songName

                    if(MusicActivity.musicService?.mediaPlayer==null)           MusicActivity.musicService?.mediaPlayer= MediaPlayer()
                    MusicActivity.musicService?.mediaPlayer?.reset()
                    MusicActivity.musicService?.mediaPlayer?.setDataSource(MusicActivity.myMusicList[MusicActivity.songPosition].path)
//                title.text = "$name"
//                artist.text = "$desc"
//                dur.text = "$duration"
                    MusicActivity.musicService?.mediaPlayer?.prepare()
                    MusicActivity.musicService?.mediaPlayer?.start()
                    MusicActivity.isPlaying = true





                }
                else{
                    MusicActivity.songPosition++
                    SongNameNp!!.text = MusicActivity.myMusicList[MusicActivity.songPosition].songName

                    if(MusicActivity.musicService?.mediaPlayer==null)           MusicActivity.musicService?.mediaPlayer= MediaPlayer()
                    MusicActivity.musicService?.mediaPlayer?.reset()
                    MusicActivity.musicService?.mediaPlayer?.setDataSource(MusicActivity.myMusicList[MusicActivity.songPosition].path)
//                title.text = "$name"
//                artist.text = "$desc"
//                dur.text = "$duration"
                    MusicActivity.musicService?.mediaPlayer?.prepare()
                    MusicActivity.musicService?.mediaPlayer?.start()
                    MusicActivity.isPlaying = true


                }

            }
            PlayPauseBtnNp?.setOnClickListener {


                if(MusicActivity.isPlaying)
                {
                    PlayPauseBtnNp?.setBackgroundResource(R.drawable.play_icon)
                    MusicActivity.musicService?.mediaPlayer!!.pause()
                    MusicActivity.isPlaying = false


                }
                else{

                    PlayPauseBtnNp?.setBackgroundResource(R.drawable.pause_icon)
                    MusicActivity.musicService?.mediaPlayer!!.start()
                    MusicActivity.isPlaying = true
                }

            }



            nowPlayingF?.setOnClickListener {
                val intent = Intent(requireContext(),MusicActivity::class.java)
                intent.putExtra("index",MusicActivity.songPosition)
                intent.putExtra("class","NowPlayingFragment")

                startActivity(intent)


            }
        }}}




