package com.yuvrajdeshmukh.mp3musicplayer.data

import com.yuvrajdeshmukh.mp3musicplayer.activity.MusicActivity
import com.yuvrajdeshmukh.mp3musicplayer.fragment.FavoritesFragment
import java.util.concurrent.TimeUnit

//class Music(val id :Long, var songName: String, var songSinger:String)
class Music {
 public var id : String? = null

 public var songName : String? = null
 public var songSonger : String? = null
 public var duration : Long = 0
 public var path : String? = null

 constructor(id : String,songName: String,songSonger : String ,duration: Long,path : String)
 {
  this.id = id

  this.songName = songName
  this.songSonger = songSonger
  this.duration = duration
  this.path = path
  formatDuration(duration)
  favoriteSongChecker(id)



 }

 fun formatDuration(duration: Long):String
 {
  var minutes = TimeUnit.MINUTES.convert(duration,TimeUnit.MILLISECONDS)
  var seconds = TimeUnit.SECONDS.convert(duration,TimeUnit.MILLISECONDS) -minutes *(TimeUnit
   .SECONDS.convert(1,TimeUnit.MINUTES))


  return String.format("%2d:%2d",minutes,seconds)
 }
 fun favoriteSongChecker(id:String) : Int {
  MusicActivity.isFavourite = false
  FavoritesFragment.FmusicListMA?.forEachIndexed { index, music ->
   if (id == music.id) {
    MusicActivity.isFavourite = true
    return index
   }
  }
  return -1


 }
}
