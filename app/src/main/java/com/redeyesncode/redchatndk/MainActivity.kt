package com.redeyesncode.redchatndk

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.redeyesncode.redchatndk.databinding.ActivityMainBinding
import com.redeyesncode.reddoctorndk.NativeLib

class MainActivity : AppCompatActivity(),ChatAdapter.onResponsePatient {
    lateinit var binding:ActivityMainBinding

    override fun onResponseChat(response: String) {
//        Toast.makeText(this@MainActivity,"You choose response ${response}",Toast.LENGTH_LONG).show()
        callJniNativeInterface()
    }

    companion object {
        init {
            System.loadLibrary("reddoctorndk")

        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        initClicks()

        setContentView(binding.root)

    }
    fun callJniNativeInterface(){
        val assetManager = assets

        // Call the JNI function to load JSON data from the NDK
        val nativeLib = NativeLib()
        val jsonString = nativeLib.stringFromJNI(assetManager,binding.edtKeyword.text.toString())
        val jsonObject = Gson().fromJson(jsonString,SymptomData.DoctorConversations::class.java) as SymptomData.DoctorConversations
        var listResponse = arrayListOf<String>()
        if(jsonObject.prompt.equals("Thank you for your time !")){
            binding.btnStartChat.visibility=View.VISIBLE
        }else{
            binding.btnStartChat.visibility = View.INVISIBLE
        }
        updateTextViewWithAnimation(binding.tvDoctorPrompt,jsonObject.prompt.toString())
        for (chat in jsonObject.patientResponses){
            listResponse.add(chat)
        }
        binding.recvUserResponses.adapter = ChatAdapter(this@MainActivity,listResponse,this)
        binding.recvUserResponses.layoutManager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.VERTICAL,false)


//        setRecyclerViewWithAnimation(binding.recvUserResponses,ChatAdapter(this@MainActivity,listResponse,this),LinearLayoutManager(this@MainActivity,LinearLayoutManager.VERTICAL,false))
    }
    private fun initClicks() {
        binding.btnStartChat.setOnClickListener {
            if(binding.edtKeyword.text.toString().isEmpty()){
                Toast.makeText(this@MainActivity,"Please enter something",Toast.LENGTH_LONG).show()

            }else{
               callJniNativeInterface()

            }
        }

    }
    private fun updateTextViewWithAnimation(textView: TextView, newText: String) {
        textView.text = "Loading.."
        // Create a fade-out animation
        val fadeOutAnimator = ObjectAnimator.ofFloat(textView, "alpha", 1f, 0f)
        fadeOutAnimator.duration = 1000
        fadeOutAnimator.interpolator = AccelerateDecelerateInterpolator()

        // Set a listener to update the text and trigger the fade-in animation
        fadeOutAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                // Update the text
                textView.text = newText

                // Create a fade-in animation
                val fadeInAnimator = ObjectAnimator.ofFloat(textView, "alpha", 0f, 1f)
                fadeInAnimator.duration = 1000
                fadeInAnimator.interpolator = AccelerateDecelerateInterpolator()
                fadeInAnimator.start()
            }
        })

        // Start the fade-out animation
        fadeOutAnimator.start()
    }
    private fun setRecyclerViewWithAnimation(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>, layoutManager: RecyclerView.LayoutManager) {
        // Create a fade-in animation for the RecyclerView
        val fadeInAnimator = ObjectAnimator.ofFloat(recyclerView, "alpha", 0f, 1f)
        fadeInAnimator.duration = 1000
        fadeInAnimator.interpolator = AccelerateDecelerateInterpolator()

        // Set a listener to set the adapter and layout manager after the fade-in animation
        fadeInAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                recyclerView.adapter = adapter
                recyclerView.layoutManager = layoutManager
            }
        })

        // Start the fade-in animation
        fadeInAnimator.start()
    }
}