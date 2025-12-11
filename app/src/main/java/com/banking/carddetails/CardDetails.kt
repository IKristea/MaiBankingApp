package com.banking.carddetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Receipt
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.SwapHoriz
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.banking.carddetails.data.MockSubscriptionData
import com.banking.carddetails.models.SubscriptionCategory
import com.banking.carddetails.viewmodel.SubscriptionViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun CardDetailsScreen(
    onBack: () -> Unit = {},
    onEdit: () -> Unit = {},
    onSubscriptions: () -> Unit = {},
    viewModel: SubscriptionViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    // Light blue background to match new design
    val background = Color(0xFFD4E8EE)
    val cardColor = Color(0xFF00A896)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
    ) {
        // Top bar
        Surface(
            modifier = Modifier.fillMaxWidth().statusBarsPadding(),
            color = background
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = "Back",
                        tint = Color(0xFF1C1C1E)
                    )
                }

                Row {
                    IconButton(onClick = onEdit) {
                        Icon(
                            imageVector = Icons.Outlined.Add,
                            contentDescription = "Add",
                            tint = Color(0xFF1C1C1E),
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    IconButton(onClick = onEdit) {
                        Icon(
                            imageVector = Icons.Outlined.Add,
                            contentDescription = "Add",
                            tint = Color(0xFF1C1C1E),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }

        // Scrollable content
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Account name and balance
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Cont gama universal",
                        fontSize = 16.sp,
                        color = Color(0xFF1C1C1E),
                    )
                    Icon(
                        painter = painterResource(R.drawable.edit_wrapper),
                        contentDescription = "Edit",
                        tint = Color(0xFF1C1C1E),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            item {
                Text(
                    text = "15 000.00 MDL",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1C1C1E)
                )
            }

            item {
                Box(
                    modifier = Modifier
                        .height(120.dp)
                ) {
                    // Background image
                    Image(
                        painter = painterResource(R.drawable.card_icon),
                        contentDescription = "Card background",
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.FillWidth
                    )
                }
            }

            // Action buttons
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ActionButton(
                        icon = Icons.Outlined.Receipt,
                        label = "Payments"
                    )
                    ActionButton(
                        icon = Icons.Outlined.SwapHoriz,
                        label = "Transfers"
                    )
                }
            }

            // Information section
            item {
                Text(
                    text = "Information",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF1C1C1E)
                )
            }

            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White
                ) {
                    Column {
                        MenuListItem(
                            icon = Icons.Outlined.History,
                            title = "Transaction history",
                            onClick = {}
                        )
                        Divider(color = Color(0xFFE5E5EA))
                        MenuListItem(
                            icon = Icons.Outlined.Receipt,
                            title = "Account statement",
                            onClick = {}
                        )
                        Divider(color = Color(0xFFE5E5EA))
                        MenuListItem(
                            icon = Icons.Outlined.CalendarMonth,
                            title = "Banking details",
                            onClick = {}
                        )
                    }
                }
            }

            // Payments and transfers section
            item {
                Text(
                    text = "Payments and transfers",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF1C1C1E)
                )
            }

            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White
                ) {
                    MenuListItem(
                        icon = Icons.Outlined.Refresh,
                        title = "Subscriptions",
                        onClick = onSubscriptions
                    )
                }
            }

            // Upcoming payments section
            item {
                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Upcoming payments",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF1C1C1E)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Get upcoming subscriptions from ViewModel
                val upcomingSubscriptions = uiState.subscriptions
                    .filter { it.category == SubscriptionCategory.UPCOMING }
                    .take(2)

                upcomingSubscriptions.forEach { subscription ->
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .clickable(onClick = onSubscriptions),
                        shape = RoundedCornerShape(12.dp),
                        color = Color.White
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Service icon
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
                                    text = "Tomorrow",
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
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
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

@Composable
private fun ActionButton(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = Color(0xFF1C1C1E),
                modifier = Modifier.size(28.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color(0xFF1C1C1E)
        )
    }
}

@Composable
private fun MenuListItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF1C1C1E),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            fontSize = 16.sp,
            color = Color(0xFF1C1C1E),
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.Outlined.ChevronRight,
            contentDescription = null,
            tint = Color(0xFF8E8E93)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCardDetails() {
    CardDetailsScreen()
}
