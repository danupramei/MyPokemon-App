package com.pokemon.shared.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.pokemon.shared.Inflate
import com.pokemon.shared.singleton.InternetConnection

abstract class BaseFragment<VB : ViewBinding>(
    private val inflate: Inflate<VB>
) : Fragment() {

    private var mBinding: VB? = null
    val binding get() = mBinding!!

    protected var isContentLoaded = false

    private val observer by lazy {
        Observer<Boolean> {
            activity?.runOnUiThread {
                if (it == true) onInternetConnectionReady() else onInternetConnectionLost()
            }
        }
    }

    open fun onInternetConnectionReady() {}
    open fun onInternetConnectionLost() {}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mBinding = inflate.invoke(inflater, container, false)
        return mBinding?.root
    }

    override fun onResume() {
        super.onResume()
        isContentLoaded = false
        InternetConnection.isInternetConnected.observe(viewLifecycleOwner, observer)
    }

    override fun onPause() {
        super.onPause()
        InternetConnection.isInternetConnected.removeObserver(observer)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }
}