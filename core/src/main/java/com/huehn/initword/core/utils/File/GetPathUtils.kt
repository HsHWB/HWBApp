package com.huehn.initword.core.utils.File

import android.os.Environment
import android.text.TextUtils
import com.huehn.initword.core.app.App

class GetPathUtils {
    companion object {
        const val TAG = "GetPathUtils"
        /**
         * App的data/data/包名 的路径
         */
        fun getAppCacheAbsolutePath() : String {

            App.getApp()?.run {
                this.cacheDir.absolutePath
            }?.takeIf {
                !TextUtils.isEmpty(it)
            }?.let {
                return it
            }?:let{
                return ""
            }

        }

        fun getAppExternalAbsolutePath() : String {
            return Environment.getExternalStorageDirectory().absolutePath;
        }
    }
}

