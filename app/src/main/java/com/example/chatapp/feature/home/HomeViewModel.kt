package com.example.chatapp.feature.home

import androidx.lifecycle.ViewModel
import com.example.chatapp.feature.model.Channel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val firebaseDatabase = Firebase.database

    val _channels = MutableStateFlow<List<Channel>>(emptyList())
    val channels = _channels.asStateFlow()

    //추가
    private val _state = MutableStateFlow<SignOutState>(SignOutState.Nothing)
    val state = _state.asStateFlow()

    init {
        getChannel()
    }

    private fun getChannel() {
        firebaseDatabase.getReference("channel").get()
            .addOnSuccessListener {
                val list = mutableListOf<Channel>()
                it.children.forEach { data ->
                    val channel = Channel(data.key!!, data.value.toString())
                    list.add(channel)
                }
                _channels.value = list
            }
    }

    fun addChannel(name: String) {
        val key = firebaseDatabase.getReference("channel").push().key
        firebaseDatabase.getReference("channel").child(key!!).setValue(name)
            .addOnSuccessListener {
                getChannel()
            }
    }

    //추가
    fun signOut() {
        //이건 비동기 함수가 아니라서, add, listener 추가 등이 없음.
        FirebaseAuth.getInstance().signOut()
        _state.value = SignOutState.LoggedOut
    }

}

//signOutState 만들기
sealed class SignOutState {
    object Nothing : SignOutState()
    object LoggedOut : SignOutState()
}