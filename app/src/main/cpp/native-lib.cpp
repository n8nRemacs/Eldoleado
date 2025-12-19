#include <jni.h>
#include <string>
#include <cstdlib>
#include <android/log.h>
#include "node.h"

#define LOG_TAG "NodeJS"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

extern "C" JNIEXPORT jint JNICALL
Java_com_eldoleado_app_nodejs_NodeJSBridge_startNodeWithArguments(
        JNIEnv *env,
        jobject /* this */,
        jobjectArray arguments) {

    LOGI("startNodeWithArguments called");

    // Get number of arguments
    jsize arg_count = env->GetArrayLength(arguments);
    LOGI("Argument count: %d", arg_count);

    // Convert to char* array
    int c_arguments_size = 0;
    for (int i = 0; i < arg_count; i++) {
        auto element = (jstring) env->GetObjectArrayElement(arguments, i);
        const char *temp_string = env->GetStringUTFChars(element, nullptr);
        c_arguments_size += strlen(temp_string) + 1;
        env->ReleaseStringUTFChars(element, temp_string);
    }

    // Allocate contiguous memory for arguments (required by libuv)
    char *args_buffer = (char *) calloc(c_arguments_size, sizeof(char));
    char **argv = (char **) calloc(arg_count, sizeof(char *));

    char *current_position = args_buffer;
    for (int i = 0; i < arg_count; i++) {
        auto element = (jstring) env->GetObjectArrayElement(arguments, i);
        const char *temp_string = env->GetStringUTFChars(element, nullptr);
        size_t len = strlen(temp_string);
        strcpy(current_position, temp_string);
        argv[i] = current_position;
        current_position += len + 1;
        env->ReleaseStringUTFChars(element, temp_string);
    }

    // Log arguments
    for (int i = 0; i < arg_count; i++) {
        LOGI("argv[%d] = %s", i, argv[i]);
    }

    // Start Node.js
    LOGI("Calling node::Start...");
    int result = node::Start(arg_count, argv);
    LOGI("node::Start returned: %d", result);

    free(args_buffer);
    free(argv);

    return result;
}
