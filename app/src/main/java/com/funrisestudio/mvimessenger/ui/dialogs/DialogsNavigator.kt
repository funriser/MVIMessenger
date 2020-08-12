package com.funrisestudio.mvimessenger.ui.dialogs

import androidx.navigation.NavController
import com.funrisestudio.mvimessenger.R
import com.funrisestudio.mvimessenger.core.navigation.NavAction
import com.funrisestudio.mvimessenger.core.navigation.Navigator
import com.funrisestudio.mvimessenger.core.navigation.ToMessages
import com.funrisestudio.mvimessenger.ui.conversation.ConversationFragment
import javax.inject.Inject

class DialogsNavigator @Inject constructor(): Navigator<NavAction.DialogNavAction>() {

    override fun handleAction(controller: NavController, action: NavAction.DialogNavAction) {
        when(action) {
            is ToMessages -> {
                val args = ConversationFragment.getBundle(action.contact)
                controller.navigate(R.id.toConversation, args)
            }
            else -> throw IllegalStateException("Action is not supported")
        }
    }

}