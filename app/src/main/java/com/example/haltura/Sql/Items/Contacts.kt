package com.example.haltura.Sql.Items

class Contacts {
    private lateinit var contacts: List<User>

    constructor()
    {
        this.contacts = emptyList()
    }
    constructor(contacts: List<User>)
    {
        this.contacts = contacts
    }

    fun getContacts() : List<User>
    {
        return contacts
    }
}