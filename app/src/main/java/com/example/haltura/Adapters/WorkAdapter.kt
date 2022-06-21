package com.example.haltura.Adapters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.haltura.R
import com.example.haltura.Sql.Items.Chat
import com.example.haltura.Sql.Items.Message
import com.example.haltura.Sql.Items.Work
import com.example.haltura.Sql.Items.WorkSerializable
import com.example.haltura.Utils.DateTime
import com.example.haltura.Utils.ImageHelper


class WorkAdapter(
    private var _dataSet: MutableList<WorkSerializable>,
    private val _clickOnItemListener: (WorkSerializable) -> Unit
) : RecyclerView.Adapter<WorkAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val task: TextView = view.findViewById(R.id.itemTask)
        val salary: TextView = view.findViewById(R.id.itemSalary)
        val dateAndTime: TextView = view.findViewById(R.id.itemDateAndTime)
        val location: TextView = view.findViewById(R.id.itemLocation)
        val image: ImageView= view.findViewById(R.id.Image)
    }

    fun setData(data: MutableList<WorkSerializable>) {
        _dataSet = data
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.work_item_list2, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val currentItem = _dataSet[position]
        viewHolder.task.text = currentItem.task
        viewHolder.salary.text = currentItem.salary.toString()
        viewHolder.dateAndTime.text = DateTime.getDate(currentItem.startTime) + " From " +
                DateTime.getTime(currentItem.startTime) + " To " +
                DateTime.getTime(currentItem.endTime)
//        viewHolder.location.text = currentItem.address.street + " " + currentItem.address.streetNum +
//                ", " + currentItem.address.city
        viewHolder.location.text = currentItem.address.address
        var bm = Base64.decode(currentItem.image, Base64.DEFAULT)
        var data = BitmapFactory.decodeByteArray(bm, 0, bm.size)
        //var dataRoundedCorner = ImageHelper.getRoundedCornerBitmap(data,10)
        var dataRoundedCorner = ImageHelper.getRoundedCornerBitmap(Bitmap.createScaledBitmap(data, 200, 200, false),10)
        viewHolder.image.setImageBitmap(dataRoundedCorner)
        //viewHolder.image.setImageBitmap(Bitmap.createScaledBitmap(dataRoundedCorner, 200, 200, false))

        viewHolder.itemView.setOnClickListener {
            _clickOnItemListener(_dataSet[position])//todo: check if it is your work
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = _dataSet.size
}



//import android.app.Activity
//import android.graphics.Color
//import android.net.Uri
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView.*
//import com.bumptech.glide.Glide
//import com.example.haltura.R
//import com.firebase.ui.database.FirebaseRecyclerAdapter
//import com.firebase.ui.database.FirebaseRecyclerOptions
////import com.example.haltura.databinding.ImageMessageBinding
//import com.example.haltura.databinding.WorkItemListBinding
//import com.example.haltura.Sql.Items.Message
//import com.example.haltura.Sql.Items.Work
//import com.example.haltura.activities.ChatsActivity
////import com.example.haltura.activities.ChatsActivity.Companion.ANONYMOUS
//import com.example.haltura.activities.MainActivity
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.FirebaseDatabase
//import com.google.firebase.database.ktx.database
//import com.google.firebase.ktx.Firebase
//import com.google.firebase.storage.StorageReference
//import com.google.firebase.storage.ktx.storage
//
//
//class WorkAdapter(
//    private var works: MainActivity,
//    private var auth: FirebaseAuth =  FirebaseAuth.getInstance(),
//    private var db: FirebaseDatabase = Firebase.database,
//    private val messages_ref: DatabaseReference = db.reference.child(MESSAGES_CHILD),
//    private val options: FirebaseRecyclerOptions<Work> = FirebaseRecyclerOptions.Builder<Work>()
//        .setQuery(messages_ref, Work::class.java)
//        .build(),
//) : FirebaseRecyclerAdapter<Work, ViewHolder>(options) {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val inflater = LayoutInflater.from(parent.context)
//        val view = inflater.inflate(R.layout.work_item_list, parent, false)
//        val binding = WorkItemListBinding.bind(view)
//        return WorkViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Work) {
//        (holder as WorkViewHolder).bind(model)
//    }
//
//    inner class WorkViewHolder(private val binding: WorkItemListBinding) : ViewHolder(binding.root) {
//        fun bind(item: Work) {
//            binding.itemTask.text = item.getTask()
//            binding.itemSalary.text = item.getSalary().toString()
//            binding.itemDateAndTime.text = item.getDate().toString() + " FROM " + item.getEndTime().toString() +" TO " + item.getStartTime().toString()
//            binding.itemLocation.text = item.getAddress()
//        }
//    }
//
//    companion object {
//        const val MESSAGES_CHILD = "Works"
//        const val TAG = "WorkAdapter"
//    }
//}