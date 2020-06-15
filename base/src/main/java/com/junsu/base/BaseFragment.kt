package com.junsu.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<T : ViewDataBinding>: Fragment() {
    lateinit var bind:T

    private var viewContainer: ViewGroup? = null

    @LayoutRes
    abstract fun getLayoutResId(): Int

    abstract fun initFragment()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(viewContainer != container) {
            viewContainer = container
            bind = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false)
            bind.lifecycleOwner = viewLifecycleOwner
            initFragment()
        }
        return bind.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bind.unbind()
    }

    fun hideKeyboard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        viewContainer?.run {
            imm.hideSoftInputFromWindow(this.windowToken, 0)
        }
    }

    fun showKeyboard(v: EditText) {
        if( v.isAttachedToWindow ) {
            v.isFocusableInTouchMode = true
            v.requestFocus()
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(v, 0)
        } else {
            v.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    v.let {
                        it.viewTreeObserver.removeOnGlobalLayoutListener(this)
                        v.isFocusableInTouchMode = true
                        v.requestFocus()
                        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.showSoftInput(v, 0)
                    }
                }
            })
        }
    }
}