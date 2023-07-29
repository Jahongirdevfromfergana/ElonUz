package uz.fergana.elonuz.data.repository

import retrofit2.Response
import uz.fergana.elonuz.data.models.response.BaseResponse
import uz.fergana.elonuz.data.repository.sealed.DataResult

open class BaseRepository {
    fun <T> wrapResponse(response: Response<BaseResponse<T>>): DataResult<T> {
        return if (response.isSuccessful) {
            if (response.body()?.success == true) {
                DataResult.Success(response.body()!!.data!!)
            } else {
                if (response.body()?.errorCode == 1) {
                    //
                }
                DataResult.Error(response.body()?.message ?: "")
            }
        } else {
            DataResult.Error(response.message())
        }
    }
}