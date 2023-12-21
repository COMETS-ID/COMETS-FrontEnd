package com.comets.comets.data.repository

import com.comets.comets.data.remote.GeneralApiService
import com.comets.comets.data.response.AssessmentFromUserRoomResponse
import com.comets.comets.data.response.BasicResponse
import com.comets.comets.data.response.RoomDetailUserResponse
import com.comets.comets.data.response.RoomResponse
import com.comets.comets.ui.screen.add_room.CreatePostRequest
import com.comets.comets.ui.screen.classroom_detail.InviteRequest
import javax.inject.Inject

class RoomRepository @Inject constructor(private val generalApiService: GeneralApiService) {

    suspend fun createRoom(createPostRequest: CreatePostRequest): Resource<BasicResponse> {
        return try {
            val result = generalApiService.createRoom(createPostRequest)

            Resource.Success(result)
        } catch (e: Exception) {
            Resource.Error(e.message)
        }
    }

    suspend fun getRooms(): Resource<List<RoomResponse>> {
        return try {
            val result = generalApiService.getRooms()

            Resource.Success(result)
        } catch (e: Exception) {
            Resource.Error(e.message)
        }
    }

    suspend fun getRoomDetailUser(uuid: String): Resource<RoomDetailUserResponse> {
        return try {
            val result = generalApiService.getRoomDetailUser(uuid)

            Resource.Success(result)
        } catch (e: Exception) {
            Resource.Error(e.message)
        }
    }

    suspend fun getAssessmentFromUserRoom(uuid: String): Resource<AssessmentFromUserRoomResponse> {
        return try {
            val result = generalApiService.getAssessmentFromUserRoom(uuid)

            Resource.Success(result)
        } catch (e: Exception) {
            Resource.Error(e.message)
        }
    }

    suspend fun inviteUser(
        uuid: String,
        inviteRequest: InviteRequest,
    ): Resource<BasicResponse> {
        return try {
            val result = generalApiService.inviteUser(
                uuid,
                inviteRequest
            )

            Resource.Success(result)
        } catch (e: Exception) {
            Resource.Error(e.message)
        }
    }
}