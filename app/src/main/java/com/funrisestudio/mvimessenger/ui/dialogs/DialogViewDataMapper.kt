package com.funrisestudio.mvimessenger.ui.dialogs

import com.funrisestudio.mvimessenger.domain.dialogs.Dialog
import com.funrisestudio.mvimessenger.ui.utils.DateFormat
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