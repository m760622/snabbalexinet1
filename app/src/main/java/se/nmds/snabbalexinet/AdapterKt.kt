package se.nmds.snabbalexinet

import android.content.Intent
import android.support.design.widget.CoordinatorLayout
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.rvrow.view.*
import se.nmds.snabbalexinet.AdapterKt.MyViewHolder

private lateinit var coordinatorLayout: CoordinatorLayout

//class dataClass(var name: String, var id: Int = 0, var description: String, var price: Double, var thumbnail: String) { }
//mutableListOf
class dataClass(val objectIdNr:String, val svenska:String, val arabiska:String, val type:String){
   constructor():this("", "","", "")
}

class AdapterKt (val usersList : ArrayList<dataClass>):RecyclerView.Adapter<MyViewHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {
            val rowView = LayoutInflater.from(parent.context).inflate(R.layout.rvrow, parent, false)
            return MyViewHolder(rowView)
        }

        override fun getItemCount(): Int = usersList.size

            override fun onBindViewHolder(holderMy: MyViewHolder, position: Int) {
                val userItem = usersList[position]
                holderMy.itemVH = userItem
                holderMy.nameVHTxt.text = userItem.svenska
                holderMy.nameArVHTxt.text = userItem.arabiska
                holderMy.typeVHTxt.text = userItem.type
            }  //onBindViewHolder


            fun removeAt(position: Int, itemVH:dataClass) {
                //Deleting from firebase
                val myDatabase = FirebaseDatabase.getInstance().getReference("results")
                myDatabase.child(itemVH.objectIdNr).removeValue()
                Log.d("TAGLexin", "itemVH.objectIdNr ${itemVH.objectIdNr}")

                //Deleting from ArrayList
                usersList.removeAt(position)
                notifyItemRemoved(position)
            }// removeAt

             fun deleteInfoAt(itemVH:dataClass){
                val myDatabase = FirebaseDatabase.getInstance().getReference("results")
                myDatabase.child(itemVH.objectIdNr).removeValue()
                //  Toast.makeText(itemView,"Deleted ;(", Toast.LENGTH_LONG).show()
            }

            //MyViewHolder
            class MyViewHolder(itemView: View, var itemVH: dataClass?= null):RecyclerView.ViewHolder(itemView) {

                val nameVHTxt = itemView.svenskaRowTV as TextView
                val nameArVHTxt = itemView.arabiskaRowTV as TextView
                val typeVHTxt = itemView.typeRowTV as TextView

                fun intentFn(intent: Intent){
                    intent.putExtra("objectIdNrKey", itemVH?.objectIdNr)
                    intent.putExtra("svenskaKey", itemVH?.svenska)
                    intent.putExtra("arabiskaKey", itemVH?.arabiska)
                    intent.putExtra("typeKey", itemVH?.type)
                    itemView.context.startActivity(intent)
                    var viewBackground: RelativeLayout = itemView.findViewById(R.id.itemCardVew)
                    var viewForeground: RelativeLayout = itemView.findViewById(R.id.itemCardVew)


                }


                init {
                    itemView.deleteRowBtn.setOnClickListener {
                        deleteInfo(itemVH!!)
                    }

                    itemView.updateRowBtn.setOnClickListener {
                        val intent = Intent(itemView.context, UpdateActivity::class.java)
                        intentFn(intent)

                    }

                    itemView.setOnClickListener{
                        //  nameVHTxt.text = "nameVHTxt"  // This is changing the nameVHTxt in the row
                        //  Toast.makeText(itemView.context, "svenska "+itemVH?.svenska, Toast.LENGTH_LONG).show()

                       // val intent = Intent(itemView.context, UpdateActivity::class.java)
                          val intent = Intent(itemView.context, DetailsActivity::class.java)
                        intentFn(intent)
                    }


                }

                private fun deleteInfo(itemVH:dataClass){
                    val myDatabase = FirebaseDatabase.getInstance().getReference("results")
                    myDatabase.child(itemVH.objectIdNr).removeValue()
                  //  Toast.makeText(itemView,"Deleted ;(", Toast.LENGTH_LONG).show()
                }
            } //MyViewHolder


        }   //AdapterKt



/*
    companion object {
        fun start(context: Context, userId: String, region: Int, admin: Boolean) {
            val intent = Intent(context, UsersActivity::class.java)
            intent.putExtra("userId", userId)
            intent.putExtra("region", region)
            intent.putExtra("admin", admin)
            context.startActivity(intent)
        }
    }
 */

/*
    override fun onCreateViewHolder(parent: ViewGroup, type: Int): UserViewHolder =  UserViewHolder.newInstance(parent, viewModel)
    override fun getItemCount(): Int = this.usersList.size
    override fun onBindViewHolder(viewHolder: UserViewHolder, position: Int) = viewHolder.bind(usersList[position])
}

   // override fun getItemCount(): Int { return userList.size }

 */




/*

data class dataClass(
        val svenska: String,
        val arabiska: String,
        val type: String
        )
class dataClass(val objectIdNr:String, val svenska:String, val arabiska:String, val type:String,
                val avledningarAr:String, val avledningarSv:String, val exempelAr:String, val exempelSv:String,
                val forklaringSv:String, val idiomAr:String, val idiomSv:String, val indexSv:String, val inflektionSv:String,
                val kommentarAr:String, val kommentarSv:String, val meaningAr:String, val meaningSv:String
                , val motsatserAr:String, val motsatserSv:String, val objectIdOld:String, val referenceSv:String
                , val sammansattningarAr:String, val sammansattningarSv:String, val synonymerAr:String){
    constructor():this("", "","", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""){}
}
*/