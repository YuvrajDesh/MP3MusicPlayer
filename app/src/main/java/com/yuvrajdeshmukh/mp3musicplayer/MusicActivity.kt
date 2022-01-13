package com.yuvrajdeshmukh.mp3musicplayer

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView

class MusicActivity : AppCompatActivity() ,ServiceConnection{
    companion object{
        const val NAME_EXTRA = "name_extra"
        const val DESC_EXTRA = "desc_extra"
        const val DURATION_EXTRA = "duration_extra"
        var songPosition : Int = 0

    }

    var isPlaying : Boolean = false
    var shuffleButtonEnabled : Boolean = false
    lateinit var myMusicList: ArrayList<Music>

    var seekBar : SeekBar? = null
    var musicService : MusicService? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)
        //For starting service
        val intent = Intent(this,MusicService::class.java)
        bindService(intent,this, BIND_AUTO_CREATE)
        startService(intent)
        setUpLayout()



        seekBar = findViewById(R.id.seekBar)
//       seekBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
//           override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
//               if(fromUser) musicService?.mediaPlayer?.seekTo(progress)
//
//           }
//
//           override fun onStartTrackingTouch(seekBar: SeekBar?) =Unit
//
//
//
//           override fun onStopTrackingTouch(seekBar: SeekBar?) =Unit
//
//
//
//       })





    }
    fun setUpLayout()
    {
//        val name = intent.getStringExtra(NAME_EXTRA)
//        val desc = intent.getStringExtra(DESC_EXTRA)



        when(intent.getStringExtra("class"))
        {
            "dashboardadapter" ->
            {
                myMusicList = ArrayList()
                myMusicList.addAll(DashboardFragment.musicListMA!!)
                myLayout()







            }
        }

    }
    fun myLayout()
    {
        songPosition = intent.getIntExtra("index",0)

        val dn = intent.getLongExtra(DURATION_EXTRA,0)
//


        var title = findViewById<TextView>(R.id.songTitle) as TextView
        var artist = findViewById<TextView>(R.id.songArtist) as TextView
        var dur = findViewById<TextView>(R.id.endTime) as TextView
        var currentTime = findViewById<TextView>(R.id.startTime) as TextView
//        binding.songNamePA.text = musicListPA[songPosition].title
        title.text = myMusicList[songPosition].songName
        artist.text = myMusicList[songPosition].songSonger
//                dur.text =myMusicList[songPosition].formatDuration(dur)
        dur.text = myMusicList[songPosition].formatDuration(myMusicList[songPosition].duration)


    }
    fun mediaPlaying()
    {
        if(musicService?.mediaPlayer==null)           musicService?.mediaPlayer= MediaPlayer()
        musicService?.mediaPlayer?.reset()
        musicService?.mediaPlayer?.setDataSource(myMusicList[songPosition].path)
//                title.text = "$name"
//                artist.text = "$desc"
//                dur.text = "$duration"
        musicService?.mediaPlayer?.prepare()
        musicService?.mediaPlayer?.start()
        isPlaying = true


    }

    fun pauseFunction(view: android.view.View) {
        var buttonPlayPause : ImageButton = findViewById(R.id.buttonPlayPause)

        if(isPlaying)
        {
            buttonPlayPause.setBackgroundResource(R.drawable.play_icon)
            musicService?.mediaPlayer!!.pause()
            isPlaying = false


        }
        else{

            buttonPlayPause.setBackgroundResource(R.drawable.pause_icon)
            musicService?.mediaPlayer!!.start()
            isPlaying = true
        }


    }

    fun previousSongPlay(view: android.view.View) {
        if (songPosition==0)
        {
            songPosition = myMusicList.size - 1
            setLayout()
            mediaPlaying()

        }
        else{
            songPosition--
            setLayout()
            mediaPlaying()

        }

    }
    fun nextSongPlay(view: android.view.View) {
        if(songPosition==myMusicList.size - 1)
        {
            songPosition = 0
            setLayout()
            mediaPlaying()


        }
        else{
            songPosition++
            setLayout()
            mediaPlaying()

        }

    }

    fun shuffleButton(view: android.view.View) {
        var buttonShuffle : ImageButton = findViewById(R.id.buttonShuffle)

        if(shuffleButtonEnabled ==false)
        {

            buttonShuffle.setBackgroundResource(R.drawable.shuffle_icon)
            myMusicList.shuffle()
            setUpLayout()
            shuffleButtonEnabled = true


        }
        else if(shuffleButtonEnabled==true)
        {
            buttonShuffle.setBackgroundResource(R.drawable.shuffle_white_icon)

            shuffleButtonEnabled = false
        }


    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        val binder = service as MusicService.MyBinder
        musicService = binder.currentService()

        mediaPlaying()


    }
    fun setLayout()
    {
        val dn = intent.getLongExtra(DURATION_EXTRA,0)
//


        var title = findViewById<TextView>(R.id.songTitle) as TextView
        var artist = findViewById<TextView>(R.id.songArtist) as TextView
        var dur = findViewById<TextView>(R.id.endTime) as TextView
        var currentTime = findViewById<TextView>(R.id.startTime) as TextView
//        binding.songNamePA.text = musicListPA[songPosition].title
        title.text = myMusicList[songPosition].songName
        artist.text = myMusicList[songPosition].songSonger
//                dur.text =myMusicList[songPosition].formatDuration(dur)
        dur.text = myMusicList[songPosition].formatDuration(myMusicList[songPosition].duration)


    }

    override fun onServiceDisconnected(name: ComponentName?) {
        musicService = null
    }


}