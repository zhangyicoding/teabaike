package estyle.teabaike.fragment.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import estyle.teabaike.R
import kotlinx.android.synthetic.main.dialog_share.*

class ShareDialogFragment : BottomSheetDialogFragment() {

    var shareCallback: ((state: String) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_share, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        qq_text_view.setOnClickListener { click(it) }
        qzone_text_view.setOnClickListener { click(it) }
        wechat_text_view.setOnClickListener { click(it) }
        moment_text_view.setOnClickListener { click(it) }
        cancel_text_view.setOnClickListener { click(it) }
    }

    private fun click(v: View) {
        if (v.id != R.id.cancel_text_view) {
            shareCallback?.invoke(STATE_SUCCESS)
        }
        dismiss()
    }

    companion object {
        const val STATE_SUCCESS = "成功"
        const val STATE_FAIL = "失败"
        const val STATE_CANCEL = "取消"

        fun newInstance(content: String?) = ShareDialogFragment().apply {
            arguments = Bundle().apply { putString("content", content) }
        }
    }
}