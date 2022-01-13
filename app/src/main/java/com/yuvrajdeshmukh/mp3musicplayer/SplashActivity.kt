package com.yuvrajdeshmukh.mp3musicplayer

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.widget.Toast
import androidx.core.app.ActivityCompat

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
      val permission_code = 1001
       val permission = arrayOf(
           Manifest.permission.READ_EXTERNAL_STORAGE,
           Manifest.permission.MODIFY_AUDIO_SETTINGS,

       )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        if(checkPermissions())
        {
            gohome()
        }
        else
        {
            ActivityCompat.requestPermissions(this,permission,permission_code)
        }
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN

        )
        // we used the postDelayed(Runnable, time) method
        // to send a message with a delayed time.

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode)
        {
            permission_code ->{
                   if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED
                       && grantResults[1]==PackageManager.PERMISSION_GRANTED
                       && grantResults[2]==PackageManager.PERMISSION_GRANTED
                       && grantResults[3]==PackageManager.PERMISSION_GRANTED)


                   {
                       gohome()
                   }
                else
                   {
                       Toast.makeText(this,"Please grant permissions",Toast.LENGTH_SHORT).show()
                       finish()
                   }
            }else ->
        {
             showToast()
        }
        }
    }
    private fun showToast()
    {
        Toast.makeText(this,"Error occured",Toast.LENGTH_SHORT).show()
        finish()
    }
    private fun checkPermissions():Boolean
    {
        var perms : String? = null

        for (perms in permission)
        {
            var data : Int = application.checkCallingOrSelfPermission(perms)
            if(data!=PackageManager.PERMISSION_GRANTED)
            {
                return false
            }
        }
        return true
    }
    private fun gohome()
    {
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }
}