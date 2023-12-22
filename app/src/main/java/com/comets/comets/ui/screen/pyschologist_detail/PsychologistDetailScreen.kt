package com.comets.comets.ui.screen.pyschologist_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.comets.comets.R
import com.comets.comets.ui.components.SectionTitle
import com.comets.comets.ui.navigation.Screen
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

@Composable
fun PsychologistDetailScreen(
    id: Int,
    navController: NavHostController,
    viewModel: PsychologistDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    val paymentOptions = listOf(
        "Bank Transfer",
        "DANA",
        "Gopay",
        "Outlet"
    )
    var selectedPaymentOptions by remember {
        mutableStateOf(paymentOptions[0])
    }

    LaunchedEffect(
        Unit
    ) {
        viewModel.getPsychologistByIdFromLocal(
            context,
            id
        )
    }
    uiState.psychologist?.let {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
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
                    .size(120.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))
            Column {
                Text(
                    text = it.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                Text(
                    text = it.description ?: "",
                    textAlign = TextAlign.Justify,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                SectionTitle(text = stringResource(R.string.psychologist_payment_detail))
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "Biaya layanan",
                                style = MaterialTheme.typography.labelMedium
                            )
                            Text(
                                text = "Biaya Admin",
                                style = MaterialTheme.typography.labelMedium
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = stringResource(R.string.psychologist_total),
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "Rp 20,000",
                                textAlign = TextAlign.Right,
                                style = MaterialTheme.typography.labelMedium,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Text(text = NumberFormat
                                .getCurrencyInstance(Locale.getDefault())
                                .apply {
                                    currency = Currency.getInstance("IDR")
                                }
                                .format(it.tariff.toFloat())
                                .toString(),
                                textAlign = TextAlign.Right,
                                style = MaterialTheme.typography.labelMedium,
                                modifier = Modifier.fillMaxWidth())
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(text = NumberFormat
                                .getCurrencyInstance(Locale.getDefault())
                                .apply {
                                    currency = Currency.getInstance("IDR")
                                }
                                .format(
                                    it.tariff.toFloat() + 20000.0
                                )
                                .toString(),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold,
                                style = MaterialTheme.typography.labelLarge,
                                textAlign = TextAlign.Right,
                                modifier = Modifier.fillMaxWidth())
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                SectionTitle(text = stringResource(R.string.psychologist_payment_method))
                Spacer(modifier = Modifier.height(8.dp))
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    paymentOptions.forEachIndexed { index, s ->
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            ),
                            elevation = CardDefaults.cardElevation(4.dp),
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        horizontal = 16.dp,
                                        vertical = 8.dp
                                    )
                            ) {
                                Text(
                                    text = s,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                RadioButton(selected = selectedPaymentOptions == paymentOptions[index],
                                    onClick = {
                                        selectedPaymentOptions = paymentOptions[index]
                                    })
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Total: " + NumberFormat
                    .getCurrencyInstance(Locale.getDefault())
                    .apply {
                        currency = Currency.getInstance("IDR")
                    }
                    .format(
                        it.tariff.toFloat() + 20000.0
                    )
                    .toString())
                Button(onClick = {
                    navController.navigate(Screen.ApplicationContent.Connect.PaymentDetail.route)
                }) {
                    Text(text = "Confirm")
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}