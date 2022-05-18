package com.example.haltura.Sql.Items

import android.media.Image

class Chat {
    private var name: String? = null
    private var image: String? = null
    //todo: admin

    private lateinit var members: ArrayList<String>
    private lateinit var messages : ArrayList<Message>


    constructor()

    constructor(name: String?,image: String? ,members:  ArrayList<String> ,messages : ArrayList<Message>)
    {
        this.name = name
        this.image = image
        this.members = members
        this.messages = messages
    }

    constructor(members:  ArrayList<String> ,messages : ArrayList<Message>)
    {
        this.members = members
        this.messages = messages
    }

    fun addMember(member: String)
    {
        members.add(member)
    }

    //name
    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name!!
    }

    //image
    fun getImage(): String? {
        return image
    }

    fun setImage(image: String?) {
        this.image = image!!
    }

    //Members
    fun getMembers(): ArrayList<String>? {
        return members
    }

    fun setMembers(members: ArrayList<String>?) {
        this.members = members!!
    }

    //Messages
    fun getMessages(): ArrayList<Message>? {
        return messages
    }

    fun setMessages(messages: ArrayList<Message>?) {
        this.messages = messages!!
    }
}