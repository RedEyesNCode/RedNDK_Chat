#include <jni.h>
#include <string>
#include <sstream>
#include <android/asset_manager.h>
#include <android/asset_manager_jni.h>
#include "json.hpp" // Include the JSON library header
using nlohmann::json; // Use the JSON namespace



#include <jni.h>
#include <string>
#include <android/asset_manager.h>
#include <android/asset_manager_jni.h>
#include "json.hpp" // Include the nlohmann's JSON library header
using nlohmann::json; // Use the JSON namespace
#include <jni.h>
#include <string>
#include <android/asset_manager.h>
#include <android/asset_manager_jni.h>
#include "json.hpp" // Include the nlohmann's JSON library header
using nlohmann::json; // Use the JSON namespace

#include <jni.h>
#include <string>
#include <android/asset_manager.h>
#include <android/asset_manager_jni.h>
#include "json.hpp" // Include the nlohmann's JSON library header
using nlohmann::json; // Use the JSON namespace

// Define a global index variable to keep track of the current prompt.
int currentPromptIndex = 0;

extern "C" JNIEXPORT jstring JNICALL Java_com_redeyesncode_reddoctorndk_NativeLib_stringFromJNI(
        JNIEnv* env, jobject thiz, jobject assetManager, jstring symptomKeyword) {
    AAssetManager* aAssetManager = AAssetManager_fromJava(env, assetManager);

    const char* symptom = env->GetStringUTFChars(symptomKeyword, nullptr);
    std::string jsonFilename = std::string(symptom) + ".json";
    env->ReleaseStringUTFChars(symptomKeyword, symptom);

    // Open the JSON file.
    AAsset* asset = AAssetManager_open(aAssetManager, jsonFilename.c_str(), AASSET_MODE_BUFFER);

    if (asset == nullptr) {
        return nullptr; // Return null to indicate that the symptom data is not found.
    }

    // Read the JSON content.
    const void* data = AAsset_getBuffer(asset);
    std::string jsonData(static_cast<const char*>(data), AAsset_getLength(asset));

    // Close the asset.
    AAsset_close(asset);

    // Now, you have the JSON data in the `jsonData` string.
    // You can parse it using a JSON library like nlohmann's JSON library.

    json symptomData = json::parse(jsonData);

    // Check if the provided role is "doctor."
    if (std::string("doctor") == "doctor") {
        // Retrieve the specific conversation entry based on the current index.
        json doctorConversation = symptomData["doctor_conversations"][currentPromptIndex];

        // Check if the conversation entry has prompts and responses.
        if (doctorConversation.find("prompt") != doctorConversation.end() &&
            doctorConversation.find("patient_responses") != doctorConversation.end()) {
            // Get the prompt and patient responses.
            std::string prompt = doctorConversation["prompt"];
            json patientResponses = doctorConversation["patient_responses"];

            // Increment the current index for the next prompt.
            currentPromptIndex = (currentPromptIndex + 1) % symptomData["doctor_conversations"].size();

            // Return the prompt and responses as a JSON object.
            json response;
            response["prompt"] = prompt;
            response["patient_responses"] = patientResponses;
            return env->NewStringUTF(response.dump().c_str());
        }
    }

    // If the role is not "doctor" or data is not available, return null.
    return nullptr;
}





