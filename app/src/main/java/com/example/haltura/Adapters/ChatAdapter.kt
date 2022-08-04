package com.example.haltura.Adapters


import android.content.res.ColorStateList
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Base64.DEFAULT
import android.util.Base64.decode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.haltura.R
import com.example.haltura.Sql.Items.MessageSerializable
import com.example.haltura.Utils.DateTime
import com.example.haltura.Utils.UserData


class ChatAdapter(
    private var _dataSet: List<MessageSerializable>,
    private val _clickOnItemListener: (MessageSerializable) -> Unit,
) : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val card: androidx.cardview.widget.CardView = view.findViewById(R.id.cardView)
        val messageLayout: RelativeLayout = view.findViewById(R.id.message_layout)
        val name: TextView = view.findViewById(R.id.username_text)
        val text: TextView = view.findViewById(R.id.text)
        val image: ImageView = view.findViewById(R.id.image)
        val time: TextView = view.findViewById(R.id.time)
    }

    fun setData(data: MutableList<MessageSerializable>) {
        _dataSet = data
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.message_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val currentItem = _dataSet[position]
        val name = currentItem.userId
        if (UserData.currentUser?.userId == currentItem.userId)
        {
            viewHolder.card.setCardBackgroundColor(ColorStateList.valueOf(-0x352701))
            (viewHolder.card.getLayoutParams() as ViewGroup.MarginLayoutParams).setMargins(0, 0, 15, 0)
            viewHolder.name.setTextSize(0f)
            viewHolder.name.text = null // check if legal
        }
        else
        {
            viewHolder.name.text = name
            (viewHolder.messageLayout.getLayoutParams() as RelativeLayout.LayoutParams).addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
            (viewHolder.card.getLayoutParams()as ViewGroup.MarginLayoutParams).setMargins(15, 0, 0, 0)

        }
        val image = currentItem.image
        if (image == null)
        {
            viewHolder.image.setImageResource(0) //empty
            viewHolder.image.getLayoutParams().height = 0
        }
        else
        {
            val bm = decode(image , DEFAULT)
            val data = BitmapFactory.decodeByteArray(bm, 0, bm.size)
            viewHolder.image.setImageBitmap(data)
        }
        val text = currentItem.text
        if (text == null)
        {
            viewHolder.text.setTextSize(0f)
            viewHolder.text.text = null
        }
        else
        {
            viewHolder.text.text = text //empty
        }
        val time = currentItem.time
        viewHolder.time.text = DateTime.getTime(time!!)


        //viewHolder.itemView.
        viewHolder.itemView.setOnClickListener {
            _clickOnItemListener(_dataSet[position])
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = _dataSet.size
}