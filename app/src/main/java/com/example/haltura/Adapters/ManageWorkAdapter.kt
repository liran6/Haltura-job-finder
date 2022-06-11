package com.example.haltura.Adapters

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
import com.example.haltura.Utils.ImageHelper


class ManageWorkAdapter(
    private var _dataSet: MutableList<WorkSerializable>,
    private val _clickOnItemListener: (WorkSerializable) -> Unit,
    private val _clickDeleteItemListener: (WorkSerializable) -> Unit

) : RecyclerView.Adapter<ManageWorkAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val task: TextView = view.findViewById(R.id.task)
        val date: TextView = view.findViewById(R.id.date)
        val image: ImageView= view.findViewById(R.id.image)
        val trash : ImageView= view.findViewById(R.id.trash)
    }

    fun setData(data: MutableList<WorkSerializable>) {
        _dataSet = data
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.manage_work_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val currentItem = _dataSet[position]
        viewHolder.task.text = currentItem.task
        viewHolder.date.text = "FROM: " +currentItem.startTime + " TO " + currentItem.startTime
        var bm = Base64.decode(currentItem.image, Base64.DEFAULT)
        var data = BitmapFactory.decodeByteArray(bm, 0, bm.size)
        var dataRoundedCorner = ImageHelper.getRoundedCornerBitmap(data,10)
        viewHolder.image.setImageBitmap(dataRoundedCorner)

        viewHolder.trash.setOnClickListener()
        {
            _clickDeleteItemListener(_dataSet[position])
        }

        viewHolder.itemView.setOnClickListener {
            _clickOnItemListener(_dataSet[position])//todo: check if it is your work
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = _dataSet.size
}