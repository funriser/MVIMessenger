package com.funrisestudio.buzzmessenger.core.navigation

import android.content.Context

abstract class Navigator<NA: NavAction> {

    abstract fun handleAction(context: Context, action: NA)

}