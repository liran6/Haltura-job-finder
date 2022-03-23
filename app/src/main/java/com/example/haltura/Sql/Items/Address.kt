package com.example.haltura.Sql.Items

class Address{
    private lateinit var city: String
    private lateinit var street: String
    private lateinit var streetNum: String
    private lateinit var floor: String
    private lateinit var appartment: String

    constructor(city: String, street: String, streetNum: String, floor: String, appartment: String)
    {
        this.city = city
        this.street = street
        this.streetNum = streetNum
        this.floor = floor
        this.appartment = appartment
    }


    fun getCity(): String? {
        return city
    }

    fun setCity(city: String?) {
        this.city = city!!
    }

    fun getStreet(): String? {
        return street
    }

    fun setStreet(street: String?) {
        this.street = street!!
    }

    fun getStreetNum(): String? {
        return streetNum
    }

    fun setStreetNum(streetNum: String?) {
        this.streetNum = streetNum!!
    }

    fun setFloor(floor: String?) {
        this.floor = floor!!
    }

    fun setAppartment(appartment: String?) {
        this.appartment = appartment!!
    }

    fun getFloor(): String? {
        return floor
    }

    fun getAppartment(): String? {
        return appartment
    }

    override fun equals(o: Any?): Boolean {
        if (o is Address) {
            val address = o
            return city == address.city && street == address.street && streetNum == address.streetNum && floor == address.floor && appartment == address.appartment
        }
        return false
    }

    override fun toString(): String {
        return """
            Address:
            city: $city
            street: $street
            streetNum: $streetNum
            floor: $floor
            apartment: $appartment
            """.trimIndent()
    }
}
