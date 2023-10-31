package com.redeyesncode.redchatndk

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.redeyesncode.redchatndk.databinding.ItemPatientPromptsBinding

class ChatAdapter(var context: Context,var data:ArrayList<String>,var onActivityResponse:onResponsePatient):RecyclerView.Adapter<ChatAdapter.MyViewholder>() {

    lateinit var binding: ItemPatientPromptsBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewholder {

        binding = ItemPatientPromptsBinding.inflate(LayoutInflater.from(context),parent,false)


        return MyViewholder(binding)
    }

    override fun onBindViewHolder(holder: MyViewholder, position: Int) {
        val data = data[position]

        updateTextViewWithAnimation(holder.binding.responseChip,data)

        binding.responseChip.setOnClickListener {
            onActivityResponse.onResponseChat(data)
        }



    }
    private fun updateTextViewWithAnimation(textView: TextView, newText: String) {
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
    override fun getItemCount(): Int {
        return data.size
    }
    interface onResponsePatient{
        fun onResponseChat(response:String)

    }



    class MyViewholder(var binding:ItemPatientPromptsBinding):RecyclerView.ViewHolder(binding.root)
}