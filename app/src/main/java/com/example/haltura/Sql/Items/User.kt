package com.example.haltura.Sql.Items

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.descriptors.StructureKind



@Parcelize
data class ExtendedChatSerializable(
    @SerializedName("id") var id: String? = null,
    @SerializedName("adminID") var adminID: String,
    @SerializedName("chatName") var chatName: String?,
    @SerializedName("chatImage") var chatImage: String?,
    @SerializedName("members") var members: List<String>,
    @SerializedName("messages") var messages: String,
    @SerializedName("mapUsernames") var mapUsernames: Map<String,String>,
    @SerializedName("lastMessage") var lastMessage: MessageSerializable,
) : Parcelable


@Parcelize
data class MessageSerializable(
    @SerializedName("userId") var userId: String,
    @SerializedName("text") var text: String?,
    @SerializedName("image") var image: String?,
    @SerializedName("createdAt") var time: String? = null
) : Parcelable

@Parcelize
data class ChatSerializable(
    @SerializedName("id") var id: String? = null,
    @SerializedName("adminID") var adminID: String,
    @SerializedName("chatName") var chatName: String?,
    @SerializedName("chatImage") var chatImage: String?,
    @SerializedName("members") var members: List<String>,
    @SerializedName("messages") var messages: String,
    @SerializedName("mapUsernames") var mapUsernames: Map<String,String>,//added
    @SerializedName("lastMessage") var lastMessage: MessageSerializable,//added
) : Parcelable

@Parcelize
data class UserSerializable(
    @SerializedName("email") var email: String,
    @SerializedName("createdDate") var createdDate: String?,
    @SerializedName("id") var id: String,
    @SerializedName("token") var token: String,
    @SerializedName("password") var password: String?
) : Parcelable

@Parcelize
data class UserLoginSerializable(
    @SerializedName("email") var email: String,
    @SerializedName("password") var password: String
) : Parcelable

@Parcelize
data class UserObject(
    @SerializedName("id") var userId: String,
    @SerializedName("email") var email: String,
    @SerializedName("token") var token: String,
    @SerializedName("profile") var profile: ProfileSerializable?,
    @SerializedName("business") var business: BusinessSerializable?
) : Parcelable

@Parcelize
data class ProfileSerializable(
    @SerializedName("userId") var userId: String,
    @SerializedName("firstName") var firstName: String,
    @SerializedName("lastName") var lastName: String,
    @SerializedName("phone") var phone: String,
    @SerializedName("address") var address: String,
    @SerializedName("chatList") var chatList: List<String>,
    @SerializedName("workList") var workList: List<String>,
    @SerializedName("businessList") var businessList: List<String>,
    @SerializedName("id") var id: String,
) : Parcelable

@Parcelize
data class BusinessSerializable(
    @SerializedName("listOfWork") var listOfWork: List<String>?,
    @SerializedName("userId") var userId: String,
    @SerializedName("name") var name: String,
    @SerializedName("about") var about: String,
    @SerializedName("image") var image: String,
    @SerializedName("id") var id: String,
) : Parcelable

@Parcelize
data class WorkSerializable(
    @SerializedName("publisher") var publisher: String,
    @SerializedName("company") var company: String,
    @SerializedName("task") var task: String,
    @SerializedName("salary") var salary: Int,
    @SerializedName("numberOfWorkers") var numberOfWorkers: Int,
    @SerializedName("address") var address: AddresSerializable,
    @SerializedName("info") var info: String,
    @SerializedName("startTime") var startTime: String,
    @SerializedName("endTime") var endTime: String,
    @SerializedName("image") var image: String,
    @SerializedName("id") var id: String? = null,
) : Parcelable

@Parcelize
data class AddresSerializable(
    @SerializedName("city") var city: String,
    @SerializedName("street") var street: String,
    @SerializedName("streetNum") var streetNum: Int,
    @SerializedName("floor") var floor: Int,
    @SerializedName("appartment") var appartment: String,
) : Parcelable

@Parcelize
data class BusinessesList(
    @SerializedName("business_list")
    val business_list: List<BusinessSerializable>
) : Parcelable

@Parcelize
data class WorksList(
    @SerializedName("work_list")
    val work_list: List<WorkSerializable>
) : Parcelable


data class UserResponse(
    val error: Boolean,
    val message: String,
    val code: Int?,
    val meta: String?,
    val data: User?
)

@Parcelize
data class RetroUser(val email: String, val password: String) : Parcelable


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