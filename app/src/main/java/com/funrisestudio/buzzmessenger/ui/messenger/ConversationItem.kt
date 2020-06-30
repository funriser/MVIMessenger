package com.funrisestudio.buzzmessenger.ui.messenger

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.core.tag
import androidx.ui.foundation.Box
import androidx.ui.foundation.ContentGravity
import androidx.ui.foundation.Text
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.material.Surface
import androidx.ui.unit.Dp
import androidx.ui.unit.dp
import com.funrisestudio.buzzmessenger.ui.*

@Composable
fun ConversationListItem(
    item: MessageViewData,
    paddingTop: Dp
) {
    val (gravity, color) = if (item.message.isReceived) {
        ContentGravity.CenterStart to colorPrimaryDark
    } else {
        ContentGravity.CenterEnd to colorPrimary
    }
    Box(
        modifier = Modifier.fillMaxWidth(),
        gravity = gravity
    ) {
        Surface(
            color = color,
            shape = RoundedCornerShape(cornerSize),
            modifier = Modifier
                .padding(top = paddingTop)
                .widthIn(minWidth = 60.dp)
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .padding(
                        start = paddingM,
                        end = paddingM,
                        top = paddingS,
                        bottom = paddingS
                    ),
                constraintSet = ConstraintSet {
                    val tvMsg = tag("tvMsg").apply {
                        left constrainTo parent.left
                        top constrainTo parent.top
                    }
                    val tvTime = tag("tvTime").apply {
                        top constrainTo tvMsg.bottom
                        right constrainTo parent.right
                    }
                }
            ) {
                Text(
                    text = item.message.text,
                    modifier = Modifier
                        .tag("tvMsg"),
                    style = typography.body2.copy(color = Color.White)
                )
                Text(
                    text = item.formattedDate,
                    modifier = Modifier
                        .tag("tvTime"),
                    style = typography.caption
                )
            }
        }
    }
}