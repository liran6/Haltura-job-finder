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
import com.example.haltura.Sql.Items.WorkSerializable
import com.example.haltura.Utils.DateTime
import com.example.haltura.Utils.ImageHelper
import java.time.LocalDate

class MyCalendarAdapter(
    private var _dataSet: MutableMap<LocalDate, MutableList<WorkSerializable>>? =
        mutableMapOf<LocalDate, MutableList<WorkSerializable>>(),
    private val _clickOnItemListener: (WorkSerializable) -> Unit,
    private var _dataView: MutableList<WorkSerializable>? = mutableListOf<WorkSerializable>(),
) : RecyclerView.Adapter<MyCalendarAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val task: TextView = view.findViewById(R.id.task)
        val date: TextView = view.findViewById(R.id.date)
        val image: ImageView = view.findViewById(R.id.image)
    }

    fun updateDataSet(newData: MutableMap<LocalDate, MutableList<WorkSerializable>>) {
        this._dataSet = newData
        this.notifyDataSetChanged()
    }

    fun setData(date: LocalDate) {
        _dataView = _dataSet?.get(date)
        this.notifyDataSetChanged()
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.calendar_event_item_view_usless, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: MyCalendarAdapter.ViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        if (_dataView != null) {
            val currentItem = _dataView!![position]
            viewHolder.task.text = currentItem.task
            viewHolder.date.text = DateTime.getDate(currentItem.startTime) + " From " +
                    DateTime.getTime(currentItem.startTime) + " To " +
                    DateTime.getTime(currentItem.endTime)//"FROM: " +currentItem.startTime + " TO " + currentItem.startTime
            var bm = Base64.decode(currentItem.image, Base64.DEFAULT)
            var data = BitmapFactory.decodeByteArray(bm, 0, bm.size)
            var dataRoundedCorner = ImageHelper.getRoundedCornerBitmap(data, 10)
            viewHolder.image.setImageBitmap(dataRoundedCorner)

            viewHolder.itemView.setOnClickListener {
                _clickOnItemListener(_dataView!![position])
            }
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = if (_dataView == null) {
        0
    } else {
        _dataView!!.size
    }

}
