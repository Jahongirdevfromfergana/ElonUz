package uz.fergana.elonuz.data.models


import com.google.gson.annotations.SerializedName

data class District(
    @SerializedName("district_name")
    val districtName: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("region_id")
    val regionId: Int
):java.io.Serializable