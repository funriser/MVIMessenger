package com.funrisestudio.buzzmessenger.data

import com.funrisestudio.buzzmessenger.R
import com.funrisestudio.buzzmessenger.domain.Sender

val contacts = listOf(
    Sender(
        id = 1,
        name = "McFly",
        avatar = R.drawable.avatar_mc_fly
    ),
    Sender(
        id = 2,
        name = "Dr Brown",
        avatar = R.drawable.avatar_mc_fly
    ),
    Sender(
        id = 3,
        name = "Biff",
        avatar = R.drawable.avatar_mc_fly
    )
)

val messages = listOf(
    "Hey", "Hello", "It's time", "Wazzup", "Howdy"
)