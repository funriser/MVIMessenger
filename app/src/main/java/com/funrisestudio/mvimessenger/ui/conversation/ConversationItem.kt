package com.funrisestudio.mvimessenger.ui.conversation

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.funrisestudio.mvimessenger.ui.*

@Composable
fun ConversationListItem(
    item: MessageViewData,
    paddingTop: Dp
) {
    val (alignment, color) = if (item.message.isReceived) {
        Alignment.CenterStart to colorPrimaryDark
    } else {
        Alignment.CenterEnd to colorPrimary
    }
    Box(
        modifier = Modifier.fillMaxWidth(),
        alignment = alignment
    ) {
        Surface(
            color = color,
            shape = RoundedCornerShape(cornerSize),
            modifier = Modifier
                .padding(top = paddingTop)
                .widthIn(min = 60.dp)
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
                    val tvMsg = createRefFor("tvMsg")
                    val tvTime = createRefFor("tvTime")

                    constrain(tvMsg) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    }
                    constrain(tvTime) {
                        top.linkTo(tvMsg.bottom)
                        end.linkTo(parent.end)
                    }
                }
            ) {
                Text(
                    text = item.message.text,
                    modifier = Modifier
                        .layoutId("tvMsg"),
                    style = typography.body2.copy(color = Color.White)
                )
                Text(
                    text = item.formattedDate,
                    modifier = Modifier
                        .layoutId("tvTime"),
                    style = typography.caption
                )
            }
        }
    }
}