package uz.fergana.elonuz.data.models.request


import com.google.gson.annotations.SerializedName

data class RegistrationRequest(
    @SerializedName("fullname")
    val fullname: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("phone")
    val phone: String
)