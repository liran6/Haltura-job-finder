package com.example.haltura.Sql.Items

class Time {
    private var hour: Int = -1
    private var minutes: Int = -1
    constructor(){}
    constructor(hour: Int, minutes: Int) {
        this.hour = hour
        this.minutes = minutes
    }

    fun isBefore(other: Time) :Boolean
    {
        //note same date is not before
        if(this.hour == other.hour)
        {
            return this.minutes < other.minutes
        }
        else
        {
            return this.hour < other.hour
        }
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
        var h :String = ""
        var m :String = ""
        if (hour < 10)
        {
            h += "0"
        }
        h += hour.toString()
        if (minutes < 10)
        {
            m += "0"
        }
        m += minutes.toString()
        return h + ":" + m
    }
}