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
//    private val simulatedData = listOf(
//        CommunityForumPostItemContent(
//            id = 1,
//            name = "Alice Johnson",
//            title = "Exploring Nature",
//            description = "Took a hike today and enjoyed the beauty of nature. Here are some snapshots!",
//            commentsAmount = 8,
//            likesAmount = 20
//        ),
//        CommunityForumPostItemContent(
//            id = 2,
//            name = "Bob Anderson",
//            title = "Tech Talk",
//            description = "Just attended a fascinating tech conference. Exciting developments in the industry!",
//            commentsAmount = 12,
//            likesAmount = 15
//        ),
//        CommunityForumPostItemContent(
//            id = 3,
//            name = "Eva Carter",
//            title = "Book Recommendation",
//            description = "Finished an amazing book! Highly recommend 'The Silent Stars' for sci-fi lovers.",
//            commentsAmount = 5,
//            likesAmount = 25
//        ),
//        CommunityForumPostItemContent(
//            id = 4,
//            name = "Charlie Davis",
//            title = "Fitness Journey",
//            description = "Started my fitness journey today. Feeling motivated and ready to make positive changes!",
//            commentsAmount = 18,
//            likesAmount = 30
//        ),
//        CommunityForumPostItemContent(
//            id = 5,
//            name = "Grace Miller",
//            title = "Cooking Adventures",
//            description = "Experimented with a new recipe in the kitchen. The results were delicious!",
//            commentsAmount = 10,
//            likesAmount = 22
//        )
//    )

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


//    suspend fun getCommunityForumPosts(): Resource<List<CommunityForumPostItemContent>> {
//        return try {
//            Resource.Success(simulatedData)
//        } catch (e: Exception) {
//            Resource.Error(e.message)
//        }
//    }

//    suspend fun getCommunityForumPostCommentsById(postId: Int): Resource<List<CommunityForumPostCommentItemContent>> {
//        return try {
//            Resource.Success(simulatedCommentsData.find { it.postId == postId }?.comments.orEmpty())
//        } catch (e: Exception) {
//            Resource.Error(e.message)
//        }
//    }

    private fun generateRealisticComments(
        postId: Int,
        commentsAmount: Int,
        commenterNames: List<String>,
    ): List<CommunityForumPostCommentItemContent> {
        val comments = mutableListOf<CommunityForumPostCommentItemContent>()
        for (i in 1..commentsAmount) {
            val randomCommenterName = commenterNames.random()
            val commentText = generateRandomComment(randomCommenterName)
            comments.add(
                CommunityForumPostCommentItemContent(
                    commenter = randomCommenterName,
                    comment = commentText
                )
            )
        }
        return comments
    }

    private fun generateRandomComment(commenterName: String): String {
        val commentOptions = listOf(
            "Great post, $commenterName!",
            "I totally agree!",
            "Interesting insights!",
            "Thanks for sharing, $commenterName!",
            "I had a similar experience.",
            "Well done!",
            "Looking forward to more posts!"
        )
        return commentOptions.random()
    }

    private val commenterNames = listOf(
        "John",
        "Emma",
        "David",
        "Sophia",
        "Daniel",
        "Olivia",
        "Michael",
        "Ava",
        "Christopher",
        "Emily"
    )

//    private val simulatedCommentsData = simulatedData.map { post ->
//        CommunityForumPostComments(
//            postId = post.id,
//            comments = generateRealisticComments(
//                post.id,
//                post.commentsAmount,
//                commenterNames
//            )
//        )
//    }

}

data class CommunityForumPostCommentItemContent(
    val commenter: String,
    val comment: String,
)

data class CommunityForumPostComments(
    val postId: Int,
    val comments: List<CommunityForumPostCommentItemContent>,
)