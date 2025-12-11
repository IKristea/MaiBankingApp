package com.banking.carddetails

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Subscriptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SmartSubscriptionsScreen(onBack: () -> Unit) {

    val gradient = Brush.verticalGradient(
        listOf(Color(0xFFE4EFF2), Color(0xFFEFF4F5))
    )

    Column(
        Modifier
            .fillMaxSize()
            .background(gradient)
            .padding(20.dp)
    ) {

        BackButton(onBack)

        Spacer(Modifier.height(20.dp))

        Text("Smart subscriptions", fontSize = 26.sp, fontWeight = FontWeight.Bold)
        Text("Detected recurring payments", fontSize = 14.sp, color = Color.Gray)

        Spacer(Modifier.height(30.dp))

        Subscription("Netflix", "9.99 USD", "Monthly", "14.01.2025")
        Subscription("iCloud 200GB", "2.99 USD", "Monthly", "02.01.2025")
        Subscription("ChatGPT Plus", "20 USD", "Monthly", "03.01.2025")
        Subscription("Adobe CC", "52.99 USD", "Monthly", "28.12.2024")
    }
}

@Composable
fun Subscription(name: String, price: String, type: String, date: String) {
    Column(Modifier.fillMaxWidth().padding(vertical = 12.dp)) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Outlined.Subscriptions, null, tint = Color(0xFF8B969A))
            Spacer(Modifier.width(14.dp))

            Column {
                Text(name, fontSize = 18.sp)
                Text("Recurring · $price · $type", fontSize = 14.sp, color = Color(0xFF6A7477))
                Text("Last charge: $date", fontSize = 13.sp, color = Color.Gray)
            }
        }

        Spacer(Modifier.height(10.dp))
        Box(
            Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(Color(0xFFD0D7DA))
        )
    }
}

@Composable
fun BackButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.6f))
            .border(1.dp, Color(0xFFE0E0E0), CircleShape)
            .clickable(
                indication = null,
                interactionSource = MutableInteractionSource()
            ) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Outlined.ArrowBack,
            contentDescription = "Back",
            tint = Color(0xFF555555)
        )
    }
}
