package com.m800.sdk.call.demo.navigation

import com.m800.sdk.call.demo.fragment.BaseFragment
import com.m800.sdk.call.demo.call.CallFragment

open class DefaultPageCreator : PageCreator {
    
    override fun getCallFragment(): BaseFragment {
        return CallFragment()
    }
}