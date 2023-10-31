package com.redeyesncode.redchatndk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.redeyesncode.redchatndk.databinding.ActivityMainBinding
import com.redeyesncode.reddoctorndk.NativeLib

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    companion object {
        init {
            System.loadLibrary("reddoctorndk")

        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)

        val assetManager = assets

        // Call the JNI function to load JSON data from the NDK
        val nativeLib = NativeLib()
        Toast.makeText(this@MainActivity,nativeLib.stringFromJNI(assetManager,"HeadAche"),Toast.LENGTH_LONG).show()

        setContentView(binding.root)
    }
}