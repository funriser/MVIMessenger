package com.funrisestudio.buzzmessenger.core.navigation

import com.funrisestudio.buzzmessenger.domain.Sender

sealed class NavAction {
    abstract class DialogNavAction: NavAction()
}
class ToMessages(val sender: Sender): NavAction.DialogNavAction() {

    companion object {
        const val KEY_SENDER = "key_sender"
    }

}