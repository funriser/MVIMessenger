package com.funrisestudio.buzzmessenger

import androidx.annotation.DrawableRes

class Sender(
    val id: Int,
    val name: String,
    @DrawableRes val avatar: Int
) {

    companion object {
        fun macFly(): Sender {
            return Sender(1, "McFly", R.drawable.avatar_mc_fly)
        }
    }

}