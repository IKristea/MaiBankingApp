package com.banking.carddetails

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import com.banking.carddetails.data.MockSubscriptionData
import kotlinx.coroutines.delay
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.banking.carddetails.models.SubscriptionCategory

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onCardClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {},
    onUpcomingPaymentsClick: () -> Unit = {}
) {
    val pagerState = rememberPagerState(pageCount = { 3 })
    val context = LocalContext.current

    var showToast by remember { mutableStateOf(true) }
    val upcomingSubscription = remember {
        MockSubscriptionData.subscriptions.firstOrNull { it.category == SubscriptionCategory.UPCOMING }
    }

    // Auto-hide toast after 3 seconds
    LaunchedEffect(showToast) {
        if (showToast) {
            delay(2000)
            showToast = false
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0xFF1E5A6E),
                            Color(0xFF2A7A8E)
                        )
                    )
                )
        ) {
            // Top bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .statusBarsPadding(),
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.2f))
                        .clickable { },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = "Profile",
                        tint = Color.White
                    )
                }

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.2f))
                        .clickable { onNotificationClick() },
                    contentAlignment = Alignment.Center
                ) {
                    BadgedBox(
                        badge = {
                            Badge(
                                containerColor = Color(0xFFFF6B6B),
                                modifier = Modifier.offset(x = (-8).dp, y = 8.dp)
                            ) {
                                Text("3", fontSize = 10.sp)
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Notifications,
                            contentDescription = "Notifications",
                            tint = Color.White
                        )
                    }
                }
            }

            // Card Pager
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                pageSpacing = 16.dp
            )
            { page ->
                CardItem(
                    accountName = "Acccount gama universal",
                    balance = "120 800 MDL",
                    creditLimit = "10 000.00 MDL",
                    cardNumber = "1111",
                    onClick = onCardClick
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Pager indicator
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            )
            {
                repeat(3) { index ->
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 3.dp)
                            .size(if (index == pagerState.currentPage) 8.dp else 6.dp)
                            .clip(CircleShape)
                            .background(
                                if (index == pagerState.currentPage)
                                    Color.White
                                else
                                    Color.White.copy(alpha = 0.4f)
                            )
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Quick actions
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                QuickActionButton(
                    icon = Icons.Outlined.SwapHoriz,
                    label = "Transfer to\nmy account"
                )
                QuickActionButton(
                    icon = Icons.Outlined.Person,
                    label = "Transfer to\nanother\nperson"
                )
                QuickActionButton(
                    icon = Icons.Outlined.CreditCard,
                    label = "Top up card"
                )
                QuickActionButton(
                    icon = Icons.Outlined.Receipt,
                    label = "Pay the bills"
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Column(
                modifier = Modifier
                    .background(Color(0xFFF7F8FA))
                    .fillMaxSize()
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .graphicsLayer {
                            translationY = -32f // mutat în sus, fără gol jos
                        },
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                    shadowElevation = 3.dp,
                )
                {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()

                            .padding(horizontal = 20.dp)
                    )
                    {
                        item {
                            Spacer(modifier = Modifier.height(20.dp))

                            // Service cards row
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                ServiceCard(
                                    modifier = Modifier.weight(1f),
                                    color = Color(0xFF6FDDC6),
                                    text = "Check\ncredit offer"
                                )
                                ServiceCard(
                                    modifier = Modifier.weight(1f),
                                    color = Color(0xFFFFAA6F),
                                    text = "Ion"
                                )
                                ServiceCard(
                                    modifier = Modifier.weight(1f),
                                    color = Color(0xFFB0D9F1),
                                    text = "maib alto"
                                )
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            // Upcoming payments - white card with rounded corners
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable(onClick = onUpcomingPaymentsClick),
                                shape = RoundedCornerShape(12.dp),
                                color = Color.White
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "Upcoming payments",
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = Color(0xFF1C1C1E)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        // Subscription icons row with overlap
                                        Row {
                                            // Spotify icon
                                            Image(
                                                painter = painterResource(id = R.drawable.spotify),
                                                contentDescription = "Spotify",
                                                modifier = Modifier
                                                    .size(28.dp)
                                                    .clip(CircleShape),
                                                contentScale = ContentScale.Fit
                                            )
                                            // YouTube icon
                                            Image(
                                                painter = painterResource(id = R.drawable.youtube_logo),
                                                contentDescription = "YouTube",
                                                modifier = Modifier
                                                    .offset(x = (-8).dp)
                                                    .size(28.dp)
                                                    .clip(CircleShape),
                                                contentScale = ContentScale.Fit
                                            )
                                            // Netflix icon
                                            Image(
                                                painter = painterResource(id = R.drawable.netflix),
                                                contentDescription = "Netflix",
                                                modifier = Modifier
                                                    .offset(x = (-16).dp)
                                                    .size(28.dp)
                                                    .clip(CircleShape),
                                                contentScale = ContentScale.Fit
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "Next on ${upcomingSubscription?.nextPaymentDate ?: "1 Dec"}",
                                        fontSize = 13.sp,
                                        color = Color(0xFF8E8E93)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            // Transactions
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Transactions",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF1C1C1E)
                                )
                                TextButton(onClick = {}) {
                                    Text(
                                        text = "View all",
                                        fontSize = 14.sp,
                                        color = Color(0xFF00C896)
                                    )
                                    Icon(
                                        imageVector = Icons.Outlined.ArrowForward,
                                        contentDescription = null,
                                        tint = Color(0xFF00C896),
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }

                            // Transaction items
                            TransactionItem(
                                name = "Adrian N.",
                                date = "Yesterday, 19:30",
                                amount = "-200.00 MDL"
                            )
                            TransactionItem(
                                name = "Adrian N.",
                                date = "Yesterday, 19:30",
                                amount = "-200.00 MDL"
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            // Loans
                            Text(
                                text = "Loans",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF1C1C1E)
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(16.dp),
                                color = Color(0xFF00C896)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(20.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = "Check for free which credit offers you can benefit from",
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = Color.White,
                                            lineHeight = 20.sp
                                        )
                                        Spacer(modifier = Modifier.height(12.dp))
                                        Button(
                                            onClick = {},
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = Color.White
                                            ),
                                            shape = RoundedCornerShape(8.dp)
                                        ) {
                                            Text(
                                                text = "Check",
                                                color = Color(0xFF00C896),
                                                fontWeight = FontWeight.SemiBold
                                            )
                                        }
                                    }
                                    Icon(
                                        imageVector = Icons.Outlined.Phone,
                                        contentDescription = null,
                                        tint = Color.White.copy(alpha = 0.3f),
                                        modifier = Modifier.size(80.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(80.dp))
                        }
                    }
                }

            }

        }

        // Banner notification at the top
        if (showToast && upcomingSubscription != null) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .statusBarsPadding()
                    .align(Alignment.TopCenter),
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFF323232),
                shadowElevation = 4.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Notifications,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "You have upcoming payment for your ${upcomingSubscription.name} subscription on ${upcomingSubscription.nextPaymentDate}",
                            color = Color.White,
                            fontSize = 14.sp,
                            lineHeight = 18.sp
                        )
                    }
                    IconButton(
                        onClick = { showToast = false },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = "Close",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CardItem(
        accountName: String,
        balance: String,
        creditLimit: String,
        cardNumber: String,
        onClick: () -> Unit
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick),
            shape = RoundedCornerShape(20.dp),
            color = Color(0xFF6FA8B8).copy(alpha = 0.6f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = accountName,
                    fontSize = 13.sp,
                    color = Color.White.copy(alpha = 0.9f)
                )
                Text(
                    text = balance,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Credit limit: $creditLimit",
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Mini card
                Surface(
                    modifier = Modifier
                        .width(120.dp)
                        .height(75.dp),
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFF00A896)
                )
                {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp)
                    ) {
                        Text(
                            text = "maib gama",
                            fontSize = 10.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Text(
                                text = cardNumber,
                                fontSize = 14.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                            // Mastercard logo
                            Row {
                                Box(
                                    modifier = Modifier
                                        .size(16.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFFFF5F00))
                                )
                                Box(
                                    modifier = Modifier
                                        .offset(x = (-6).dp)
                                        .size(16.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFFEB001B))
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onClick,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White.copy(alpha = 0.3f)
                    ),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = "Account details",
                        color = Color.White,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.Outlined.Info,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }

    @Composable
    fun QuickActionButton(
        icon: ImageVector,
        label: String
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(70.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.2f))
                    .clickable { },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = label,
                fontSize = 11.sp,
                color = Color.White,
                lineHeight = 13.sp,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }

    @Composable
    fun ServiceCard(
        modifier: Modifier = Modifier,
        color: Color,
        text: String
    ) {
        Surface(
            modifier = modifier.height(100.dp),
            shape = RoundedCornerShape(12.dp),
            color = color
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Text(
                    text = text,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    lineHeight = 16.sp
                )
            }
        }
    }

    @Composable
    fun TransactionItem(
        name: String,
        date: String,
        amount: String
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF6B8DA8)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = name.take(2),
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF1C1C1E)
                )
                Text(
                    text = date,
                    fontSize = 13.sp,
                    color = Color(0xFF8E8E93)
                )
            }
            Text(
                text = amount,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1C1C1E)
            )
        }
    }

@Preview(showBackground = true)
@Composable
private fun PreviewHome() {
    HomeScreen()
}
