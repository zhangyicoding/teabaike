package estyle.base.fragment

import androidx.fragment.app.Fragment
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModelProvider


open class BaseFragment : Fragment(), HasDefaultViewModelProviderFactory {

    override fun getDefaultViewModelProviderFactory() =
        ViewModelProvider.AndroidViewModelFactory.getInstance(activity!!.application)
}