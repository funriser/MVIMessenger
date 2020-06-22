package com.funrisestudio.buzzmessenger.ui.main

import android.content.Context
import android.content.Intent
import com.funrisestudio.buzzmessenger.core.navigation.NavAction
import com.funrisestudio.buzzmessenger.core.navigation.Navigator
import com.funrisestudio.buzzmessenger.core.navigation.ToMessages
import com.funrisestudio.buzzmessenger.ui.messenger.ConversationActivity
import javax.inject.Inject

class DialogsNavigator @Inject constructor(): Navigator<NavAction.DialogNavAction>() {

    override fun handleAction(context: Context, action: NavAction.DialogNavAction) {
        when(action) {
            is ToMessages -> {
                Intent(context, ConversationActivity::class.java).also {
                    it.putExtra(ToMessages.KEY_SENDER, action.sender)
                    context.startActivity(it)
                }
            }
            else -> throw IllegalStateException("Action is not supported")
        }
    }

}