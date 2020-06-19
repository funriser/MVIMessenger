package com.funrisestudio.buzzmessenger.ui.main

import com.funrisestudio.buzzmessenger.domain.dialogs.Dialog
import com.funrisestudio.buzzmessenger.ui.utils.DateFormat
import javax.inject.Inject

class DialogViewDataMapper @Inject constructor(
    private val dateFormat: DateFormat
) {

    fun getDialogViewDataList(dialogs: List<Dialog>): List<DialogViewData> {
        return dialogs.map {
            DialogViewData(it, dateFormat.timeFormat(it.lastMessage.date))
        }
    }

}