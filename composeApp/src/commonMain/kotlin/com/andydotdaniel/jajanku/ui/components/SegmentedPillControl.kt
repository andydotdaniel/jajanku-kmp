package com.andydotdaniel.jajanku.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andydotdaniel.jajanku.utils.AppColor
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun PillToggle(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .clip(RoundedCornerShape(24.dp))
            .background(AppColor.PrimaryActive) // Lime green from your image
    )
}

@Composable
fun SegmentedPillControl(options: List<String>, selectedIndex: Int, onOptionSelect: (Int) -> Unit) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(28.dp))
            .background(AppColor.BackgroundGray) // Dark background from your image
            .padding(6.dp)
    ) {
        val maxWidth = maxWidth
        val segmentWidth = maxWidth / options.size

        // The Sliding "Pill"
        val offset by animateDpAsState(
            targetValue = segmentWidth * selectedIndex,
            animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
            label = "pillOffset"
        )
        PillToggle(Modifier.offset(x = offset).width(segmentWidth))

        // The Labels
        Row(modifier = Modifier.fillMaxSize()) {
            options.forEachIndexed { index, option ->
                val isSelected = index == selectedIndex
                Box(
                    modifier = Modifier.weight(1f).fillMaxHeight()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null // Remove ripple for a cleaner look
                        ) { onOptionSelect(index) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = option,
                        style = TextStyle(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = if (isSelected) AppColor.Black else AppColor.PlaceholderGray
                        )
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun SegmentedPillControlPreview() {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = AppColor.Black,
        ) {
            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp), horizontalArrangement = Arrangement.Center) {
                val options = listOf("Monthly", "Weekly", "Daily")
                SegmentedPillControl(options = options, selectedIndex = 0) {}
            }
        }
    }
}
