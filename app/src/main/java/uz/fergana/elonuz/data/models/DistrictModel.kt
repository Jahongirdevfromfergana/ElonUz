package uz.fergana.elonuz.data.models


import com.google.gson.annotations.SerializedName

data class DistrictModel(
    @SerializedName("created_at")
    val createdAt: Any,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name_uz")
    val nameUz: String,
    @SerializedName("region_id")
    val regionId: Int,
    var active: Boolean = false
)