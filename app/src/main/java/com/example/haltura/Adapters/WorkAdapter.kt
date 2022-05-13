package com.example.haltura.Adapters

import android.app.Activity
import android.graphics.Color
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.*
import com.bumptech.glide.Glide
import com.example.haltura.R
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
//import com.example.haltura.databinding.ImageMessageBinding
import com.example.haltura.databinding.WorkItemListBinding
import com.example.haltura.Sql.Items.Message
import com.example.haltura.Sql.Items.Work
import com.example.haltura.activities.ChatsActivity
//import com.example.haltura.activities.ChatsActivity.Companion.ANONYMOUS
import com.example.haltura.activities.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage


class WorkAdapter(
    private var works: MainActivity,
    private var auth: FirebaseAuth =  FirebaseAuth.getInstance(),
    private var db: FirebaseDatabase = Firebase.database,
    private val messages_ref: DatabaseReference = db.reference.child(MESSAGES_CHILD),
    private val options: FirebaseRecyclerOptions<Work> = FirebaseRecyclerOptions.Builder<Work>()
        .setQuery(messages_ref, Work::class.java)
        .build(),
) : FirebaseRecyclerAdapter<Work, ViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.work_item_list, parent, false)
        val binding = WorkItemListBinding.bind(view)
        return WorkViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Work) {
        (holder as WorkViewHolder).bind(model)
    }

    inner class WorkViewHolder(private val binding: WorkItemListBinding) : ViewHolder(binding.root) {
        fun bind(item: Work) {
            binding.itemTask.text = item.getTask()
            binding.itemSalary.text = item.getSalary().toString()
            binding.itemDateAndTime.text = item.getDate().toString() + " FROM " + item.getEndTime().toString() +" TO " + item.getStartTime().toString()
            binding.itemLocation.text = item.getAddress()
        }
    }

    companion object {
        const val MESSAGES_CHILD = "Works"
        const val TAG = "WorkAdapter"
    }
}