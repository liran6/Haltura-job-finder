package com.example.haltura.Utils

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import android.util.Base64
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupWindow
import com.example.haltura.R
import com.example.haltura.activities.AddWorkActivity
import java.io.ByteArrayOutputStream

class ImageHandler {
    companion object {
        private val REQ_CAMERA = 1
        val PICK_IMAGE = 2
        private val AUTOCOMPLETE_REQUEST_CODE = 3

        private fun convertImageToString(image: Bitmap): String {
            val baos = ByteArrayOutputStream()
            image.compress(Bitmap.CompressFormat.PNG, 100, baos)
            val data = baos.toByteArray()
            return  Base64.encodeToString(data, Base64.DEFAULT)
        }
//        fun SetImage(view: View)
//        {
//            val imagePopup: View = layoutInflater.inflate(R.layout.camera_or_gallery_popup, null)
//
//            val popup = PopupWindow(
//                imagePopup,
//                ViewGroup.LayoutParams.WRAP_CONTENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT
//            )
//
//            popup.elevation = 3.0f
//
//            val camera = imagePopup.findViewById(R.id.camera) as ImageView
//            val gallery = imagePopup.findViewById(R.id.gallery) as ImageView
//
//
//            camera.setOnClickListener {
//                setCameraImage()
//                popup.dismiss()
//                removeBackground(true)
//            }
//
//            gallery.setOnClickListener {
//                setGalleryImage()
//                popup.dismiss()
//                removeBackground(true)
//            }
//            removeBackground(false)
//            popup.showAtLocation(_layout, Gravity.CENTER, 0, 0) //popup.showAtLocation(_fragmentView, Gravity.CENTER, 0, 0)
//        }
//
//        private fun removeBackground(show: Boolean) {
//            if (show) {
//                _layout.visibility = View.VISIBLE
//
//            } else {
//                _layout.visibility = View.GONE
//            }
//        }
//
//
//        fun setCameraImage(activity:Activity)
//        {
//            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            activity.startActivityForResult(intent, AddWorkActivity.REQ_CAMERA)
//        }

//        fun setGalleryImage(activity:Activity)
//        {
//            val intent = Intent()
//            intent.type = "image/*"
//            intent.action = Intent.ACTION_GET_CONTENT
//            activity.startActivityForResult(
//                Intent.createChooser(intent, "Select Picture"),
//                AddWorkActivity.PICK_IMAGE
//            )
//        }
//
  }
}