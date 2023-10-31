package com.redeyesncode.redchatndk

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
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

        binding.responseChip.text = data

        binding.responseChip.setOnClickListener {
            onActivityResponse.onResponseChat(data)
        }



    }

    override fun getItemCount(): Int {
        return data.size
    }
    interface onResponsePatient{
        fun onResponseChat(response:String)

    }



    class MyViewholder(var binding:ItemPatientPromptsBinding):RecyclerView.ViewHolder(binding.root)
}