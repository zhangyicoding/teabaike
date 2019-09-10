package estyle.teabaike.test

import androidx.lifecycle.ViewModelProviders
import estyle.teabaike.viewmodel.MainViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class MainActivityTest : BaseTest() {

    private lateinit var viewModel: MainViewModel

    override fun init() {
        viewModel = ViewModelProviders.of(activity)
            .get(MainViewModel::class.java)
    }

    @Test
    fun checkVersion() {
        viewModel.checkVersion()
            .subscribe {
                assertEquals(true, it.force_update)
                assertEquals(2, it.version_code)
                assertNotNull("apk url is null", it.apk_link)
            }
    }
}
