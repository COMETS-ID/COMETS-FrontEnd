package com.comets.comets.data.repository

import com.comets.comets.data.remote.GeneralApiService
import com.comets.comets.data.response.BasicResponse
import com.comets.comets.data.response.CommentResponse
import com.comets.comets.data.response.GetPostResponse
import com.comets.comets.ui.screen.add_post.AddPostRequest
import com.comets.comets.ui.screen.community_forum.CommentRequest
import javax.inject.Inject

class CommunityForumRepository @Inject constructor(
    private val generalApiService: GeneralApiService,
) {
    suspend fun getCommunityForumPosts(): Resource<List<GetPostResponse>> {
        return try {
            val result = generalApiService.getCommunityForumPosts()
            Resource.Success(result)
        } catch (e: Exception) {
            Resource.Error(e.message)
        }
    }

    suspend fun postCommunityForum(addPostRequest: AddPostRequest): Resource<BasicResponse> {
        return try {
            val result = generalApiService.postCommunityForum(addPostRequest)
            Resource.Success(result)
        } catch (e: Exception) {
            Resource.Error(e.message)
        }
    }

    suspend fun getCommunityForumPostCommentById(uuid: String): Resource<CommentResponse> {
        return try {
            val result = generalApiService.getCommunityForumPostCommentById(uuid)

            Resource.Success(result)
        } catch (e: Exception) {
            Resource.Error(e.message)
        }
    }

    suspend fun postCommunityForumPostComment(
        uuid: String,
        commentRequest: CommentRequest,
    ): Resource<BasicResponse> {
        return try {
            val result = generalApiService.postCommunityForumPostComment(
                uuid,
                commentRequest
            )

            Resource.Success(result)
        } catch (e: Exception) {
            Resource.Error(e.message)
        }
    }
}

data class CommunityForumPostCommentItemContent(
    val commenter: String,
    val comment: String,
)
