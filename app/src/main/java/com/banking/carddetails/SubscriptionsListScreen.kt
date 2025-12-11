package com.banking.carddetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.banking.carddetails.data.MockSubscriptionData
import com.banking.carddetails.models.Subscription
import com.banking.carddetails.models.SubscriptionCategory
import com.banking.carddetails.viewmodel.SubscriptionViewModel

@Composable
fun SubscriptionsListScreen(
    onBack: () -> Unit = {},
    onSubscriptionClick: (String) -> Unit = {},
    onAddSubscription: () -> Unit = {},
    viewModel: SubscriptionViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val upcomingSubscriptions = viewModel.getUpcomingSubscriptions()
    val activeSubscriptions = viewModel.getActiveSubscriptions()
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F7))
    ) {
        // Show loading indicator if loading
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color(0xFF00A896))
            }
            return
        }

        // Show error message if error occurred
        if (uiState.error != null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Error loading subscriptions",
                        color = Color.Red,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { viewModel.loadSubscriptions() }) {
                        Text("Retry")
                    }
                }
            }
            return
        }
        // Top bar
        Surface(
            modifier = Modifier.fillMaxWidth().statusBarsPadding(),
            color = Color(0xFFF5F5F7)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = "Back",
                        tint = Color(0xFF1C1C1E)
                    )
                }
                Text(
                    text = "Subscription list",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f),
                    color = Color(0xFF1C1C1E)
                )
                Box(

                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF00C896)).clickable{
                            onAddSubscription()
                        }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Add,
                        contentDescription = "Add subscription",
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            // Search bar
            item {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    shape = RoundedCornerShape(12.dp),
                    color = Color.White
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = "Search",
                            tint = Color(0xFF8E8E93),
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Search subscription",
                            color = Color(0xFF8E8E93),
                            fontSize = 16.sp
                        )
                    }
                }
            }

            // Upcoming section
            if (upcomingSubscriptions.isNotEmpty()) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Upcoming renewals",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF1C1C1E)
                        )
                        Text(
                            text = "-${viewModel.getTotalUpcoming().toInt()} MDL",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF1C1C1E)
                        )
                    }
                }

                items(upcomingSubscriptions) { subscription ->
                    SubscriptionListItem(
                        subscription = subscription,
                        onClick = { onSubscriptionClick(subscription.id) }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            // Active subscriptions section
            if (activeSubscriptions.isNotEmpty()) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Subscriptions",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF1C1C1E)
                        )
                        Text(
                            text = "-${viewModel.getTotalActive().toInt()} MDL",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF1C1C1E)
                        )
                    }
                }

                items(activeSubscriptions) { subscription ->
                    SubscriptionListItem(
                        subscription = subscription,
                        onClick = { onSubscriptionClick(subscription.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun SubscriptionListItem(
    subscription: Subscription,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        color = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Logo from drawable resources
            val iconRes = getSubscriptionIcon(subscription.name)
            if (iconRes != null) {
                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = subscription.name,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Fit
                )
            } else {
                // Fallback to colored circle with letter
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(getSubscriptionColor(subscription.name)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = subscription.name.first().toString(),
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = subscription.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF1C1C1E)
                )
                Text(
                    text = "${subscription.frequency}, next on ${subscription.nextPaymentDate}",
                    fontSize = 14.sp,
                    color = Color(0xFF8E8E93)
                )
            }

            Text(
                text = "${subscription.amount.toInt()}.00 ${subscription.currency}",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1C1C1E)
            )
        }
    }
}

@Composable
private fun getSubscriptionIcon(name: String): Int? {
    return when (name.lowercase()) {
        "amazon" -> R.drawable.amazon
        "youtube" -> R.drawable.youtube_logo
        "netflix" -> R.drawable.netflix
        "spotify" -> R.drawable.spotify
        "capcut" -> R.drawable.cupcut
        "icloud" -> R.drawable.icloud
        else -> null
    }
}

@Composable
private fun getSubscriptionColor(name: String): Color {
    return when (name.lowercase()) {
        "amazon" -> Color(0xFFFF9900)
        "youtube" -> Color(0xFFFF0000)
        "netflix" -> Color(0xFFE50914)
        "spotify" -> Color(0xFF1DB954)
        "capcut" -> Color(0xFF000000)
        "icloud" -> Color(0xFF0071E3)
        "canva pro" -> Color(0xFF00C4CC)
        else -> Color(0xFF6366F1)
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSubscriptionsList() {
    SubscriptionsListScreen()
}
