package com.example.haltura.Adapters

import android.app.Activity
import android.graphics.BitmapFactory
import android.graphics.Color
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
import com.example.haltura.Sql.Items.ImageMessage
import com.example.haltura.Sql.Items.Message
import com.example.haltura.Sql.Items.TextMessage
import com.example.haltura.Sql.UserOpenHelper
import com.example.haltura.activities.ChatsActivity
import com.example.haltura.databinding.MessageBinding
import org.jetbrains.anko.sdk27.coroutines.onClick

class ChatAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    companion object {
        const val MESSAGES_CHILD = "messages"
        const val TAG = "ChatAdapter"
        const val VIEW_TYPE_TEXT = 1
        const val VIEW_TYPE_IMAGE = 2
        private const val LOADING_IMAGE_URL = "https://www.google.com/images/spin-32.gif"
    }

    lateinit var messageList : ArrayList<Message>
    private lateinit var uid  : String

    constructor(messageList : ArrayList<Message> ,context : Activity):super()
    {
        this.messageList = messageList
        this.uid = UserOpenHelper(context).getUserId()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //lateinit var itemView : View
        when (viewType) {
            0 ->
            {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.text_item_out, parent,false)
                return TextMessageInViewHolder(itemView)
            }
            1 ->
            {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.text_item_in, parent,false)
                return TextMessageOutViewHolder(itemView)
            }
            3 ->
            {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.image_item_out, parent,false)
                return ImageMessageOutViewHolder(itemView)
            }
            4 ->
            {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.image_item_in, parent,false)
                return ImageMessageInViewHolder(itemView)
            }
            else -> { // Note the block
                //todo: err
                print("x is ")
                return null!!
            }
        }
        //val itemView = LayoutInflater.from(parent.context).inflate(R.layout.work_item_list,parent,false)
        //itemView.onClick()
        //return myViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = messageList[position]
        when (getItemViewType(position)) {
            0 -> {
                (holder as ChatAdapter.TextMessageInViewHolder).bind(currentItem as TextMessage)
            }
            1 -> {
                (holder as ChatAdapter.TextMessageOutViewHolder).bind(currentItem as TextMessage)
            }
            3 -> {
                (holder as ChatAdapter.ImageMessageOutViewHolder).bind(currentItem as ImageMessage)
            }
            4 -> {
                (holder as ChatAdapter.ImageMessageInViewHolder).bind(currentItem as ImageMessage)
            }
            else -> { // Note the block
                //todo: err
                print("x is ")
            }
        }
    }

    //todo add item add

    override fun getItemViewType(position: Int): Int {
        var isCurrenUser = 0
        if (messageList[position].getUserId() != this.uid)
        {
            isCurrenUser = 1
        }
        return messageList[position].getType()!! * 2 + isCurrenUser
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    class myViewHolder : RecyclerView.ViewHolder
    {
        constructor(itemView: View):super(itemView)
        {
        }
    }

    class TextMessageInViewHolder : RecyclerView.ViewHolder {

        private lateinit var iv :View
        lateinit var Name : TextView
        lateinit var Text : TextView
        lateinit var TimeOfMessage : TextView
        lateinit var avatar : ImageView

        constructor(itemView: View):super(itemView)
        {
            this.iv = itemView
            this.Name = itemView.findViewById(R.id.username_text) as TextView
            this.Text = itemView.findViewById(R.id.textMessage) as TextView
            this.TimeOfMessage = itemView.findViewById(R.id.time) as TextView
            this.avatar = itemView.findViewById(R.id.messageImageView) as ImageView
        }

        fun bind(item: TextMessage) {
//            var Name = iv.findViewById(R.id.username_text) as TextView
//            var Text = itemView.findViewById(R.id.textMessage) as TextView
//            var Time = itemView.findViewById(R.id.time) as TextView
//            var avatar = itemView.findViewById(R.id.messageImageView) as ImageView

            Name.text = item.getName()
            Text.text = item.getText()
            TimeOfMessage.text = item.getTime().toString()

            if (item.getUserImage() != null) {
                var bm = decode(item.getUserImage() , DEFAULT)
                var data = BitmapFactory.decodeByteArray(bm, 0, bm.size)
                avatar.setImageBitmap(data)
            } else {
                avatar.setImageResource(R.drawable.ic_account_circle_black_36dp)
            }
        }
    }


    class TextMessageOutViewHolder : RecyclerView.ViewHolder {

        constructor(itemView: View):super(itemView)
        {
        }

        fun bind(item: TextMessage) {
            var Text = itemView.findViewById(R.id.textMessage) as TextView
            var Time = itemView.findViewById(R.id.time) as TextView
            Text.text = item.getText()
            Time.text = item.getTime().toString()
        }
    }

    class ImageMessageInViewHolder : RecyclerView.ViewHolder {

        constructor(itemView: View ):super(itemView)
        {
        }

        fun bind(item: ImageMessage) {
            var Name = itemView.findViewById(R.id.username_text) as TextView
            var Image = itemView.findViewById(R.id.imageMessage) as ImageView
            var Time = itemView.findViewById(R.id.time) as TextView
            var avatar = itemView.findViewById(R.id.messageImageView) as ImageView

            Name.text = item.getName()
            var bm = decode(item.getUserImage() , DEFAULT)
            var data = BitmapFactory.decodeByteArray(bm, 0, bm.size)
            Image.setImageBitmap(data)
            Time.text = item.getTime().toString()

            if (item.getUserImage() != null) {
                var bm = decode(item.getUserImage() , DEFAULT)
                var data = BitmapFactory.decodeByteArray(bm, 0, bm.size)
                avatar.setImageBitmap(data)
            } else {
                avatar.setImageResource(R.drawable.ic_account_circle_black_36dp)
            }
        }
    }

    class ImageMessageOutViewHolder : RecyclerView.ViewHolder {

        constructor(itemView: View):super(itemView)
        {

        }

        fun bind(item: ImageMessage) {
            var Image = itemView.findViewById(R.id.imageMessage) as ImageView
            var Time = itemView.findViewById(R.id.time) as TextView

            var bm = decode(item.getUserImage() , DEFAULT)
            var data = BitmapFactory.decodeByteArray(bm, 0, bm.size)
            Image.setImageBitmap(data)
            Time.text = item.getTime().toString()
        }
    }
}