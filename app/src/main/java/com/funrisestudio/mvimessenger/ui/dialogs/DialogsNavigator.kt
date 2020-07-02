package com.funrisestudio.mvimessenger.ui.dialogs

import android.content.Context
import com.funrisestudio.mvimessenger.core.navigation.NavAction
import com.funrisestudio.mvimessenger.core.navigation.Navigator
import com.funrisestudio.mvimessenger.core.navigation.ToMessages
import com.funrisestudio.mvimessenger.ui.conversation.ConversationActivity
import javax.inject.Inject

class DialogsNavigator @Inject constructor(): Navigator<NavAction.DialogNavAction>() {

    override fun handleAction(context: Context, action: NavAction.DialogNavAction) {
        when(action) {
            is ToMessages -> {
                val intent = ConversationActivity.getIntent(context, action.contact)
                context.startActivity(intent)
            }
            else -> throw IllegalStateException("Action is not supported")
        }
    }

}