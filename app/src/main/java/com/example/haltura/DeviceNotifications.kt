package com.example.haltura

//import io.karn.notify.R
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.util.Base64
import androidx.core.app.NotificationCompat
import io.karn.notify.Notify
import java.util.*


object DeviceNotifications {

    fun init() {
        Notify.defaultConfig {
            header {
                color = R.color.colorPrimaryDark
            }
            alerting(Notify.CHANNEL_DEFAULT_KEY) {
                lightColor = Color.RED
            }
        }
    }

    fun notifyDefault(context: Context, headLine: String, message: String) {
        init()
        Notify.with(context)
            .content {
                title = headLine
                text = message
            }
            .stackable {
                key = "test_key"
                summaryContent = "test summary content"
                summaryTitle = { count -> "Summary title" }
                summaryDescription = { count -> count.toString() + " new notifications." }
            }
            .show()
    }
}