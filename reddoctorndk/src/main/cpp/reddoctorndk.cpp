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

extern "C" JNIEXPORT jstring JNICALL Java_com_redeyesncode_reddoctorndk_NativeLib_stringFromJNI(
        JNIEnv* env, jobject thiz, jobject assetManager, jstring symptomKeyword) {
    AAssetManager* aAssetManager = AAssetManager_fromJava(env, assetManager);

    const char* symptom = env->GetStringUTFChars(symptomKeyword, nullptr);
    std::string jsonFilename = std::string(symptom) + ".json";
    env->ReleaseStringUTFChars(symptomKeyword, symptom);

    // Open the JSON file.
    AAsset* asset = AAssetManager_open(aAssetManager, jsonFilename.c_str(), AASSET_MODE_BUFFER);

    if (asset == nullptr) {
        return env->NewStringUTF("Symptom data not found.");
    }

    // Read the JSON content.
    const void* data = AAsset_getBuffer(asset);
    std::string jsonData(static_cast<const char*>(data), AAsset_getLength(asset));

    // Close the asset.
    AAsset_close(asset);

    // Now, you have the JSON data in the `jsonData` string.
    // You can parse it using a JSON library like nlohmann's JSON library.

    json symptomData = json::parse(jsonData);

    // Access and use the symptom data as needed.
    std::string symptomName = symptomData["symptom"];
    std::string symptomDescription = symptomData["symptom_description"];

    // Extract and process conversations
    json conversations = symptomData["conversations"];
    for (const auto& conversation : conversations) {
        std::string role = conversation["role"];
        std::string prompt = conversation["prompt"];
        std::string response = conversation["response"];
        // Process the conversation data as needed.
    }

    // Return a response, such as symptom description or a specific conversation.
    return env->NewStringUTF(symptomDescription.c_str());
}



