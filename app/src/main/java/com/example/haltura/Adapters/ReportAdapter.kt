package com.example.haltura.Adapters

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.haltura.Models.ReportSerializable
import com.example.haltura.R
import com.example.haltura.Utils.DateTime
import com.example.haltura.Utils.ImageHelper

class ReportAdapter(
    private var _dataSet: MutableList<ReportSerializable>,
    private val _clickOnItemListener: (ReportSerializable) -> Unit,
) : RecyclerView.Adapter<ReportAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val report: TextView = view.findViewById(R.id.report_data)
    }

    fun setData(data: MutableList<ReportSerializable>) {
        _dataSet = data
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.report_item_list, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val currentItem = _dataSet[position]
        var text = currentItem.reportText
        if (text!!.length > 40)
        {
            text = text.substring(40) + "..."
        }
        viewHolder.report.text = text

        viewHolder.itemView.setOnClickListener {
            _clickOnItemListener(_dataSet[position])//todo: check if it is your work
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = _dataSet.size
}