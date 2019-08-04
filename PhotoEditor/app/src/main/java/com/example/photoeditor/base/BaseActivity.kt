@file:Suppress("DEPRECATION")

package com.example.photoeditor.base

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.pm.PackageManager
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {
    private var mProgressDialog: ProgressDialog? = null

    fun requestPermission(permission: String): Boolean {
        val isGranted = ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
        if (!isGranted) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(permission),
                READ_WRITE_STORAGE
            )
        }
        return isGranted
    }

    open fun isPermissionGranted(isGranted: Boolean, permission: String) {
    }

    fun makeFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            READ_WRITE_STORAGE -> isPermissionGranted(
                grantResults[0] == PackageManager.PERMISSION_GRANTED,
                permissions[0]
            )
        }
    }

    protected fun showLoading(message: String) {
        mProgressDialog = ProgressDialog(this)
        mProgressDialog!!.setMessage(message)
        mProgressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        mProgressDialog!!.setCancelable(false)
        mProgressDialog!!.show()
    }

    protected fun hideLoading() {
        if (mProgressDialog != null) {
            mProgressDialog!!.dismiss()
        }
    }

    protected fun showSnackbar(message: String) {
        val view = findViewById<View>(android.R.id.content)
        if (view != null) {
            Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val READ_WRITE_STORAGE = 52
    }
}
