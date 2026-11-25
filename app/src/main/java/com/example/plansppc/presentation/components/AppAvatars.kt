package com.example.plansppc.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.plansppc.presentation.theme.CardBorder
import com.example.plansppc.presentation.theme.GreenPrimary
import com.example.plansppc.presentation.theme.GreenSecondary
import com.example.plansppc.presentation.theme.PlansPPCTheme
import com.example.plansppc.presentation.theme.TextDark
import com.example.plansppc.presentation.theme.TextMuted

@Composable
fun AvatarGroup(
    memberNames: List<String>,
    modifier: Modifier = Modifier,
    maxVisible: Int = 3
) {
    val visibleMembers = memberNames.take(maxVisible)
    val remaining = memberNames.size - maxVisible

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        visibleMembers.forEachIndexed { index, name ->
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(GreenSecondary)
                    .border(2.dp, Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = name.firstOrNull()?.uppercase() ?: "?",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = GreenPrimary
                )
            }
            if (index < visibleMembers.size - 1) {
                Spacer(modifier = Modifier.width((-10).dp))
            }
        }
        if (remaining > 0) {
            Spacer(modifier = Modifier.width((-10).dp))
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(GreenPrimary)
                    .border(2.dp, Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "+$remaining",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun UserProfileHeader(
    userName: String,
    userRole: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(GreenSecondary),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = Icons.Default.Person, contentDescription = userName, tint = GreenPrimary, modifier = Modifier.size(28.dp))
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = userName, fontSize = 17.sp, fontWeight = FontWeight.Bold, color = TextDark)
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(imageVector = Icons.Default.Verified, contentDescription = "Verificado", tint = GreenPrimary, modifier = Modifier.size(18.dp))
                }
                Text(text = userRole, fontSize = 13.sp, color = TextMuted)
            }
            StatusBadge(text = "Activo")
        }
    }
}

@Composable
fun LeaderboardSaverItem(
    rank: Int,
    name: String,
    totalSaved: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val rankColor = when (rank) {
            1 -> Color(0xFFEAB308)
            2 -> Color(0xFF94A3B8)
            3 -> Color(0xFFD97706)
            else -> TextMuted
        }

        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(rankColor.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            if (rank == 1) {
                Icon(imageVector = Icons.Default.EmojiEvents, contentDescription = "Primero", tint = rankColor, modifier = Modifier.size(18.dp))
            } else {
                Text(text = "#$rank", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = rankColor)
            }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = name, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = TextDark, modifier = Modifier.weight(1f))
        Text(text = totalSaved, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = GreenPrimary)
    }
}

@Preview(showBackground = true)
@Composable
fun AppAvatarsPreview() {
    PlansPPCTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            UserProfileHeader(userName = "Jorge Rodríguez", userRole = "Administrador del Plan")

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Miembros Activos:", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                AvatarGroup(memberNames = listOf("Jorge", "Ana", "Luis", "Carlos", "María"))
            }

            Column {
                Text(text = "Top Ahorradores del Mes", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                LeaderboardSaverItem(rank = 1, name = "Jorge", totalSaved = "$ 500.000")
                LeaderboardSaverItem(rank = 2, name = "Ana", totalSaved = "$ 450.000")
                LeaderboardSaverItem(rank = 3, name = "Luis", totalSaved = "$ 300.000")
            }
        }
    }
}
