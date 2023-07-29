package uz.fergana.elonuz.data.models


import com.google.gson.annotations.SerializedName

data class Region(
    @SerializedName("id")
    val id: Int,
    @SerializedName("region_name")
    val regionName: String
):java.io.Serializable