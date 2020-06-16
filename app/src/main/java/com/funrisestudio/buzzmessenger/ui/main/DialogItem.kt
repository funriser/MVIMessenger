package com.funrisestudio.buzzmessenger.ui.main

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.core.clip
import androidx.ui.core.tag
import androidx.ui.foundation.Box
import androidx.ui.foundation.ContentGravity
import androidx.ui.foundation.Image
import androidx.ui.foundation.Text
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.res.imageResource
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.funrisestudio.buzzmessenger.DialogPreview
import com.funrisestudio.buzzmessenger.MessagePreview
import com.funrisestudio.buzzmessenger.R
import com.funrisestudio.buzzmessenger.Sender
import com.funrisestudio.buzzmessenger.ui.*

@Composable
fun DialogListItem(item: DialogPreview) {
    val avatarAsset = imageResource(item.contact.avatar)
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingM),
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
            text = item.contact.name,
            modifier = Modifier
                .tag("tvSender")
                .padding(start = paddingM),
            style = typography.body1
        )
        Text(
            text = item.lastMessage.text,
            modifier = Modifier
                .tag("tvLastMessage")
                .padding(start = paddingM, top = paddingS),
            style = typography.body2
        )
        Text(
            text = item.lastMessage.date,
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
                text = item.unreadCount.toString(),
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
            item = DialogPreview(
                contact = Sender(
                    id = 1,
                    name = "McFly",
                    avatar = R.drawable.avatar_mc_fly
                ),
                lastMessage = MessagePreview(
                    text = "It's time",
                    date = "15:02"
                ),
                unreadCount = 1
            )
        )
    }
}