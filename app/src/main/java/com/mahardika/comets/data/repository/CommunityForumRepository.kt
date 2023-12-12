package com.mahardika.comets.data.repository

import com.mahardika.comets.data.remote.ApiService
import com.mahardika.comets.ui.screen.community_forum.CommunityForumPostItemContent
import javax.inject.Inject

class CommunityForumRepository @Inject constructor(
    private val apiService: ApiService
){
    private val simulatedData = listOf(
        CommunityForumPostItemContent(
            name = "Alice Johnson",
            title = "Exploring Nature",
            description = "Took a hike today and enjoyed the beauty of nature. Here are some snapshots!",
            commentsAmount = 8,
            likesAmount = 20
        ),
        CommunityForumPostItemContent(
            name = "Bob Anderson",
            title = "Tech Talk",
            description = "Just attended a fascinating tech conference. Exciting developments in the industry!",
            commentsAmount = 12,
            likesAmount = 15
        ),
        CommunityForumPostItemContent(
            name = "Eva Carter",
            title = "Book Recommendation",
            description = "Finished an amazing book! Highly recommend 'The Silent Stars' for sci-fi lovers.",
            commentsAmount = 5,
            likesAmount = 25
        ),
        CommunityForumPostItemContent(
            name = "Charlie Davis",
            title = "Fitness Journey",
            description = "Started my fitness journey today. Feeling motivated and ready to make positive changes!",
            commentsAmount = 18,
            likesAmount = 30
        ),
        CommunityForumPostItemContent(
            name = "Grace Miller",
            title = "Cooking Adventures",
            description = "Experimented with a new recipe in the kitchen. The results were delicious!",
            commentsAmount = 10,
            likesAmount = 22
        )
    )

    suspend fun getCommunityForumPosts(): Resource<List<CommunityForumPostItemContent>> {
        return try {
            Resource.Success(simulatedData)
        } catch (e: Exception) {
            Resource.Error(e.message)
        }
    }
}