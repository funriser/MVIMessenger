package com.funrisestudio.buzzmessenger.ui.main

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.core.clip
import androidx.ui.core.tag
import androidx.ui.foundation.*
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.res.imageResource
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.funrisestudio.buzzmessenger.ui.*

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
            val ivAvatar = tag("ivAvatar").apply {
                left constrainTo parent.left
                top constrainTo parent.top
                bottom constrainTo parent.bottom
            }
            val tvSender = tag("tvSender").apply {
                left constrainTo ivAvatar.right
                top constrainTo parent.top
            }
            val tvLastMessage = tag("tvLastMessage").apply {
                left constrainTo ivAvatar.right
                bottom constrainTo parent.bottom
            }
            val tvLastMessageTime = tag("tvLastMessageTime").apply {
                top constrainTo tvSender.top
                bottom constrainTo tvSender.bottom
                right constrainTo parent.right
            }
            val tvUnreadCountHolder = tag("tvUnreadCountHolder").apply {
                top constrainTo tvLastMessageTime.bottom
                bottom constrainTo parent.bottom
                right constrainTo parent.right
            }
            createVerticalChain(
                tvSender,
                tvLastMessage,
                chainStyle = ConstraintSetBuilderScope.ChainStyle.Packed
            )}
    ) {
        Image(
            asset = avatarAsset,
            modifier = Modifier
                .tag("ivAvatar")
                .size(48.dp)
                .clip(shape = CircleShape)
        )
        Text(
            text = dgData.contact.name,
            modifier = Modifier
                .tag("tvSender")
                .padding(start = paddingL),
            style = typography.body1
        )
        Text(
            text = dgData.lastMessage.text,
            modifier = Modifier
                .tag("tvLastMessage")
                .padding(start = paddingL, top = paddingS),
            style = typography.body2
        )
        Text(
            text = item.formattedDate,
            modifier = Modifier
                .tag("tvLastMessageTime"),
            style = typography.caption
        )
        Box(
            shape = CircleShape,
            backgroundColor = colorAccent,
            modifier = Modifier
                .tag("tvUnreadCountHolder")
                .size(24.dp),
            gravity = ContentGravity.Center
        ) {
            Text(
                text = dgData.unreadCount.toString(),
                style = typography.body2.copy(color = Color.White)
            )
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