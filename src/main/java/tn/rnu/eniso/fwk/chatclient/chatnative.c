#include <jni.h>
#include <stdio.h>
#include "tn_rnu_eniso_fwk_chatclient_ChatClientApplication.h"

JNIEXPORT void JNICALL Java_tn_rnu_eniso_fwk_chatclient_ChatClientApplication_nativePrintf
  (JNIEnv *env, jobject obj, jstring message) {
    const char *cMessage = (*env)->GetStringUTFChars(env, message, 0);
    printf("%s\n", cMessage);
    (*env)->ReleaseStringUTFChars(env, message, cMessage);
}
