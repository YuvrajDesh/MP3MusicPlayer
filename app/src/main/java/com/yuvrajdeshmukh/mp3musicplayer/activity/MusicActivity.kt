package com.yuvrajdeshmukh.mp3musicplayer.activity

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import com.cleveroad.audiovisualization.AudioVisualization
import com.cleveroad.audiovisualization.GLAudioVisualizationView
import com.yuvrajdeshmukh.mp3musicplayer.*
import com.yuvrajdeshmukh.mp3musicplayer.data.Music
import com.yuvrajdeshmukh.mp3musicplayer.fragment.DashboardFragment
import com.yuvrajdeshmukh.mp3musicplayer.fragment.FavoritesFragment
import com.yuvrajdeshmukh.mp3musicplayer.fragment.NowPlayingFragment
import com.yuvrajdeshmukh.mp3musicplayer.service.MusicService

class MusicActivity : AppCompatActivity() ,ServiceConnection{
    companion object{
        const val NAME_EXTRA = "name_extra"
        const val DESC_EXTRA = "desc_extra"
        var runnable : Runnable? = null
        private lateinit var glAudioVisualizationView : GLAudioVisualizationView

        lateinit var audioVisualization : AudioVisualization



        lateinit var myMusicList: ArrayList<Music>
        const val DURATION_EXTRA = "duration_extra"
        var songPosition : Int = 0
        var musicService : MusicService? = null
        var isPlaying : Boolean = false

        var isFavourite = false
        var fsongIndex : Int = -1
        var favBtn : ImageButton? = null
        var nowPlayingId : String = ""




    }


    var currentTime: TextView? = null
    var seekBar : SeekBar? = null




    var shuffleButtonEnabled : Boolean = false





    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)
        //For starting service
        myMusicList = ArrayList()
        favBtn = findViewById(R.id.buttonFav) as ImageButton


//        val intent = Intent(this, MusicService::class.java)
//        bindService(intent,this, BIND_AUTO_CREATE)
//        startService(intent)
        setUpLayout()

        if (isFavourite)
        { favBtn?.setImageResource(R.drawable.favorite_on)
        }
        else
        {favBtn?.setImageResource(R.drawable.favorite_off)
        }

//


        musicService?.displayNotification()




        seekBar = findViewById(R.id.seekBar)
       seekBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
           override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
               if(fromUser) musicService?.mediaPlayer?.seekTo(progress)

           }

           override fun onStartTrackingTouch(seekBar: SeekBar?) =Unit



           override fun onStopTrackingTouch(seekBar: SeekBar?) =Unit
       })
    }
    fun setUpLayout()
    {
//        val name = intent.getStringExtra(NAME_EXTRA)
//        val desc = intent.getStringExtra(DESC_EXTRA)
        when(intent.getStringExtra("class"))
        {
            "favouriteadapter" ->
            {
                val intent = Intent(this, MusicService::class.java)
                bindService(intent,this, BIND_AUTO_CREATE)
                startService(intent)
//                myMusicList = ArrayList()
//                myMusicList.addAll(FavoritesFragment.FmusicListMA)

                myLayout()
            }
            "dashboardadapter" ->
            {
                val intent = Intent(this, MusicService::class.java)
                bindService(intent,this, BIND_AUTO_CREATE)
                startService(intent)
                myMusicList = ArrayList()
                myMusicList?.addAll(DashboardFragment.musicListMA!!)
                myLayout()







            }
            "NowPlayingFragment" ->
            {
//                val intent = Intent(this, MusicService::class.java)
//                bindService(intent,this, BIND_AUTO_CREATE)
//                startService(intent)
                currentTime = findViewById(R.id.startTime)
                seekBar = findViewById(R.id.seekBar)

                glAudioVisualizationView = findViewById(R.id.visualizer_view)
                audioVisualization = glAudioVisualizationView as AudioVisualization
                audioVisualization?.linkTo(musicService?.mediaPlayer)
                audioVisualization.onResume()
                seekBar?.progress = musicService!!.mediaPlayer!!.currentPosition
                myMusicList = ArrayList()
                myMusicList?.addAll(DashboardFragment.musicListMA!!)
                currentTime?.text = myMusicList[songPosition].formatDuration(musicService?.mediaPlayer?.currentPosition!!.toLong())

                myLayout()
               






                if(isPlaying)
                {
                    NowPlayingFragment.PlayPauseBtnNp?.setBackgroundResource(R.drawable.pause_icon)

                }
                else
                {
                    NowPlayingFragment.PlayPauseBtnNp?.setBackgroundResource(R.drawable.play_icon)

                }

            }
        }

    }

    fun myLayout()
    {

        songPosition = intent.getIntExtra("index",0)

        val dn = intent.getLongExtra(DURATION_EXTRA,0)
        fsongIndex = myMusicList[songPosition].favoriteSongChecker(myMusicList[songPosition].id.toString())
        var title = findViewById<TextView>(R.id.songTitle) as TextView
        var artist = findViewById<TextView>(R.id.songArtist) as TextView
        var dur = findViewById<TextView>(R.id.endTime) as TextView

//        binding.songNamePA.text = musicListPA[songPosition].title
        title.text = myMusicList[songPosition].songName
        artist.text = myMusicList[songPosition].songSonger
//                dur.text =myMusicList[songPosition].formatDuration(dur)
        dur.text = myMusicList[songPosition].formatDuration(myMusicList[songPosition].duration)

    }
    private fun mediaPlaying()
    {

        currentTime = findViewById(R.id.startTime)
        glAudioVisualizationView = findViewById(R.id.visualizer_view)
        audioVisualization = glAudioVisualizationView as AudioVisualization

        if(musicService?.mediaPlayer==null)           musicService?.mediaPlayer= MediaPlayer()
        musicService?.mediaPlayer?.reset()
        musicService?.mediaPlayer?.setDataSource(myMusicList[songPosition].path)
//                title.text = "$name"
//                artist.text = "$desc"
//                dur.text = "$duration"
        musicService?.mediaPlayer?.prepare()
        musicService?.mediaPlayer?.start()
        isPlaying = true
//        audioVisualization?.linkTo(musicService?.mediaPlayer)
        audioVisualization?.linkTo(musicService?.mediaPlayer)

        currentTime?.text = myMusicList[songPosition].formatDuration(musicService?.mediaPlayer?.currentPosition!!.toLong())
        seekBar?.progress = 0
        seekBar?.max = musicService!!.mediaPlayer!!.duration
        nowPlayingId = myMusicList[songPosition].id.toString()



    }

    override fun onDestroy() {
        try{
            audioVisualization.release()
        }catch(e:Exception)
        {


        }

        super.onDestroy()

    }



    fun pauseFunction(view: android.view.View) {
        var buttonPlayPause : ImageButton = findViewById(R.id.buttonPlayPause)
        glAudioVisualizationView = findViewById(R.id.visualizer_view)
        audioVisualization = glAudioVisualizationView as AudioVisualization

        if(isPlaying)
        {
            buttonPlayPause.setBackgroundResource(R.drawable.play_icon)
            musicService?.mediaPlayer!!.pause()
            isPlaying = false
            NowPlayingFragment.PlayPauseBtnNp?.setBackgroundResource(R.drawable.play_icon)
            audioVisualization?.onPause()




        }
        else{

            buttonPlayPause.setBackgroundResource(R.drawable.pause_icon)
            musicService?.mediaPlayer!!.start()
            isPlaying = true
            NowPlayingFragment.PlayPauseBtnNp?.setBackgroundResource(R.drawable.pause_icon)
            audioVisualization?.onResume()

        }


    }

    fun previousSongPlay(view: android.view.View) {
        if (songPosition ==0)
        {
            audioVisualization.release()
            songPosition = myMusicList.size - 1

            setLayout()
            mediaPlaying()

        }
        else{
            audioVisualization.release()
            songPosition--

            setLayout()
            mediaPlaying()

        }

    }
    fun nextSongPlay(view: android.view.View) {
        if(songPosition == myMusicList.size - 1)
        {
            audioVisualization.release()
            songPosition = 0

            setLayout()
            mediaPlaying()


        }
        else{
            audioVisualization.release()
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
        musicService!!.displayNotification()
        seekBarRunnerTime()



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
    fun seekBarRunnerTime()
    {
        seekBar = findViewById(R.id.seekBar)




        runnable = Runnable {
            currentTime?.text = myMusicList[songPosition].formatDuration(musicService?.mediaPlayer?.currentPosition!!.toLong())
            seekBar?.progress = musicService!!.mediaPlayer!!.currentPosition
            Handler(Looper.getMainLooper()).postDelayed(runnable!!,200)


        }
        Handler(Looper.getMainLooper()).postDelayed(runnable!!,0)

    }
    fun isFavouriteOrNot(view: android.view.View) {
////        FavoritesFragment.FmusicListMA = ArrayList()

    fsongIndex = myMusicList[songPosition].favoriteSongChecker(myMusicList[songPosition].id.toString())
    favBtn = findViewById(R.id.buttonFav) as ImageButton
        if (isFavourite)
        {

            favBtn?.setImageResource(R.drawable.favorite_off)
            FavoritesFragment.FmusicListMA?.removeAt(fsongIndex)
            isFavourite = false
            Toast.makeText(this,"Removed from favorite",Toast.LENGTH_SHORT).show()





        }
        else
        {

            favBtn?.setImageResource(R.drawable.favorite_on)
            FavoritesFragment.FmusicListMA.add(myMusicList[songPosition])
            isFavourite = true
            Toast.makeText(this,"Added to favorite",Toast.LENGTH_SHORT).show()


//            FavoritesFragment.FmusicListMA?.remove(fsongIndex)

        }
//
//
//
    }


    override fun onServiceDisconnected(name: ComponentName?) {
        musicService = null
    }






}