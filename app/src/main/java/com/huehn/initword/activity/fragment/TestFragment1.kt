package com.huehn.initword.activity.fragment

import android.os.Bundle
import android.view.View
import com.huehn.initword.R
import com.huehn.initword.basecomponent.base.BaseFragment

class TestFragment1 : BaseFragment() {

    companion object {
        const val TAG = "TestFragment1"
        public fun newInstance(args : Bundle?): TestFragment1 {
            var fragment = TestFragment1()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getBundle(bundle: Bundle?) {
    }

    override fun getLayoutView(): Int {
        return R.layout.fragment_test1
    }

    override fun initView(view: View?) {
    }

}