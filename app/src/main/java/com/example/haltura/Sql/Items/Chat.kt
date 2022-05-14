package com.example.haltura.Sql.Items

import android.media.Image

class Chat {
    private var name: String? = null
    private var image: String? = null

    private lateinit var members:  ArrayList<String>
    private lateinit var messages : ArrayList<Message>

    constructor(name: String?,image: String? ,members:  List<String> ,listOfWork : List<Message>)
    {

    }

    constructor(members:  List<String> ,listOfWork : List<Message>)
    {

    }

    fun addMember(member: String)
    {
        members.add(member)
    }

}