package com.huehn.initword.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class ProxyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_proxy)
        Toast.makeText(this, "插件activity ProxyActivity", Toast.LENGTH_SHORT).show()
    }
}