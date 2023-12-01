package com.m800.sdk.call.demo.navigation

import com.m800.sdk.call.demo.fragment.BaseFragment

interface PageCreator {

    /**
     * Returns the fragment for [Page.CALL]
     */
    fun getCallFragment(): BaseFragment
}