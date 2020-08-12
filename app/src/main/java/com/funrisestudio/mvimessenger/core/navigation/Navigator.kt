package com.funrisestudio.mvimessenger.core.navigation

import androidx.navigation.NavController

abstract class Navigator<NA: NavAction> {

    abstract fun handleAction(controller: NavController, action: NA)

}