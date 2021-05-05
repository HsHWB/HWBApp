package com.huehn.initword.activity.fragment

import android.os.Bundle
import android.view.View
import com.huehn.initword.R
import com.huehn.initword.basecomponent.base.BaseFragment

class TestFragment2 : BaseFragment() {

    companion object {
        const val TAG = "TestFragment2"
        public fun newInstance(args : Bundle?): TestFragment2 {
            var fragment = TestFragment2()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getBundle(bundle: Bundle?) {
    }

    override fun getLayoutView(): Int {
        return R.layout.fragment_test2
    }

    override fun initView(view: View?) {
    }

}