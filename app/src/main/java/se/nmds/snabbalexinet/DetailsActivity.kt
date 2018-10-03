package se.nmds.snabbalexinet

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.details_activity.*

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_activity)
        startFn()

        shareBtn.setOnClickListener { shareFn() }
    }

    fun startFn(){
        val svenskaKey  = intent.extras.get("svenskaKey") as String
        val arabiskaKey  = intent.extras.get("arabiskaKey") as String
        val typeKey  = intent.extras.get("typeKey") as String

        supportActionBar?.title = svenskaKey.toString() //"Update the item"
        svenskaTV.text = svenskaKey.toString()
        arabiskaTV.text = arabiskaKey.toString()
        typeTV.text = typeKey.toString()

    }


    fun shareFn(){
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, svenskaTV.text.toString()+"\n\n"+arabiskaTV.text.toString() +"\n\n"+typeTV.text.toString())
            type = "text/plain"
        }
        startActivity(Intent.createChooser(sendIntent, resources.getText(R.string.send_to)))
    }

}
