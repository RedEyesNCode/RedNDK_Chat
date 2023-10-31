package com.redeyesncode.redchatndk

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.redeyesncode.redchatndk.databinding.ActivityMainBinding
import com.redeyesncode.reddoctorndk.NativeLib

class MainActivity : AppCompatActivity(),ChatAdapter.onResponsePatient {
    lateinit var binding:ActivityMainBinding

    override fun onResponseChat(response: String) {
        Toast.makeText(this@MainActivity,"You choose response ${response}",Toast.LENGTH_LONG).show()
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
        binding.tvDoctorPrompt.text = jsonObject.prompt.toString()
        for (chat in jsonObject.patientResponses){
            listResponse.add(chat)
        }
        binding.recvUserResponses.adapter = ChatAdapter(this@MainActivity,listResponse,this)
        binding.recvUserResponses.layoutManager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.VERTICAL,false)

    }
    private fun initClicks() {
        binding.btnStartChat.setOnClickListener {
            if(binding.edtKeyword.text.toString().isEmpty()){
                Toast.makeText(this@MainActivity,"Please enter something",Toast.LENGTH_LONG).show()

            }else{
                val assetManager = assets

                // Call the JNI function to load JSON data from the NDK
                val nativeLib = NativeLib()
                val jsonString = nativeLib.stringFromJNI(assetManager,binding.edtKeyword.text.toString())
                Log.i("RED_NDK",jsonString)
//                val myType = object : TypeToken<List<SymptomData.Conversations>>() {}.type
//                val jsonObject = Gson().fromJson<List<SymptomData.Conversations>>(jsonString, myType)
//                val jsonObject = Gson().toJson(jsonString, TypeToken<ArrayList<SymptomData.Conversations>>()) as ArrayList<SymptomData.Conversations>

                val jsonObject = Gson().fromJson(jsonString,SymptomData.DoctorConversations::class.java) as SymptomData.DoctorConversations
                var listResponse = arrayListOf<String>()
                binding.tvDoctorPrompt.text = jsonObject.prompt.toString()
                for (chat in jsonObject.patientResponses){
                    listResponse.add(chat)
                }
                binding.recvUserResponses.adapter = ChatAdapter(this@MainActivity,listResponse,this)
                binding.recvUserResponses.layoutManager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.VERTICAL,false)

            }


        }

    }
}