package com.example.haltura.Sql.Items


import android.graphics.Bitmap
import android.media.Image
import android.util.Base64.DEFAULT
import android.util.Base64.encodeToString
import android.widget.*
import io.realm.annotations.PrimaryKey
import java.io.ByteArrayOutputStream
import java.util.*

class Work {
    private lateinit var image: String
    private lateinit var company: String
    private lateinit var task: String
    private  var salary: Int = -1
    private  var numberOfWorkers: Int  = -1
    private lateinit var address: String
    private lateinit var info: String
    private lateinit var date: Date
    private lateinit var startTime: Time
    private lateinit var endTime: Time
    private  var id: Int  = -1

    constructor()
    {
        val baos = ByteArrayOutputStream()
        Bitmap.createBitmap(5,6,Bitmap.Config.ARGB_8888).compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        image = encodeToString(data, DEFAULT)
        company = ""
        task = ""
        address = ""
        info = ""
        date = Date(2000,2,22)
        startTime = Time(0,0)
        endTime = Time(0,0)
    }
    //todo: add constructor of image type string ()

    constructor(
    image: Bitmap,
    company: String,
    task: String,
    salary: Int,
    numberOfWorkers: Int,
    address: String,
    info: String,
    date: Date,
    startTime: Time,
    endTime: Time,
) {
        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        this.image = encodeToString(data, DEFAULT)
        this.company = company
        this.task = task
        this.salary = salary
        this.numberOfWorkers = numberOfWorkers
        this.address = address
        this.info = info
        this.date = date
        this.startTime = startTime
        this.endTime = endTime
        this.id = GetWorkId()
    }
//    private var image: Bitmap
//    private var company: String
//    private var task: String
//    private var salary: Int
//    private var numberOfWorkers: Int
//    private var address: String
//    private var info: String
//    private var date: Date
//    private var startTime: Time
//    private var endTime: Time
//    private var id: Int

    companion object
    {
        private var WorkId = 1;
    }


    fun GetWorkId() :Int
    {
        var id = WorkId
        WorkId += 1
        return id
    }
    //Company
    fun setCompany(company: String?) {
        this.company = company!!
    }

    fun getCompany(): String? {
        return company
    }
    //Task
    fun setTask(task: String?) {
        this.task = task!!
    }

    fun getTask(): String? {
        return task
    }
    //Salary
    fun setSalary(salary: Int?) {
        this.salary = salary!!
    }

    fun getSalary(): Int? {
        return salary
    }
    //NumberOfWorkers
    fun setNumberOfWorkers(numberOfWorkers: Int?) {
        this.salary = numberOfWorkers!!
    }

    fun getNumberOfWorkers(): Int? {
        return numberOfWorkers
    }
    //Address
    fun setAddress(address: String?) {
        this.address = address!!
    }

    fun getAddress(): String? {
        return address
    }
    //Info
    fun setInfo(info: String?) {
        this.info = info!!
    }

    fun getInfo(): String? {
        return info
    }
    //Date
    fun setDate(date: Date?) {
        this.date = date!!
    }

    fun getDate(): Date? {
        return date
    }

    fun getImage(): String? {
        return image
    }

    fun setImage(image: String?) {
        this.image = image!!
    }

    fun getStartTime(): Time? {
        return startTime
    }

    fun setStartTime(startTime: Time?) {
        this.startTime = startTime!!
    }

    fun getEndTime(): Time? {
        return endTime
    }

    fun setEndTime(endTime: Time?) {
        this.endTime = endTime!!
    }

    fun getId(): Int? {
        return id
    }

    fun setId(id: Int?) {
        this.id = id!!
    }

    //todo: need to be in specific format
    override fun toString(): String {
        return "Work(company='$company', task='$task', salary=$salary, numberOfWorkers=$numberOfWorkers, address='$address', info='$info')"
        //todo change
        //return "Work(image=$image, company='$company', task='$task', salary='$salary', numberOfWorkers=$numberOfWorkers, address='$address', info='$info', date=$date, startTime=$startTime, endTime=$endTime)"
    }

//    init {
//        this.image = image
//        this.company = company
//        this.task = task
//        this.salary = salary
//        this.numberOfWorkers = numberOfWorkers
//        this.address = address
//        this.info = info
//        this.date = date
//        this.startTime = startTime
//        this.endTime = endTime
//        this.id = GetWorkId()
//    }
}