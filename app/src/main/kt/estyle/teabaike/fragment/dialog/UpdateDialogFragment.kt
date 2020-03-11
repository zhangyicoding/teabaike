package estyle.teabaike.fragment.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import estyle.base.fragment.dialog.BaseDialogFragment
import estyle.teabaike.entity.VersionEntity

class UpdateDialogFragment : BaseDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val version = arguments!!.getParcelable<VersionEntity>("version")
        return AlertDialog.Builder(context)
            .setTitle("发现新版本")
            .setMessage(version?.info)
            .setCancelable(!version!!.force_update)
            .setPositiveButton("更新") { dialog, which -> }
            .setNegativeButton("取消", if (version.force_update)
                DialogInterface.OnClickListener { dialog, which -> activity?.finish() }
            else
                null)
            .create()
    }

    companion object {
        fun newInstance(version: VersionEntity) = UpdateDialogFragment().apply {
            arguments = Bundle().apply {
                putParcelable("version", version)
            }
        }
    }
}