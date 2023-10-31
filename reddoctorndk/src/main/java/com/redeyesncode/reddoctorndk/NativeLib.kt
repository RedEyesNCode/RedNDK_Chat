package com.redeyesncode.reddoctorndk

import android.content.res.AssetManager

class NativeLib {

    /**
     * A native method that is implemented by the 'reddoctorndk' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(assetManager: AssetManager,symptomKey:String): String

    companion object {
        // Used to load the 'reddoctorndk' library on application startup.
        init {
            System.loadLibrary("reddoctorndk")
        }
    }
}