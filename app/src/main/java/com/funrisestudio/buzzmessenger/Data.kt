package com.funrisestudio.buzzmessenger

import androidx.annotation.DrawableRes

class Sender(
    val name: String,
    @DrawableRes val avatar: Int
) {

    companion object {
        fun macFly(): Sender {
            return Sender("McFly", R.drawable.avatar_mc_fly)
        }
    }

}