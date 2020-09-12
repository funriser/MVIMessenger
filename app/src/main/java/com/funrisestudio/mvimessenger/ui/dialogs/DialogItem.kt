package com.funrisestudio.mvimessenger.ui.dialogs

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.funrisestudio.mvimessenger.ui.*

@Composable
fun DialogListItem(item: DialogViewData, onClick: (DialogViewData) -> Unit) {
    val dgData = item.dialog
    val avatarAsset = imageResource(dgData.contact.avatar)
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = {
                onClick(item)
            })
            .padding(paddingL),
        constraintSet = ConstraintSet {

            val ivAvatar = createRefFor("ivAvatar")
            val tvSender = createRefFor("tvSender")
            val tvLastMessage = createRefFor("tvLastMessage")
            val tvLastMessageTime = createRefFor("tvLastMessageTime")
            val tvUnreadCountHolder = createRefFor("tvUnreadCountHolder")

            constrain(ivAvatar) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
            constrain(tvSender) {
                start.linkTo(ivAvatar.end)
                top.linkTo(parent.top)
            }
            constrain(tvLastMessage) {
                start.linkTo(ivAvatar.end)
                bottom.linkTo(parent.bottom)
            }
            constrain(tvLastMessageTime) {
                top.linkTo(tvSender.top)
                bottom.linkTo(tvSender.bottom)
                end.linkTo(parent.end)
            }
            constrain(tvUnreadCountHolder) {
                top.linkTo(tvLastMessageTime.bottom)
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
            }

            createVerticalChain(
                tvSender,
                tvLastMessage,
                chainStyle = ChainStyle.Packed
            )
        }
    ) {
        Image(
            asset = avatarAsset,
            modifier = Modifier
                .layoutId("ivAvatar")
                .size(48.dp)
                .clip(shape = CircleShape)
        )
        Text(
            text = dgData.contact.name,
            modifier = Modifier
                .layoutId("tvSender")
                .padding(start = paddingL),
            style = typography.body1
        )
        Text(
            text = dgData.lastMessage.text,
            modifier = Modifier
                .layoutId("tvLastMessage")
                .padding(start = paddingL, top = paddingS),
            style = typography.body2
        )
        Text(
            text = item.formattedDate,
            modifier = Modifier
                .layoutId("tvLastMessageTime"),
            style = typography.caption
        )
        if (dgData.unreadCount != 0) {
            Box(
                shape = CircleShape,
                backgroundColor = colorAccent,
                modifier = Modifier
                    .layoutId("tvUnreadCountHolder")
                    .size(24.dp),
                gravity = ContentGravity.Center
            ) {
                Text(
                    text = dgData.unreadCount.toString(),
                    style = typography.body2.copy(color = Color.White)
                )
            }
        } else {
            //empty view
            Column(modifier = Modifier.layoutId("tvUnreadCountHolder")) {}
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DialogListItemPreview() {
    AppTheme {
        DialogListItem(
            getFakeDialogViewData()[0],
            onClick = {

            }
        )
    }
}