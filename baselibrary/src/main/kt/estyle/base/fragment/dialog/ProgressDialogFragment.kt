package estyle.base.fragment.dialog

import android.app.Dialog
import android.app.ProgressDialog
import android.os.Bundle
import estyle.base.R

class ProgressDialogFragment : BaseDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return ProgressDialog(context).apply { setMessage(getString(R.string.please_wait)) }
    }

    companion object {
        fun newInstance() = ProgressDialogFragment()
    }
}