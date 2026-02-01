package com.andydotdaniel.jajanku.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andydotdaniel.jajanku.utils.AppColor
import jajanku.composeapp.generated.resources.Res
import jajanku.composeapp.generated.resources.close_24px
import jajanku.composeapp.generated.resources.icon_close_24px
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

data class ExpenseItem(
    val id: Int,
    val icon: String,
    val amount: String,
    val category: String,
    val time: String,
    val description: String? = null
)

@Composable
fun ExpenseListItem(
    expense: ExpenseItem,
    isEditMode: Boolean,
    onDelete: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            if (isEditMode) {
                Box(modifier = Modifier.padding(top = 16.dp)) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(Color.Red)
                            .clickable { onDelete() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.close_24px),
                            contentDescription = stringResource(resource = Res.string.icon_close_24px),
                            tint = AppColor.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
            }
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(Color.Transparent)
                    .border(1.dp, AppColor.MutedGray, shape = RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = expense.icon, fontSize = 24.sp)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = expense.amount, color = AppColor.White, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = expense.category, color = Color.LightGray, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                expense.description?.let {
                    Text(
                        text = it,
                        color = AppColor.White,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 8.dp) // Align with text above
                    )
                }
            }
            Text(text = expense.time, color = AppColor.LightGray, fontSize = 14.sp)
        }
    }
}