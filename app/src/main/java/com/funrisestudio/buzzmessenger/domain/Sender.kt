package com.funrisestudio.buzzmessenger.domain

import androidx.annotation.DrawableRes

data class Sender(
    val id: Int,
    val name: String,
    @DrawableRes val avatar: Int
)