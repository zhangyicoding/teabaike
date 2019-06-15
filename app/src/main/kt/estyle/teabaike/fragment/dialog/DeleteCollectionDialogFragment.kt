package estyle.teabaike.fragment.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import estyle.base.fragment.dialog.BaseDialogFragment
import estyle.teabaike.R

class DeleteCollectionDialogFragment : BaseDialogFragment() {

    var positiveCallback: (() -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(context!!)
            .setMessage(R.string.delete_collection_tip)
            .setNegativeButton(R.string.no, null)
            .setPositiveButton(R.string.yes) { dialog, which -> positiveCallback?.invoke() }
            .create()

    companion object {
        fun newInstance() = DeleteCollectionDialogFragment()
    }
}

