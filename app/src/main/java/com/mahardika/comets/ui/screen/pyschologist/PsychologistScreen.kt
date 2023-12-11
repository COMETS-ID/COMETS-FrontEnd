package com.mahardika.comets.ui.screen.pyschologist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mahardika.comets.ui.commons.PsychologistItemContent

@Composable
fun PsychologistScreen(){
    val psychologistList = listOf(
        PsychologistItemContent(
            name = "Dr. Smith",
            specialization = "Clinical Psychologist",
            tariff = "Rp 80,000",
            image = null
        ),
        PsychologistItemContent(
            name = "Dr. Johnson",
            specialization = "Counseling Psychologist",
            tariff = "Rp 60,000",
            image = null
        ),
        PsychologistItemContent(
            name = "Dr. Williams",
            specialization = "Child Psychologist",
            tariff = "Rp 90,000",
            image = null
        ),
        PsychologistItemContent(
            name = "Dr. Davis",
            specialization = "Sports Psychologist",
            tariff = "Rp 70,000",
            image = null
        ),
        PsychologistItemContent(
            name = "Dr. Brown",
            specialization = "Educational Psychologist",
            tariff = "Rp 50,000",
            image = null
        )
    )
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        psychologistList.forEach { psychologist ->
            PsychologistItem(psychologist = psychologist)
        }
    }
}

@Composable
fun PsychologistItem(
    psychologist: PsychologistItemContent
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
        ,
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = psychologist.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = psychologist.specialization ?: "",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                AsyncImage(
                    model = ImageRequest
                        .Builder(LocalContext.current)
                        .data("https://source.unsplash.com/random?person")
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(64.dp)
                )
//                psychologist.image?.let {
//                    Image(
//                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
//                        contentDescription = null,
//                        modifier = Modifier
//                            .size(64.dp)
//                            .clip(RoundedCornerShape(8.dp)),
//                        contentScale = ContentScale.Crop
//                    )
//                }
            }
            Text(
                text = psychologist.tariff,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}