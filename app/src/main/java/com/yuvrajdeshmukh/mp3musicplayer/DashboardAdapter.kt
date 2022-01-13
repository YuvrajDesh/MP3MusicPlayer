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

class DashboardAdapter(val context: Context, val music:ArrayList<Music>): RecyclerView.Adapter<DashboardAdapter.MusicViewHolder>() {

    class MusicViewHolder(view: View): RecyclerView.ViewHolder(view)
    {

        val txtSinger: TextView = view.findViewById(R.id.txtSinger) as TextView
        val txtSongName: TextView = view.findViewById(R.id.txtSongName) as TextView
       val txtTime : TextView = view.findViewById(R.id.txtTime)
        val llContent : RelativeLayout = view.findViewById(R.id.llContent) as RelativeLayout


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_layout,parent,false)
        return MusicViewHolder(view)

    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        val musics = music[position]
        holder.txtSongName.text = musics.songName
         holder.txtSinger.text = musics.songSonger
        holder.txtTime.text = musics.formatDuration(musics.duration)



        holder.llContent.setOnClickListener{
//            Toast.makeText(context,"clicked on ${holder.txtSongName.text}",Toast.LENGTH_SHORT).show()
            val intent = Intent(context,MusicActivity::class.java)
//            intent.putExtra(MusicActivity.NAME_EXTRA,musics.songName)
//            intent.putExtra(MusicActivity.DESC_EXTRA,musics.songSonger)
//            intent.putExtra(MusicActivity.DURATION_EXTRA,musics.duration)
            intent.putExtra("index",position)
            intent.putExtra("class","dashboardadapter")
//            intent.putExtra(SingleSportActivity.IMG_EXTRA,sport.sportImage)
//            context.startActivity(intent)
            ContextCompat.startActivity(context,intent,null)
        }
        holder.llContent.setOnLongClickListener {
            showDialogBox(position)
            return@setOnLongClickListener true
        }

    }

    override fun getItemCount(): Int {
        return music.size
    }
    private fun showDialogBox(position: Int)
    {
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