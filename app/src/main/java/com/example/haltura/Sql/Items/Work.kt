package com.example.haltura.Sql.Items


import android.media.Image
import android.widget.*
import com.google.type.Date
import java.sql.Time

class Work(
    image: Image,
    company: String,
    task: String,
    salary: String,
    numberOfWorkers: String,
    address: String,
    info: String,
    date: Date,
    startTime: Time,
    endTime: Time
) {
    private var image: Image
    var company: String
    var task: String
    var salary: String
    var numberOfWorkers: String
    var address: String
    var info: String
    var date: Date
    private var startTime: Time
    private var endTime: Time
    fun getImage(): Image {
        return image
    }

    fun setImage(image: Image) {
        this.image = image
    }

    fun getStartTime(): Time {
        return startTime
    }

    fun setStartTime(startTime: Time) {
        this.startTime = startTime
    }

    fun getEndTime(): Time {
        return endTime
    }

    fun setEndTime(endTime: Time) {
        this.endTime = endTime
    }

    override fun toString(): String {
        return "Work{" +
                "image=" + image +
                ", company='" + company + '\'' +
                ", task='" + task + '\'' +
                ", salary='" + salary + '\'' +
                ", numberOfWorkers='" + numberOfWorkers + '\'' +
                ", address='" + address + '\'' +
                ", info='" + info + '\'' +
                ", date=" + date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}'
    }

    init {
        this.image = image
        this.company = company
        this.task = task
        this.salary = salary
        this.numberOfWorkers = numberOfWorkers
        this.address = address
        this.info = info
        this.date = date
        this.startTime = startTime
        this.endTime = endTime
    }
}