package com.example.haltura.Sql.Items

class Time {
    private var hour: Int = -1
    private var minutes: Int = -1
    constructor(){}
    constructor(hour: Int, minutes: Int) {
        this.hour = hour
        this.minutes = minutes
    }

    //Hour
    fun getHour(): Int? {
        return hour
    }

    fun setHour(hour: Int?) {
        this.hour = hour!!
    }
    //Minutes
    fun getMinutes(): Int? {
        return minutes
    }

    fun setMinutes(minutes: Int?) {
        this.minutes = minutes!!
    }

    override fun toString(): String {
        return "$hour:$minutes"
    }
}