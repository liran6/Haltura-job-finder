package com.example.haltura.Sql.Items

class User {
    private var id: Long = 0
    private lateinit var userFirstName: String
    private lateinit var userLastName: String
    private lateinit var password: String
    private lateinit var userName: String
    private lateinit var email: String
    private lateinit var userPhone: String
    private lateinit var address: Address
    private var isAdmin: Boolean = false

    constructor() {}
    constructor(
        id: Long,
        userFirstName: String,
        userLastName: String,
        password: String,
        userName: String,
        email: String,
        userPhone: String,
        address: Address,
        isAdmin: Boolean
    ) {
        this.id = id
        this.userFirstName = userFirstName
        this.userLastName = userLastName
        this.password = password
        this.userName = userName
        this.email = email
        this.userPhone = userPhone
        this.address = address
        this.isAdmin = isAdmin
    }

    constructor(
        userFirstName: String,
        userLastName: String,
        password: String,
        userName: String,
        email: String,
        userPhone: String,
        address: Address,
        isAdmin: Boolean
    ) {
        this.userFirstName = userFirstName
        this.userLastName = userLastName
        this.password = password
        this.userName = userName
        this.email = email
        this.userPhone = userPhone
        this.address = address
        this.isAdmin = isAdmin
    }

    constructor(
        id: Long,
        userFirstName: String,
        userLastName: String,
        password: String,
        userName: String,
        email: String,
        userPhone: String,
        address: Address
    ) {
        this.id = id
        this.userFirstName = userFirstName
        this.userLastName = userLastName
        this.password = password
        this.userName = userName
        this.email = email
        this.userPhone = userPhone
        this.address = address
        isAdmin = false
    }

    constructor(
        userFirstName: String,
        userLastName: String,
        password: String,
        userName: String,
        email: String,
        userPhone: String,
        address: Address
    ) {
        this.userFirstName = userFirstName
        this.userLastName = userLastName
        this.password = password
        this.userName = userName
        this.email = email
        this.userPhone = userPhone
        this.address = address
        isAdmin = false
    }

    fun getIsAdmin(): Boolean? {
        return isAdmin
    }

    fun setIsAdmin(isAdmin: Boolean?) {
        this.isAdmin = isAdmin!!
    }

    fun getUserFirstName(): String? {
        return userFirstName
    }

    fun setUserFirstName(userFirstName: String?) {
        this.userFirstName = userFirstName!!
    }

    fun getUserLastName(): String? {
        return userLastName
    }

    fun setUserLastName(userLastName: String?) {
        this.userLastName = userLastName!!
    }

    fun getPassword(): String? {
        return password
    }

    fun setPassword(password: String?) {
        this.password = password!!
    }

    fun getAddress(): Address? {
        return address
    }

    fun setAddress(address: Address?) {
        this.address = address!!
    }

    fun getId(): Long {
        return id
    }

    fun setId(id: Long) {
        this.id = id
    }

    fun setUserName(userName: String?) {
        this.userName = userName!!
    }

    fun getUserName(): String? {
        return userName
    }

    fun setEmail(email: String?) {
        this.email = email!!
    }

    fun getEmail(): String? {
        return email
    }
    fun getUserPhone(): String? {
        return userPhone
    }

    fun setUserPhone(userPhone: String?) {
        this.userPhone = userPhone!!
    }

    override fun equals(o: Any?): Boolean {
        if (o is User) {
            val user = o
            return id == user.id && userFirstName == user.userFirstName && userLastName == user.userLastName && password == user.password && userName == user.userName && userPhone == user.userPhone &&
                    address!!.equals(user.address) && isAdmin == user.isAdmin
        }
        return false
    }

    override fun toString(): String {

        return "User(id=$id, userFirstName='$userFirstName', userLastName='$userLastName', password='$password', userName='$userName', email='$email', userPhone='$userPhone', address=$address, isAdmin=$isAdmin)"
    }

}
