package com.example.haltura.Sql.Items

class Date {
    private var year: Int = -1
    private var month: Int = -1
    private var day: Int =  -1
    constructor(){}
    constructor(year: Int, month: Int, day: Int) {
        this.year = year
        this.month = month
        this.day = day
    }
    //Year
    fun getYear(): Int? {
        return year
    }

    fun setYear(year: Int?) {
        this.year = year!!
    }
    //Month
    fun getMonth(): Int? {
        return month
    }

    fun setMonth(month: Int?) {
        this.month = month!!
    }
    //Day
    fun getDay(): Int? {
        return day
    }

    fun setDay(day: Int?) {
        this.day = day!!
    }

    override fun toString(): String {
        return "$year/$month/$day"
    }
}