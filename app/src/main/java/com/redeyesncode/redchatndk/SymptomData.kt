package com.redeyesncode.redchatndk

import com.google.gson.annotations.SerializedName

data class SymptomData(@SerializedName("symptom"              ) var symptom             : String?                        = null,
                       @SerializedName("symptom_description"  ) var symptomDescription  : String?                        = null,
                       @SerializedName("doctor_conversations" ) var doctorConversations : ArrayList<DoctorConversations> = arrayListOf()){

    data class DoctorConversations (

        @SerializedName("prompt"            ) var prompt           : String?           = null,
        @SerializedName("patient_responses" ) var patientResponses : ArrayList<String> = arrayListOf()

    )
}
