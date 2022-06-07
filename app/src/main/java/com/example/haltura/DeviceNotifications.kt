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

//    fun notifyTextList(context: Context, headLine: String, message: String) {
//        Notify.with(context)
//            .asTextList {
//                lines = Arrays.asList(
//                    "New! Fresh Strawberry Cheesecake.",
//                    "New! Salted Caramel Cheesecake.",
//                    "New! OREO Dream Dessert."
//                )
//                title = "New menu items!"
//                text = lines.size.toString() + " new dessert menu items found."
//            }
//            .show()
//
//    }

//    fun notifyBigText(context: Context, headLine: String, message: String) {
//        Notify.with(this)
//            .asBigText {
//                title = "Chocolate brownie sundae"
//                text = "Try our newest dessert option!"
//                expandedText = "Mouthwatering deliciousness."
//                bigText =
//                    "Our own Fabulous Godiva Chocolate Brownie, Vanilla Ice Cream, Hot Fudge, Whipped Cream and Toasted Almonds.\n" +
//                            "\n" +
//                            "Come try this delicious new dessert and get two for the price of one!"
//            }
//            .show()
//    }

//    fun notifyBigPicture(context: Context, headLine: String, message: String,imageString:String) {
//        Notify.with(context)
//            .asBigPicture {
//                title = headLine
//                text = text
//                expandedText = "The delicious brownie sundae now available."
//                //varius code, depend on how we implement image
////                val imageString: String = Base64.encodeToString(imageBytes, Base64.DEFAULT)
//                val imageBytes = Base64.decode(imageString, Base64.DEFAULT)
//                val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
//                image = decodedImage
//            }
//            .show()
//    }

//    fun notifyMessage(context: Context, headLine: String, message: String) {
//        Notify.with(context)
//            .asMessage {
//                userDisplayName = "Karn"
//                conversationTitle = "Sundae chat"
//                messages = Arrays.asList(
//                    NotificationCompat.MessagingStyle.Message("Are you guys ready to try the Strawberry sundae?",
//                        System.currentTimeMillis() - (6 * 60 * 1000), // 6 Mins ago
//                        "Karn"),
//                    NotificationCompat.MessagingStyle.Message("Yeah! I've heard great things about this place.",
//                        System.currentTimeMillis() - (5 * 60 * 1000), // 5 Mins ago
//                        "Nitish"),
//                    NotificationCompat.MessagingStyle.Message("What time are you getting there Karn?",
//                        System.currentTimeMillis() - (1 * 60 * 1000), // 1 Mins ago
//                        "Moez")
//                )
//            }
//            .show()
//    }

//    fun notifyBubble(context: Context, headLine: String, message: String) {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
//            Toast.makeText(
//                this,
//                "Notification Bubbles are only supported on a device running Android Q or later.",
//                Toast.LENGTH_SHORT
//            ).show()
//            return
//        }
//
//        Notify.with(context)
//            .content {
//                title = "New dessert menu"
//                text = "The Cheesecake Factory has a new dessert for you to try!"
//            }
//            .bubblize {
//                // Create bubble intent
//                val target = Intent(this@MainActivity, BubbleActivity::class.java)
//                val bubbleIntent =
//                    PendingIntent.getActivity(this@MainActivity, 0, target, 0 /* flags */)
//
//                bubbleIcon =
//                    IconCompat.createWithResource(this@MainActivity, R.drawable.ic_app_icon)
//                targetActivity = bubbleIntent
//                suppressInitialNotification = true
//            }
//            .show()
//    }

//    fun notifyIndeterminateProgress(context: Context, headLine: String, message: String) {
//
//        Notify.with(context)
//            .asBigText {
//                title = "Uploading files"
//                expandedText = "The files are being uploaded!"
//                bigText = "Daft Punk - Get Lucky.flac is uploading to server /music/favorites"
//            }
//            .progress {
//                showProgress = true
//            }
//            .show()
//    }

//    fun notifyDeterminateProgress(context: Context, headLine: String, message: String) {
//
//        Notify.with(context)
//            .asBigText {
//                title = "Bitcoin payment processing"
//                expandedText = "Your payment was sent to the Bitcoin network"
//                bigText = "Your payment #0489 is being confirmed 2/4"
//            }
//            .progress {
//                showProgress = true
//                enablePercentage = true
//                progressPercent = 30
//            }
//            .show()
//    }


}