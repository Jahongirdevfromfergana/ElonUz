package uz.fergana.elonuz.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import uz.fergana.elonuz.data.api.NetworkingObject
import uz.fergana.elonuz.data.models.AdsModel
import uz.fergana.elonuz.data.models.CategoryModel
import uz.fergana.elonuz.data.models.RegionModel
import uz.fergana.elonuz.data.models.UserModel
import uz.fergana.elonuz.data.models.request.AdsFilter
import uz.fergana.elonuz.data.models.request.LoginRequest
import uz.fergana.elonuz.data.models.request.RegistrationRequest
import uz.fergana.elonuz.data.models.response.AuthResponse
import uz.fergana.elonuz.data.repository.BaseRepository
import uz.fergana.elonuz.data.repository.sealed.DataResult
import java.io.File

class UserRepository : BaseRepository() {
    val api = NetworkingObject.getClientInstance()

    suspend fun getCategories() = withContext(Dispatchers.IO) {
        try {
            val response = api.getCategories()
            return@withContext wrapResponse(response)
        } catch (e: Exception) {
            return@withContext DataResult.Error<List<CategoryModel>>(e.localizedMessage)
        }
    }

    suspend fun getAds(filter: AdsFilter) = withContext(Dispatchers.IO) {
        try {
            val response = api.getAds(filter)
            return@withContext wrapResponse(response)
        } catch (e: Exception) {
            return@withContext DataResult.Error<List<AdsModel>>(e.localizedMessage)
        }
    }

    suspend fun getRegions() = withContext(Dispatchers.IO) {
        try {
            val response = api.getRegions()
            return@withContext wrapResponse(response)
        } catch (e: Exception) {
            return@withContext DataResult.Error<List<RegionModel>>(e.localizedMessage)
        }
    }

    suspend fun registration(request: RegistrationRequest) = withContext(Dispatchers.IO) {
        try {
            val response = api.registration(request)
            return@withContext wrapResponse(response)
        } catch (e: Exception) {
            return@withContext DataResult.Error<AuthResponse>(e.localizedMessage)
        }
    }

    suspend fun login(request: LoginRequest) = withContext(Dispatchers.IO) {
        try {
            val response = api.login(request)
            return@withContext wrapResponse(response)
        } catch (e: Exception) {
            return@withContext DataResult.Error<AuthResponse>(e.localizedMessage)
        }
    }

    suspend fun addAds(
        mainImage: String,
        title: String,
        comment: String,
        address: String,
        phone: String,
        price: Double,
        categoryId: Int,
        districtId: Int
    ) = withContext(Dispatchers.IO) {
        try {
            val file = File(mainImage)

            val requestFile=RequestBody.create(MediaType.parse("multipart/form-data"),file)

            val body=MultipartBody.Part.createFormData("main_image",file.name,requestFile)

            val titleBody: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                title
            )

            val commentBody: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                comment
            )

            val addressBody: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                address
            )

            val phoneBody: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                phone
            )

            val priceBody: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                price.toString()
            )

            val categoryIdBody: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                categoryId.toString()
            )

            val districtIdBody: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                districtId.toString()
            )
            val response = api.addAds(body,categoryIdBody,districtIdBody,districtIdBody,titleBody,commentBody,priceBody,addressBody,phoneBody)
            return@withContext wrapResponse(response)
        } catch (e: Exception) {
            return@withContext DataResult.Error<Boolean>(e.localizedMessage)
        }
    }

    suspend fun getUser() = withContext(Dispatchers.IO) {
        try {
            val response = api.getUser()
            return@withContext wrapResponse(response)
        } catch (e: Exception) {
            return@withContext DataResult.Error<UserModel>(e.localizedMessage)
        }
    }
}