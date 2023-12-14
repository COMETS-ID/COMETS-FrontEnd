package com.mahardika.comets.ui.screen.community_forum

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Comment
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityForumScreen(
    communityForumViewModel: CommunityForumViewModel = hiltViewModel(),
) {
    val communityForumUiState by communityForumViewModel.communityForumUiState.collectAsState()
    LaunchedEffect(Unit) {
        communityForumViewModel.getCommunityForumPosts()
    }
    Log.d(
        "comments",
        communityForumUiState.comments.toString()
    )

    val bottomSheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (communityForumUiState.errorMessage != null) {
            Log.d(
                "CommunityForumScreen",
                communityForumUiState.errorMessage!!
            )
        } else if (communityForumUiState.isLoading) {
            Log.d(
                "CommunityForumScreen",
                communityForumUiState.isLoading.toString()
            )
        } else {
            LazyColumn {
                items(communityForumUiState.posts) {
                    PostItem(
                        item = it,
                        onCommentClick = {
                            showBottomSheet = true
                            communityForumViewModel.getPostCommentsById(it.id)
                        })
                }
            }
        }
    }
    if (showBottomSheet) {
        ModalBottomSheet(
            sheetState = bottomSheetState,
            onDismissRequest = {
                showBottomSheet = false
            },
            modifier = Modifier.fillMaxHeight(0.5f)
        ) {
            LazyColumn(
                contentPadding = PaddingValues(16.dp)
            ) {
                items(communityForumUiState.comments) {
                    Column {
                        Text(
                            text = it.commenter,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(text = it.comment)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun PostItem(
    item: CommunityForumPostItemContent,
    onCommentClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = item.name,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = item.description,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ThumbUp,
                        contentDescription = null
                    )
                    Text(
                        text = item.likesAmount.toString() + " Likes",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Light
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.clickable {
                        onCommentClick()
                    }) {
                    Icon(
                        imageVector = Icons.Outlined.Comment,
                        contentDescription = null
                    )
                    Text(
                        text = item.commentsAmount.toString() + " Comments",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Light
                    )
                }
            }
        }
    }
}