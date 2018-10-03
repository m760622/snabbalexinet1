package se.nmds.snabbalexinet

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.update_activity.*

class UpdateActivity : AppCompatActivity() {

    lateinit var objectIdNrKey : String
    lateinit var svenskaKey : String
    lateinit var arabiskaKey : String
    lateinit var typeKey : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.update_activity)

        startFn()

        updateRowBtn.setOnClickListener {
            println("Attempt to load webview somehow  mailAdapter  setOnClickListener ???")
               updateInfo(objectIdNrKey)
        }

    }



fun startFn(){
     objectIdNrKey = intent.extras.get("objectIdNrKey") as String
     svenskaKey  = intent.extras.get("svenskaKey") as String
     arabiskaKey  = intent.extras.get("arabiskaKey") as String
     typeKey  = intent.extras.get("typeKey") as String

 //   supportActionBar?.title = svenskaKey.toString() //"Update the item"
    supportActionBar?.title = resources.getString(R.string.updateView) //"Update the item"
    svenskaTxt.setText(svenskaKey)
    arabiskaTxt.setText(arabiskaKey)
    typeTxt.setText(typeKey)
}

    private  fun updateInfo(objectIdNrKey:String){

                val  myDatabase = FirebaseDatabase.getInstance().getReference("results")
                val svenska    = svenskaTxt.text.toString().trim()
                val arabiska     = arabiskaTxt.text.toString().trim()
                val type     = typeTxt.text.toString().trim()

                if (svenska.isEmpty()){
                    //   svenska.error = "Please enter your svenska"
                    Toast.makeText(this, resources.getString(R.string.svenskaField), Toast.LENGTH_LONG).show()
                    return
                }
                if (arabiska.isEmpty()){
                    Toast.makeText(this, resources.getString(R.string.arabiskaField), Toast.LENGTH_LONG).show()
                    return
                }
                if (type.isEmpty()){
                    Toast.makeText(this, resources.getString(R.string.typeField), Toast.LENGTH_LONG).show()
                    return
                }

                val itemRow = dataClass(objectIdNrKey,svenska,arabiska,type)
                myDatabase.child(itemRow.objectIdNr).setValue(itemRow)
                Toast.makeText(this,"Updated :) ", Toast.LENGTH_LONG).show()

    }


}
