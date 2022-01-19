package com.yuvrajdeshmukh.mp3musicplayer

import android.app.Service
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer

import android.media.MediaSession2Service
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.support.v4.media.session.MediaSessionCompat
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat

import com.yuvrajdeshmukh.mp3musicplayer.MusicActivity.Companion.myMusicList


class MusicService : Service(){
    var myBinder =MyBinder()





    var mediaPlayer : MediaPlayer? = null
    private lateinit var mediaSession : MediaSessionCompat
    override fun onBind(intent: Intent?): IBinder {
        mediaSession = MediaSessionCompat(baseContext,"My music")
        return myBinder
    }
    inner class MyBinder:Binder(){
        fun currentService():MusicService{
            return this@MusicService
        }


    }
     fun displayNotification(){
        val notificationId = 45
        val notification = NotificationCompat.Builder(baseContext,ApplicationClass.channelID)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            // Add media control buttons that invoke intents in your media service
            .addAction(R.drawable.play_previous_icon, "Previous", null) // #0
            .addAction(R.drawable.pause_icon, "Pause", null) // #1
            .addAction(R.drawable.play_next_icon, "Next", null) // #2
            .setStyle(androidx.media.app.NotificationCompat.MediaStyle().setMediaSession(mediaSession.sessionToken))
            .setContentTitle(MusicActivity.Companion.myMusicList[MusicActivity.songPosition].songName)
            .setContentText(MusicActivity.myMusicList[MusicActivity.songPosition].songSonger)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setLargeIcon(BitmapFactory.decodeResource(resources,R.drawable.sort_icon))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setOnlyAlertOnce(true)
            .build()
//        ApplicationClass.notificationManager?.notify(notificationId,notification)
         startForeground(notificationId,notification)

    }



}