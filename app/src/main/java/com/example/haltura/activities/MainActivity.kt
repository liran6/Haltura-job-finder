package com.example.haltura.activities

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.haltura.Adapters.*
//import com.example.haltura.Dialogs.WatchWorkDialog
import com.example.haltura.R
import com.example.haltura.Sql.Items.Work
import com.example.haltura.Sql.UserOpenHelper
import com.google.android.gms.dynamic.SupportFragmentWrapper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
//import jdk.jpackage.internal.Arguments.CLIOptions.context


class MainActivity : AppCompatActivity(), MyAdapter.OnWorkListener {

    private lateinit var auth: FirebaseAuth
    private lateinit var helper: UserOpenHelper

    var context: MyAdapter.OnWorkListener? = null

    private lateinit var dbref : DatabaseReference
    private lateinit var workRecyclerView: RecyclerView
    private lateinit var workArrayList: ArrayList<Work>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        helper = UserOpenHelper(this)
        context = this
        workRecyclerView = findViewById(R.id.workRecyclerView)
        workRecyclerView.layoutManager = LinearLayoutManager(this)
        workRecyclerView.setHasFixedSize(true)

        workArrayList = arrayListOf<Work>()
        //getWorkData()


        //workRecyclerView.addOnItemTouchListener(RecyclerItemClickListener(){})
//            RecyclerItemClickListener(
//                context,
//                recyclerView,
//                object : OnItemClickListener() {
//                    fun onItemClick(view: View?, position: Int) {
//                        // do whatever
//                    }
//
//                    fun onLongItemClick(view: View?, position: Int) {
//                        // do whatever
//                    }
//                })



    }

    private fun getWorkData() {
        dbref = FirebaseDatabase.getInstance().getReference("Works")
        //dbref.addValueEventListener(object : ValueEventListener{
        dbref.addListenerForSingleValueEvent(object : ValueEventListener{ // todo: will not change data after first init - need to add check for is work still available
            override fun onDataChange(snapshot: DataSnapshot) {
                workArrayList.clear()
                if (snapshot.exists())
                {
                    for(workSnapshot in snapshot.children)
                    {
                        for (work in workSnapshot.children)
                        {
                            val w = work.getValue(Work::class.java)
                            workArrayList.add(w!!)
                        }
                    }
                    workRecyclerView.adapter = MyAdapter(workArrayList, context!!)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun signOut(view: View)
    {
        helper.signOut()
    }

//    fun chat(view: View)
//    {
//        //helper.chats() // todo: should not be in user open helper
//        //this.startActivity(Intent(this, ChatsActivity::class.java))
//        this.startActivity(Intent(this, ChatsActivity::class.java))
//    }

    fun MoveToBusiness(view: View)
    {
        //helper.moveToBusiness() // todo: should not be in user open helper
        this.startActivity(Intent(this, BusinessAccountActivity::class.java))
    }

    override fun onWorkClick(pos: Int){
        //todo: here make dialog
        //var dwork = workArrayList.get(pos)
//        val view = View.inflate(this,R.layout.watch_work_dialog2, null)
//        //view.
//        val builder = AlertDialog.Builder(this)
//        builder.setView(view)
//        val dialog = builder.create()
//        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        dialog.show()

        //var dialog = WatchWorkDialog(dwork,this)
        //dialog.show(supportFragmentManager,"WatchWorkDialog")//SupportFragmentManager,"WatchWorkDialog")
        //Log.d("Main","onClick: Clicked" + pos)
    }


}