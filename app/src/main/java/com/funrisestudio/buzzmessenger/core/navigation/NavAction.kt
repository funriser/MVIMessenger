package com.funrisestudio.buzzmessenger.core.navigation

import com.funrisestudio.buzzmessenger.domain.Contact

sealed class NavAction {
    abstract class DialogNavAction: NavAction()
}
class ToMessages(val contact: Contact): NavAction.DialogNavAction() {

    companion object {
        const val KEY_CONTACT = "key_sender"
    }

}