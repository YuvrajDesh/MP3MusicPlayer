package com.yuvrajdeshmukh.mp3musicplayer.application

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

class ApplicationClass:Application() {
    companion object{


        val PLAY = "play"
        val NEXT = "next"
        val PREVIOUS = "previous"
        val EXIT = "exit"
         val channelID = "com.yuvrajdeshmukh.mp3musicplayer.channel1"
         var notificationManager: NotificationManager? = null

    }


    override fun onCreate() {
        super.onCreate()
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel(channelID,"Playing song..","This is channel")

    }
    private fun createNotificationChannel(id : String, name:String, channelDescription:String){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(id,name,importance).apply {
                description = channelDescription
            }

            notificationManager?.createNotificationChannel(channel)

        }

    }
}