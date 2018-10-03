package se.nmds.snabbalexinet

import android.content.Context
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.main_activity.*


class MainActivity : AppCompatActivity() {
    val dataListDB = ArrayList<dataClass>()
   // val dataListDB = MutableList<dataClass>
    lateinit var ref : DatabaseReference
    private lateinit var MAdapterKt: AdapterKt
    private lateinit var coordinatorLayout: CoordinatorLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        getFn()

        saveBtn.setOnClickListener {
            saveFn()
        }
    }

        private fun getFn() {

            ref = FirebaseDatabase.getInstance().getReference("results")
            ref.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError?) {
                }

                override fun onDataChange(p0: DataSnapshot?) {
                    if (p0!!.exists() ){
                        dataListDB.clear()
                        for (e in p0.children){
                            val itemRow = e.getValue(dataClass::class.java)
                            print("itemRow "+itemRow)
                            dataListDB.add(itemRow!!)
                        }
                 //       val adapter = AdapterKt(dataListDB)


                        val dataListDBFiltered  = dataListDB.filter {
                            it.svenska.startsWith("F")
                        }
                      //  val MAdapterKt = AdapterKt(dataListDBFiltered)
                        val MAdapterKt = AdapterKt(dataListDB)
                        rvList.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayout.VERTICAL, false)  //If its true will sort upsideDown
                        rvList.adapter = MAdapterKt

                        val swipeHandler = object : SwipeToDeleteCallback(this@MainActivity) {

                            override fun onSwiped(holderMy: RecyclerView.ViewHolder,  position: Int) {
                                val adapter = rvList.adapter as AdapterKt
                                adapter.removeAt(holderMy.adapterPosition, adapter.usersList[position])
                             //   adapter.deleteInfoAt(adapter.usersList[position])  //Delete from data base firebase
                            }


                           // override fun onSwiped(holderMy: AdapterKt.MyViewHolder, direction: Int, position: Int) {

                        }
                        val itemTouchHelper = ItemTouchHelper(swipeHandler)
                        itemTouchHelper.attachToRecyclerView(rvList)


                    }
                }
            })

        } //getFn


    private fun saveFn(){
        val SvenskaTxt = svenskaTxt.text.toString()
        val ArabiskaTxt = arabiskaTxt.text.toString()
        val TypeTxt = typeTxt.text.toString()

        var toasten = Toast.makeText(this, "The text ", Toast.LENGTH_LONG)
        var toastStyl = toasten.view
        toasten.setGravity(Gravity.TOP, 0,0)
        toastStyl.setBackgroundColor(Color.WHITE)

        if (SvenskaTxt.isNullOrEmpty()){
            //    editText.error = "Enter Svenska field"
            //   Toast.makeText(this, "Enter Svenska field  ", Toast.LENGTH_LONG).show()

            toasten.setText(resources.getString(R.string.svenskaField))
            toasten.show()
            return
        }

        if (ArabiskaTxt.isNullOrEmpty()){
            toasten.setText(resources.getString(R.string.arabiskaField))
            toasten.show()
            return
        }
        if (TypeTxt.isNullOrEmpty()){
            toasten.setText(resources.getString(R.string.typeField))
            toasten.show()
            return
        }

        val sp = getSharedPreferences("SP", Context.MODE_PRIVATE)
        val editSP = sp.edit()
        // editSP.remove("Svenska")  // to delete the key then put this line editSP.apply()
        // editSP.clear() //to delete all keys then put this line editSP.apply()
        editSP.putString("Svenska", SvenskaTxt)
        editSP.putString("Arabiska", ArabiskaTxt)
        editSP.putString("Type", TypeTxt)
        editSP.apply()


        val myDataBase = FirebaseDatabase.getInstance().getReference("results")
        val objectIdNr = myDataBase.push().key
        val dataLists = dataClass(objectIdNr, SvenskaTxt, ArabiskaTxt, TypeTxt)
        myDataBase.child(objectIdNr).setValue(dataLists).addOnCompleteListener{

            toasten.setText("Saved")
            toasten.show()

        }


    }

  //  class MyViewHolder(itemView: View, var itemVH: dataClass?= null):RecyclerView.MyViewHolder(itemView) {
//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

/*
    //    override fun onBindViewHolder(holderMy: MyViewHolder, position: Int) {
    fun onSwiped(holderMy: AdapterKt.MyViewHolder, direction: Int, position: Int) {
     //   if (holderMy is AdapterKt.MyViewHolder) {
            // get the removed item name to display it in snack bar
            val name = dataListDB[holderMy.getAdapterPosition()].svenska

            // backup of removed item for undo purpose
            val deletedItem = dataListDB[holderMy.getAdapterPosition()]
            val deletedIndex = holderMy.getAdapterPosition()

            // remove the item from recycler view
            MAdapterKt.removeItem(holderMy.getAdapterPosition())

            // showing snack bar with Undo option
            //val snackbar = Snackbar.make(coordinatorLayout?, name + " removed from cart!", Snackbar.LENGTH_LONG)
            val snackbar = Snackbar.make(coordinatorLayout, "${name} removed from Lexin!", Snackbar.LENGTH_LONG)
            snackbar.setAction("UNDO", View.OnClickListener {
                // undo is selected, restore the deleted item
                MAdapterKt.restoreItem(deletedItem, deletedIndex)
            })
            snackbar.setActionTextColor(Color.YELLOW)
            snackbar.show()
      //  }
    }  //onSwiped
*/
}
