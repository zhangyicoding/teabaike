package estyle.teabaike.fragment.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import estyle.teabaike.R
import kotlinx.android.synthetic.main.dialog_share.*

class ShareDialogFragment : BottomSheetDialogFragment() {

    var channelCallback: ((channel: String) -> Unit)? = null

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
            channelCallback?.invoke((v as TextView).text.toString())
        }
        dismiss()
    }

    companion object {
        fun newInstance() = ShareDialogFragment()
    }
}