package estyle.teabaike.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.uber.autodispose.ObservableSubscribeProxy
import estyle.teabaike.R
import estyle.teabaike.databinding.ActivityFeedbackBinding
import estyle.teabaike.entity.FeedbackEntity
import estyle.teabaike.rxjava.DisposableConverter
import estyle.teabaike.rxjava.SnackbarObserver
import estyle.teabaike.viewmodel.FeedbackViewModel
import kotlinx.android.synthetic.main.activity_feedback.*

class FeedbackActivity : BaseActivity(), View.OnFocusChangeListener {

    private lateinit var binding: ActivityFeedbackBinding
    private val viewModel by lazy { ViewModelProviders.of(this)[FeedbackViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_feedback)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        title_edit_text.onFocusChangeListener = this
        content_edit_text.onFocusChangeListener = this
        commit_btn.setOnClickListener {
            if (!checkTextEmpty(title_text_input_layout) &&
                !checkTextEmpty(content_text_input_layout)
            ) {
                feedback(title_edit_text.text.toString(), content_edit_text.text.toString())
            }
        }
    }

    // 反馈回调
    private fun feedback(title: String, content: String) {
        viewModel.feedback(title, content)
            .`as`<ObservableSubscribeProxy<FeedbackEntity>>(DisposableConverter.dispose(this))
            .subscribe(object : SnackbarObserver<FeedbackEntity>(binding.root) {
                override fun onNext(it: FeedbackEntity) {
                    super.onNext(it)
                    val msg: String
                    val color: Int
                    val errorCode = it.errorCode
                    if (errorCode == 0) {
                        msg = getString(R.string.suggestion_successful)
                        color = R.color.colorAccent
                        binding.text = ""
                    } else {
                        msg = it.errorMessage
                        color = R.color.error
                    }

                    showTip(msg, color)
                }
            })
    }

    // 检查文本是否为空
    private fun checkTextEmpty(textInputLayout: TextInputLayout): Boolean {
        if (TextUtils.isEmpty(textInputLayout.editText?.text.toString())) {
            textInputLayout.error = getString(
                if (textInputLayout.id == R.id.title_text_input_layout)
                    R.string.feedback_title_empty
                else
                    R.string.feedback_content_empty
            )
            textInputLayout.isErrorEnabled = true
        } else {
            textInputLayout.isErrorEnabled = false
        }
        return textInputLayout.isErrorEnabled
    }

    // 展示Snackbar
    private fun showTip(message: String, colorResId: Int) {
        val snackbar = Snackbar.make(toolbar, message, Snackbar.LENGTH_SHORT)
        snackbar.view.setBackgroundResource(colorResId)
        snackbar.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onFocusChange(v: View, hasFocus: Boolean) {
        if (!hasFocus) {
            when (v.id) {
                R.id.title_edit_text -> checkTextEmpty(title_text_input_layout)
                R.id.content_edit_text -> checkTextEmpty(content_text_input_layout)
            }
        }
    }

    companion object {

        fun startActivity(context: Context) {
            val intent = Intent(context, FeedbackActivity::class.java)
            context.startActivity(intent)
        }
    }
}
