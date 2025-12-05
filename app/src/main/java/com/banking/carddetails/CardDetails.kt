package com.banking.carddetails

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.foundation.layout.Arrangement

@Composable
fun CardDetailsScreen(
    onBack: () -> Unit = {},
    onEdit: () -> Unit = {},
    onSmartSubs: () -> Unit = {}
) {
    // Colors chosen to match the look in your screenshot
    val background = Brush.verticalGradient(listOf(Color(0xFFF9EFE5), Color(0xFFF4F5F7)))
    val headerTextColor = Color(0xFF6E6E6E)
    val mainText = Color(0xFF111111)
    val cardGradient = Brush.horizontalGradient(listOf(Color(0xFFFFB74D), Color(0xFFE86B1B)))
    val sectionBg = Color(0xFFFFFFFF)
    val subtle = Color(0xFF9AA3A6)
    val itemDivider = Color(0xFFF0F0F0)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Top bar row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = "Back",
                    tint = headerTextColor
                )
            }

            Spacer(modifier = Modifier.width(6.dp))

            Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Gama universal",
                    fontSize = 14.sp,
                    color = headerTextColor
                )
                // two balances row
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "42 100.52 MDL",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = mainText
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "| 42 100.52 MDL",
                        fontSize = 14.sp,
                        color = headerTextColor
                    )
                }
            }

            IconButton(onClick = onEdit) {
                Icon(
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = "Edit",
                    tint = headerTextColor
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Card carousel placeholder (single card)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp))
                .background(cardGradient)
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "universal",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "0755",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.weight(1f)
                    )
                    // Mastercard mock logo
                    MastercardLogoSmall()
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Three action buttons row (centered)
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            RoundAction(
                icon = Icons.Outlined.RemoveRedEye,
                label = "Show details",
                onClick = {}
            )
            RoundAction(
                icon = Icons.Outlined.StarOutline,
                label = "Smart subscription",
                onClick = { onSmartSubs() }
            )
            RoundAction(
                icon = Icons.Outlined.AcUnit,
                label = "Temporary block",
                onClick = {}
            )
        }
    }
}

@Composable
private fun RoundAction(icon: ImageVector, label: String, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(Color.White)
                .border(width = 1.dp, color = Color(0xFFE6E6E6), shape = CircleShape)
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null
                ) { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = label, tint = Color(0xFF6B777A), modifier = Modifier.size(28.dp))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = label, fontSize = 13.sp)
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
private fun CardListItem(icon: ImageVector, title: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
            .padding(vertical = 6.dp),
        color = Color.White,
        shape = RoundedCornerShape(10.dp),
        tonalElevation = 1.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, tint = Color(0xFF6B777A), modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = title, fontSize = 15.sp, modifier = Modifier.weight(1f))
            Icon(Icons.Outlined.ChevronRight, contentDescription = null, tint = Color(0xFFC0C7C9))
        }
    }
}

@Composable
private fun MastercardLogoSmall() {
    // simple Mastercard circles at bottom-right of the card
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(Color(0xFFFF5C5C))
        )
        Box(
            modifier = Modifier
                .offset(x = (-10).dp)
                .size(28.dp)
                .clip(CircleShape)
                .background(Color(0xFFFFB74D))
        )
    }
}

fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier =
    this.clickable(indication = null, interactionSource = MutableInteractionSource()) { onClick() }

@Preview(showBackground = true)
@Composable
private fun PreviewCardDetails() {
    CardDetailsScreen(onBack = {}, onEdit = {}, onSmartSubs = {})
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