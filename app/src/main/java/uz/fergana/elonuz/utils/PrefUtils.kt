package uz.devapp.elonuz.utils

import com.orhanobut.hawk.Hawk
import uz.devapp.elonuz.MyApp
import uz.fergana.elonuz.data.models.AdsModel
import uz.fergana.elonuz.data.models.CategoryModel
import uz.fergana.elonuz.data.models.RegionModel

object PrefUtils {
    const val PREF_TOKEN = "token"
    const val PREF_CATEGORIES = "categories"
    const val PREF_REGIONS = "regions"
    const val PREF_FAVORITE = "favorite"

    fun init() {
        Hawk.init(MyApp.app).build()
    }

    fun setToken(value: String?) {
        Hawk.put(PREF_TOKEN, value)
    }

    fun getToken(): String {
        return Hawk.get(PREF_TOKEN, "")
    }

    fun setCategories(value: List<CategoryModel>) {
        Hawk.put(PREF_CATEGORIES, value)
    }

    fun getCategories(): List<CategoryModel> {
        return Hawk.get(PREF_CATEGORIES, listOf())
    }

    fun setRegions(value: List<RegionModel>) {
        Hawk.put(PREF_REGIONS, value)
    }

    fun getRegions(): List<RegionModel> {
        return Hawk.get(PREF_REGIONS, listOf())
    }

    fun setFavorites(item: AdsModel) {
        val items = Hawk.get(PREF_FAVORITE, arrayListOf<Int>())

        if (items.filter { it == item.id }.firstOrNull() != null) {
            items.remove(item.id)
        } else {
            items.add(item.id)
        }

        Hawk.put(PREF_FAVORITE, items)
    }

    fun getFavoriteList():ArrayList<Int>{
        return Hawk.get(PREF_FAVORITE, arrayListOf())
    }

    fun checkFavorite(item: AdsModel):Boolean{
        val items = Hawk.get(PREF_FAVORITE, arrayListOf<Int>())
        return items.filter { it == item.id }.firstOrNull() != null
    }

    fun clear() {
        Hawk.deleteAll()
    }
}