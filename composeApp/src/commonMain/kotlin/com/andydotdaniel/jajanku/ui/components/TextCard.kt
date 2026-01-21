package com.andydotdaniel.jajanku.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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

@Composable
fun TextCard(
    title: String,
    body: String,
    id: String,
    onClick: (id: String) -> Unit = {},
    selected: Boolean = false,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        onClick = {
            onClick(id)
        },
        shape = RoundedCornerShape(12.dp),
        color = if (selected) AppColor.PrimaryActive else AppColor.BackgroundGray,
    ) {
        Column(Modifier.padding(horizontal = 16.dp, vertical = 24.dp)) {
            Text(title, fontWeight = FontWeight.Bold, color = if (selected) AppColor.Black else AppColor.White, fontSize = 28.sp)
            Text(body, fontWeight = FontWeight.Normal, color = if (selected) AppColor.BackgroundGray else AppColor.LightGray, fontSize = 12.sp, modifier = Modifier.padding(top = 2.dp))
        }
    }
}

@Preview
@Composable
fun TextCardPreview() {
    TextCard(title = "Some title", body = "Ut enim ad minim veniam", selected = true, onClick = {}, id = "1")
}

