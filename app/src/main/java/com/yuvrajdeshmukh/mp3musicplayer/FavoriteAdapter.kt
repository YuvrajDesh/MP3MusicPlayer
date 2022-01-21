package com.yuvrajdeshmukh.mp3musicplayer

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class FavoriteAdapter(val context: Context, val music:ArrayList<Music>): RecyclerView.Adapter<FavoriteAdapter.FMusicViewHolder>() {

    class FMusicViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val FtxtSinger: TextView = view.findViewById(R.id.FtxtSinger) as TextView
        val FtxtSongName: TextView = view.findViewById(R.id.FtxtSongName) as TextView
        val FtxtTime: TextView = view.findViewById(R.id.FtxtTime)
        val FllContent: RelativeLayout = view.findViewById(R.id.FllContent) as RelativeLayout


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FMusicViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fav_row_layout, parent, false)
        return FMusicViewHolder(view)

    }

    override fun onBindViewHolder(holder: FMusicViewHolder, position: Int) {
        val musics = music[position]
        holder.FtxtSongName.text = musics.songName
        holder.FtxtSinger.text = musics.songSonger
        holder.FtxtTime.text = musics.formatDuration(musics.duration)



        holder.FllContent.setOnClickListener {
//            Toast.makeText(context,"clicked on ${holder.txtSongName.text}",Toast.LENGTH_SHORT).show()
            val intent = Intent(context, MusicActivity::class.java)
//            intent.putExtra(MusicActivity.NAME_EXTRA,musics.songName)
//            intent.putExtra(MusicActivity.DESC_EXTRA,musics.songSonger)
//            intent.putExtra(MusicActivity.DURATION_EXTRA,musics.duration)
            intent.putExtra("index", position)
            intent.putExtra("class", "favouriteadapter")
//            intent.putExtra(SingleSportActivity.IMG_EXTRA,sport.sportImage)
//            context.startActivity(intent)
            ContextCompat.startActivity(context, intent, null)
        }
        holder.FllContent.setOnLongClickListener {
            showDialogBox(position)
            return@setOnLongClickListener true
        }

    }

    override fun getItemCount(): Int {
        return music.size
    }

    private fun showDialogBox(position: Int) {
        val builder = AlertDialog.Builder(context!!)
        builder.apply {
//            setPositiveButton(R.string.duplicate, DialogInterface.OnClickListener { dialog, which ->
//                celebrityDatabase?.celebritiesList?.add(position,
//                    Celebrity("No Name","No description" ,R.drawable.angelina_jolie)
//                )
//                notifyDataSetChanged()
//
//            })
            setNegativeButton(R.string.delete, DialogInterface.OnClickListener { dialog, which ->
                DashboardFragment.musicListMA?.removeAt(position)
                notifyDataSetChanged()
            })
        }
        builder.setTitle("Actions ")
        //set message for alert dialog
        builder.setMessage("Choose Any One?")
        builder.setCancelable(true)
        builder.create()
        builder.show()
    }

}