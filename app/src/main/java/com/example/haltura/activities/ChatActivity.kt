package com.example.haltura.activities

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.MenuItem
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.haltura.R
import com.example.haltura.Fragments.ChatFragments.ChatFragment
import com.example.haltura.Utils.Const


class ChatActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.chat_fragment, ChatFragment(), Const.chat_fragment)
                .addToBackStack(Const.chat_fragment)
                .setReorderingAllowed(true)
                .commit()
        }

        val actionBar = getSupportActionBar()
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    //remove focus from edit texts
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm: InputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }
}
