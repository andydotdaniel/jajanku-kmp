package com.andydotdaniel.jajanku.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andydotdaniel.jajanku.utils.AppColor
import jajanku.composeapp.generated.resources.Res
import jajanku.composeapp.generated.resources.chevron_right_24px
import jajanku.composeapp.generated.resources.icon_chevron_right_24px
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun NavigableListItem(title: String, onClick: () -> Unit) {
    Box(modifier = Modifier.clickable(onClick = onClick)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                color = AppColor.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
            Icon(
                painter = painterResource(Res.drawable.chevron_right_24px),
                contentDescription = stringResource(resource = Res.string.icon_chevron_right_24px),
                tint = AppColor.PrimaryActive,
                modifier = Modifier.absoluteOffset(y = (-0.5).dp)
            )
        }
    }
}

@Preview
@Composable
fun Preview_NavigableListItem() {
    Surface(modifier = Modifier.fillMaxWidth(), color = AppColor.Black) {
        NavigableListItem(title = "Edit Budget", onClick = {})
    }

}
