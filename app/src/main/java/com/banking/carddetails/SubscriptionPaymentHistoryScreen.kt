package com.banking.carddetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.banking.carddetails.data.MockSubscriptionData

@Composable
fun SubscriptionPaymentHistoryScreen(
    subscriptionId: String,
    onBack: () -> Unit = {}
) {
    val subscriptionDetails = remember { MockSubscriptionData.getSubscriptionDetails(subscriptionId) }
    var isDetailsExpanded by remember { mutableStateOf(false) }

    if (subscriptionDetails == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Subscription not found")
        }
        return
    }

    val sub = subscriptionDetails.subscription

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2C3033))
    ) {
        // Top section with dark background
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(16.dp)
        ) {
            // Back button
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Title
                Text(
                    text = "Details",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(end = 40.dp).fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Date and status
            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "05.12.2025 • 11:42  |  ",
                    fontSize = 14.sp,
                    color = Color(0xFFB0B0B0)
                )
                Icon(
                    imageVector = Icons.Outlined.Refresh,
                    contentDescription = null,
                    tint = Color(0xFFB0B0B0),
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = "  Processing",
                    fontSize = 14.sp,
                    color = Color(0xFFB0B0B0)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Amount
            Text(
                text = "${sub.amount.toInt()}.00 ${sub.currency}",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Balance after transaction
            Text(
                text = "Balance after transaction: 9 734.73 ${sub.currency}",
                fontSize = 14.sp,
                color = Color(0xFFB0B0B0),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(24.dp))
        }

        // White card section
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            color = Color.White
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                // Subscription info card
                item {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        color = Color.White,
                        shadowElevation = 2.dp
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            // Logo and name
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Use actual icon from drawable
                                val iconRes = getSubscriptionIcon(sub.name)
                                if (iconRes != null) {
                                    Image(
                                        painter = painterResource(id = iconRes),
                                        contentDescription = sub.name,
                                        modifier = Modifier
                                            .size(48.dp)
                                            .clip(CircleShape),
                                        contentScale = ContentScale.Fit
                                    )
                                } else {
                                    // Fallback to colored circle
                                    Box(
                                        modifier = Modifier
                                            .size(48.dp)
                                            .clip(CircleShape)
                                            .background(getSubscriptionColor(sub.name)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = sub.name.first().toString(),
                                            color = Color.White,
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = sub.name,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF1C1C1E)
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))
                            Divider(color = Color(0xFFE5E5EA))
                            Spacer(modifier = Modifier.height(16.dp))

                            // Always visible details
                            DetailRow("Source", subscriptionDetails.source)
                            Spacer(modifier = Modifier.height(12.dp))
                            DetailRow(
                                "Amount",
                                "${sub.amount.toInt()}.00 ${sub.currency}",
                                hasInfoIcon = true
                            )

                            // Additional details - shown/hidden based on isDetailsExpanded
                            if (isDetailsExpanded) {
                                Spacer(modifier = Modifier.height(12.dp))
                                DetailRow("RRN", subscriptionDetails.rrn)
                                Spacer(modifier = Modifier.height(12.dp))
                                DetailRow("APPC", subscriptionDetails.appc)
                                Spacer(modifier = Modifier.height(12.dp))
                                DetailRow("Additional information", subscriptionDetails.additionalInfo)
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Toggle details
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { isDetailsExpanded = !isDetailsExpanded }
                                    .padding(vertical = 8.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = if (isDetailsExpanded) "Hide details" else "Show details",
                                    fontSize = 14.sp,
                                    color = Color(0xFF8E8E93)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    imageVector = if (isDetailsExpanded) Icons.Outlined.KeyboardArrowUp else Icons.Outlined.KeyboardArrowDown,
                                    contentDescription = null,
                                    tint = Color(0xFF8E8E93),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                }

                item {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { /* TODO: Handle receipt click */ },
                        shape = RoundedCornerShape(12.dp),
                        color = Color.White,
                        shadowElevation = 1.dp
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Outlined.Description,
                                    contentDescription = null,
                                    tint = Color(0xFF34C759),
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = "Receipt",
                                    fontSize = 16.sp,
                                    color = Color(0xFF1C1C1E)
                                )
                            }
                            Icon(
                                imageVector = Icons.Outlined.KeyboardArrowRight,
                                contentDescription = null,
                                tint = Color(0xFF8E8E93),
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                }

                // Chat support button
                item {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { /* TODO: Handle chat support click */ },
                        shape = RoundedCornerShape(12.dp),
                        color = Color.White,
                        shadowElevation = 1.dp
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Outlined.Chat,
                                    contentDescription = null,
                                    tint = Color(0xFF34C759),
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = "Chat support",
                                    fontSize = 16.sp,
                                    color = Color(0xFF1C1C1E)
                                )
                            }
                            Icon(
                                imageVector = Icons.Outlined.KeyboardArrowRight,
                                contentDescription = null,
                                tint = Color(0xFF8E8E93),
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String, hasInfoIcon: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color(0xFF8E8E93)
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = value,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF1C1C1E)
            )
            if (hasInfoIcon) {
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = null,
                    tint = Color(0xFF8E8E93),
                    modifier = Modifier.size(16.dp)
                )
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

@Preview(showBackground = true)
@Composable
private fun PreviewSubscriptionPaymentHistory() {
    SubscriptionPaymentHistoryScreen(subscriptionId = "5")
}
