package com.comets.comets.data.remote

import com.comets.comets.data.prefs.AuthTokenPreferences
import com.comets.comets.data.response.AssessmentFromUserRoomResponse
import com.comets.comets.data.response.AssessmentResponse
import com.comets.comets.data.response.BasicResponse
import com.comets.comets.data.response.CommentResponse
import com.comets.comets.data.response.GetPostResponse
import com.comets.comets.data.response.LoginResponse
import com.comets.comets.data.response.RoomDetailUserResponse
import com.comets.comets.data.response.RoomResponse
import com.comets.comets.ui.screen.add_post.AddPostRequest
import com.comets.comets.ui.screen.add_room.CreatePostRequest
import com.comets.comets.ui.screen.classroom_detail.InviteRequest
import com.comets.comets.ui.screen.community_forum.CommentRequest
import com.comets.comets.ui.screen.login.LoginRequest
import com.comets.comets.ui.screen.signup.SignupRequest
import com.comets.comets.ui.viewmodels.assessment.PostAssessmentRequest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface GeneralApiService {

    @GET("user")
    suspend fun getUser()

    @POST("login")
    suspend fun loginUser(
        @Body loginRequest: LoginRequest,
    ): LoginResponse

    @POST("users")
    suspend fun signupUser(
        @Body signupRequest: SignupRequest,
    ): BasicResponse

    @DELETE("logout")
    suspend fun logoutUser()

    @GET("me")
    suspend fun getMe(): BasicResponse

    @GET("Assesments")
    suspend fun getUserAssessments(): List<AssessmentResponse>

    @POST("Posting")
    suspend fun postCommunityForum(@Body addPostRequest: AddPostRequest): BasicResponse

    @GET("Posting")
    suspend fun getCommunityForumPosts(): List<GetPostResponse>

    @GET("Comment/Posting/{uuid}")
    suspend fun getCommunityForumPostCommentById(
        @Path("uuid") uuid: String,
    ): CommentResponse

    @POST("Comment/Posting/{uuid}")
    suspend fun postCommunityForumPostComment(
        @Path("uuid") uuid: String,
        @Body commentRequest: CommentRequest,
    ): BasicResponse

    @POST("Room")
    suspend fun createRoom(
        @Body createPostRequest: CreatePostRequest,
    ): BasicResponse

    @GET("Rooms")
    suspend fun getRooms(): List<RoomResponse>

    @GET("User/Room/{uuid}")
    suspend fun getRoomDetailUser(
        @Path("uuid") uuid: String,
    ): RoomDetailUserResponse

    @POST("Assesment")
    suspend fun postAssessment(
        @Body postAssessmentRequest: PostAssessmentRequest,
    ): BasicResponse

    @POST("Assesment/UserRoom/{userId}")
    suspend fun postAssesmentFromUserRoom(
        @Path("userId") userId: String,
        @Body postAssessmentRequest: PostAssessmentRequest,
    ): BasicResponse

    @GET("Assesment/UserRoom/{roomId}")
    suspend fun getAssessmentFromUserRoom(
        @Path("roomId") roomId: String,
    ): AssessmentFromUserRoomResponse

    @POST("User/Room/{roomId}")
    suspend fun inviteUser(
        @Path("roomId") roomId: String,
        @Body inviteRequest: InviteRequest,
    ): BasicResponse

    companion object {
        private const val BASE_URL = "https://comets-okxscopjda-et.a.run.app/"

        fun create(authTokenPreferences: AuthTokenPreferences): GeneralApiService {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient
                .Builder()
                .addInterceptor(AuthInterceptor(authTokenPreferences))
                .addInterceptor(loggingInterceptor)
                .build()

            return Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GeneralApiService::class.java)
        }
    }
}

class AuthInterceptor(private val authTokenPreferences: AuthTokenPreferences) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val authToken = runBlocking {
            authTokenPreferences.authToken.first()
        }

        val request = chain
            .request()
            .newBuilder()
            .apply {
                authToken?.let {
                    addHeader(
                        "Authorization",
                        "Bearer $it"
                    )
                }
            }
            .build()


        return chain.proceed(request)
    }

}