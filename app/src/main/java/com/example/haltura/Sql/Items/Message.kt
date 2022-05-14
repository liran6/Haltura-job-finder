package com.example.haltura.Sql.Items


import com.google.protobuf.Timestamp

//import java.util.*
//
//
//object MessageType {
//    const val TEXT = "TEXT"
//    const val IMAGE = "IMAGE"
//}
//
//interface Message {
//    val time: Date
//    val senderId: String
//    val recipientId: String
//    val senderName: String
//    val type: String

open class Message {
    private lateinit var time: Time
    private lateinit var name: String
    private lateinit var userId: String
    //private lateinit var userImage :String
    private lateinit var timestamp : Timestamp

    private var text: String? = null
    private var image: String? = null

    private var type : Int =-1

        companion object {
        const val TYPE_TEXT = 0
            const val TYPE_IMAGE = 1
    }

    // Empty constructor needed for Firestore serialization
    constructor()

    constructor(text: String?, image: String?, userId: String, name: String , time: Time){
        this.text = text
        this.image = image
        this.name = name
        this.userId = userId
        this.time = time
        //this.userImage =userImage //todo: change in image and text
        this.type = TYPE_TEXT
    }

//    constructor(image: String, userId: String, name: String , userImage : String, time: Time){
//        this.name = name
//        this.userId = userId
//        this.time = time
//        this.userImage =userImage //todo: change in image and text
//        this.type = TYPE_IMAGE
//    }

    //constructor(name: String, userId: String ,time: Time, userImage : String, type :Int) {
//        this.name = name
//        this.userId = userId
//        this.time = time
//        this.userImage =userImage //todo: change in image and text
//        this.type = type
        //Timestamp.get
    //}

    fun setTime(time: Time?) {
        this.time = time!!
    }

    fun getTime(): Time? {
        return time
    }

    fun setName(name: String?) {
        this.name = name!!
    }

    fun getName(): String? {
        return name
    }

    fun setUserId(userId: String?) {
        this.userId = userId!!
    }

    fun getUserId(): String? {
        return userId
    }

//    fun setUserImage(userImage: String?) {
//        this.userImage = userImage!!
//    }
//
//    fun getUserImage(): String? {
//        return userImage
//    }


    fun setType(type: Int?) {
        this.type = type!!
    }

    fun getType(): Int? {
        return type
    }

    ///////////////////////////
    fun setImage(image: String?) {
        this.image = image!!
    }

    fun getImage(): String? {
        return image
    }

    fun setText(text: String?) {
        this.text = text!!
    }

    fun getText(): String? {
        return text
    }
}
