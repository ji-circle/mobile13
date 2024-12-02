package com.example.chatapp.feature.chat

import androidx.lifecycle.ViewModel
import com.example.chatapp.feature.model.Message
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor() : ViewModel() {

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages = _messages.asStateFlow()

    private val firebaseDatabase = Firebase.database


    //어떤 채널에 어떤 메시지를 보냈다
    fun sendMessage(
        channelId: String,
        messageText: String
    ) {
        val message = Message(
            //여기에 넣을것이다
            // 해당 데이터베이스 레퍼런스에 message 안에 추가할테니까 그 안에 추가할 키를 생성하고
            id = firebaseDatabase.reference.child("message").push().key
            //  key가 null이면 랜덤하게 생성해라
                ?: UUID.randomUUID().toString(),

            //senderId는 현재 나의 uid
            senderId = Firebase.auth.currentUser?.uid ?: "",
            message = messageText,
//            createdAt = ,
            senderName = Firebase.auth.currentUser?.displayName ?: ""
        )

        //여기 다르다.
        // 채널 아이디를 넣고 setValue로 위에서 만든 메시지를 전달
        firebaseDatabase.reference.child("message").child(channelId).push().setValue(message)
    }

    //언제 메시지가 올지 모르니 리스너
    fun listenForMessages(channelId: String) {
        //시간순서대로 입력받기. child 중의 "createdAt"
        firebaseDatabase.reference.child("message").child(channelId).orderByChild("createdAt")
            //value가 추가가 된다면
            //  ValueEventListener는 인터페이스. 그래서 1. onDataChange, 2. onCancelled 오버라이드해야 함
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    //데이터가 바뀔 때마다 메시지를 업데이트
                    // 데이터 바뀔때마다 스냅샷 찍어서 그걸 가져옴
                    val list = mutableListOf<Message>()
                    snapshot.children.forEach { data ->
                        //데이터를 Message 형태로 받아온다
                        val message = data.getValue(Message::class.java)
                        message?.let {
                            list.add(it)
                        }
                    }
                    _messages.value = list
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }

}