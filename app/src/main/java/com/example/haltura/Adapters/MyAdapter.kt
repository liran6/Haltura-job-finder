package com.example.haltura.Adapters

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.haltura.R
import com.example.haltura.Sql.Items.Work
import android.util.Base64.DEFAULT
import android.util.Base64.decode
import org.jetbrains.anko.sdk27.coroutines.onClick

//todo: change to WorkAdapter
class MyAdapter : RecyclerView.Adapter<MyAdapter.myViewHolder>{
    lateinit var workList : ArrayList<Work>
    lateinit var onWorkListener : MyAdapter.OnWorkListener

    constructor(workList : ArrayList<Work> ,onWorkListener : MyAdapter.OnWorkListener):super()
    {
        this.workList = workList
        this.onWorkListener = onWorkListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.work_item_list,parent,false)
        //itemView.onClick()
        return myViewHolder(itemView, onWorkListener)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        val currentItem = workList[position]
        holder.Task.text = currentItem.getTask()
        holder.Salary.text = "Salary: " + currentItem.getSalary().toString()
        holder.DateAndTime.text = currentItem.getDate().toString() + " FROM " + currentItem.getEndTime().toString() +" TO " + currentItem.getStartTime().toString()
        holder.Location.text = currentItem.getAddress()
        var bm = decode(currentItem.getImage(), DEFAULT)
        var data = BitmapFactory.decodeByteArray(bm, 0, bm.size)
        holder.Image.setImageBitmap(data)
    }

    override fun getItemCount(): Int {
        return workList.size
    }

    class myViewHolder : RecyclerView.ViewHolder, View.OnClickListener
    {
        lateinit var Task :TextView
        lateinit var Salary : TextView
        lateinit var DateAndTime : TextView
        lateinit var Location : TextView
        lateinit var Image : ImageView
        lateinit var onWokListener : OnWorkListener

        constructor(itemView: View ,onWorkListener : OnWorkListener):super(itemView)
        {
            Task = itemView.findViewById(R.id.itemTask)
            Salary = itemView.findViewById(R.id.itemSalary)
            DateAndTime = itemView.findViewById(R.id.itemDateAndTime)
            Location = itemView.findViewById(R.id.itemLocation)
            Image = itemView.findViewById(R.id.Image)
            this.onWokListener = onWorkListener
            itemView.setOnClickListener(this)
        }
//        val Task :TextView = itemView.findViewById(R.id.itemTask)
//        val Salary : TextView = itemView.findViewById(R.id.itemSalary)
//        val DateAndTime : TextView = itemView.findViewById(R.id.itemDateAndTime)
//        val Location : TextView = itemView.findViewById(R.id.itemLocation)
//        val Image : ImageView =  itemView.findViewById(R.id.Image)
//        var onWokListener: OnWorkListener = onWokListener1
//        itemView.setOnClickListener(this)

        override fun onClick(v: View?) {
            onWokListener.onWorkClick(getAdapterPosition())
        }
    }

//    class myViewHolder(var itemView: View ,onWorkListener1 : OnWorkListener) : RecyclerView.ViewHolder(itemView) , View.OnClickListener
//    {
//        val Task :TextView = itemView.findViewById(R.id.itemTask)
//        val Salary : TextView = itemView.findViewById(R.id.itemSalary)
//        val DateAndTime : TextView = itemView.findViewById(R.id.itemDateAndTime)
//        val Location : TextView = itemView.findViewById(R.id.itemLocation)
//        val Image : ImageView =  itemView.findViewById(R.id.Image)
//        var onWokListener: OnWorkListener = onWokListener1
//        itemView.setOnClickListener(this)
//
//        override fun onClick(v: View?) {
//            onWokListener.onWorkClick(getAdapterPosition())
//        }
//    }

    public interface OnWorkListener
    {
        fun onWorkClick(pos: Int)
    }
}