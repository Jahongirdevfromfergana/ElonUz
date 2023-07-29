package uz.fergana.elonuz.data.models


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AdsModel(
    @SerializedName("address")
    val address: String,
    @SerializedName("category_id")
    val categoryId: Int,
    @SerializedName("comment")
    val comment: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("district")
    val district: District,
    @SerializedName("district_id")
    val districtId: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("images")
    val images: List<String>,
    @SerializedName("main_image")
    val mainImage: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("region")
    val region: Region,
    @SerializedName("region_id")
    val regionId: Int,
    @SerializedName("review_count")
    val reviewCount: Int,
    @SerializedName("date")
    val date: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("updated_at")
    val updatedAt: Any,
    @SerializedName("user_id")
    val userId: Int
) : Serializable