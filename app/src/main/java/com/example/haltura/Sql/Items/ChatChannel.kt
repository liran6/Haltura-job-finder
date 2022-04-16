package com.example.haltura.Sql.Items


data class ChatChannel(val userIds: MutableList<String>) {
    constructor() : this(mutableListOf())
}