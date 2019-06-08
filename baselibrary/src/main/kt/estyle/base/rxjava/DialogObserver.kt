package estyle.base.rxjava

import androidx.fragment.app.DialogFragment

open class DialogObserver<T>(
    private val dialogFragment: DialogFragment?,
    errorText: String? = null
) : AbstractViewObserver<T>(dialogFragment?.view, errorText) {

    override fun gone() {
        dialogFragment?.dismiss()
    }
}