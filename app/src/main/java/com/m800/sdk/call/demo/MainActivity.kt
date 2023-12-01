package com.m800.sdk.call.demo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.m800.sdk.call.CinnoxCallSdkError
import com.m800.sdk.call.CinnoxCallSdkException
import com.m800.sdk.core.CinnoxCore
import com.m800.sdk.call.demo.call.CallController
import com.m800.sdk.call.demo.databinding.ActivityMainBinding
import com.m800.sdk.call.demo.navigation.UIManager
import com.m800.sdk.call.demo.utils.CustomManager
import com.m800.sdk.call.demo.utils.PermissionHelper
import kotlinx.coroutines.launch

const val SERVICE_ID = "*****.cinnox.com" // input your service id
const val USER_ACCOUNT = "email" // input your user account
const val USER_PASSWORD = "password" // input your user password
const val CALLEE_EID = "eid" // input your callee eid
const val CALLEE_PHONE_NUMBER = "phone number" // input your callee phone number
const val CLI_NUMBER = "cli number" // input your cli number

class MainActivity: AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var cinnoxCore: CinnoxCore
    private val permissionHelper = PermissionHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cinnoxCore = CinnoxCore.getInstance(SERVICE_ID) ?: throw CinnoxCallSdkException(
            CinnoxCallSdkError.SDK_INIT_ERROR,
            "cinnox core is null"
        )
        UIManager.initialize()
        UIManager.getNavigator().setRoot(this, android.R.id.content)
        CustomManager.init(this)
        CallController.getInstance().initialize(cinnoxCore, permissionHelper)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initView()
    }

    override fun onResume() {
        super.onResume()
        (application as MainApplication).isActivityInForeground = true
    }

    override fun onPause() {
        (application as MainApplication).isActivityInForeground = false
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        CallController.uninitialize()
        cinnoxCore.uninitialize()
    }

    private fun initView() {
        updateLogin(cinnoxCore.getAuthenticationManager().isUserLogin())
        mBinding.btnLogin.setOnClickListener {
            if (cinnoxCore.getAuthenticationManager().isUserLogin()) {
                logout()
            } else {
                login()
            }
        }

        mBinding.btnOnnet.setOnClickListener {
            CallController.getInstance().makeOnnetCall(CALLEE_EID)
        }

        mBinding.btnOffnet.setOnClickListener {
            CallController.getInstance().makeOffnetCall(CALLEE_PHONE_NUMBER, CLI_NUMBER)
        }
    }

    private fun login() {
        Log.i(TAG, "login")
        CinnoxCore.getInstance(SERVICE_ID) ?: run {
            cinnoxCore = CinnoxCore.initialize(applicationContext, SERVICE_ID)
            CallController.getInstance().initialize(cinnoxCore, permissionHelper)
        }
        cinnoxCore.getAuthenticationManager().login(
            USER_ACCOUNT,
            USER_PASSWORD
        ) { data, throwable ->
            Log.i(TAG, "login data: $data, throwable: $throwable")
            lifecycleScope.launch {
                updateLogin(data?.success == true)
            }
        }
    }

    private fun logout() {
        Log.i(TAG, "logout")
        cinnoxCore.getAuthenticationManager().logout { data, throwable ->
            Log.i(TAG, "logout data: $data, throwable: $throwable")
            lifecycleScope.launch {
                CallController.uninitialize()
                cinnoxCore.uninitialize()
                updateLogin(data == false)
            }
        }
    }

    private fun updateLogin(isLoggedIn: Boolean) {
        Log.i(TAG, "updateLogin, isLoggedIn: $isLoggedIn")
        if (isLoggedIn) {
            mBinding.btnLogin.setText(R.string.logout)
        } else {
            mBinding.btnLogin.setText(R.string.login)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
