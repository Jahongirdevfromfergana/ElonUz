package uz.fergana.elonuz.data.models


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CategoryModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("parent_id")
    val parentId: Int,
    @SerializedName("title")
    val title: String
): Serializable