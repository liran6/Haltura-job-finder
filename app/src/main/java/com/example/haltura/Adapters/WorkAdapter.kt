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
        viewHolder.location.text = currentItem.address.address
        var bm = Base64.decode(currentItem.image, Base64.DEFAULT)
        var data = BitmapFactory.decodeByteArray(bm, 0, bm.size)
        //var dataRoundedCorner = ImageHelper.getRoundedCornerBitmap(data,10)
        var dataRoundedCorner = ImageHelper.getRoundedCornerBitmap(Bitmap.createScaledBitmap(data, 200, 200, false),10)
        viewHolder.image.setImageBitmap(dataRoundedCorner)
        //viewHolder.image.setImageBitmap(Bitmap.createScaledBitmap(dataRoundedCorner, 200, 200, false))

        viewHolder.itemView.setOnClickListener {
            _clickOnItemListener(_dataSet[position])
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = _dataSet.size
}