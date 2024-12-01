package com.example.chatapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ChatApp: Application() {
//    이러고 manifest에서 android:name=".ChatApp" 추가함
}