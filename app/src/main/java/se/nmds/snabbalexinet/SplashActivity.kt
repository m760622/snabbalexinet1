package se.nmds.snabbalexinet

import android.animation.ValueAnimator
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.ImageView
import kotlinx.android.synthetic.main.splash_activity.*

class SplashActivity : AppCompatActivity() {

    private var mDelayHandler:Handler? = null
    private var SPLASH_DELAY:Long = 2500

    internal val mRunnable:Runnable = Runnable {
        if(!isFinishing){
            val intent=Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
       // start()
    }


    fun start(){
        mDelayHandler = Handler()
        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)

        splashImage.scaleX = 0.2f
        splashImage.scaleY = 0.2f

        animate(splashImage,"yPos", 600f, 300f, 0, 1000)
        animate(splashImage,"rotatePos", -180f, 0f, 1000, 1000)
        animate(splashImage,"scaleX", 0.2f, 1f, 1000, 1000)
        animate(splashImage,"scaleY", 0.2f, 1f, 1000, 1000)

    }


    fun animate(obj: ImageView, type: String, posStart: Float, posFinish: Float, dly: Long, dur:Long){
        Log.d("tes", "########################################################################")
        ValueAnimator.ofFloat(posStart, posFinish).apply {
            startDelay = dly
            duration = dur
            Log.d("tes", type)
            addUpdateListener {
                when(type){
                    "yPos" -> obj.y = it.animatedValue as Float
                    "rotatePos" -> obj.rotation = it.animatedValue as Float
                    "scaleX" -> obj.scaleX = it.animatedValue as Float
                    "scaleY" -> obj.scaleY = it.animatedValue as Float
                }
            }
            start()
        }
    }


    override fun onPause() {
        if(mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }
        super.onPause()
    }

    override fun onResume() {
        start()

        super.onResume()
    }

}




/*

    public override fun onDestroy(){
        if(mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }

        super.onDestroy()
    }

 */