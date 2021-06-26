package com.huehn.initword.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.text.TextUtils
import android.util.SparseArray
import androidx.fragment.app.Fragment
import com.huehn.initword.R
import com.huehn.initword.activity.fragment.TestFragment1
import com.huehn.initword.activity.fragment.TestFragment2
import com.huehn.initword.basecomponent.base.BaseActivity
import com.huehn.initword.basecomponent.base.BaseFragment
import kotlinx.android.synthetic.main.activity_test.*

class TestFragmentActivity : BaseActivity(){

    private var list : SparseArray<Fragment> = SparseArray()

    companion object {
        const val TAG = "TestFragmentActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_test)

        textview.setOnClickListener() {
            var tag = TestFragment2.TAG
            if (list.size() <= 0) {
                tag = TestFragment1.TAG
            } else if (list.size() >= 2) {
                return@setOnClickListener
            }
            addFragment(tag)
        }
    }

    private fun addFragment(tag : String){
        if (TextUtils.isEmpty(tag)) {
            return
        }

        var fragmentManager = this.supportFragmentManager
        fragmentManager?.run {

            if (this.findFragmentByTag(tag) != null) {
                return
            }

            var transaction = this.beginTransaction()
            transaction?.run {
                var fragment : BaseFragment?
                when(tag) {
                    TestFragment1.TAG -> fragment = TestFragment1.newInstance(null)
                    TestFragment2.TAG -> fragment = TestFragment2.newInstance(null)
                    else -> fragment = null
                }
                list.put(list.size(), fragment)
                fragment?.let {
                    this.add(R.id.container, fragment, tag).addToBackStack(tag).commitAllowingStateLoss()
                }
            }
        }
    }

    override fun finish() {
        super.finish()
        if (list != null && list.size() > 0) {
            list.removeAtRange(0, list.size())
        }
    }
}