package com.andydotdaniel.jajanku.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andydotdaniel.jajanku.utils.AppColor
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TextCard(title: String, body: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = AppColor.BackgroundGray,
    ) {
        Column(Modifier.padding(horizontal = 16.dp, vertical = 24.dp)) {
            Text(title, fontWeight = FontWeight.Bold, color = AppColor.White, fontSize = 28.sp)
            Text(body, fontWeight = FontWeight.Normal, color = AppColor.LightGray, fontSize = 12.sp, modifier = Modifier.padding(top = 2.dp))
        }
    }
}

@Preview
@Composable
fun TextCardPreview() {
    TextCard(title = "Some title", body = "Ut enim ad minim veniam")
}

