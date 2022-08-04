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
  }
}