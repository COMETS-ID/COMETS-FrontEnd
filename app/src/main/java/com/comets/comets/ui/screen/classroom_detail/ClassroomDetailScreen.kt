package com.comets.comets.ui.screen.classroom_detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.comets.comets.data.response.AssesmentItem
import com.comets.comets.data.response.UserRoomItem
import com.comets.comets.ui.navigation.Screen
import com.comets.comets.ui.screen.prediction_result.interpretPrediction
import com.comets.comets.utils.showToast
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ClassroomDetailScreen(
    userRoomUuid: String,
    uuid: String,
    roleRoom: String,
    viewModel: ClassroomDetailViewModel = hiltViewModel(),
    applicationContentNavController: NavHostController,
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(
        Unit
    ) {
        viewModel.getAssessmentFromUserRoom(userRoomUuid)
        viewModel.getRoomDetailUser(uuid)
    }

    val moodImageList = mutableListOf<AssesmentItem>()
    val moodFormList = mutableListOf<AssesmentItem>()

    uiState.assessments.groupBy { it.createdAt }.values.forEach { item ->
        if (item.size > 1) {
            val moodImageAssessment = item.find { it.type == "Gambar" }
            val moodFormAssessment = item.find { it.type == "Form" }

            moodImageAssessment?.let { moodImageList.add(it) }
            moodFormAssessment?.let { moodFormList.add(it) }
        }
    }

    val pagerState = rememberPagerState(initialPage = 0,
        pageCount = { 3 })
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            Spacer(modifier = Modifier.height(16.dp))
            HeaderSection(
                teacher = uiState.roomDetail?.user?.username ?: "",
                title = uiState.roomDetail?.name ?: "",
                participants = "${uiState.users.size}/${uiState.roomDetail?.capacity}"
            )
            Spacer(modifier = Modifier.height(16.dp))
            NavigationSection(
                pagerState,
                roleRoom == "admin"
            )
            HorizontalPager(state = pagerState) {
                when (it) {
                    0 -> AssessmentsSection(
                        moodImageList.reversed(),
                        moodFormList.reversed()
                    )

                    1 -> ParticipantsSection(uiState.users)
                    2 -> InviteSection(onInviteClick = { id ->
                        viewModel.inviteUser(uuid,
                            id,
                            onSuccess = { msg ->
                                showToast(
                                    context,
                                    msg
                                )
                                viewModel.getAssessmentFromUserRoom(userRoomUuid)
                                viewModel.getRoomDetailUser(uuid)
                            },
                            onError = { msg ->
                                showToast(
                                    context,
                                    msg
                                )
                            })
                    })
                }
            }
        }
        FloatingActionButton(
            onClick = {
                applicationContentNavController.navigate(
                    "${Screen.ApplicationContent.Camera.route}/$userRoomUuid"
                ) {
                    launchSingleTop = true
                }
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null
            )
        }
    }
}

@Composable
fun HeaderSection(
    teacher: String,
    title: String,
    participants: String,
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = teacher,
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = title,
                style = MaterialTheme.typography.displayLarge
            )
            Text(
                text = "$participants participants",
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NavigationSection(
    pagerState: PagerState,
    isAdmin: Boolean = false,
) {
    val coroutineScope = rememberCoroutineScope()
    val navigationList = mutableListOf(
        "Assessment",
        "Participant",
    )
    if (isAdmin) {
        navigationList.add("Invite")
    }
    Column() {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            itemsIndexed(navigationList) { index, item ->
                Text(text = item,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (pagerState.currentPage == index) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onSurface,
                    fontWeight = if (pagerState.currentPage == index) FontWeight.Bold else FontWeight.Normal,
                    modifier = Modifier.clickable {
                        coroutineScope.launch {
                            pagerState.scrollToPage(index)
                        }
                    })
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun AssessmentsSection(
    moodsImage: List<AssesmentItem>,
    moodsForm: List<AssesmentItem>,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column() {
            moodsImage.forEachIndexed { index, item ->
                val dateFormat = SimpleDateFormat(
                    "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                    Locale.getDefault()
                )
                val date = dateFormat.parse(item.createdAt)

                val formatter = SimpleDateFormat(
                    "EEEE, MMM d'th' yyyy",
                    Locale.getDefault()
                )
                val formattedDate = date?.let { value -> formatter.format(value) }
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {

                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = formattedDate.toString(),
                            style = MaterialTheme.typography.labelMedium
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = interpretPrediction(item.value).moodSelectedIcon),
                                contentDescription = null,
                                tint = interpretPrediction(item.value).moodColor,
                                modifier = Modifier.size(48.dp)
                            )
                            Text(
                                text = interpretPrediction(item.value).moodName,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                        Text(
                            text = "Score: ${moodsForm[index].value.split("-")[0]}/50",
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        if (moodsForm.isEmpty() && moodsImage.isEmpty()) {
            Text(
                text = "No assessment",
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
fun ParticipantsSection(
    users: List<UserRoomItem>,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(users) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null
                )
                Text(text = it.user.username)
            }
        }
    }
}

@Composable
fun InviteSection(
    onInviteClick: (id: String) -> Unit,
) {
    var id by remember {
        mutableStateOf(TextFieldValue())
    }
    Column {
        Text(
            text = "Invite someone to join this room",
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = id,
            onValueChange = {
                id = it
            },
            label = {
                Text(text = "User ID")
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                onInviteClick(id.text)
                id = TextFieldValue("")
            },
            shape = RoundedCornerShape(16.dp),
            enabled = id.text.isNotBlank()
        ) {
            Text(
                text = "Send Invite",
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}