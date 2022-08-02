package com.example.haltura.Sql.Items

import android.os.Parcelable
import com.example.haltura.Models.ProfileSerializable
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
data class MapSerializable(
    @SerializedName("map") var map: Map<String,List<WorkSerializable>>,
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
    @SerializedName("email") var email: String?,
    @SerializedName("username") var username: String?,
    @SerializedName("password") var password: String?
) : Parcelable

@Parcelize
data class UserObject(
    @SerializedName("id") var userId: String,
    @SerializedName("username") var username: String,
    @SerializedName("email") var email: String,
    @SerializedName("token") var token: String,
    @SerializedName("profile") var profile: ProfileSerializable?,
    @SerializedName("business") var business: BusinessSerializable?
) : Parcelable

//@Parcelize
//data class ProfileSerializable(
//    @SerializedName("userId") var userId: String,
//    @SerializedName("firstName") var firstName: String,
//    @SerializedName("lastName") var lastName: String,
//    @SerializedName("phone") var phone: String,
//    @SerializedName("address") var address: String,
//    @SerializedName("chatList") var chatList: List<String>,
//    @SerializedName("workList") var workList: List<String>,
//    @SerializedName("businessList") var businessList: List<String>,
//    @SerializedName("id") var id: String,
//) : Parcelable

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
//    @SerializedName("city") var city: String,
//    @SerializedName("street") var street: String,
//    @SerializedName("streetNum") var streetNum: Int,
//    @SerializedName("floor") var floor: Int,
//    @SerializedName("appartment") var appartment: String,
    @SerializedName("address") var address: String,
    @SerializedName("latitude") var latitude: Double,
    @SerializedName("longitude") var longitude: Double
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

@Parcelize
data class WorksByDateMap(
    @SerializedName ("works_list") val works_list: Map<String,WorksList>
): Parcelable

data class UserResponse(
    val error: Boolean,
    val message: String,
    val code: Int?,
    val meta: String?,
    val data: UserSerializable?
)

@Parcelize
data class RetroUser(val email: String, val password: String) : Parcelable
