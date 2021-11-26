package com.junsu.base

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.LayoutRes
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<T : ViewDataBinding>: Fragment() {
    lateinit var bind:T

    @LayoutRes
    abstract fun getLayoutResId(): Int

    abstract fun initFragment()
    protected var rootView: View? = null

    private lateinit var requestMultiplePermissions: ActivityResultLauncher<Array<out String>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false)
        bind.lifecycleOwner = viewLifecycleOwner
        rootView = bind.root
        initFragment()

        requestMultiplePermissions =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
                // We should dispatch by ourselves rather than call dispatchRequestPermissions(),
                // or we'll stick in infinite recursion because dispatchRequestPermissions()
                // can't distinguish permanently denied permissions and never dispatches them.
                val granted = result.filterValues { it }.keys
                if (granted.isNotEmpty()) {
                    onRequestPermissionsSuccess(granted, savedInstanceState)
                }
                val denied = result.keys - granted
                if (denied.isNotEmpty()) {
                    val shouldShowRationale = denied.asSequence().filter {
                        ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), it)
                    }.toSet()
                    val permanentlyDenied = denied - shouldShowRationale
                    onRequestPermissionsFailure(
                        shouldShowRationale, permanentlyDenied, savedInstanceState
                    )
                }
            }
        return bind.root
    }

    protected open fun onRequestPermissions(
        permissions: Array<String>, savedInstanceState: Bundle?
    ) {
        requestMultiplePermissions.launch(permissions)
    }

    protected open fun onRequestPermissionsSuccess(
        permissions: Set<String>, savedInstanceState: Bundle?
    ) {
    }

    protected open fun onRequestPermissionsFailure(
        shouldShowRationale: Set<String>, permanentlyDenied: Set<String>,
        savedInstanceState: Bundle?
    ) {
        if (shouldShowRationale.isNotEmpty()) {
            onRequestPermissions(shouldShowRationale.toTypedArray(), savedInstanceState)
        } else if (permanentlyDenied.isNotEmpty()) {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.fromParts("package", requireContext().packageName, null)
            }
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bind.unbind()
        hideKeyboard()
        requestMultiplePermissions.unregister()
    }

    override fun onPause() {
        super.onPause()
        hideKeyboard()
    }

    fun requestPermission(requiredPermissions: Array<String>) {
        requestMultiplePermissions.launch(requiredPermissions)
    }

    fun hideKeyboard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        bind.root?.also {
            imm.hideSoftInputFromWindow(it.windowToken, 0)
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