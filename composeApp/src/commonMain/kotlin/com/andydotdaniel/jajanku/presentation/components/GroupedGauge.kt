package com.andydotdaniel.jajanku.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andydotdaniel.jajanku.utils.AppColor
import org.jetbrains.compose.ui.tooling.preview.Preview

data class GaugeData (
    val title: String,
    val value: String,
    val gaugeFraction: Float
)

@Composable
fun Gauge(fraction: Float) {
    Box(
        Modifier
            .height(18.dp)
            .fillMaxWidth()
            .background(AppColor.MutedGray, shape = RoundedCornerShape(9.dp))
    ) {
        Box(
            Modifier
                .fillMaxHeight()
                .fillMaxWidth(fraction)
                .background(AppColor.PrimaryActive, shape = RoundedCornerShape(9.dp))
        )
    }
}

@Composable
fun GroupedGauge(data: List<GaugeData>) {
    Surface(
        color = AppColor.BackgroundGray,
        shape = RoundedCornerShape(12.dp),
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(24.dp), modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp)) {
            data.forEach { item ->
                Column {
                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
                        Text(item.title, fontWeight = FontWeight.SemiBold, color = AppColor.White, fontSize = 16.sp)
                        Text(item.value, fontWeight = FontWeight.SemiBold, color = AppColor.White, fontSize = 16.sp)
                    }
                    Gauge(item.gaugeFraction)
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewGroupedGauge() {
    Surface(Modifier.fillMaxWidth(), color = AppColor.Black) {
        val gaugeData = listOf<GaugeData>(
            GaugeData("Needs", "440,000", 0.4f),
            GaugeData("Wants", "1,440,000", 0.6f),
        )
        GroupedGauge(gaugeData)
    }
}
