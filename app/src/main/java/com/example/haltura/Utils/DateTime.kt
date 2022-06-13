package com.example.haltura.Utils

class DateTime {
    companion object {
        fun getDate(dateTime : String) :String
        {
            var arrDate = dateTime?.split('T')?.get(0)?.split('-')//"yyyy-MM-dd'
            val year = arrDate?.get(0)
            val month = arrDate?.get(1)
            val day = arrDate?.get(2)
            return "$day/$month/$year"
        }

        fun getTime(dateTime : String) :String
        {
            var arrDate = dateTime?.split('T')?.get(1)?.split(':')//"yyyy-MM-dd'
            val hours = arrDate?.get(0)
            val minutes = arrDate?.get(1)
            return "$hours:$minutes"
        }
    }
}