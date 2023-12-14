package com.mahardika.comets.data.repository

import com.mahardika.comets.data.remote.MoodRecognitionApiService
import com.mahardika.comets.data.response.FaceRecognitionPostResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MoodRecognitionRepository @Inject constructor(
    private val apiService: MoodRecognitionApiService,
) {

    suspend fun postFaceCapture(image: MultipartBody.Part): Flow<Resource<FaceRecognitionPostResponse>> {
        return flow {
            try {
                emit(Resource.Loading(true))
                val uploadImageResponse = apiService.postFaceCapture(
                    image = image,
                )

                emit(Resource.Success(data = uploadImageResponse))
                emit(Resource.Loading(false))
            } catch (e: IOException) {
                emit(Resource.Error(message = "Couldn't upload image."))
            } catch (e: HttpException) {
                emit(Resource.Error(message = "${e.message}"))
            }
        }
    }
}
