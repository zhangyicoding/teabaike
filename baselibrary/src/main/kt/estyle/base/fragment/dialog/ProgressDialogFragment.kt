package estyle.base.fragment.dialog

import android.app.Dialog
import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import estyle.base.R

class ProgressDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return ProgressDialog(context).apply { setMessage(getString(R.string.please_wait)) }
    }

    companion object {
        fun newInstacne() = ProgressDialogFragment()
    }
}