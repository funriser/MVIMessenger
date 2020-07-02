package com.funrisestudio.mvimessenger.core.navigation

import com.funrisestudio.mvimessenger.domain.entity.Contact

sealed class NavAction {
    abstract class DialogNavAction: NavAction()
}
class ToMessages(val contact: Contact): NavAction.DialogNavAction() {

    companion object {
        const val KEY_CONTACT = "key_sender"
    }

}