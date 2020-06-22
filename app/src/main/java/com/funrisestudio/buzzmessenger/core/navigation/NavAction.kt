package com.funrisestudio.buzzmessenger.core.navigation

sealed class NavAction {
    abstract class DialogNavAction: NavAction()
}
class ToMessages(val senderId: Int): NavAction.DialogNavAction()