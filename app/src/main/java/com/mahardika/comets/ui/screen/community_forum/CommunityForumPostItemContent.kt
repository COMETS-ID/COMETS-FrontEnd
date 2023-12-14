package com.mahardika.comets.ui.screen.community_forum

data class CommunityForumPostItemContent(
    val id: Int,
    val name: String,
    val title: String,
    val description: String,
    val commentsAmount: Int,
    val likesAmount: Int,
)
