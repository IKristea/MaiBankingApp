package com.banking.carddetails

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.RadioButtonChecked
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.banking.carddetails.data.MockSubscriptionData
import com.banking.carddetails.models.LikelihoodLevel

@Composable
fun CancelReasonScreen(
    subscriptionId: String,
    onBack: () -> Unit = {},
    onSkip: () -> Unit = {},
    onSubmit: (String, String?, LikelihoodLevel?) -> Unit = { _, _, _ -> }
) {
    val subscription = remember {
        MockSubscriptionData.subscriptions.find { it.id == subscriptionId }
    }

    var selectedReasonId by remember { mutableStateOf<String?>(null) }
    var additionalComments by remember { mutableStateOf("") }
    var selectedLikelihood by remember { mutableStateOf<LikelihoodLevel?>(null) }

    val reasons = remember { MockSubscriptionData.cancellationReasons }

    Column(
        modifier = Modifier
            .fillMaxSize()

            .background(Color(0xFF121212))
    ) {
        // Spotify logo and header
        Surface(
            modifier = Modifier.fillMaxWidth().statusBarsPadding(),
            color = Color(0xFF000000)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(getSubscriptionColor(subscription?.name ?: "Spotify")),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = subscription?.name?.first()?.toString() ?: "S",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = subscription?.name ?: "Spotify",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Row {
                    IconButton(
                        onClick = {},
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Person,
                            contentDescription = "Profile",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    IconButton(
                        onClick = {},
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Menu,
                            contentDescription = "Menu",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }

        // Search bar
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(8.dp),
            color = Color(0xFF282828)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = "Search",
                    tint = Color(0xFF888888),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Search account or help articles",
                    color = Color(0xFF888888),
                    fontSize = 14.sp
                )
            }
        }

        // Back arrow button
        IconButton(
            onClick = onBack,
            modifier = Modifier
                .padding(start = 8.dp)
                .size(40.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 20.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "You've cancelled\nyour subscription.",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    lineHeight = 34.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Your subscription will end on 1/1/1970.",
                    fontSize = 16.sp,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "To help us improve ${subscription?.name ?: "Spotify"}, please take a minute to tell us about why you cancelled.",
                    fontSize = 16.sp,
                    color = Color.White,
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "What's the main reason you cancelled your ${subscription?.name ?: "Spotify"} subscription?",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            // Reason options
            items(reasons) { reason ->
                ReasonOption(
                    text = reason.text,
                    isSelected = selectedReasonId == reason.id,
                    onSelect = { selectedReasonId = reason.id }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Let us know more about why you are canceling:",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Text input
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFF282828)
                ) {
                    TextField(
                        value = additionalComments,
                        onValueChange = { additionalComments = it },
                        modifier = Modifier.fillMaxSize(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFF282828),
                            unfocusedContainerColor = Color(0xFF282828),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        placeholder = {
                            Text(
                                text = "",
                                color = Color(0xFF888888)
                            )
                        }
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "How likely are you to subscribe to ${subscription?.name ?: "Spotify"} again in the near future?",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Likelihood options
                LikelihoodOption(
                    text = "Extremely likely",
                    isSelected = selectedLikelihood == LikelihoodLevel.EXTREMELY_LIKELY,
                    onSelect = { selectedLikelihood = LikelihoodLevel.EXTREMELY_LIKELY }
                )
                Spacer(modifier = Modifier.height(12.dp))

                LikelihoodOption(
                    text = "Likely",
                    isSelected = selectedLikelihood == LikelihoodLevel.LIKELY,
                    onSelect = { selectedLikelihood = LikelihoodLevel.LIKELY }
                )
                Spacer(modifier = Modifier.height(12.dp))

                LikelihoodOption(
                    text = "Neutral",
                    isSelected = selectedLikelihood == LikelihoodLevel.NEUTRAL,
                    onSelect = { selectedLikelihood = LikelihoodLevel.NEUTRAL }
                )
                Spacer(modifier = Modifier.height(12.dp))

                LikelihoodOption(
                    text = "Unlikely",
                    isSelected = selectedLikelihood == LikelihoodLevel.UNLIKELY,
                    onSelect = { selectedLikelihood = LikelihoodLevel.UNLIKELY }
                )
                Spacer(modifier = Modifier.height(12.dp))

                LikelihoodOption(
                    text = "Extremely unlikely",
                    isSelected = selectedLikelihood == LikelihoodLevel.EXTREMELY_UNLIKELY,
                    onSelect = { selectedLikelihood = LikelihoodLevel.EXTREMELY_UNLIKELY }
                )

                Spacer(modifier = Modifier.height(32.dp))
            }
        }

        // Bottom buttons
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color(0xFF121212)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(
                    onClick = onSkip,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Skip",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = {
                        selectedReasonId?.let { reasonId ->
                            onSubmit(reasonId, additionalComments, selectedLikelihood)
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    shape = RoundedCornerShape(26.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1DB954)
                    ),
                    enabled = selectedReasonId != null
                ) {
                    Text(
                        text = "Submit",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )
                }
            }
        }
    }
}

@Composable
private fun ReasonOption(
    text: String,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onSelect),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = if (isSelected) Icons.Outlined.RadioButtonChecked else Icons.Outlined.RadioButtonUnchecked,
            contentDescription = null,
            tint = if (isSelected) Color(0xFF1DB954) else Color(0xFF888888),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            fontSize = 15.sp,
            color = Color.White,
            lineHeight = 20.sp
        )
    }
}

@Composable
private fun LikelihoodOption(
    text: String,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onSelect),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = if (isSelected) Icons.Outlined.RadioButtonChecked else Icons.Outlined.RadioButtonUnchecked,
            contentDescription = null,
            tint = if (isSelected) Color(0xFF1DB954) else Color(0xFF888888),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            fontSize = 15.sp,
            color = Color.White
        )
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
private fun PreviewCancelReason() {
    CancelReasonScreen(subscriptionId = "5")
}
