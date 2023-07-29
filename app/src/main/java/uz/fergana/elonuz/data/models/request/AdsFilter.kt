package uz.fergana.elonuz.data.models.request

data class AdsFilter(
    val region_id: Int = 0,
    val district_id: Int = 0,
    val category_id: Int = 0,
    val limit: Int = 0,
    val keyword: String = ""
)