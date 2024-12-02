package com.example.chatapp.feature.model

data class Channel(
    val id: String = "",
    val name: String,
    //현재 시점
    val createAt: Long = System.currentTimeMillis()
)
