package com.funrisestudio.buzzmessenger.ui.main

import android.content.Context
import com.funrisestudio.buzzmessenger.core.navigation.NavAction
import com.funrisestudio.buzzmessenger.core.navigation.Navigator
import com.funrisestudio.buzzmessenger.core.navigation.ToMessages
import com.funrisestudio.buzzmessenger.ui.messenger.ConversationActivity
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