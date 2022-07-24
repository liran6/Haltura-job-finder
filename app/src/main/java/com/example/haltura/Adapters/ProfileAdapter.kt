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
import com.example.haltura.Models.ProfileSerializable
import com.example.haltura.R



class ProfileAdapter(
    private var _dataSet: List<ProfileSerializable>,
    private val _clickOnItemListener: (ProfileSerializable) -> Unit,
    private val _clickOnLongItemListener: (ProfileSerializable) -> Unit,
    private val _enableOnClick: Boolean
) : RecyclerView.Adapter<ProfileAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val username: TextView = view.findViewById(R.id.username)
        val image: ImageView= view.findViewById(R.id.profile_image)
    }

    fun setData(data: MutableList<ProfileSerializable>) {
        _dataSet = data
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.chat_member_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val currentItem = _dataSet[position]
        //username
        viewHolder.username.text = currentItem.username
        //image
//        var bm = Base64.decode(currentItem.image, Base64.DEFAULT)
//        var data = BitmapFactory.decodeByteArray(bm, 0, bm.size)
//        viewHolder.image.setImageBitmap(data)
        //set On Click
        viewHolder.itemView.setOnClickListener{
            _clickOnItemListener(_dataSet[position])
        }
        if (_enableOnClick)
        {
            viewHolder.itemView.setOnLongClickListener{
                _clickOnLongItemListener(_dataSet[position])
                true
            }
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = _dataSet.size
}
