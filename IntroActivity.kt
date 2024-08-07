package kr.co.telecons.searchsample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("lsm","intro")
        setContentView(R.layout.activity_intro)

        var handler = Handler()
        handler.postDelayed({var intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }, 1000)
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}