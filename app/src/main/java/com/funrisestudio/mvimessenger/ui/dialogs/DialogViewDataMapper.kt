package com.funrisestudio.mvimessenger.ui.dialogs

import com.funrisestudio.mvimessenger.domain.dialogs.Dialog
import com.funrisestudio.mvimessenger.ui.utils.DateFormat
import com.funrisestudio.mvimessenger.utils.Open
import javax.inject.Inject

@Open
class DialogViewDataMapper @Inject constructor(
    private val dateFormat: DateFormat
) {

    @Open
    fun getDialogViewDataList(dialogs: List<Dialog>): List<DialogViewData> {
        return dialogs.map {
            DialogViewData(it, dateFormat.timeFormat(it.lastMessage.date))
        }
    }

}